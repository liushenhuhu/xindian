package com.ruoyi.xindian.wx_pay.domain;

import lombok.Data;

@Data
public class MessageTemplateEntity {
 
    private MessageValueEntity thing1;
 
    private MessageValueEntity time2;
 
    private MessageValueEntity thing3;
 
    public void setMessageData(MessageValueEntity thing1, MessageValueEntity time2, MessageValueEntity thing3) {
        this.thing1 = thing1;
        this.time2 = time2;
        this.thing3 = thing3;
    }
 
}