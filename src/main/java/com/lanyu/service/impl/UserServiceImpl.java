package com.lanyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.common.R;
import com.lanyu.entity.User;
import com.lanyu.mapper.UserMapper;
import com.lanyu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public R<User> saveUser(String phone, String code, Object codeInSession) {
        if(codeInSession!=null&&codeInSession.equals(code))
        {
            LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone,phone);
            User user=userMapper.selectOne(lqw);
            if(user==null)
            {
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userMapper.insert(user);
            }

            return R.success(user);
        }

        return R.error("失败");
    }
}
