package com.teemo.schoolmap.user.mapper;

import com.teemo.schoolmap.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 15:13
 * @version 1.0
 * @name schoolmap-server
 * @description 用户表映射
 */
@Mapper
public interface UserMapper {

    /**
     * 用户登录
     * @param user user
     * @return 登录结果
     */
    User userLogin(User user);
}
