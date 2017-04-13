package com.teemo.schoolmap.user.enums;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 14:28
 * @version 1.0
 * @name schoolmap-server
 * @description 性别枚举类
 */
public enum Sex {
    MALE("MALE"), FEMALE("FEMALE"), SECRET("SECRET");

    private String content;

    Sex(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
