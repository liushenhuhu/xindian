package com.ruoyi.xindian.statistics.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import com.ruoyi.xindian.statistics.domain.Statistics;
import com.ruoyi.xindian.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixinlong
 * 2023/7/11 11:25
 */
@RestController
@RequestMapping("/statistics/statistics")
public class StatisticsController extends BaseController {

    @Autowired
    private IStatisticsService statisticsService;

    /**
     * 医生统计
     * @return
     */
    @GetMapping("doctor")
    public List<Statistics> selectDoctor(){
        return statisticsService.selectDoctor();
    }

    @GetMapping("/list")
    public TableDataInfo list(Statistics str){
        List<Statistics> list = statisticsService.selectDoctorData(str);
        return getDataTable(list);
    }

    @GetMapping ("/ageList")
    public Map<String,Object> ageList(AgeStatistics str){
        /* 查询性别男 */
        List<AgeStatistics> manList = statisticsService.ageListByMan(str);
        /* 查询性别女 */
        List<AgeStatistics> womanList = statisticsService.ageListByWoman(str);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("men",manList);
        map.put("women",womanList);
        return map;
    }


}
