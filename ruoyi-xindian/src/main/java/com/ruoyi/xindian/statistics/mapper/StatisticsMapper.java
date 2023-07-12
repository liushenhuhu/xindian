package com.ruoyi.xindian.statistics.mapper;

import com.ruoyi.xindian.statistics.domain.Statistics;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/11 14:53
 */
public interface StatisticsMapper {

    List<Statistics> selectDoctorData(Statistics str);

    List<Statistics> selectDoctor();
}
