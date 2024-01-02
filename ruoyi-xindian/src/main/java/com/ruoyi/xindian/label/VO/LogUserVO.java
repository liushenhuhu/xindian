package com.ruoyi.xindian.label.VO;

import lombok.Data;

@Data
public class LogUserVO {


    private String logId;

    private Long userId;

    private String logNoiseLevel;

    private String logType;
    private int isSuspected;
}
