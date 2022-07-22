package com.ruoyi.framework.websocket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: han
 * @Date: 2022-07-14
 */
@RestController
@RequestMapping("/websocket")
public class WebSocketController {

    @GetMapping("/push")
    public void pushone() {
        WebSocketService.sendMessage("韩涵111111111","111");
    }
}
