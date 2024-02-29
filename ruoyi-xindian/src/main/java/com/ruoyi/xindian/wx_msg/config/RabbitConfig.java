package com.ruoyi.xindian.wx_msg.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {


//    @Bean
//    public Queue delayedQueue() {
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-dead-letter-exchange", "real_exchange");
//        args.put("x-dead-letter-routing-key", "real_routing_key");
//        return new Queue("xd_wx_msg_delayed_queue", true, false, false, args);
//    }
//
//    @Bean
//    public CustomExchange delayedExchange() {
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-delayed-type", "direct");
//        return new CustomExchange("xd_wx_msg_delayed_queue", "x-delayed-message", true, false, args);
//    }
}