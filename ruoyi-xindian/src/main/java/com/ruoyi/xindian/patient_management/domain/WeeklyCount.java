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
    //房早占比
    private double apBeatPercentage;
    //室早占比
    private double pvBeatPercentage;
    //房颤占比
    private double atrialFibrillationPercentage;
    //室扑占比
    private double atrialFlutterPercentage;
    //心动过缓占比
    private double bradycardiaPercentage;
    //心动过速占比
    private double tachycardiaPercentage;

    //比较上周
    //房早占比
    private double apBeatPercentageS;
    //室早占比
    private double pvBeatPercentageS;
    //房颤占比
    private double atrialFibrillationPercentageS;
    //室扑占比
    private double atrialFlutterPercentageS;
    //心动过缓占比
    private double bradycardiaPercentageS;
    //心动过速占比
    private double tachycardiaPercentageS;

    //每天的统计
    private ECGTendency monday;
    private ECGTendency tuesday;
    private ECGTendency wednesday;
    private ECGTendency thursday;
    private ECGTendency friday;
    private ECGTendency saturday;
    private ECGTendency sunday;
}
