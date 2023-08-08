package com.ruoyi.xindian.statistics.service;

import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import com.ruoyi.xindian.statistics.domain.Statistics;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/11 14:52
 */
public interface IStatisticsService {
    List<Statistics> selectDoctorData(Statistics str);

    List<Statistics> selectDoctor();

    List<AgeStatistics> ageListByMan(AgeStatistics str);

    List<AgeStatistics> ageListByWoman(AgeStatistics str);

    List<Statistics> selectDateList(Statistics str);

    List<Statistics> typeList();


    List<Statistics> selectCountList(Statistics statistics);
}
