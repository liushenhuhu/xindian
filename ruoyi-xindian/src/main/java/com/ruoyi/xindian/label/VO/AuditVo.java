package com.ruoyi.xindian.label.VO;

import lombok.Data;

@Data
public class AuditVo {


    private String logId;

    private String userLogNoiseLevel;


    private String auditLogNoiseLevel;

    private String userUserId;

    private String auditUserId;


    private String userAnoStatus;

    private String auditAnoStatus;

    private Long userId;

    private String ecgType;
    private int userIsSuspected;
}
