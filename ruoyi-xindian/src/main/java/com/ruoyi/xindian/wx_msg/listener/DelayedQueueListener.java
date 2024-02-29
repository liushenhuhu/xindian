package com.ruoyi.xindian.wx_msg.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DelayedQueueListener {

//    @RabbitListener(queues = "xd_wx_msg_delayed_queue")
//    public void processDelayedMessage(String message) {
//        System.out.println("Received delayed message: " + message);
//        // 处理接收到的消息逻辑
//    }
}