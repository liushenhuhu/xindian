package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import  org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx-public")
public class WxPublicConfig {
    /**
     * AppId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;


    public WxPublicConfig() {
    }

    public WxPublicConfig(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    /**
     * 获取
     * @return appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取
     * @return appSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * 设置
     * @param appSecret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String toString() {
        return "WxAppConfig{appId = " + appId + ", appSecret = " + appSecret + "}";
    }
}
