package com.ruoyi.xindian.statistics.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xindian.statistics.domain.Statistics;
import com.ruoyi.xindian.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/11 11:25
 */
@RestController
@RequestMapping("/statistics/statistics")
public class StatisticsController extends BaseController {

    @Autowired
    private IStatisticsService statisticsService;

    @GetMapping("doctor")
    public List<Statistics> selectDoctor(){
        return statisticsService.selectDoctor();
    }

    @GetMapping("/list")
    public TableDataInfo list(Statistics str){
        List<Statistics> list = statisticsService.selectDoctorData(str);
        return getDataTable(list);
    }

}
