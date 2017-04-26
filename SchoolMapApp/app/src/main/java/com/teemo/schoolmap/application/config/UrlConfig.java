package com.teemo.schoolmap.application.config;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/20 13:59
 * @description
 */
public class UrlConfig {

    private static final String SPILT = "/";

    /**
     * 协议
     */
    private static final String PROTOCOL = "http://";

    /**
     * 服务器 IP
     */
    private static final String IP = "10.211.98.170";   // 10.211.98.170

    /**
     * 用户服务端口
     */
    private static final String USER_PORT = "8081";

    /**
     * 单用户 URL
     */
    public static final String USER = PROTOCOL + IP + ":" + USER_PORT + SPILT + "api/user";

    /**
     * 批量用户 URL
     */
    public static final String USERS = PROTOCOL + IP + ":" + USER_PORT + SPILT + "api/users";
}
