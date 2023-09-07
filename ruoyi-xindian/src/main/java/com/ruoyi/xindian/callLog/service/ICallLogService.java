package com.ruoyi.xindian.callLog.service;

import com.ruoyi.xindian.callLog.domain.CallLog;

import java.util.List;

/**
 * 【通话记录】Service接口
 * 
 * @author ruoyi
 * @date 2023-09-05
 */
public interface ICallLogService 
{
    /**
     * 查询【通话记录】
     * 
     * @param id 【通话记录】主键
     * @return 【通话记录】
     */
    public CallLog selectCallLogById(Long id);

    /**
     * 查询【通话记录】列表
     * 
     * @param callLog 【通话记录】
     * @return 【通话记录】集合
     */
    public List<CallLog> selectCallLogList(CallLog callLog);

    /**
     * 新增【通话记录】
     * 
     * @param callLog 【通话记录】
     * @return 结果
     */
    public int insertCallLog(CallLog callLog);

    /**
     * 修改【通话记录】
     * 
     * @param callLog 【通话记录】
     * @return 结果
     */
    public int updateCallLog(CallLog callLog);

    /**
     * 批量删除【通话记录】
     * 
     * @param ids 需要删除的【通话记录】主键集合
     * @return 结果
     */
    public int deleteCallLogByIds(Long[] ids);

    /**
     * 删除【通话记录】信息
     * 
     * @param id 【通话记录】主键
     * @return 结果
     */
    public int deleteCallLogById(Long id);

    List<CallLog> selectCallLogWebList(Long pId);

    Long selectPidByDeviceSn(String deviceSn);
}
