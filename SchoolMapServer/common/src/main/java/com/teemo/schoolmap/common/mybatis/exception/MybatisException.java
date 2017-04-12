package com.teemo.schoolmap.common.mybatis.exception;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 20:02
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class MybatisException extends RuntimeException {

    public MybatisException(String message) {
        super(message);
    }
}
