package com.teemo.schoolmap.common.easemob.client;

import com.teemo.schoolmap.common.easemob.common.EasemobAPI;
import com.teemo.schoolmap.common.easemob.common.ResponseHandler;
import com.teemo.schoolmap.common.easemob.config.EasemobConfig;
import io.swagger.client.ApiException;
import io.swagger.client.api.UsersApi;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.springframework.stereotype.Component;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/14 15:36
 * @version 1.0
 * @name schoolmap-server
 * @description Easemob 用户体系 Rest Client
 */
@Component
public class UserRestClient {

    private UsersApi api = new UsersApi();
    private ResponseHandler responseHandler = new ResponseHandler();

    /**
     * 创建一个新的 IM 用户
     * @param payload payload
     * @return payload
     * @throws ApiException ApiException
     */
    private Object createNewIMUserSingle(final RegisterUsers payload) throws ApiException {
        return responseHandler.handle(() ->
                () -> api.orgNameAppNameUsersPost(EasemobConfig.orgName, EasemobConfig.appName, payload, TokenRestClient.getToken())
        );
    }

    public Object userSignUp(String username, String password) throws ApiException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        RegisterUsers registerUsers = new RegisterUsers();
        registerUsers.add(user);
        return createNewIMUserSingle(registerUsers);
    }
}
