package com.teemo.schoolmap.common.annotation;
/**
 * Created by neng.liu@hand-china.com on 2017/3/24.
 */

import org.springframework.boot.web.servlet.ServletComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author neng.liu@hand-china.com	2017/3/2410:53
 * @description 启用请求上下文过滤器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ServletComponentScan("com.teemo.schoolmap.common.filter")
public @interface EnableRequestFilter {
}
