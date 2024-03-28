package com.ruoyi.xindian.log_user.service;

import com.ruoyi.xindian.log_user.domain.AlertLogCount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;

import java.util.List;

/**
* @author 13401
* @description 针对表【alert_log_count】的数据库操作Service
* @createDate 2024-03-28 10:23:05
*/
public interface AlertLogCountService extends IService<AlertLogCount> {
    /**
     * 查询预警类型统计
     *
     * @param countId 预警类型统计主键
     * @return 预警类型统计
     */
    public AlertLogCount selectAlertLogCountByCountId(Long countId);

    /**
     * 查询预警类型统计列表
     *
     * @param alertLogCount 预警类型统计
     * @return 预警类型统计集合
     */
    public List<AlertLogCount> selectAlertLogCountList(AlertLogCount alertLogCount);

    /**
     * 新增预警类型统计
     *
     * @param alertLogCount 预警类型统计
     * @return 结果
     */
    public int insertAlertLogCount(AlertLogCount alertLogCount);

    /**
     * 修改预警类型统计
     *
     * @param alertLogCount 预警类型统计
     * @return 结果
     */
    public int updateAlertLogCount(AlertLogCount alertLogCount);

    /**
     * 批量删除预警类型统计
     *
     * @param countIds 需要删除的预警类型统计主键集合
     * @return 结果
     */
    public int deleteAlertLogCountByCountIds(Long[] countIds);

    /**
     * 删除预警类型统计信息
     *
     * @param countId 预警类型统计主键
     * @return 结果
     */
    public int deleteAlertLogCountByCountId(Long countId);

    List<ListValueAndLabelVO> getLogType();
}
