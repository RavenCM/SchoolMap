package com.teemo.schoolmap.user.controller;

import com.teemo.schoolmap.common.component.ResponseData;
import com.teemo.schoolmap.user.dto.User;
import com.teemo.schoolmap.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 16:14
 * @version 1.0
 * @name schoolmap-server
 * @description 用户服务控制器
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseData getUser(User user) {
        logger.info("getUser={}", user);
        if (user.getUserId() == null){
            user = userService.userLogin(user);
        }
        return new ResponseData(Collections.singletonList(user));
    }
}
