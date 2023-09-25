package com.ruoyi.xindian.statistics.mapper;

import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import com.ruoyi.xindian.statistics.domain.Reportstic;
import com.ruoyi.xindian.statistics.domain.Statistics;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/11 14:53
 */
public interface StatisticsMapper {

    List<Statistics> selectDoctorData(Statistics str);

    List<Statistics> selectDoctor();

    List<AgeStatistics> ageListByMan(AgeStatistics str);

    List<AgeStatistics> ageListByWoman(AgeStatistics str);

    List<Statistics> selectDateList(Statistics str);

    List<Statistics> typeList();

    List<Statistics> selectCountList(Statistics statistics);

    List<Statistics> selectDocDiagnose(Statistics statistics);




    List<AgeStatistics> getmonth(Reportstic str);

    List<AgeStatistics> getday(Reportstic str);
}
