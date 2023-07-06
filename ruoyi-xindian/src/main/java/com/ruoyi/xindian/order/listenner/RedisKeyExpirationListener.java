package com.ruoyi.xindian.order.listenner;

import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis失效key监听
 * 
 * @author 郑文
 *
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * redis失效key事件处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // message.toString()可以获取失效的key
        String expiredKey = message.toString();
        System.out.println("-------------------");

        String[] split = expiredKey.split(":");
        if (split[0].equals("order")){
            orderInfoService.redisOrderKey(split[1]);
        }

    }
}
