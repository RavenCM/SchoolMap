package com.teemo.schoolmap.user.controller;

import com.teemo.schoolmap.common.component.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 16:14
 * @version 1.0
 * @name schoolmap-server
 * @description 用户服务控制器
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping
    public ResponseData getUser() {
        return new ResponseData();
    }
}
