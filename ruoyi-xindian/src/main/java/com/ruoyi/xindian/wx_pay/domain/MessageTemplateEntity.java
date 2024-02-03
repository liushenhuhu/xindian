package com.ruoyi.xindian.wx_pay.domain;

import lombok.Data;

@Data
public class MessageTemplateEntity {

 
    private MessageValueEntity date7;
 
    private MessageValueEntity thing8;
    private MessageValueEntity phrase4;
    private MessageValueEntity thing11;
    private MessageValueEntity thing3;

    private MessageValueEntity thing5;
    private MessageValueEntity thing1;
    private MessageValueEntity thing2;

    private MessageValueEntity phrase2;

    private MessageValueEntity thing22;

    private MessageValueEntity name3;

    private MessageValueEntity time8;

    public MessageTemplateEntity() {
    }

    public MessageTemplateEntity(MessageValueEntity thing1, MessageValueEntity thing22, MessageValueEntity name3, MessageValueEntity time8) {
        this.thing1 = thing1;
        this.thing22 = thing22;
        this.name3 = name3;
        this.time8 = time8;
    }

    public void setMessageData(MessageValueEntity date7, MessageValueEntity thing8, MessageValueEntity phrase4, MessageValueEntity thing11, MessageValueEntity thing3) {
        this.date7 = date7;
        this.thing8 = thing8;
        this.phrase4 = phrase4;
        this.thing11 = thing11;
        this.thing3 = thing3;
    }

    public MessageTemplateEntity( MessageValueEntity thing1, MessageValueEntity thing2,MessageValueEntity thing3) {
        this.thing3 = thing3;
        this.thing1 = thing1;
        this.thing2 = thing2;
    }




}