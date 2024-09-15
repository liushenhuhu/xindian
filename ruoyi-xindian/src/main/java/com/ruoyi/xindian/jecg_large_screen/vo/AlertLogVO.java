package com.ruoyi.xindian.jecg_large_screen.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AlertLogVO {

    private String logId;

    private Date createTime;

    private String onlineState;

    private String countName;

    private String gatewayno;

    private String type;

}
