package com.teemo.schoolmap.application.bean;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 14:30
 * @version 1.0
 * @name schoolmap-server
 * @description 用户额外信息
 */
public class UserExtraInformation extends BaseDTO{

    /**
     * 用户附加信息表ID
     */
    private Integer userExtraInformationId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户手机号
     */
    private String telephone;

    /**
     * 用户地址表ID
     */
    private Integer userAddressId;

    /**
     * 用户头像路径
     */
    private String avatarPath;

    /**
     * 个性签名
     */
    private String signature;

    private UserAddress userAddress;

    public Integer getUserExtraInformationId() {
        return userExtraInformationId;
    }

    public void setUserExtraInformationId(Integer userExtraInformationId) {
        this.userExtraInformationId = userExtraInformationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "UserExtraInformation{" +
                "userExtraInformationId=" + userExtraInformationId +
                ", userId=" + userId +
                ", telephone='" + telephone + '\'' +
                ", userAddressId=" + userAddressId +
                ", avatarPath='" + avatarPath + '\'' +
                ", signature='" + signature + '\'' +
                ", userAddress=" + userAddress.toString() +
                '}';
    }
}
