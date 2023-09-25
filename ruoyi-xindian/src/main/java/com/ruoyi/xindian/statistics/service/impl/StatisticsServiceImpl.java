package com.ruoyi.xindian.statistics.service.impl;

import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import com.ruoyi.xindian.statistics.domain.Reportstic;
import com.ruoyi.xindian.statistics.domain.Statistics;
import com.ruoyi.xindian.statistics.mapper.StatisticsMapper;
import com.ruoyi.xindian.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/11 14:53
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;


    @Override
    public List<Statistics> selectDoctorData(Statistics str) {
        return statisticsMapper.selectDoctorData(str);
    }

    @Override
    public List<Statistics> selectDoctor() {
        return statisticsMapper.selectDoctor();
    }

    @Override
    public List<AgeStatistics> ageListByMan(AgeStatistics str) {
        return statisticsMapper.ageListByMan(str);
    }

    @Override
    public List<AgeStatistics> ageListByWoman(AgeStatistics str) {
        return statisticsMapper.ageListByWoman(str);
    }

    @Override
    public List<Statistics> selectDateList(Statistics str) {
        return statisticsMapper.selectDateList(str);
    }

    @Override
    public List<Statistics> typeList() {
        return statisticsMapper.typeList();
    }

    @Override
    public List<Statistics> selectCountList(Statistics statistics) {
        return statisticsMapper.selectCountList(statistics);
    }

    @Override
    public List<Statistics> selectDocDiagnose(Statistics statistics) {
        return statisticsMapper.selectDocDiagnose(statistics);
    }

    @Override
    public List<AgeStatistics> getmonth(Reportstic str) {
        return statisticsMapper.getmonth(str);
    }

    @Override
    public List<AgeStatistics> getday(Reportstic str) {
        return statisticsMapper.getday(str);
    }


}
