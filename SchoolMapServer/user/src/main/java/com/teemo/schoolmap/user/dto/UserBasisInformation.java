package com.teemo.schoolmap.user.dto;

import com.teemo.schoolmap.common.mybatis.annotation.Condition;
import com.teemo.schoolmap.common.mybatis.component.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 14:17
 * @version 1.0
 * @name schoolmap-server
 * @description 用户基础信息
 */
@Table(name = "sm_user_basis_information")
public class UserBasisInformation extends BaseDTO{

    /**
     * 用户基础信息表ID
     */
    @Id
    @Column
    @Condition
    private Integer userBasisInformationId;

    /**
     * 用户ID
     */
    @Column
    @NotNull
    @Condition
    private Integer userId;

    /**
     * 用户注册邮箱
     */
    @Column
    @NotEmpty
    @Condition
    private String email;

    /**
     * 用户名
     */
    @Column
    @NotEmpty
    @Condition
    private String username;

    /**
     * 用户性别
     */
    @Column
    @NotEmpty
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
}
