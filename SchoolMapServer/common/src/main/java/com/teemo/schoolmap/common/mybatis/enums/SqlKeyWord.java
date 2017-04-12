package com.teemo.schoolmap.common.mybatis.enums;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 19:42
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public enum SqlKeyWord {
    /**
     * 公共的列
     */
    OBJECT_VERSION_NUMBER("OBJECT_VERSION_NUMBER"),
    CREATED_BY("CREATED_BY"),
    CREATION_DATE("CREATION_DATE"),
    LAST_UPDATED_BY("LAST_UPDATED_BY"),
    LAST_UPDATED_DATE("LAST_UPDATED_DATE"),
    IS_ENABLE("IS_ENABLE"),
    /**
     * 关键词
     */
    TABLE("table"),
    FROM("from"),
    WHERE("where"),
    COLUMNS("columns"),
    VALUES("values"),
    UPDATE("update");

    String content;

    SqlKeyWord(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
