package com.teemo.schoolmap.common.mybatis.annotation;

import com.teemo.schoolmap.common.mybatis.config.CommonMapperAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/14 14:24
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(CommonMapperAutoConfiguration.class)
public @interface EnableCommonMapper {
}
