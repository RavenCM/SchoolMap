package com.teemo.schoolmap.application.bean;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 16:52
 * @version 1.0
 * @name schoolmap-server
 * @description 用户信息 DTO
 */
public class User extends BaseDTO{

    /**
     * 单例模式
     */
    private static User instance;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户类型表ID
     */
    private Integer userTypeId;

    /**
     * 用户密码
     */
    private String password;

    private UserBasisInformation userBasisInformation;

    private UserExtraInformation userExtraInformation;

    private User() {
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
            instance.userBasisInformation = new UserBasisInformation();
            instance.userExtraInformation = new UserExtraInformation();
            instance.userExtraInformation.setUserAddress(new UserAddress());
        }
        return instance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserBasisInformation getUserBasisInformation() {
        return userBasisInformation;
    }

    public void setUserBasisInformation(UserBasisInformation userBasisInformation) {
        this.userBasisInformation = userBasisInformation;
    }

    public UserExtraInformation getUserExtraInformation() {
        return userExtraInformation;
    }

    public void setUserExtraInformation(UserExtraInformation userExtraInformation) {
        this.userExtraInformation = userExtraInformation;
    }

}
