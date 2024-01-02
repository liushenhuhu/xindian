package com.ruoyi.xindian.alert_log.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.mapper.PatientManagementMapper;
import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.alert_log.mapper.AlertLogMapper;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.alert_log.service.IAlertLogService;

import javax.annotation.Resource;

/**
 * 预警日志Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Service
public class AlertLogServiceImpl implements IAlertLogService
{
    @Autowired
    private AlertLogMapper alertLogMapper;


    @Autowired
    private PatientManagementMapper patientManagementMapper;



    @Autowired
    private PatientMapper patientMapper;


    @Resource
    private RedisTemplate<String, AlertLog> redisTemplate;
    /**
     * 查询预警日志
     *
     * @param logId 预警日志主键
     * @return 预警日志
     */
    @Override
    public AlertLog selectAlertLogByLogId(String logId)
    {
        return alertLogMapper.selectAlertLogByLogId(logId);
    }

    /**
     * 查询预警日志列表
     *
     * @param alertLog 预警日志
     * @return 预警日志
     */
    @Override
    public List<AlertLog> selectAlertLogList(AlertLog alertLog)
    {
        return alertLogMapper.selectAlertLogList(alertLog);
    }

    /**
     * 新增预警日志
     *
     * @param alertLog 预警日志
     * @return 结果
     */
    @Override
    public int insertAlertLog(AlertLog alertLog)
    {
        return alertLogMapper.insertAlertLog(alertLog);
    }

    /**
     * 修改预警日志
     *
     * @param alertLog 预警日志
     * @return 结果
     */
    @Override
    public int updateAlertLog(AlertLog alertLog)
    {
        return alertLogMapper.updateAlertLog(alertLog);
    }

    /**
     * 批量删除预警日志
     *
     * @param logIds 需要删除的预警日志主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogByLogIds(String[] logIds)
    {
        return alertLogMapper.deleteAlertLogByLogIds(logIds);
    }

    /**
     * 删除预警日志信息
     *
     * @param logId 预警日志主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogByLogId(String logId)
    {
        return alertLogMapper.deleteAlertLogByLogId(logId);
    }

    @Override
    public List<AlertLog> selectAnoListByUserId(AlertLog alertLog) {
        return alertLogMapper.selectAnoListByUserId(alertLog);
    }

    @Override
    public int insertAno(String pId) {
        return alertLogMapper.insertAno(pId);
    }

    @Override
    public int insertAnoUser(String userId, String pId) {
        return alertLogMapper.insertAnoUser(userId, pId);
    }

    @Override
    public int updateAno(String userId, String pId) {
        return alertLogMapper.updateAno(userId, pId);
    }

    @Override
    public List<AlertLog> selectAlertLogList12(AlertLog alertLog) {
//        if (alertLog.getEcgType()!=null&&!"".equals(alertLog.getEcgType())){
//            if(Boolean.TRUE.equals(redisTemplate.hasKey("selectAlertLogList12:"+alertLog.getEcgType()))){
//                //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
//                List<AlertLog> alertLogs =  redisTemplate.opsForList().range("selectAlertLogList12:"+alertLog.getEcgType(), 0,-1);
//                //返回这个集合
//                return alertLogs;
//            }else{
//                //从mysql里查询这个集合
//                List<AlertLog> alertLogs = alertLogMapper.selectAlertLogList12(alertLog);
//                //存入redis
//                redisTemplate.opsForList().leftPushAll("selectAlertLogList12:"+alertLog.getEcgType(), alertLogs);
//                //给redis设置毫秒值
//                //第一个参数是key
//                //第二个参数是值
//                //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
//                redisTemplate.expire("selectAlertLogList12:"+alertLog.getEcgType(),55,TimeUnit.MINUTES);
//                //返回这个集合
//                return alertLogs;
//            }
//        }

        return alertLogMapper.selectAlertLogList12(alertLog);
    }

    @Override
    public List<AlertLog> selectAlertLogListSingle(AlertLog alertLog) {
//        if (alertLog.getEcgType()!=null&&!"".equals(alertLog.getEcgType())){
//            if(Boolean.TRUE.equals(redisTemplate.hasKey("selectAlertLogListSingle:"+alertLog.getEcgType()))){
//                //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
//                List<AlertLog> alertLogs =  redisTemplate.opsForList().range("selectAlertLogListSingle:"+alertLog.getEcgType(), 0,-1);
//                //返回这个集合
//                return alertLogs;
//            }else{
//                //从mysql里查询这个集合
//                List<AlertLog> alertLogs = alertLogMapper.selectAlertLogListSingle(alertLog);
//                //存入redis
//                redisTemplate.opsForList().leftPushAll("selectAlertLogListSingle:"+alertLog.getEcgType(), alertLogs);
//                //给redis设置毫秒值
//                //第一个参数是key
//                //第二个参数是值
//                //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
//                redisTemplate.expire("selectAlertLogListSingle:"+alertLog.getEcgType(),50,TimeUnit.MINUTES);
//                //返回这个集合
//                return alertLogs;
//            }
//        }
//        redisTemplate.opsForList().size(Key)
        return alertLogMapper.selectAlertLogListSingle(alertLog);
    }

    @Override
    public Patient  select12List(AlertLog alertLog) {

        PatientManagement patientManagement = patientManagementMapper.selectPatientManagementByPId(alertLog.getpId());

        Patient patient = patientMapper.selectPatientByPatientPhone(patientManagement.getPatientPhone());

        patient.setAlertLogList(alertLogMapper.select12List(alertLog));

        patient.setpId(patientManagement.getpId());
        return patient;
    }

    @Override
    public List<AlertLog> selectEarly(AlertLog alertLog) {


        if (alertLog.getEcgType().equals("J12")){


            if(Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest01"))){
                //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
                List<AlertLog> alertLogs =  redisTemplate.opsForList().range("earlyLogTest01", 0,-1);
                //返回这个集合
                return alertLogs;
            }else{
                //从mysql里查询这个集合
                List<AlertLog> list = alertLogMapper.selectEarly(alertLog);
                List<AlertLog> alertLogs = list.stream().filter(item -> item.getName().length() == 4).collect(Collectors.toList());

                if (alertLogs!=null&&alertLogs.size()>0){
                    if (Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest01"))){
                        redisTemplate.opsForList().remove("earlyLogTest01", 0, -1);
                    }
                    //存入redis
                    redisTemplate.opsForList().leftPushAll("earlyLogTest01", alertLogs);
                    //给redis设置毫秒值
                    //第一个参数是key
                    //第二个参数是值
                    //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
                    redisTemplate.expire("earlyLogTest01",60,TimeUnit.MINUTES);
                }

                //返回这个集合
                return alertLogs;
            }
        }
        else if(alertLog.getEcgType().equals("Jsingle")){
            if(Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest02"))){
                //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
                List<AlertLog> alertLogs =  redisTemplate.opsForList().range("earlyLogTest02", 0,-1);
                //返回这个集合
                return alertLogs;
            }else{
                //从mysql里查询这个集合
                List<AlertLog> alertLogs = alertLogMapper.selectEarly(alertLog);
                if (alertLogs!=null&&alertLogs.size()>0){
                    if (Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest02"))){
                        redisTemplate.opsForList().remove("earlyLogTest02", 0, -1);
                    }
                    //存入redis
                    redisTemplate.opsForList().leftPushAll("earlyLogTest02", alertLogs);
                    //给redis设置毫秒值
                    //第一个参数是key
                    //第二个参数是值
                    //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
                    redisTemplate.expire("earlyLogTest02",61,TimeUnit.MINUTES);
                }

                //返回这个集合
                return alertLogs;
            }
        }
        else if(alertLog.getEcgType().equals("D12")){
            if(Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest03"))){
                //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
                List<AlertLog> alertLogs =  redisTemplate.opsForList().range("earlyLogTest03", 0,-1);
                //返回这个集合
                return alertLogs;
            }else{
                //从mysql里查询这个集合
                List<AlertLog> alertLogs = alertLogMapper.selectEarly(alertLog);
                if (alertLogs!=null&&alertLogs.size()>0){
                    if (Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest03"))){
                        redisTemplate.opsForList().remove("earlyLogTest03", 0, -1);
                    }
                    //存入redis
                    redisTemplate.opsForList().leftPushAll("earlyLogTest03", alertLogs);
                    //给redis设置毫秒值
                    //第一个参数是key
                    //第二个参数是值
                    //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
                    redisTemplate.expire("earlyLogTest03",62,TimeUnit.MINUTES);
                }

                //返回这个集合
                return alertLogs;
            }
        }
        else if(alertLog.getEcgType().equals("Dsingle")) {
            if(Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest04"))){
                //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
                List<AlertLog> alertLogs =  redisTemplate.opsForList().range("earlyLogTest04", 0,-1);
                //返回这个集合
                return alertLogs;
            }else{
                //从mysql里查询这个集合
                List<AlertLog> alertLogs = alertLogMapper.selectEarly(alertLog);
                if (alertLogs!=null&&alertLogs.size()>0){
                    if (Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogTest04"))){
                        redisTemplate.opsForList().remove("earlyLogTest04", 0, -1);
                    }
                    //存入redis
                    redisTemplate.opsForList().leftPushAll("earlyLogTest04", alertLogs);
                    //给redis设置毫秒值
                    //第一个参数是key
                    //第二个参数是值
                    //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
                    redisTemplate.expire("earlyLogTest04",63,TimeUnit.MINUTES);
                }
                //返回这个集合
                return alertLogs;
            }

        }
        return alertLogMapper.selectEarly(alertLog);

    }

    @Override
    public void redisEarly(AlertLog alertLog) {
        List<AlertLog> alertLogs = alertLogMapper.selectEarly(alertLog);
        if (alertLogs!=null&&alertLogs.size()>0){
            //存入redis
            redisTemplate.opsForList().leftPushAll(alertLog.getRedisKey(), alertLogs);
            //给redis设置毫秒值
            //第一个参数是key
            //第二个参数是值
            //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
            redisTemplate.expire(alertLog.getRedisKey(),60,TimeUnit.MINUTES);
        }


    }

}
