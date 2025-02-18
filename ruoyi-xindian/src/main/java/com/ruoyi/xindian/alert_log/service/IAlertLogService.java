package com.ruoyi.xindian.alert_log.service;

import java.util.List;

import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.patient.domain.Patient;

/**
 * 预警日志Service接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface IAlertLogService {
    /**
     * 查询预警日志
     *
     * @param logId 预警日志主键
     * @return 预警日志
     */
    public AlertLog selectAlertLogByLogId(String logId);

    /**
     * 查询预警日志列表
     *
     * @param alertLog 预警日志
     * @return 预警日志集合
     */
    public List<AlertLog> selectAlertLogList(AlertLog alertLog);

    /**
     * 新增预警日志
     *
     * @param alertLog 预警日志
     * @return 结果
     */
    public int insertAlertLog(AlertLog alertLog);

    /**
     * 修改预警日志
     *
     * @param alertLog 预警日志
     * @return 结果
     */
    public int updateAlertLog(AlertLog alertLog);

    /**
     * 批量删除预警日志
     *
     * @param logIds 需要删除的预警日志主键集合
     * @return 结果
     */
    public int deleteAlertLogByLogIds(String[] logIds);

    /**
     * 删除预警日志信息
     *
     * @param logId 预警日志主键
     * @return 结果
     */
    public int deleteAlertLogByLogId(String logId);

    public List<AlertLog> selectAnoListByUserId(AlertLog alertLog);

    public int insertAno(String pId);

    public int insertAnoUser(String userId, String pId);

    public int updateAno(String userId, String pId);

    /**
     * 查询预警日志列表
     *
     * @param alertLog 预警日志
     * @return 预警日志集合
     */
    public List<AlertLog> selectAlertLogList12(AlertLog alertLog);

    /**
     * 查询预警日志列表
     *
     * @param alertLog 预警日志
     * @return 预警日志集合
     */
    public List<AlertLog> selectAlertLogListSingle(AlertLog alertLog);


    /**
     * 查询单导或者12导大的单人的信息统计
     * @param alertLog
     * @return
     */
    Patient select12List(AlertLog alertLog);


    /**
     * 查询所有
     * @return
     */
    List<AlertLog> selectEarly(AlertLog alertLog);

    void redisEarly(AlertLog alertLog);

}
