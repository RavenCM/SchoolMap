package com.teemo.schoolmap.common.easemob.client;


import com.google.gson.Gson;
import com.teemo.schoolmap.common.easemob.config.EasemobConfig;
import io.swagger.client.ApiException;
import io.swagger.client.api.AuthenticationApi;
import io.swagger.client.model.Token;

import java.util.Map;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/14 16:12
 * @version 1.0
 * @name schoolmap-server
 * @description Easemob token Rest Client
 */
public class TokenRestClient {

    private static Token tokenBody;

    private static AuthenticationApi authenticationApi;

    /**
     * 过期时间
     */
    private static double expireDate;

    private static String token;

    static {
        expireDate = -1d;
        authenticationApi = new AuthenticationApi();
        tokenBody = new Token().clientId(EasemobConfig.clientId).clientSecret(EasemobConfig.clientSecret).grantType(EasemobConfig.grantType);
    }

    private static void accessToken() throws ApiException{
        String tokenContent = authenticationApi.orgNameAppNameTokenPost(EasemobConfig.orgName, EasemobConfig.appName, tokenBody);
        Map tokenMap = new Gson().fromJson(tokenContent, Map.class);
        token =  "Bearer " + tokenMap.get("access_token");
        expireDate = System.currentTimeMillis() + + (Double) tokenMap.get("expires_in") - EasemobConfig.errorTime;
    }

    public static String getToken() throws ApiException {
        if (token == null || expireDate < System.currentTimeMillis()){
            accessToken();
        }
        return token;
    }
}
