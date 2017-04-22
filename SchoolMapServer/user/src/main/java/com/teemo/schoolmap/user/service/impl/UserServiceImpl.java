package com.teemo.schoolmap.user.service.impl;

import com.teemo.schoolmap.common.easemob.client.UserRestClient;
import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.common.util.CommonUtil;
import com.teemo.schoolmap.user.dto.User;
import com.teemo.schoolmap.user.dto.UserBasisInformation;
import com.teemo.schoolmap.user.dto.UserExtraInformation;
import com.teemo.schoolmap.user.exception.UserException;
import com.teemo.schoolmap.user.mapper.UserMapper;
import com.teemo.schoolmap.user.service.IUserService;
import io.swagger.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 15:12
 * @version 1.0
 * @name schoolmap-server
 * @description 用户服务实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommonMapper<User> userCommonMapper;

    @Autowired
    private CommonMapper<UserBasisInformation> userBasisInformationCommonMapper;

    @Autowired
    private CommonMapper<UserExtraInformation> userExtraInformationCommonMapper;

    @Autowired
    private UserRestClient userRestClient;

    /**
     * 用户登录
     *
     * @param user user
     * @return 登录结果
     * @throws UserException 登录失败
     */
    @Override
    public User userLogin(User user) throws UserException {
        Assert.notNull(user.getUserBasisInformation().getEmail(), CommonUtil.buildAssertNotNullMessage(user.getUserBasisInformation().getEmail(), "userLogin"));
        Assert.notNull(user.getPassword(), CommonUtil.buildAssertNotNullMessage(user.getPassword(), "userLogin"));
        user = userMapper.userLogin(user);
        if (user.getUserId() != null) {
            // 用户名和密码正确，在客户端完成环信中登录
            return user;
        }
        throw new UserException("登录失败，请确认邮箱和密码是否正确！" + user.toString());
    }

    /**
     * 用户注册
     *
     * @param user user
     * @return 新增后的用户（包含用户ID）
     * @throws UserException 注册失败
     */
    @Override
    @Transactional
    public User userSignUp(User user) throws UserException {
        int result = userMapper.insert(user);
        if (result == 1) {
            UserBasisInformation userBasisInformation = user.getUserBasisInformation();
            result = userBasisInformationCommonMapper.insert(userBasisInformation);
        }
        if (result != 1) {
            throw new UserException("用户注册(本地)失败！" + user.toString());
        }
        // 用户注册成功，在环信中同样注册用户
        try {
            userRestClient.userSignUp(user.getUserBasisInformation().getUsername(), user.getPassword());
        } catch (ApiException e) {
            e.printStackTrace();
            throw new UserException("用户注册(环信)失败！" + user.toString());
        }
        return user;
    }

    /**
     * 用户更新
     *
     * @param user user
     * @return 更新后的用户
     */
    @Override
    @Transactional
    public User userUpdate(User user) {
        int result = userCommonMapper.updateSelective(user);
        if (result == 1) {
            result = userBasisInformationCommonMapper.updateSelective(user.getUserBasisInformation());
            if (result == 1) {
                result = userExtraInformationCommonMapper.updateSelective(user.getUserExtraInformation());
                if (result == 1) {
                    // 如果更新成功在环信端也更新密码
                    try {
                        userRestClient.updateUserPassword(user.getUserBasisInformation().getUsername(), user.getPassword());
                    } catch (ApiException e) {
                        e.printStackTrace();
                        throw new UserException("用户密码更新(环信)失败！" + user.toString());
                    }
                }
            }
        }
        if (result != 1) {
            throw new UserException("用户信息更新失败！" + user.toString());
        }
        return user;
    }
}
