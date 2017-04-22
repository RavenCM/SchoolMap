package com.teemo.schoolmap.user.dto;

import com.teemo.schoolmap.common.mybatis.annotation.Condition;
import com.teemo.schoolmap.common.mybatis.component.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 16:52
 * @version 1.0
 * @name schoolmap-server
 * @description 用户信息 DTO
 */
@Table(name = "sm_user")
public class User extends BaseDTO{
    /**
     * 用户ID
     */
    @Id
    @Column
    @Condition
    private Integer userId;

    /**
     * 用户类型表ID
     */
    @Column
    private Integer userTypeId;

    /**
     * 用户密码
     */
    @Column
    @Condition
    @NotEmpty
    private String password;

    @Valid
    private UserBasisInformation userBasisInformation;

    @Valid
    private UserExtraInformation userExtraInformation;

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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userTypeId=" + userTypeId +
                ", password='" + password + '\'' +
                ", userBasisInformation=" + userBasisInformation +
                ", userExtraInformation=" + userExtraInformation +
                '}';
    }
}
