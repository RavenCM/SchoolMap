package com.teemo.schoolmap.application.bean;

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
    protected int objectVersionNumber;

    /**
     * 创建人ID
     */
    protected int createdBy;

    /**
     * 创建日期
     */
    protected Timestamp creationDate;

    /**
     * 最后更新人ID
     */
    protected int lastUpdatedBy;

    /**
     * 最后更新日期
     */
    protected Timestamp lastUpdatedDate;

    /**
     * 是否启用
     */
    protected int isEnable;

    public int getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(int objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

}
