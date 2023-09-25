package com.ruoyi.xindian.alert_log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.alert_log.domain.AlertLogJsonData;
import com.ruoyi.xindian.alert_log.service.AlertLogJsonDataService;
import com.ruoyi.xindian.alert_log.mapper.AlertLogJsonDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
        return alertLogJsonDataMapper.selectOne(new QueryWrapper<AlertLogJsonData>().eq("log_id",alertLogJsonData.getLogId()).eq("user_id",alertLogJsonData.getUserId()));
    }

    @Override
    public Boolean updateOrInsertAlertLogJsonData(AlertLogJsonData alertLogJsonData) {
        Date date = new Date();
        AlertLogJsonData alertLogJsonData1 = selectJsonData(alertLogJsonData);
        //判断是否已经存在相同的数据，如果有，则修改，没有则添加
        if (alertLogJsonData1==null){

            alertLogJsonData.setCreateTime(date);
            alertLogJsonData.setUpdateTime(date);
            return alertLogJsonDataMapper.insert(alertLogJsonData)>0;
        }
        JSONObject parse =(JSONObject) JSONObject.parse(alertLogJsonData.getJsonData());
        JSONObject jsonData =(JSONObject) JSONObject.parse(alertLogJsonData1.getJsonData());
        Object o = parse.get(alertLogJsonData.getType());
        jsonData.remove(alertLogJsonData.getType());
        jsonData.put(alertLogJsonData.getType(),o);
        alertLogJsonData1.setJsonData(JSONObject.toJSONString(jsonData));
        alertLogJsonData1.setUpdateTime(date);
        return alertLogJsonDataMapper.update(alertLogJsonData1, new QueryWrapper<AlertLogJsonData>().eq("alert_log_id", alertLogJsonData1.getAlertLogId()))>0;
    }
}




