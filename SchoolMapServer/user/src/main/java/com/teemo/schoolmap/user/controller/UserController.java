package com.teemo.schoolmap.user.controller;

import com.teemo.schoolmap.user.dto.User;
import com.teemo.schoolmap.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 16:14
 * @version 1.0
 * @name schoolmap-server
 * @description 用户服务控制器
 */
@Api(value = "用户", description = "用户")
@RestController
@RequestMapping("api")
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
    @ApiOperation(value = "通过邮箱和密码查询用户", notes = "获取单个用户")
    @GetMapping("user")
    public ResponseEntity<User> selectUser(User user) {
        // 登录
        if (user.getUserId() == null) {
            user = userService.userLogin(user);
        }
        logger.info("selectUser={}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 新增用户
     *
     * @param user 用户
     * @return 新增后的用户（包含用户ID）
     */
    @PostMapping("user")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        // 注册
        if (user.getUserId() == null) {
            user = userService.userSignUp(user);
        }
        logger.info("insertUser={}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 更新用户
     *
     * @return 更新后用户
     */
    @PutMapping("user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        logger.info("updateUser={}", user);
        // 更新
        return new ResponseEntity<>(userService.userUpdate(user), HttpStatus.OK);
    }

    /**
     * 获取多个用户
     *
     * @param userId userId
     * @return 多个用户
     */
    @GetMapping("users")
    public ResponseEntity<List> selectUsers(@RequestParam(required = false) List<Integer> userId,
                                            @RequestParam(required = false) String userInfo) {
        if (userId != null && userId.size() > 0) {
            logger.info("selectUsers={}", userId);
            return new ResponseEntity<>(userService.userSelect(userId), HttpStatus.OK);
        } else {
            logger.info("selectUsers={}", userInfo);
            return new ResponseEntity<>(userService.userSelectByUserInfo(userInfo), HttpStatus.OK);
        }
    }
}
