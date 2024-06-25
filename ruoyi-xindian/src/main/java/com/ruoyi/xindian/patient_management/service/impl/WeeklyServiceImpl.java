package com.ruoyi.xindian.patient_management.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.ruoyi.xindian.patient.service.SingleHistoryDataService;
import com.ruoyi.xindian.patient_management.domain.WeeklyCount;
import com.ruoyi.xindian.patient_management.service.IWeeklyService;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.pmEcgData.mapper.PmEcgDataMapper;
import com.ruoyi.xindian.pmEcgData.service.IPmEcgDataService;
import com.ruoyi.xindian.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class WeeklyServiceImpl implements IWeeklyService {

    @Autowired
    private IPmEcgDataService pmEcgDataService;

    @Resource
    private SingleHistoryDataService singleHistoryDataService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean allZero(SingleHistoryData singleHistoryData) {
        return singleHistoryData.getApBeat().equals(0) &&
                singleHistoryData.getPvBeat().equals(0) &&
                singleHistoryData.getAtrialFlutter().equals(0) &&
                singleHistoryData.getAtrialFibrillation().equals(0) &&
                singleHistoryData.getLeftBlock().equals(0) &&
                singleHistoryData.getRightBlock().equals(0) &&
                singleHistoryData.getBradycardia().equals(0) &&
                singleHistoryData.getTachycardia().equals(0);
    }

    @Override
    public WeeklyCount SingleHistoryDataToweeklyCount(WeeklyCount weeklyCount, SingleHistoryData singleHistoryData) {
        weeklyCount.setMonday(0);
        weeklyCount.setTuesday(0);
        weeklyCount.setWednesday(0);
        weeklyCount.setThursday(0);
        weeklyCount.setFriday(0);
        weeklyCount.setSaturday(0);
        weeklyCount.setSaturday(0);
//        weeklyCount.setMonday_hr(new LinkedList<>());
//        weeklyCount.setTuesday_hr(new LinkedList<>());
//        weeklyCount.setWednesday_hr(new LinkedList<>());
//        weeklyCount.setThursday_hr(new LinkedList<>());
//        weeklyCount.setFriday_hr(new LinkedList<>());
//        weeklyCount.setSaturday_hr(new LinkedList<>());
//        weeklyCount.setSaturday_hr(new LinkedList<>());
        weeklyCount.setPatientPhone(singleHistoryData.getPatientPhone());
        weeklyCount.setNormalEcg(singleHistoryData.getNormalEcg());
        weeklyCount.setApBeat(singleHistoryData.getApBeat());
        weeklyCount.setPvBeat(singleHistoryData.getPvBeat());
        weeklyCount.setAtrialFibrillation(singleHistoryData.getAtrialFibrillation());
        weeklyCount.setAtrialFlutter(singleHistoryData.getAtrialFlutter());
        weeklyCount.setLeftBlock(singleHistoryData.getLeftBlock());
        weeklyCount.setRightBlock(singleHistoryData.getRightBlock());
        weeklyCount.setBradycardia(singleHistoryData.getBradycardia());
        weeklyCount.setTachycardia(singleHistoryData.getTachycardia());
        return weeklyCount;
    }

    @Override
    public WeeklyCount weeklyCountAddsingleHistoryData(WeeklyCount weeklyCount, SingleHistoryData singleHistoryData) {
        weeklyCount.setNormalEcg(weeklyCount.getNormalEcg() + singleHistoryData.getNormalEcg());
        weeklyCount.setApBeat(weeklyCount.getApBeat() + singleHistoryData.getApBeat());
        weeklyCount.setPvBeat(weeklyCount.getPvBeat() + singleHistoryData.getPvBeat());
        weeklyCount.setAtrialFibrillation(weeklyCount.getAtrialFibrillation() + singleHistoryData.getAtrialFibrillation());
        weeklyCount.setAtrialFlutter(weeklyCount.getAtrialFlutter() + singleHistoryData.getAtrialFlutter());
        weeklyCount.setLeftBlock(weeklyCount.getLeftBlock() + singleHistoryData.getLeftBlock());
        weeklyCount.setRightBlock(weeklyCount.getRightBlock() + singleHistoryData.getRightBlock());
        weeklyCount.setBradycardia(weeklyCount.getBradycardia() + singleHistoryData.getBradycardia());
        weeklyCount.setTachycardia(weeklyCount.getTachycardia() + singleHistoryData.getTachycardia());
        return weeklyCount;
    }

    @Override
    public void getWeekly(SingleHistoryData info) {
        HashMap<String, WeeklyCount> weeklyCountHashMap = new HashMap<>();
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        HashOperations<String, String, WeeklyCount> hash = redisTemplate.opsForHash();

        //thisweek换到lastweek
        Boolean exists_thisWeek = redisTemplate.hasKey("thisWeek");
        Boolean exists_lastWeek = redisTemplate.hasKey("lastWeek");
        if (exists_thisWeek != null && exists_thisWeek) {
            HashMap<String, WeeklyCount> thisweeklyCountHashMap = new HashMap<>();
            Set<String> week = hash.keys("thisWeek");
            for (String str : week) {
                WeeklyCount thisWeek = hash.get("thisWeek", str);
                thisweeklyCountHashMap.put(str, thisWeek);
            }
            if (exists_lastWeek != null && exists_lastWeek)
                hash.entries("lastWeek").clear();
            hash.putAll("lastWeek", thisweeklyCountHashMap);
            hash.entries("thisWeek").clear();
        }
        //0为正常，1为异常
        int flag = 1;
        //总数据概括
        List<SingleHistoryData> weeklyCountList = singleHistoryDataService.countDataByPhone(info);
        WeeklyCount weeklyCount;
        for (SingleHistoryData singleHistoryData : weeklyCountList) {
            if (!singleHistoryData.getNormalEcg().equals(0) || allZero(singleHistoryData)) {
                flag = 0;
            }
            String mapKey = singleHistoryData.getPatientPhone();
            if (weeklyCountHashMap.containsKey(mapKey)) {
                weeklyCount = weeklyCountHashMap.get(mapKey);
                weeklyCount = weeklyCountAddsingleHistoryData(weeklyCount, singleHistoryData);
                weeklyCount.setSignalCount(weeklyCount.getSignalCount() + 1);
                if (flag == 0)
                    weeklyCount.setNormalCount(weeklyCount.getNormalCount() + 1);
            } else {
                weeklyCount = new WeeklyCount();
                if (flag == 0)
                    weeklyCount.setNormalCount(1);
                else
                    weeklyCount.setNormalCount(0);
                weeklyCount.setSignalCount(1);
                weeklyCount = SingleHistoryDataToweeklyCount(weeklyCount, singleHistoryData);
            }
            //获取心率数据
            PmEcgData pmEcgData = pmEcgDataService.selectPmEcgDataByPId(singleHistoryData.getPId());
            String ecgAnalysisData = pmEcgData.getEcgAnalysisData();
            JsonObject ecgData = JsonParser.parseString(ecgAnalysisData).getAsJsonObject();
            int hr = ecgData.get("平均心率").getAsInt();
            //计算当时是第几天做的
            List<Integer> weekday_hr;
            int week = DateUtil.getWeek(singleHistoryData.getCreateTime());
            switch (week) {
                case 1:
                    weeklyCount.setSunday(weeklyCount.getSunday() + 1);
                    weekday_hr = weeklyCount.getSunday_hr();
                    if (weekday_hr == null)
                        weekday_hr = new LinkedList<>();
                    weekday_hr.add(hr);
                    weeklyCount.setSaturday_hr(weekday_hr);
                    break;
                case 2:
                    weeklyCount.setMonday(weeklyCount.getMonday() + 1);
                    weekday_hr = weeklyCount.getMonday_hr();
                    if (weekday_hr == null)
                        weekday_hr = new LinkedList<>();
                    weekday_hr.add(hr);
                    weeklyCount.setMonday_hr(weekday_hr);
                    break;
                case 3:
                    weeklyCount.setTuesday(weeklyCount.getTuesday() + 1);
                    weekday_hr = weeklyCount.getThursday_hr();
                    if (weekday_hr == null)
                        weekday_hr = new LinkedList<>();
                    weekday_hr.add(hr);
                    weeklyCount.setThursday_hr(weekday_hr);
                    break;
                case 4:
                    weeklyCount.setWednesday(weeklyCount.getWednesday() + 1);
                    weekday_hr = weeklyCount.getWednesday_hr();
                    if (weekday_hr == null)
                        weekday_hr = new LinkedList<>();
                    weekday_hr.add(hr);
                    weeklyCount.setWednesday_hr(weekday_hr);
                    break;
                case 5:
                    weeklyCount.setThursday(weeklyCount.getThursday() + 1);
                    weekday_hr = weeklyCount.getThursday_hr();
                    if (weekday_hr == null)
                        weekday_hr = new LinkedList<>();
                    weekday_hr.add(hr);
                    weeklyCount.setThursday_hr(weekday_hr);
                    break;
                case 6:
                    weeklyCount.setFriday(weeklyCount.getFriday() + 1);
                    weekday_hr = weeklyCount.getFriday_hr();
                    if (weekday_hr == null)
                        weekday_hr = new LinkedList<>();
                    weekday_hr.add(hr);
                    weeklyCount.setFriday_hr(weekday_hr);
                    break;
                case 7:
                    weeklyCount.setSaturday(weeklyCount.getSaturday() + 1);
                    weekday_hr = weeklyCount.getSaturday_hr();
                    if (weekday_hr == null)
                        weekday_hr = new LinkedList<>();
                    weekday_hr.add(hr);
                    weeklyCount.setSaturday_hr(weekday_hr);
                    break;
            }
            weeklyCountHashMap.put(mapKey, weeklyCount);
        }
        //检测次数排名
        weeklyCountHashMap.forEach((key, value) -> {
            int thisWeekCheckNumScore = value.getSignalCount();
            double thisWeekHealthScore = 0;
            if (thisWeekCheckNumScore != 0)
                thisWeekHealthScore = value.getNormalCount() * 1.0 / thisWeekCheckNumScore;
            zSet.add("thisWeekCheckNum", key, thisWeekCheckNumScore);
            zSet.add("thisWeekHealth", key, thisWeekHealthScore);
        });
        hash.putAll("thisWeek", weeklyCountHashMap);
    }

    @Override
    public HashMap<String, Object> getWeeklyByPhone(String patientPhone) {
        HashMap<String, Object> res = new HashMap<>();
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        HashOperations<String, String, WeeklyCount> hash = redisTemplate.opsForHash();
        WeeklyCount thisWeek = hash.get("thisWeek", patientPhone);
        if(thisWeek==null) return res;
        WeeklyCount lastWeek = hash.get("lastWeek", patientPhone);
        Long allPerson = zSet.size("thisWeekCheckNum");
        //检测次数排名
        double rankCheckNum = 0.0;
        double rankHealth = 0.0;
        Long thisWeekCheckNum = zSet.rank("thisWeekCheckNum", patientPhone);
        if (thisWeekCheckNum != null && allPerson != null && allPerson > 0)
            rankCheckNum = thisWeekCheckNum * 1.0 / allPerson;
        //健康排名
        Long thisWeekHealth = zSet.rank("thisWeekHealth", patientPhone);
        if (thisWeekHealth != null && allPerson != null && allPerson > 0)
            rankHealth = thisWeekHealth * 1.0 / allPerson;

        //检测时长同比
        double tb = 0.0;
        if (lastWeek != null)
            if(lastWeek.getSignalCount()==0)
                tb=-1; //无法计算
            else
                tb = (thisWeek.getSignalCount() - lastWeek.getSignalCount()) * 1.0 / lastWeek.getSignalCount();
        LinkedList<Double> weekTimeList = new LinkedList<>();
        weekTimeList.add(thisWeek.getMonday()*30*1.0/60);
        weekTimeList.add(thisWeek.getTuesday()*30*1.0/60);
        weekTimeList.add(thisWeek.getWednesday()*30*1.0/60);
        weekTimeList.add(thisWeek.getThursday()*30*1.0/60);
        weekTimeList.add(thisWeek.getFriday()*30*1.0/60);
        weekTimeList.add(thisWeek.getSaturday()*30*1.0/60);
        weekTimeList.add(thisWeek.getSaturday()*30*1.0/60);
        if(thisWeek.getMonday_hr()==null) thisWeek.setMonday_hr(new LinkedList<>());
        if(thisWeek.getTuesday_hr()==null) thisWeek.setTuesday_hr(new LinkedList<>());
        if(thisWeek.getWednesday_hr()==null) thisWeek.setWednesday_hr(new LinkedList<>());
        if(thisWeek.getThursday_hr()==null) thisWeek.setThursday_hr(new LinkedList<>());
        if(thisWeek.getFriday_hr()==null) thisWeek.setFriday_hr(new LinkedList<>());
        if(thisWeek.getSaturday_hr()==null) thisWeek.setSaturday_hr(new LinkedList<>());
        if(thisWeek.getSunday_hr()==null) thisWeek.setSunday_hr(new LinkedList<>());
        thisWeek.setPatientPhone("");
        res.put("thisWeek", thisWeek);
        res.put("rankDetectionNum", rankCheckNum*100);
        res.put("rankHealth", rankHealth*100);
        res.put("increase",tb*100);
        res.put("detectionTime",thisWeek.getSignalCount()*30*1.0/60);
        res.put("weekTimeList",weekTimeList);
        return res;
    }

}
