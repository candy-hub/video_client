package com.video.utils;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EmailUtils {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMail(Integer id){
        rabbitTemplate.convertAndSend("topicExchange","topic.me",id);
    }
}
