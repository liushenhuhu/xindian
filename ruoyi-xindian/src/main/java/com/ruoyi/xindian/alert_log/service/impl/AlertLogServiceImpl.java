package com.ruoyi.xindian.alert_log.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.alert_log.mapper.AlertLogMapper;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.alert_log.service.IAlertLogService;

/**
 * 预警日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
@Service
public class AlertLogServiceImpl implements IAlertLogService 
{
    @Autowired
    private AlertLogMapper alertLogMapper;

    /**
     * 查询预警日志
     * 
     * @param id 预警日志主键
     * @return 预警日志
     */
    @Override
    public AlertLog selectAlertLogById(Long id)
    {
        return alertLogMapper.selectAlertLogById(id);
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
     * @param ids 需要删除的预警日志主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogByIds(Long[] ids)
    {
        return alertLogMapper.deleteAlertLogByIds(ids);
    }

    /**
     * 删除预警日志信息
     * 
     * @param id 预警日志主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogById(Long id)
    {
        return alertLogMapper.deleteAlertLogById(id);
    }
}
