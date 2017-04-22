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
    private static final String IP = "192.168.1.102";   // 10.211.98.170

    /**
     * 用户服务端口
     */
    private static final String USER_PORT = "8081";

    /**
     * 用户登录 URL
     */
    public static final String USER_LOGIN = PROTOCOL + IP + ":" + USER_PORT + SPILT + "api/user";
}
