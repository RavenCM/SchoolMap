package com.teemo.schoolmap.user.controller;

import com.teemo.schoolmap.common.component.ResponseData;
import com.teemo.schoolmap.user.dto.User;
import com.teemo.schoolmap.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户登录
     *
     * @param user user
     * @return 登录结果
     */
    @GetMapping
    public ResponseData selectUser(User user) {
        logger.info("selectUser={}", user);
        // 登录
        if (user.getUserId() == null) {
            user = userService.userLogin(user);
        }
        return new ResponseData(Collections.singletonList(user));
    }

    /**
     * 新增用户
     *
     * @param user 用户
     * @return 新增后的用户（包含用户ID）
     */
    @PostMapping
    public ResponseData insertUser(@RequestBody User user) {
        logger.info("insertUser={}", user);
        // 注册
        if (user.getUserId() == null) {
            user = userService.userSignUp(user);
        }
        return new ResponseData(Collections.singletonList(user));
    }

    /**
     * 更新用户
     *
     * @return 更新后用户
     */
    @PutMapping
    public ResponseData updateUser(@RequestBody User user) {
        logger.info("updateUser={}", user);
        // 更新
        return new ResponseData(Collections.singletonList(userService.userUpdate(user)));
    }
}
