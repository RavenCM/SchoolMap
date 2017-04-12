package com.teemo.schoolmap.common.mybatis.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 17:06
 * @version 1.0
 * @name schoolmap-server
 * @description 查询条件
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    /**
     * 等于
     */
    String EQ = "=";

    /**
     * 通配符
     */
    String LIKE = "like";

    /**
     * 不等于
     */
    String NQ = "<>";

    /**
     * 大于
     */
    String GT = ">";

    /**
     * 小于
     */
    String LT = "<";

    /**
     * 大于等于
     */
    String GE = ">=";

    /**
     * 小于等于
     */
    String LE = "<=";

    @AliasFor("value")
    String operator() default EQ;

    @AliasFor("operator")
    String value() default EQ;
}
