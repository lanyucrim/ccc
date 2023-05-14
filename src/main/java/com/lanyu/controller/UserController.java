package com.lanyu.controller;

import com.lanyu.Utils.SMSUtils;
import com.lanyu.Utils.ValidateCodeUtils;
import com.lanyu.common.R;
import com.lanyu.entity.User;
import com.lanyu.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ValidateCodeUtils validateCodeUtils;

    @Autowired
    private UserService userService;



    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session)
    {
       String phone= user.getPhone();
       if(phone!=null)
       {
            String code=ValidateCodeUtils.generateValidateCode(6).toString();
//            SMSUtils.sendMessage("瑞吉外卖","SMS_460685841",phone,code);
            log.info(code);
            session.setAttribute(phone,code);//后面改成redis
           return R.success("发送成功");
       }
       return R.error("发送失败");

    }
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session)
    {
        log.info(map.toString());
        R<User> userR= userService.saveUser(map.get("phone").toString(),map.get("code").toString(),session.getAttribute(map.get("phone").toString()));
        if(userR.getCode()!=0)
        {
            session.setAttribute("user",userR.getData().getId());
            return userR;
        }
        return userR;

    }

}
