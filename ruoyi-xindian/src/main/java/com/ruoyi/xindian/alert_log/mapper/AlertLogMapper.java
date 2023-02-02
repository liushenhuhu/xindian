package com.ruoyi.xindian.alert_log.mapper;

import java.util.List;

import com.ruoyi.xindian.alert_log.domain.AlertLog;

/**
 * 预警日志Mapper接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface AlertLogMapper {
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
     * 删除预警日志
     *
     * @param logId 预警日志主键
     * @return 结果
     */
    public int deleteAlertLogByLogId(String logId);

    /**
     * 批量删除预警日志
     *
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAlertLogByLogIds(String[] logIds);

    /**
     * 查询标注数据
     *
     * @param alertLog 预警日志
     * @return 标注数据集合
     */
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
}
