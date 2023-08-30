package com.ruoyi.xindian.patient_management.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xindian.log.util.InfluxdbUtils;
import com.ruoyi.xindian.patient_management.domain.Mark;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lixinlong
 * 2023/8/29 10:35
 */
@RestController
@RequestMapping("/mark")
public class MarkController extends BaseController {

    @Autowired
    private InfluxDB influxDB;

    @Resource
    private InfluxdbUtils influxdbUtils;

    @PostMapping("/addStatic")
    public AjaxResult addStatic(@RequestBody Mark mark){
        String state = "1";
        mark.setpId(mark.getpId()+"-"+mark.getLevel());
        //先查询，是否存在相同数据  Tag='pId'
        List<Mark> markList = checkMark(mark.getpId());
        //System.out.println("pId  ============= "+pId);
        if(markList.size() > 0){
            state = deleteMark(markList.get(0).getpId());
        }
        if(state.equals("success") || state.equals("1")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mark.setDate(sdf.format(new Date()));
            System.out.println(mark);
            List<Mark> list = new ArrayList<Mark>();
            list.add(mark);
            influxdbUtils.insertBatchByPoints(list,mark.getpId());
            return AjaxResult.success(1);
        }
        return AjaxResult.error("内部异常，请联系管理员!");
    }

    @PostMapping("/checkMarkByTagId")
    public List<Mark> checkMarkByTagId(@RequestBody Mark mark) {
        String tagId = mark.getpId()+"-"+mark.getLevel();
        String queryCondition = " where \"pId\" = '"+tagId+"'";  //查询条件
        // 此处查询所有内容,如果
        String queryCmd = "SELECT * FROM ecg_mark"
                // 添加查询条件(注意查询条件选择tag值,选择field数值会严重拖慢查询速度)
                + queryCondition
                // 查询结果需要按照时间排序
                + " ORDER BY time DESC";
        //System.out.println(queryCmd);
        List<Mark> sensorList = influxdbUtils.fetchResults(queryCmd, Mark.class);
        //List<Sensor> sensorList = influxdbUtils.fetchResults("*", "sensor", Sensor.class);
        sensorList.forEach(sensor -> {
            System.out.println("query sensor => {}" + sensor);
        });
        return sensorList;
    }

    /**
     * 删除指定数据
     * @param pId
     * @return
     */
    public String deleteMark(String pId){
        String queryCondition = " where \"pId\" = '"+pId+"'";
        // 此处删除语句
        String queryCmd = "delete FROM ecg_mark" + queryCondition;
        QueryResult queryResult = influxDB.query(new Query(queryCmd, "ecg_mark"));
        if(StringUtils.isEmpty(queryResult.getError())){
            return "success";
        }else {
            return "fail";
        }
    }


    public List<Mark> checkMark(String pId) {
        String queryCondition = " where \"pId\" = '"+pId+"'";  //查询条件
        // 此处查询所有内容,如果
        String queryCmd = "SELECT * FROM ecg_mark"
                // 添加查询条件(注意查询条件选择tag值,选择field数值会严重拖慢查询速度)
                + queryCondition
                // 查询结果需要按照时间排序
                + " ORDER BY time DESC";
        //System.out.println(queryCmd);
        List<Mark> sensorList = influxdbUtils.fetchResults(queryCmd, Mark.class);
        //List<Sensor> sensorList = influxdbUtils.fetchResults("*", "sensor", Sensor.class);
        sensorList.forEach(sensor -> {
            System.out.println("query sensor => {}" + sensor);
        });
        return sensorList;
    }


}
