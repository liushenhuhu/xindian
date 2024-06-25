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
    //每周每天检测次数
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int saturday;
    private int sunday;
    //每天检测的心率
    private List<Integer> monday_hr;
    private List<Integer> tuesday_hr;
    private List<Integer> wednesday_hr;
    private List<Integer> thursday_hr;
    private List<Integer> friday_hr;
    private List<Integer> saturday_hr;
    private List<Integer> sunday_hr;
}
