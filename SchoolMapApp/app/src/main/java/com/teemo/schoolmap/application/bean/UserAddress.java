package com.teemo.schoolmap.application.bean;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 14:38
 * @version 1.0
 * @name schoolmap-server
 * @description 用户位置信息表
 */
public class UserAddress extends BaseDTO{

    /**
     * 用户地址信息表ID
     */
    private Integer userAddressId;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "userAddressId=" + userAddressId +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
