package com.teemo.demo.dto;

import com.teemo.schoolmap.common.mybatis.component.BaseDTO;
import com.teemo.schoolmap.common.mybatis.annotation.Condition;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/11 16:17
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
@Table(name = "demo")
public class DemoDto extends BaseDTO {
    @Id
    @Column
    @Condition
    private int id;

    @Column
    @Condition
    private String value;

    @Column
    @Condition
    private String meaning;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "DemoDto{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", meaning='" + meaning + '\'' +
                "objectVersionNumber=" + objectVersionNumber +
                ", createdBy=" + createdBy +
                ", creationDate=" + creationDate +
                ", lastUpdatedBy=" + lastUpdatedBy +
                ", lastUpdatedDate=" + lastUpdatedDate +
                ", isEnable=" + isEnable +
                '}';
    }
}

