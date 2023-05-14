package com.lanyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {

    public R<User> saveUser(String phone,String code,Object codeInSession);
}
