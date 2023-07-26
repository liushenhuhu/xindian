package com.ruoyi.xindian.wx_pay.domain;

import lombok.Data;

@Data
public class MessageTemplateEntity {

 
    private MessageValueEntity date7;
 
    private MessageValueEntity thing8;
    private MessageValueEntity phrase4;
    private MessageValueEntity thing11;
    private MessageValueEntity thing3;

    public void setMessageData(MessageValueEntity date7, MessageValueEntity thing8, MessageValueEntity phrase4, MessageValueEntity thing11, MessageValueEntity thing3) {
        this.date7 = date7;
        this.thing8 = thing8;
        this.phrase4 = phrase4;
        this.thing11 = thing11;
        this.thing3 = thing3;
    }
}