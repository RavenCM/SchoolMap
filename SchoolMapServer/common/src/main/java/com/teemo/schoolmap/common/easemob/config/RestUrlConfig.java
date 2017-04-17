package com.teemo.schoolmap.common.easemob.config;

import com.teemo.schoolmap.common.easemob.entity.UrlConfigEntity;
import org.springframework.http.HttpMethod;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/14 15:39
 * @version 1.0
 * @name schoolmap-server
 * @description Rest Client 的 URL
 */
public class RestUrlConfig {

    /**
     * 分隔符
     */
    private final static String urlSplit = "split";

    /**
     * 公共 URL 前缀
     */
    private final static String baseUrl = EasemobConfig.domain + EasemobConfig.orgName + urlSplit + EasemobConfig.appName + urlSplit;

    /* *****************************************************************************************************************
     * token
     * *****************************************************************************************************************/

    /**
     * 获取 token
     */
    public final static UrlConfigEntity TOKEN_GET = new UrlConfigEntity(baseUrl + "token", HttpMethod.POST);

    /**
     * 注册 URL
     */
    public final static String USER_SIGN_UP = baseUrl + "users";
}
