package com.ruoyi.xindian.patient_management.domain;

import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeeklyCount extends SingleHistoryData implements Serializable {

    //单导检测次数
    private int signalCount;
    //多导联检测次数
    //检测为正常心电图次数
    private int normalCount;
    //每天的统计
    private ECGTendency monday;
    private ECGTendency tuesday;
    private ECGTendency wednesday;
    private ECGTendency thursday;
    private ECGTendency friday;
    private ECGTendency saturday;
    private ECGTendency sunday;
}
