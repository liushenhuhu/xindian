package com.ruoyi.xindian.wx_pay.domain;

import lombok.Data;

@Data
public class SendMessageVo {

       private Integer     errcode;
       private String     errmsg;
       private Long     msgid;
}
