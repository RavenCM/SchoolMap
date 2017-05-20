package com.teemo.schoolmap.user.service.impl;

import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.user.dto.Message;
import com.teemo.schoolmap.user.dto.MessageImage;
import com.teemo.schoolmap.user.dto.UserBasisInformation;
import com.teemo.schoolmap.user.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Teemo on 2017.05.20.
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private CommonMapper<Message> messageCommonMapper;

    @Autowired
    private CommonMapper<MessageImage> messageImageCommonMapper;

    @Autowired
    private CommonMapper<UserBasisInformation> userBasisInformationCommonMapper;

    @Override
    public List<Message> getMessage() {
        Message tmp = new Message();
        tmp.setIsEnable(1);
        List<Message> messageList = messageCommonMapper.select(tmp);
        if (messageList != null)
            for (Message message : messageList) {
                MessageImage messageImage = new MessageImage();
                messageImage.setMessageId(message.getMessageId());
                message.setMessageImageList(messageImageCommonMapper.select(messageImage));
                UserBasisInformation userBasisInformation = new UserBasisInformation();
                userBasisInformation.setUserId(message.getCreatedBy());
                message.setName(userBasisInformationCommonMapper.select(userBasisInformation).get(0).getUsername());
            }
        return messageList;
    }

    @Override
    @Transactional
    public boolean insertMessage(Message message) {
        messageCommonMapper.insert(message);
        if (message.getMessageImageList() != null)
            for (MessageImage messageImage : message.getMessageImageList()) {
                messageImageCommonMapper.insert(messageImage);
            }
        return true;
    }
}
