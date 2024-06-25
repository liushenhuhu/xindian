package com.ruoyi.xindian.patient_management.service;

import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.ruoyi.xindian.patient_management.domain.WeeklyCount;

import java.util.HashMap;

public interface IWeeklyService {

    boolean allZero(SingleHistoryData singleHistoryData);

    WeeklyCount SingleHistoryDataToweeklyCount(WeeklyCount weeklyCount, SingleHistoryData singleHistoryData);

    WeeklyCount weeklyCountAddsingleHistoryData(WeeklyCount weeklyCount, SingleHistoryData singleHistoryData);

    void getWeekly(SingleHistoryData info);

    HashMap<String, Object> getWeeklyByPhone(String patientPhone);

}
