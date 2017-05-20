package com.teemo.schoolmap.user.controller;

import com.teemo.schoolmap.user.dto.Message;
import com.teemo.schoolmap.user.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Teemo on 2017.05.20.
 */
@RestController
@RequestMapping("api/message")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IMessageService messageService;

    @GetMapping
    public ResponseEntity<List> getMessage() {
        List<Message> messageList = messageService.getMessage();
        logger.info("getMessage={}", messageList);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insertMessage(@RequestBody Message message){
        logger.info("insertMessage={}", message);
        return new ResponseEntity<>(messageService.insertMessage(message) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
