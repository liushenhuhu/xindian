package com.ruoyi.xindian.statistics.service.impl;

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
}
