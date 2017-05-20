package com.teemo.schoolmap.user.dto;

import com.teemo.schoolmap.common.mybatis.annotation.Condition;
import com.teemo.schoolmap.common.mybatis.component.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Teemo on 2017.05.20.
 */
@Table(name = "SM_MESSAGE")
public class Message extends BaseDTO {
    @Id
    @Column
    @Condition
    private Integer messageId;

    @Column
    private String content;

    private String name;

    List<MessageImage> messageImageList;


    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MessageImage> getMessageImageList() {
        return messageImageList;
    }

    public void setMessageImageList(List<MessageImage> messageImageList) {
        this.messageImageList = messageImageList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
