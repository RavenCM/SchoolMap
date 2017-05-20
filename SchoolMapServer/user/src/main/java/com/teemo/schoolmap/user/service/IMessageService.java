package com.teemo.schoolmap.user.service;

import com.teemo.schoolmap.user.dto.Message;

import java.util.List;

/**
 * Created by Teemo on 2017.05.20.
 */
public interface IMessageService {

    List<Message> getMessage();

    boolean insertMessage(Message message);
}
