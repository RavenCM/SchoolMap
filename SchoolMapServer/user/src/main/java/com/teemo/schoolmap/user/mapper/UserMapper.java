package com.teemo.schoolmap.user.mapper;

import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.user.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 15:13
 * @version 1.0
 * @name schoolmap-server
 * @description 用户表映射
 */
@Mapper
public interface UserMapper extends CommonMapper<User> {

    /**
     * 用户登录
     * @param user user
     * @return 登录结果
     */
    User userSelect(User user);

    /**
     * 获取多个用户
     *
     * @param userInfo userInfo
     * @return 多个用户
     */
    List<User> selectByUserInfo(String userInfo);
}
