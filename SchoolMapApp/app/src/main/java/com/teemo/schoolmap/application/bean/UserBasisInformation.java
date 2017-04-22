package com.teemo.schoolmap.application.bean;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 14:17
 * @version 1.0
 * @name schoolmap-server
 * @description 用户基础信息
 */
public class UserBasisInformation extends BaseDTO{

    /**
     * 用户基础信息表ID
     */
    private Integer userBasisInformationId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户注册邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户性别
     */
    private String sex;

    public Integer getUserBasisInformationId() {
        return userBasisInformationId;
    }

    public void setUserBasisInformationId(Integer userBasisInformationId) {
        this.userBasisInformationId = userBasisInformationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserBasisInformation{" +
                "userBasisInformationId=" + userBasisInformationId +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
