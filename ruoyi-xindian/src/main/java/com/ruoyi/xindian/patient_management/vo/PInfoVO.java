package com.ruoyi.xindian.patient_management.vo;

import lombok.Data;

@Data
public class PInfoVO {

    /**
     * 采集总数
     */
    private String totalCollection;

    /**
     * 单导总数
     */
    private String totalSingle;

    /**
     * 12导总数
     */
    private String total12;


    /**
     * 动态心电
     */
    private String totalDong;
    /**
     * 已诊断次数
     */
    private String totalConsult;
}
