package com.ruoyi.xindian.patient_management.service;

import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.ruoyi.xindian.patient_management.domain.WeeklyCount;

import java.util.HashMap;

public interface IWeeklyService {

    boolean allZero(SingleHistoryData singleHistoryData);

    WeeklyCount SingleHistoryDataToweeklyCount(WeeklyCount weeklyCount, SingleHistoryData singleHistoryData);

    WeeklyCount weeklyCountAddsingleHistoryData(WeeklyCount weeklyCount, SingleHistoryData singleHistoryData);

    void getWeekly(SingleHistoryData info);

    HashMap<String, Object> getWeeklyByPhone(SingleHistoryData Info);

    WeeklyCount weeklyCountAddData(WeeklyCount weeklyCount, double hr, double P, double PR, double QRS, double QTC, double SDNN, double RMSSD, int week);

    HashMap<String, Object> getHistoryWeekly(SingleHistoryData info);
}
