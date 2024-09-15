package com.ruoyi.xindian.jecg_large_screen.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.domain.vo.SymCountVo;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.equipment.mapper.EquipmentMapper;
import com.ruoyi.xindian.jecg_large_screen.vo.*;
import com.ruoyi.xindian.patient.domain.PatientLogin;
import com.ruoyi.xindian.patient.mapper.PatientLoginMapper;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.mapper.PatientManagementMapper;
import com.ruoyi.xindian.util.DateUtil;
import com.tencentcloudapi.tci.v20190318.models.Person;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/large_screen")
public class LargeScreenController {



    @Resource
    private PatientLoginMapper patientLoginMapper;

    @Resource
    private PatientMapper patientMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private AesUtils aesUtils;


    @Resource
    private PatientManagementMapper patientManagementMapper;


    /**
     * 左上，查询用户信息
     * @return
     */
    @GetMapping("/getLeftTop")
    public AjaxResult getUserData(String hospitalCode){

        Map<String,Object> map = new HashMap<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayStr = today.format(formatter);

        List<SymCountVo> symCountVos1 = patientMapper.selectListByCount(hospitalCode);

        int countByEquipmentCodeInt = equipmentMapper.countByEquipmentCodeInt(hospitalCode);
        int countByEquipmentCodeTrue = equipmentMapper.countByEquipmentCodeTrue(hospitalCode);

        //设备数量
        map.put("onlineNum",countByEquipmentCodeInt);

        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        //新增加数量
        long count = symCountVos1.stream()
                .filter(vo -> vo.getCreateTime()!=null)
                .filter(vo -> vo.getCreateTime().after(startDate) && vo.getCreateTime().before(endDate))
                .count();


        map.put("offlineNum",count);

        //总用户量
        int size = symCountVos1.size();
        map.put("totalNum",size);
        map.put("alarmNum",countByEquipmentCodeTrue);

        return AjaxResult.success(map);
    }


    /**
     * 查询左中静态报告类型统计信息
     * @return
     */
    @GetMapping("/getLeftCentre")
    public AjaxResult getLeftCentre(String hospitalCode){

        Map<String,Object> map = new HashMap<>();
        List<SymCountVo> symCountVos = patientManagementMapper.selectCount(hospitalCode);
        //总报告数
        map.put("totalNum",symCountVos.size());
        //静态单导
        map.put("offlineNum",symCountVos.stream().filter(vo -> vo.getEcgType().contains("JECGsingle")).count());
        //静态4导
        map.put("lockNum",symCountVos.stream().filter(vo -> vo.getEcgType().contains("JECG4")).count());
        //静态12导
        map.put("onlineNum",symCountVos.stream().filter(vo -> vo.getEcgType().contains("JECG12")).count());
        return AjaxResult.success(map);
    }


    /**
     * 查询左下报告类型统计信息 7天
     * @param hospitalCode
     * @return
     */
    @GetMapping("/getLeftBottom")
    public AjaxResult getLeftBottom(String hospitalCode){

        List<String> dateList = new ArrayList<>();
        dateList.add("干扰信号");
        dateList.add("其它");

        List<AlertLogVO> alertLogVOS = patientManagementMapper.selectAlertLogList(hospitalCode).stream()
                .peek(vo -> {
                    try {
                        vo.setCountName(aesUtils.decrypt(vo.getCountName()));
                    } catch (Exception e) {
                    }
                })
                .filter(vo -> !dateList.contains(vo.getOnlineState()))
                .collect(Collectors.toList());
        return AjaxResult.success(alertLogVOS);
    }


    /**
     * 右上 查询风险 7天
     * @param hospitalCode
     * @return
     */
    @GetMapping("/getRightTop")
    public AjaxResult getRightTop(String hospitalCode){
        Map<String,Object> map = new HashMap<>();
        List<RightTopVo> rightTopVos = patientManagementMapper.selectLevelList(hospitalCode);

        map.put("dateList",rightTopVos.stream().map(RightTopVo::getDate).collect(Collectors.toList()));
        map.put("numList1",rightTopVos.stream().map(RightTopVo::getCount2).collect(Collectors.toList()));
        map.put("numList2",rightTopVos.stream().map(RightTopVo::getCount3).collect(Collectors.toList()));

        return AjaxResult.success(map);

    }



    /**
     * 右中 查询风险 7天
     * @param hospitalCode
     * @return
     */
    @GetMapping("/getRightCentre")
    public AjaxResult getRightCentre(String hospitalCode){
        Map<String,Object> map = new HashMap<>();
        List<AlertLogVO> rightCentreVos = patientManagementMapper.selectIntelligentList(hospitalCode);

        if (rightCentreVos.isEmpty()){
            return AjaxResult.success(map);
        }


        List<String> countName = rightCentreVos.stream().map(AlertLogVO::getCountName).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        List<String> gatewayno = rightCentreVos.stream().map(AlertLogVO::getGatewayno).filter(StringUtils::isNotEmpty).collect(Collectors.toList());

        String regex = "[，,.]";
        Pattern chineseOnlyPattern = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String str : countName) {
            String[] parts = str.split(regex);
            for (String part : parts) {
                part = part.trim();
                Matcher matcher = chineseOnlyPattern.matcher(part);
                if (matcher.matches() && !part.isEmpty()) {
                    frequencyMap.put(part, frequencyMap.getOrDefault(part, 0) + 1);
                }
            }
        }
        for (String str : gatewayno) {
            String[] parts = str.split(regex);
            for (String part : parts) {
                part = part.trim();
                Matcher matcher = chineseOnlyPattern.matcher(part);
                if (matcher.matches() && !part.isEmpty()) {
                    frequencyMap.put(part, frequencyMap.getOrDefault(part, 0) + 1);
                }
            }
        }

        List<Map<String, Object>> jsonArray = new ArrayList<>();

        List<Map.Entry<String, Integer>> topEntries = frequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(8)
                .collect(Collectors.toList());
        for (Map.Entry<String, Integer> entry : topEntries) {
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("name", entry.getKey());
            jsonObject.put("value", entry.getValue());
            jsonArray.add(jsonObject);
        }

        return AjaxResult.success(jsonArray);

    }



    @GetMapping("/getRightBottom")
    public AjaxResult getRightBottom(String hospitalCode) throws Exception {

        List<RightBottomVo> rightBottomVos = patientManagementMapper.selectReportList(hospitalCode);
        for (RightBottomVo rightBottomVo : rightBottomVos) {

            rightBottomVo.setGatewayno(aesUtils.decrypt(rightBottomVo.getGatewayno()));
            String data = rightBottomVo.getData();
            try {
                if (StringUtils.isNotEmpty(data)){
                    // 替换 'NaN' 为 '0'
                    String jsonString = data.replace("nan", "0");
                   jsonString = jsonString.replace("NaN", "0");

                    // 替换单引号 ' 为双引号 "
                    jsonString = jsonString.replace("'", "\"");

                    JSONObject jsonObject = JSONObject.parseObject(jsonString);
                    Object o = jsonObject.get("平均心率");

                    if (o!=null){
                        rightBottomVo.setAlertValue(o.toString());
                    }else {
                        rightBottomVo.setAlertValue("75");
                    }
                }else{
                    rightBottomVo.setAlertValue("75");
                }

                if (rightBottomVo.getBirthDay()!=null){
                    rightBottomVo.setAge(DateUtil.getAge(rightBottomVo.getBirthDay()));
                }else {
                    if (rightBottomVo.getAge()==null){
                        rightBottomVo.setAge(26);
                    }

                }

            } catch (Exception e) {
                System.out.println(rightBottomVo.getData());
                rightBottomVo.setAlertValue("75");
            }
            rightBottomVo.setData("");
        }

        return AjaxResult.success(rightBottomVos);
    }


    @GetMapping("/getCentreTop")
    public AjaxResult getCentreTop(String hospitalCode,String regionCode){

        Map<String,Object> map = new HashMap<>();
        List<ScreenVo> screenVos = patientManagementMapper.selectScreenList(hospitalCode);
        map.put("dateList",screenVos);
        map.put("regionCode",regionCode);

        return AjaxResult.success(map);
    }



    @GetMapping("/getCentreBottom")
    public AjaxResult getCentreBottom(String hospitalCode){

        List<CentreBottomVo> screenVos = patientManagementMapper.selectHrList(hospitalCode);
        List<CentreBottomVo> collect = screenVos.stream()
                .peek(s -> s.setAge(s.getBirthDay() != null ? DateUtil.getAge(s.getBirthDay()) : s.getAge()!=null?s.getAge():0))
                .collect(Collectors.toList());

        // 定义年龄区间
        int[] ageBounds = {0, 20, 40, 60, 80, 100, Integer.MAX_VALUE};
        String[] ageLabels = {"0-20", "21-40", "41-60", "61-80", "81-100", "100+"};

        // 定义心率区间
        int[] heartRateBounds = {0, 60, 100, Integer.MAX_VALUE};
        String[] heartRateLabels = {"<60", "60-100", "100+"};

        // 初始化统计结果
        Map<String, Map<String, Integer>> statistics = new LinkedHashMap<>();
        for (String ageLabel : ageLabels) {
            Map<String, Integer> heartRateStats = new LinkedHashMap<>();
            for (String heartRateLabel : heartRateLabels) {
                heartRateStats.put(heartRateLabel, 0);
            }
            statistics.put(ageLabel, heartRateStats);
        }

        // 统计数据
        for (CentreBottomVo person : collect) {
            String ageGroup = getAgeGroup(person.getAge(), ageBounds, ageLabels);
            String heartRateGroup = getHeartRateGroup(person.getHr(), heartRateBounds, heartRateLabels);
            Map<String, Integer> heartRateStats = statistics.get(ageGroup);
            heartRateStats.put(heartRateGroup, heartRateStats.get(heartRateGroup) + 1);
        }


        return AjaxResult.success(statistics);
    }

    private static String getAgeGroup(int age, int[] bounds, String[] labels) {
        for (int i = 0; i < bounds.length - 1; i++) {
            if (age >= bounds[i] && age < bounds[i + 1]) {
                return labels[i];
            }
        }
        return labels[labels.length - 1];
    }

    private static String getHeartRateGroup(int heartRate, int[] bounds, String[] labels) {
        for (int i = 0; i < bounds.length - 1; i++) {
            if (heartRate >= bounds[i] && heartRate < bounds[i + 1]) {
                return labels[i];
            }
        }
        return labels[labels.length - 1];
    }
}
