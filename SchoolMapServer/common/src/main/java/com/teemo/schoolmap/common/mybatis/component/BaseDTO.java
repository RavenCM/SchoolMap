package com.teemo.schoolmap.common.mybatis.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.teemo.schoolmap.common.mybatis.annotation.Condition;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 9:05
 * @version 1.0
 * @name schoolmap-server
 * @description BaseDTO
 */
public class BaseDTO {
    /**
     * 记录版本号
     */
    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer objectVersionNumber;

    /**
     * 创建人ID
     */
    @Column
    @Condition
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer createdBy;

    /**
     * 创建日期
     */
    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Timestamp creationDate;

    /**
     * 最后更新人ID
     */
    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer lastUpdatedBy;

    /**
     * 最后更新日期
     */
    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Timestamp lastUpdatedDate;

    /**
     * 是否启用
     */
    @Column
    @Condition
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer isEnable;

    public Integer getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Integer objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}
