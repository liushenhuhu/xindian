package com.ruoyi.xindian.alert_log.service;

import java.util.List;
import com.ruoyi.xindian.alert_log.domain.AlertLog;

/**
 * 预警日志Service接口
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
public interface IAlertLogService 
{
    /**
     * 查询预警日志
     * 
     * @param id 预警日志主键
     * @return 预警日志
     */
    public AlertLog selectAlertLogById(Long id);

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
     * @param ids 需要删除的预警日志主键集合
     * @return 结果
     */
    public int deleteAlertLogByIds(Long[] ids);

    /**
     * 删除预警日志信息
     * 
     * @param id 预警日志主键
     * @return 结果
     */
    public int deleteAlertLogById(Long id);
}
