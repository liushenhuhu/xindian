package com.ruoyi.xindian.statistics.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import com.ruoyi.xindian.statistics.domain.Statistics;
import com.ruoyi.xindian.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    /**
     * 就诊次数
     * @param str
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(Statistics str){
        List<Statistics> list = statisticsService.selectDoctorData(str);
        return getDataTable(list);
    }

    @GetMapping("/countList")
    public TableDataInfo countList(Statistics statistics){
        return  null;
    }

    @GetMapping("dateList")
    public TableDataInfo dateList(Statistics str){
        //月份转换
        str.setMonth(lowNumber(str.getMonth()));
        startPage();
        List<Statistics> list = statisticsService.selectDateList(str);
        for (Statistics c : list){

            BigDecimal bigDecimal = new BigDecimal(String.valueOf(Double.parseDouble(c.getTimeCount()) / 60));
            BigDecimal bigDecimal1 = bigDecimal.setScale(1, RoundingMode.UP);
            c.setTimeCount(bigDecimal1 +"分钟");
        }
        return getDataTable(list);
    }


    /**
     * 年龄分布
     * @param str
     * @return
     */
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

    public String lowNumber(String numStr){
        String str = "";
        switch(numStr){
            case "一月":
                str = "1";
                break;
            case "二月":
                str = "2";
                break;
            case "三月":
                str = "3";
                break;
            case "四月":
                str = "4";
                break;
            case "五月":
                str = "5";
                break;
            case "六月":
                str = "6";
                break;
            case "七月":
                str = "7";
                break;
            case "八月":
                str = "8";
                break;
            case "九月":
                str = "9";
                break;
            case "十月":
                str = "10";
                break;
            case "十一月":
                str = "11";
                break;
            case "十二月":
                str = "12";
                break;
        }
        return str;
    }

    @GetMapping ("/typeList")
    public List<Statistics> typeList(Statistics statistics){
        return statisticsService.typeList();
    }



}
