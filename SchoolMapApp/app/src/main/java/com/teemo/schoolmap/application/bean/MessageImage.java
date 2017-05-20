package com.teemo.schoolmap.application.bean;


/**
 * Created by Teemo on 2017.05.20.
 */
public class MessageImage extends BaseDTO {

    private Integer messageImageId;

    private Integer messageId;

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
