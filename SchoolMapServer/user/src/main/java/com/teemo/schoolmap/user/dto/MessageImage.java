package com.teemo.schoolmap.user.dto;

import com.teemo.schoolmap.common.mybatis.annotation.Condition;
import com.teemo.schoolmap.common.mybatis.component.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Teemo on 2017.05.20.
 */
@Table(name = "SM_MESSAGE_IMAGE")
public class MessageImage extends BaseDTO{

    @Id
    @Column
    @Condition
    private Integer messageImageId;

    @Column
    private Integer messageId;


    @Column
    private String path;

    public Integer getMessageImageId() {
        return messageImageId;
    }

    public void setMessageImageId(Integer messageImageId) {
        this.messageImageId = messageImageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
