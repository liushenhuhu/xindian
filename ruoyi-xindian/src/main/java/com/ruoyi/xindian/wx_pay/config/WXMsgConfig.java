package com.ruoyi.xindian.wx_pay.config;

import com.ruoyi.xindian.wx_pay.util.WXPayConstants;
import lombok.val;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class WXMsgConfig {

    @Bean
    public WxMpService wxMpService() {
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(WXPayConstants.APP_ID);
        wxStorage.setSecret(WXPayConstants.SECRET);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        return wxMpService;
    }

}
