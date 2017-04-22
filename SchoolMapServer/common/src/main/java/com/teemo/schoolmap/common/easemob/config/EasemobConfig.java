package com.teemo.schoolmap.common.easemob.config;

import com.teemo.schoolmap.common.easemob.client.UserRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/14 15:29
 * @version 1.0
 * @name schoolmap-server
 * @description 环信的配置信息
 */
@Configuration
public class EasemobConfig {

    public static final String domain = "http://a1.easemob.com/";

    /**
     * 接入环信账号的企业名称
     */
    public static final String orgName = "1126170320178197";

    /**
     * 接入环信账号的应用名称
     */
    public static final String appName = "schoolmap";

    /**
     * 接入环信账号的 Client Id
     */
    public static final String clientId = "YXA6vAdR8B2yEeeG6Evx3xkpYw";

    /**
     * 接入环信账号的 Client Secret
     */
    public static final String clientSecret = "YXA6uq0YDM5-LbwPGuc5-_si1FU8Iyc";

    /**
     * 获取 Token 的授权类型
     */
    public static final String grantType = "client_credentials";

    /**
     * Token 有效期误差
     */
    public static final double errorTime = 1000 * 60 * 5;

    @Bean
    public UserRestClient userRestClient(){
        return new UserRestClient();
    }
}
