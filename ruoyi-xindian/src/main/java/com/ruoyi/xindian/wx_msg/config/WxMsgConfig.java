package com.ruoyi.xindian.wx_msg.config;

import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WxMsgConfig {



    @Resource
    private SysUserMapper sysUserMapper;


    @Resource
    private WXPublicRequest wxPublicRequest;


    /**
     * 定时消息提醒
     */
    public void sendEverydayRemindMsg(){
//        sysUserMapper


    }







}
