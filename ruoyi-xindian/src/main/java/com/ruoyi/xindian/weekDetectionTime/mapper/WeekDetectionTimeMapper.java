package com.ruoyi.xindian.weekDetectionTime.mapper;

import java.util.List;
import com.ruoyi.xindian.weekDetectionTime.domain.WeekDetectionTime;

/**
 * weekDetectionTimeMapper接口
 * 
 * @author chenpeng
 * @date 2024-07-22
 */
public interface WeekDetectionTimeMapper 
{
    /**
     * 查询weekDetectionTime
     * 
     * @param id weekDetectionTime主键
     * @return weekDetectionTime
     */
    public WeekDetectionTime selectWeekDetectionTimeById(Long id);

    /**
     * 查询weekDetectionTime列表
     * 
     * @param weekDetectionTime weekDetectionTime
     * @return weekDetectionTime集合
     */
    public List<WeekDetectionTime> selectWeekDetectionTimeList(WeekDetectionTime weekDetectionTime);

    /**
     * 新增weekDetectionTime
     * 
     * @param weekDetectionTime weekDetectionTime
     * @return 结果
     */
    public int insertWeekDetectionTime(WeekDetectionTime weekDetectionTime);

    /**
     * 修改weekDetectionTime
     * 
     * @param weekDetectionTime weekDetectionTime
     * @return 结果
     */
    public int updateWeekDetectionTime(WeekDetectionTime weekDetectionTime);

    /**
     * 删除weekDetectionTime
     * 
     * @param id weekDetectionTime主键
     * @return 结果
     */
    public int deleteWeekDetectionTimeById(Long id);

    /**
     * 批量删除weekDetectionTime
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWeekDetectionTimeByIds(Long[] ids);
}
