package com.ruoyi.xindian.patient_management.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.ruoyi.xindian.patient.service.SingleHistoryDataService;
import com.ruoyi.xindian.patient_management.domain.ECGTendency;
import com.ruoyi.xindian.patient_management.domain.WeeklyCount;
import com.ruoyi.xindian.patient_management.service.IWeeklyService;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.pmEcgData.mapper.PmEcgDataMapper;
import com.ruoyi.xindian.pmEcgData.service.IPmEcgDataService;
import com.ruoyi.xindian.util.DateUtil;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
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
        ECGTendency mon = new ECGTendency();
        ECGTendency Tus = new ECGTendency();
        ECGTendency Wed = new ECGTendency();
        ECGTendency Thur = new ECGTendency();
        ECGTendency Fri = new ECGTendency();
        ECGTendency Sat = new ECGTendency();
        ECGTendency Sun = new ECGTendency();
        weeklyCount.setMonday(mon);
        weeklyCount.setTuesday(Tus);
        weeklyCount.setWednesday(Wed);
        weeklyCount.setThursday(Thur);
        weeklyCount.setFriday(Fri);
        weeklyCount.setSaturday(Sat);
        weeklyCount.setSaturday(Sun);

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
        //String lastWeekTime = info.getStartTime();
        //lastWeekTime = DateUtil.subDay(lastWeekTime);
        String thisWeekTime = info.getEndTime();

//        Boolean exists_thisWeek = redisTemplate.hasKey("thisWeek");
//        Boolean exists_lastWeek = redisTemplate.hasKey("lastWeek");
//        if (exists_thisWeek != null && exists_thisWeek) {
//            HashMap<String, WeeklyCount> thisweeklyCountHashMap = new HashMap<>();
//            Set<String> week = hash.keys("thisWeek");
//            for (String str : week) {
//                WeeklyCount thisWeek = hash.get("thisWeek", str);
//                thisweeklyCountHashMap.put(str, thisWeek);
//            }
//            if (exists_lastWeek != null && exists_lastWeek)
//                hash.entries("lastWeek").clear();
//            hash.putAll("lastWeek", thisweeklyCountHashMap);
//            hash.entries("thisWeek").clear();
//        }
        //0为正常，1为异常
        int flag = 1;
        //总数据概括
        List<SingleHistoryData> weeklyCountList = singleHistoryDataService.countDataByPhone(info);
        WeeklyCount weeklyCount;

        for (SingleHistoryData singleHistoryData : weeklyCountList) {
            flag = 1;
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
            //获取心率，P波，QRS等信息
            PmEcgData pmEcgData = pmEcgDataService.selectPmEcgDataByPId(singleHistoryData.getPId());
            String ecgAnalysisData = pmEcgData.getEcgAnalysisData();
            JsonObject ecgData = JsonParser.parseString(ecgAnalysisData).getAsJsonObject();

            double hr = ecgData.get("平均心率").getAsString().equals("nan") ? 0 : ecgData.get("平均心率").getAsDouble();
            double P = ecgData.get("P波时限").getAsString().equals("nan") ? 0 : ecgData.get("P波时限").getAsDouble();
            double PR = ecgData.get("PR间期").getAsString().equals("nan") ? 0 : ecgData.get("PR间期").getAsDouble();
            double QRS = ecgData.get("QRS波时限").getAsString().equals("nan") ? 0 : ecgData.get("QRS波时限").getAsDouble();
            double QTC = ecgData.get("QTc").getAsString().equals("nan") ? 0 : ecgData.get("QTc").getAsDouble();
            double SDNN = ecgData.get("SDNN").getAsString().equals("nan") ? 0 : ecgData.get("SDNN").getAsDouble();
            double RMSSD = ecgData.get("RMSSD").getAsString().equals("nan") ? 0 : ecgData.get("RMSSD").getAsDouble();

            //计算当时是第几天做的
            int week = DateUtil.getWeek(singleHistoryData.getCreateTime());
            weeklyCount = weeklyCountAddData(weeklyCount, hr, P, PR, QRS, QTC, SDNN, RMSSD, week);
            weeklyCountHashMap.put(mapKey, weeklyCount);
        }
        //检测次数排名
        weeklyCountHashMap.forEach((key, value) -> {
            int thisWeekCheckNumScore = value.getSignalCount();
            double thisWeekHealthScore = 0;
            if (thisWeekCheckNumScore != 0)
                thisWeekHealthScore = value.getNormalCount() * 1.0 / thisWeekCheckNumScore;
            zSet.add("WeekCheckNum_" + thisWeekTime, key, thisWeekCheckNumScore);
            zSet.add("thisWeekHealth_" + thisWeekTime, key, thisWeekHealthScore);
        });
        hash.putAll("week_" + thisWeekTime, weeklyCountHashMap);
    }

    @Override
    public HashMap<String, Object> getWeeklyByPhone(SingleHistoryData Info) {
        String endTime = Info.getEndTime();
        String lastSunday = DateUtil.getLastSunday(endTime);
        String lastDoubleSunday = DateUtil.getLastSunday(lastSunday);
        String patientPhone = Info.getPatientPhone();

        System.out.println(lastSunday);
        System.out.println(lastDoubleSunday);
        System.out.println(patientPhone);

        HashMap<String, Object> res = new HashMap<>();
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        HashOperations<String, String, WeeklyCount> hash = redisTemplate.opsForHash();
        WeeklyCount thisWeek = hash.get("week_" + lastSunday, patientPhone);
        if (thisWeek == null) return res;
        WeeklyCount lastWeek = hash.get("week_" + lastDoubleSunday, patientPhone);
        Long allPerson = zSet.size("thisWeekCheckNum_" + lastSunday);
        //健康统计占比
        int thisWeekSum = thisWeek.getSignalCount();
        int lastWeekSum = 0;
        //房早
        int thisWeekAPBeat = thisWeek.getApBeat();
        int lastWeekAPBeat = 0;
        //室早
        int thisWeekPVBeat = thisWeek.getPvBeat();
        int lastWeekPVBeat = 0;
        //心房颤动
        int thisWeekAF = thisWeek.getAtrialFibrillation();
        int lastWeekAF = 0;
        //心房扑动
        int thisWeekAFp = thisWeek.getAtrialFlutter();
        int lastWeekAFp = 0;
        //心动过缓
        int thisWeekB = thisWeek.getBradycardia();
        int lastWeekB = 0;
        //心动过速度
        int thisWeekT = thisWeek.getTachycardia();
        int lastWeekT = 0;
        if (lastWeek != null) {
            lastWeekSum = lastWeek.getSignalCount();
            lastWeekAPBeat = lastWeek.getApBeat();
            lastWeekPVBeat = lastWeek.getPvBeat();
            lastWeekAF = lastWeek.getAtrialFibrillation();
            lastWeekAFp = lastWeek.getAtrialFlutter();
            lastWeekB = lastWeek.getBradycardia();
            lastWeekT = lastWeek.getTachycardia();
        }
        if (thisWeekSum != 0) {
            thisWeek.setApBeatPercentage(thisWeekAPBeat * 1.0 / thisWeekSum);
            thisWeek.setPvBeatPercentage(thisWeekPVBeat * 1.0 / thisWeekSum);
            thisWeek.setAtrialFibrillationPercentage(thisWeekAF * 1.0 / thisWeekSum);
            thisWeek.setAtrialFlutterPercentage(thisWeekAFp * 1.0 / thisWeekSum);
            thisWeek.setBradycardiaPercentage(thisWeekB * 1.0 / thisWeekSum);
            thisWeek.setTachycardiaPercentage(thisWeekT * 1.0 / thisWeekSum);
        } else {
            thisWeek.setApBeatPercentage(0.0);
            thisWeek.setPvBeatPercentage(0.0);
            thisWeek.setAtrialFibrillationPercentage(0.0);
            thisWeek.setAtrialFlutterPercentage(0.0);
            thisWeek.setBradycardiaPercentage(0.0);
            thisWeek.setTachycardiaPercentage(0.0);
        }
        if (lastWeekSum != 0) {
            lastWeek.setApBeatPercentage(lastWeekAPBeat * 1.0 / lastWeekSum);
            lastWeek.setPvBeatPercentage(lastWeekPVBeat * 1.0 / lastWeekSum);
            lastWeek.setAtrialFibrillationPercentage(lastWeekAF * 1.0 / lastWeekSum);
            lastWeek.setAtrialFlutterPercentage(lastWeekAFp * 1.0 / lastWeekSum);
            lastWeek.setBradycardiaPercentage(lastWeekB * 1.0 / lastWeekSum);
            lastWeek.setTachycardiaPercentage(lastWeekT * 1.0 / lastWeekSum);
        } else if (lastWeek != null) {
            lastWeek.setApBeatPercentage(0.0);
            lastWeek.setPvBeatPercentage(0.0);
            lastWeek.setAtrialFibrillationPercentage(0.0);
            lastWeek.setAtrialFlutterPercentage(0.0);
            lastWeek.setBradycardiaPercentage(0.0);
            lastWeek.setTachycardiaPercentage(0.0);
        }

        if (lastWeek != null) {
            thisWeek.setApBeatPercentageS(thisWeek.getApBeatPercentage() - lastWeek.getApBeatPercentage());
            thisWeek.setPvBeatPercentageS(thisWeek.getPvBeatPercentage() - lastWeek.getPvBeatPercentage());
            thisWeek.setAtrialFibrillationPercentageS(thisWeek.getAtrialFibrillationPercentage() - lastWeek.getAtrialFibrillationPercentage());
            thisWeek.setAtrialFlutterPercentageS(thisWeek.getAtrialFlutterPercentage() - lastWeek.getAtrialFlutterPercentage());
            thisWeek.setBradycardiaPercentageS(thisWeek.getBradycardiaPercentage() - lastWeek.getBradycardiaPercentage());
            thisWeek.setTachycardiaPercentageS(thisWeek.getTachycardiaPercentage() - lastWeek.getTachycardiaPercentage());
        } else {
            thisWeek.setApBeatPercentageS(thisWeek.getApBeatPercentage());
            thisWeek.setPvBeatPercentageS(thisWeek.getPvBeatPercentage());
            thisWeek.setAtrialFibrillationPercentageS(thisWeek.getAtrialFibrillationPercentage());
            thisWeek.setAtrialFlutterPercentageS(thisWeek.getAtrialFlutterPercentage());
            thisWeek.setBradycardiaPercentageS(thisWeek.getBradycardiaPercentage());
            thisWeek.setTachycardiaPercentageS(thisWeek.getTachycardiaPercentage());
        }

        //检测次数排名
        double rankCheckNum = 0.0;
        double rankHealth = 0.0;
        Long thisWeekCheckNum = zSet.rank("thisWeekCheckNum_" + lastSunday, patientPhone);
        if (thisWeekCheckNum != null && allPerson != null && allPerson > 0)
            rankCheckNum = thisWeekCheckNum * 1.0 / allPerson;
        //健康排名
        Long thisWeekHealth = zSet.rank("thisWeekHealth_" + lastSunday, patientPhone);
        if (thisWeekHealth != null && allPerson != null && allPerson > 0)
            rankHealth = thisWeekHealth * 1.0 / allPerson;

        //检测时长同比
        double tb = 0.0;
        if (lastWeek != null)
            if (lastWeek.getSignalCount() == 0)
                tb = -1; //无法计算
            else
                tb = (thisWeek.getSignalCount() - lastWeek.getSignalCount()) * 1.0 / lastWeek.getSignalCount();
        LinkedList<Double> weekTimeList = new LinkedList<>();

        if (thisWeek.getMonday() == null) thisWeek.setMonday(new ECGTendency());
        if (thisWeek.getTuesday() == null) thisWeek.setTuesday(new ECGTendency());
        if (thisWeek.getWednesday() == null) thisWeek.setWednesday(new ECGTendency());
        if (thisWeek.getThursday() == null) thisWeek.setThursday(new ECGTendency());
        if (thisWeek.getFriday() == null) thisWeek.setFriday(new ECGTendency());
        if (thisWeek.getSaturday() == null) thisWeek.setSaturday(new ECGTendency());
        if (thisWeek.getSunday() == null) thisWeek.setSunday(new ECGTendency());

        weekTimeList.add(thisWeek.getMonday().getCount() * 30 * 1.0 / 60);
        weekTimeList.add(thisWeek.getTuesday().getCount() * 30 * 1.0 / 60);
        weekTimeList.add(thisWeek.getWednesday().getCount() * 30 * 1.0 / 60);
        weekTimeList.add(thisWeek.getThursday().getCount() * 30 * 1.0 / 60);
        weekTimeList.add(thisWeek.getFriday().getCount() * 30 * 1.0 / 60);
        weekTimeList.add(thisWeek.getSaturday().getCount() * 30 * 1.0 / 60);
        weekTimeList.add(thisWeek.getSunday().getCount() * 30 * 1.0 / 60);

        thisWeek.setPatientPhone("");
        res.put("thisWeek", thisWeek);
        res.put("rankDetectionNum", rankCheckNum * 100);
        res.put("rankHealth", rankHealth * 100);
        res.put("increase", tb * 100);
        res.put("detectionTime", thisWeek.getSignalCount() * 30 * 1.0 / 60);
        res.put("weekTimeList", weekTimeList);
        return res;
    }

    @Override
    public WeeklyCount weeklyCountAddData(WeeklyCount weeklyCount, double hr, double P, double PR, double QRS, double QTC, double SDNN, double RMSSD, int week) {
        ECGTendency day;
        switch (week) {
            case 1:
                day = weeklyCount.getSunday();
                break;
            case 2:
                day = weeklyCount.getMonday();
                break;
            case 3:
                day = weeklyCount.getTuesday();
                break;
            case 4:
                day = weeklyCount.getWednesday();
                break;
            case 5:
                day = weeklyCount.getThursday();
                break;
            case 6:
                day = weeklyCount.getFriday();
                break;
            default:
                day = weeklyCount.getSaturday();
        }
        if (day == null)
            day = new ECGTendency();
        day.setCount(day.getCount() + 1);
        if (P != 0) {
            List<Double> p = day.getP();
            if (p == null)
                p = new LinkedList<>();
            p.add(P);
            day.setP(p);
        }
        if (hr != 0) {
            List<Double> Hr = day.getHr();
            if (Hr == null)
                Hr = new LinkedList<>();
            Hr.add(hr);
            day.setHr(Hr);
        }
        if (PR != 0) {
            List<Double> pr = day.getPR();
            if (pr == null)
                pr = new LinkedList<>();
            pr.add(PR);
            day.setPR(pr);
        }
        if (QRS != 0) {
            List<Double> qrs = day.getQRS();
            if (qrs == null)
                qrs = new LinkedList<>();
            qrs.add(QRS);
            day.setQRS(qrs);
        }
        if (QTC != 0) {
            List<Double> qtc = day.getQTC();
            if (qtc == null)
                qtc = new LinkedList<>();
            qtc.add(QTC);
            day.setQTC(qtc);
        }
        if (SDNN != 0) {
            List<Double> sdnn = day.getSDNN();
            if (sdnn == null)
                sdnn = new LinkedList<>();
            sdnn.add(SDNN);
            day.setSDNN(sdnn);
        }
        if (RMSSD != 0) {
            List<Double> rmssd = day.getRMSSD();
            if (rmssd == null)
                rmssd = new LinkedList<>();
            rmssd.add(RMSSD);
            day.setRMSSD(rmssd);
        }
        switch (week) {
            case 1:
                weeklyCount.setSunday(day);
                break;
            case 2:
                weeklyCount.setMonday(day);
                break;
            case 3:
                weeklyCount.setTuesday(day);
                break;
            case 4:
                weeklyCount.setWednesday(day);
                break;
            case 5:
                weeklyCount.setThursday(day);
                break;
            case 6:
                weeklyCount.setFriday(day);
                break;
            default:
                weeklyCount.setSaturday(day);
        }
        return weeklyCount;
    }

    @Override
    public HashMap<String, Object> getHistoryWeekly(SingleHistoryData info) {
        return new HashMap<>();
    }

}
