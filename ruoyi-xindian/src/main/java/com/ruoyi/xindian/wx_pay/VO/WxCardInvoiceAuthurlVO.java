package com.ruoyi.xindian.wx_pay.VO;

import lombok.Data;

@Data
public class WxCardInvoiceAuthurlVO {

    private String appid;
    private String auth_url;

    public WxCardInvoiceAuthurlVO(String appid, String auth_url) {
        this.appid = appid;
        this.auth_url = auth_url;
    }
}
