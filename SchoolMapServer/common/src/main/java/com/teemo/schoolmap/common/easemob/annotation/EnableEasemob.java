package com.teemo.schoolmap.common.easemob.annotation;

import org.springframework.boot.web.servlet.ServletComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/14 16:15
 * @version 1.0
 * @name schoolmap-server
 * @description 启用 Easemob
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ServletComponentScan("com.teemo.schoolmap.common.easemob")
public @interface EnableEasemob {
}
