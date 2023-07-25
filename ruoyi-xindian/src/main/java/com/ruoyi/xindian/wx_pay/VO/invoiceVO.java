package com.ruoyi.xindian.wx_pay.VO;

import lombok.Data;

@Data
public class invoiceVO {

    private Integer money;
    private String openid;
    private String errmsg;
    private UserAuthInfo user_auth_info;

}
