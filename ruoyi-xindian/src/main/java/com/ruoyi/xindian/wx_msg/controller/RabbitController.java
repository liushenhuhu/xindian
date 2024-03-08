package com.ruoyi.xindian.wx_msg.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rabbit_mq")
public class RabbitController {



//    @Resource
//    private AmqpTemplate amqpTemplate;
//
//    public void sendDelayedMessage(String message, long delayInMilliseconds) {
//        amqpTemplate.convertAndSend("xd_wx_msg_delayed_queue", "real_routing_key", message, messagePostProcessor -> {
//            messagePostProcessor.getMessageProperties().setDelay((int) delayInMilliseconds);
//            return messagePostProcessor;
//        });
//    }


//   @GetMapping("/getMq")
//    public String getMq() {
//       sendDelayedMessage("hello", 100);
//        return "getMq";
//    }



}
