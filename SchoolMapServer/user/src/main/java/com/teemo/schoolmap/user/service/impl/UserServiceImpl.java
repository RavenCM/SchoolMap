package com.teemo.schoolmap.user.service.impl;

import com.teemo.schoolmap.user.dto.User;
import com.teemo.schoolmap.user.exception.UserException;
import com.teemo.schoolmap.user.mapper.UserMapper;
import com.teemo.schoolmap.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 15:12
 * @version 1.0
 * @name schoolmap-server
 * @description 用户服务实现类
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param user user
     * @return 登录结果
     * @throws UserException 登录失败
     */
    @Override
    public User userLogin(User user) throws UserException{
        user =  userMapper.userLogin(user);
        if (user != null && user.getUserId() != null){
            return user;
        }
        throw new UserException("登录失败，请确认邮箱和密码是否正确！");
    }
}
