package com.teemo.schoolmap.user.dto;

import com.teemo.schoolmap.common.mybatis.annotation.Condition;
import com.teemo.schoolmap.common.mybatis.component.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 14:38
 * @version 1.0
 * @name schoolmap-server
 * @description 用户位置信息表
 */
@Table(name = "sm_user_address")
public class UserAddress extends BaseDTO{

    /**
     * 用户地址信息表ID
     */
    @Id
    @Column
    @Condition
    private Integer userAddressId;

    /**
     * 国家
     */
    @Column
    @NotEmpty
    private String country;

    /**
     * 省
     */
    @Column
    private String province;

    /**
     * 市
     */
    @Column
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
}
