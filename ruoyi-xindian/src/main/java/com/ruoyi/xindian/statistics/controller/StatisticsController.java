package com.ruoyi.xindian.statistics.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.interfaces.Aes;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import com.ruoyi.xindian.statistics.domain.Reportstic;
import com.ruoyi.xindian.statistics.domain.Statistics;
import com.ruoyi.xindian.statistics.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lixinlong
 * 2023/7/11 11:25
 */
@RestController
@RequestMapping("/statistics/statistics")
public class StatisticsController extends BaseController {

    @Autowired
    private IStatisticsService statisticsService;


    @Resource
    private AesUtils aesUtils;
    /**
     * 医生统计
     * @return
     */
    @GetMapping("doctor")
    public List<Statistics> selectDoctor() throws Exception {
        List<Statistics> statistics = statisticsService.selectDoctor();
        for (Statistics c:statistics){
            if (c.getDoctorName()!=null&&!"".equals(c.getDoctorName())){
                c.setDoctorName(aesUtils.decrypt(c.getDoctorName()));
            }
            if (c.getDoctorPhone()!=null&&!"".equals(c.getDoctorPhone())){
                c.setDoctorPhone(aesUtils.decrypt(c.getDoctorPhone()));
            }
        }
        return statistics;
    }

    /**
     * 就诊次数
     * @param str
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(Statistics str) throws Exception {
        if (str.getDoctorPhone()!=null&&!"".equals(str.getDoctorPhone())){
            str.setDoctorPhone(aesUtils.encrypt(str.getDoctorPhone()));
        }
        List<Statistics> list = statisticsService.selectDoctorData(str);
        return getDataTable(list);
    }

    @GetMapping("/countList")
    public TableDataInfo countList(Statistics statistics) throws Exception {
        startPage();
        List<Statistics> statistics1 = statisticsService.selectCountList(statistics);
        for (Statistics c : statistics1){

            if (c.getDoctorName()!=null&&!"".equals(c.getDoctorName())){
                c.setDoctorName(aesUtils.decrypt(c.getDoctorName()));
            }
            if (c.getDoctorPhone()!=null&&!"".equals(c.getDoctorPhone())){
                c.setDoctorPhone(aesUtils.decrypt(c.getDoctorPhone()));
            }
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(Double.parseDouble(c.getCountTime()) / 60));
            BigDecimal bigDecimal1 = bigDecimal.setScale(1, RoundingMode.UP);
            c.setCountTime(bigDecimal1 +"分钟");
            BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(Double.parseDouble(c.getAverageTime()) / 60));
            BigDecimal bigDecimal3 = bigDecimal2.setScale(1, RoundingMode.UP);
            c.setAverageTime(bigDecimal3 +"分钟");
        }
        return  getDataTable(statistics1);
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

    @GetMapping ("/getreportcount")
    public Map<String,Object> submitReportCount(Reportstic str){
        if("".equals(str.getStartTime())||str.getStartTime()==null
                ||"".equals(str.getEndTime())||str.getEndTime()==null){
            str.setStartTime(LocalDateTime.now().plusDays(-13).format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
            str.setEndTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        }
        if("".equals(str.getYear())||str.getYear()==null){
            str.setYear(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY")));
        }

        List<LocalDate> middleDate = getMiddleDate(LocalDate.parse(str.getStartTime()), LocalDate.parse(str.getEndTime()));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("M-d");
        List<AgeStatistics> list3 = new ArrayList<>();
        for (LocalDate localDate : middleDate) {
            list3.add(new AgeStatistics(localDate.format(fmt),0));
        }

        List<AgeStatistics> list1 = statisticsService.getday(str);
        List<AgeStatistics> list2 = statisticsService.getmonth(str);
        List<Object> collect = list3.stream().map(item -> {
            for (int i = 0; i < list1.size(); i++) {
                if (item.getName().equals(list1.get(i).getName())) {
                    item.setValue(list1.get(i).getValue());
                }
            }
            return item;
        }).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("day",collect);
        map.put("month",list2);
        return map;
    }
    public static List<LocalDate> getMiddleDate(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = new ArrayList<>();
        long length = end.toEpochDay() - begin.toEpochDay();
        for (long i = length; i >= 0; i--) {
            localDateList.add(end.minusDays(i));
        }
        return localDateList;
    }

}
