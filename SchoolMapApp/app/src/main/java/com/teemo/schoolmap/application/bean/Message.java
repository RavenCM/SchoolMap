package com.teemo.schoolmap.application.bean;

import java.util.List;

/**
 * Created by Teemo on 2017.05.20.
 */
public class Message extends BaseDTO {
    private Integer messageId;

    private String content;

    List<MessageImage> messageImageList;

    private String name;


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
