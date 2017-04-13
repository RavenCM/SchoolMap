package com.teemo.schoolmap.user.service;

import com.teemo.schoolmap.user.dto.User;
import com.teemo.schoolmap.user.exception.UserException;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 15:04
 * @version 1.0
 * @name schoolmap-server
 * @description 用户服务
 */
public interface IUserService {

    /**
     * 用户登录
     *
     * @param user user
     * @return 登录结果
     * @throws UserException 登录失败
     */
    User userLogin(User user) throws UserException;
}
