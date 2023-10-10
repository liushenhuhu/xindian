package com.ruoyi.xindian.alert_log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.xindian.alert_log.domain.AlertLogJsonData;
import com.ruoyi.xindian.alert_log.service.AlertLogJsonDataService;
import com.ruoyi.xindian.alert_log.mapper.AlertLogJsonDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 13401
* @description 针对表【alert_log_json_data】的数据库操作Service实现
* @createDate 2023-09-23 11:41:54
*/
@Service
public class AlertLogJsonDataServiceImpl extends ServiceImpl<AlertLogJsonDataMapper, AlertLogJsonData>
    implements AlertLogJsonDataService{


    @Resource
    private AlertLogJsonDataMapper alertLogJsonDataMapper;

    @Override
    public AlertLogJsonData selectJsonData(AlertLogJsonData alertLogJsonData) {
        Long userId = SecurityUtils.getUserId();

        List<AlertLogJsonData> alertLogJsonData1 = alertLogJsonDataMapper.selectList(new QueryWrapper<AlertLogJsonData>().eq("log_id", alertLogJsonData.getLogId()).eq(userId != 1, "user_id", userId));
        if(alertLogJsonData1!=null&&alertLogJsonData1.size()>0){
            return alertLogJsonData1.get(0);
        }
        return null;
    }
    @Override
    public Boolean updateOrInsertAlertLogJsonData(AlertLogJsonData alertLogJsonData) {
        Long userId = SecurityUtils.getUserId();

        AlertLogJsonData alertLogJsonData1 = selectJsonData(alertLogJsonData);
        //判断是否已经存在相同的数据，如果有，则修改，没有则添加
        if (alertLogJsonData1==null){
            return false;
        }
        alertLogJsonData1.setAnoStatus(1);
        alertLogJsonData1.setDataLabel(alertLogJsonData.getDataLabel());

        return alertLogJsonDataMapper.update(alertLogJsonData1, new QueryWrapper<AlertLogJsonData>().eq("log_id", alertLogJsonData1.getLogId()).eq(userId != 1,"user_id", userId))>0;
    }
//    @Override
//    public Boolean updateOrInsertAlertLogJsonData(AlertLogJsonData alertLogJsonData) {
//        Date date = new Date();
//        AlertLogJsonData alertLogJsonData1 = selectJsonData(alertLogJsonData);
//        //判断是否已经存在相同的数据，如果有，则修改，没有则添加
//        if (alertLogJsonData1==null){
//
//            alertLogJsonData.setUpdateTime(date);
//            return alertLogJsonDataMapper.insert(alertLogJsonData)>0;
//        }
//        JSONObject parse =(JSONObject) JSONObject.parse(alertLogJsonData.getWaveLabel());
//        JSONObject jsonData =(JSONObject) JSONObject.parse(alertLogJsonData1.getWaveLabel());
//        Object o = parse.get(alertLogJsonData.getType());
//        jsonData.remove(alertLogJsonData.getType());
//        jsonData.put(alertLogJsonData.getType(),o);
//        alertLogJsonData1.setWaveLabel(JSONObject.toJSONString(jsonData));
//        alertLogJsonData1.setUpdateTime(date);
//        return alertLogJsonDataMapper.update(alertLogJsonData1, new QueryWrapper<AlertLogJsonData>().eq("log_id", alertLogJsonData1.getLogId()))>0;
//    }
}




