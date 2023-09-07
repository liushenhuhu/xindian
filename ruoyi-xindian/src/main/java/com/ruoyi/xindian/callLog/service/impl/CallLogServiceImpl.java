package com.ruoyi.xindian.callLog.service.impl;

import java.util.List;

import com.ruoyi.xindian.callLog.domain.CallLog;
import com.ruoyi.xindian.callLog.mapper.CallLogMapper;
import com.ruoyi.xindian.callLog.service.ICallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【通话记录】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-05
 */
@Service
public class CallLogServiceImpl implements ICallLogService
{
    @Autowired
    private CallLogMapper callLogMapper;

    /**
     * 查询【通话记录】
     * 
     * @param id 【通话记录】主键
     * @return 【通话记录】
     */
    @Override
    public CallLog selectCallLogById(Long id)
    {
        return callLogMapper.selectCallLogById(id);
    }

    /**
     * 查询【通话记录】列表
     * 
     * @param callLog 【通话记录】
     * @return 【通话记录】
     */
    @Override
    public List<CallLog> selectCallLogList(CallLog callLog)
    {
        return callLogMapper.selectCallLogList(callLog);
    }

    /**
     * 新增【通话记录】
     * 
     * @param callLog 【通话记录】
     * @return 结果
     */
    @Override
    public int insertCallLog(CallLog callLog)
    {
        return callLogMapper.insertCallLog(callLog);
    }

    /**
     * 修改【通话记录】
     * 
     * @param callLog 【通话记录】
     * @return 结果
     */
    @Override
    public int updateCallLog(CallLog callLog)
    {
        return callLogMapper.updateCallLog(callLog);
    }

    /**
     * 批量删除【通话记录】
     * 
     * @param ids 需要删除的【通话记录】主键
     * @return 结果
     */
    @Override
    public int deleteCallLogByIds(Long[] ids)
    {
        return callLogMapper.deleteCallLogByIds(ids);
    }

    /**
     * 删除【通话记录】信息
     * 
     * @param id 【通话记录】主键
     * @return 结果
     */
    @Override
    public int deleteCallLogById(Long id)
    {
        return callLogMapper.deleteCallLogById(id);
    }

    @Override
    public List<CallLog> selectCallLogWebList(Long pId) {
        return callLogMapper.selectCallLogWebList(pId);
    }

    @Override
    public Long selectPidByDeviceSn(String deviceSn) {
        return callLogMapper.selectPidByDeviceSn(deviceSn);
    }


}
