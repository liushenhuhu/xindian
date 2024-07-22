package com.ruoyi.xindian.weekDetectionTime.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.weekDetectionTime.mapper.WeekDetectionTimeMapper;
import com.ruoyi.xindian.weekDetectionTime.domain.WeekDetectionTime;
import com.ruoyi.xindian.weekDetectionTime.service.IWeekDetectionTimeService;

/**
 * weekDetectionTimeService业务层处理
 * 
 * @author chenpeng
 * @date 2024-07-22
 */
@Service
public class WeekDetectionTimeServiceImpl implements IWeekDetectionTimeService 
{
    @Autowired
    private WeekDetectionTimeMapper weekDetectionTimeMapper;

    /**
     * 查询weekDetectionTime
     * 
     * @param id weekDetectionTime主键
     * @return weekDetectionTime
     */
    @Override
    public WeekDetectionTime selectWeekDetectionTimeById(Long id)
    {
        return weekDetectionTimeMapper.selectWeekDetectionTimeById(id);
    }

    /**
     * 查询weekDetectionTime列表
     * 
     * @param weekDetectionTime weekDetectionTime
     * @return weekDetectionTime
     */
    @Override
    public List<WeekDetectionTime> selectWeekDetectionTimeList(WeekDetectionTime weekDetectionTime)
    {
        return weekDetectionTimeMapper.selectWeekDetectionTimeList(weekDetectionTime);
    }

    /**
     * 新增weekDetectionTime
     * 
     * @param weekDetectionTime weekDetectionTime
     * @return 结果
     */
    @Override
    public int insertWeekDetectionTime(WeekDetectionTime weekDetectionTime)
    {
        return weekDetectionTimeMapper.insertWeekDetectionTime(weekDetectionTime);
    }

    /**
     * 修改weekDetectionTime
     * 
     * @param weekDetectionTime weekDetectionTime
     * @return 结果
     */
    @Override
    public int updateWeekDetectionTime(WeekDetectionTime weekDetectionTime)
    {
        return weekDetectionTimeMapper.updateWeekDetectionTime(weekDetectionTime);
    }

    /**
     * 批量删除weekDetectionTime
     * 
     * @param ids 需要删除的weekDetectionTime主键
     * @return 结果
     */
    @Override
    public int deleteWeekDetectionTimeByIds(Long[] ids)
    {
        return weekDetectionTimeMapper.deleteWeekDetectionTimeByIds(ids);
    }

    /**
     * 删除weekDetectionTime信息
     * 
     * @param id weekDetectionTime主键
     * @return 结果
     */
    @Override
    public int deleteWeekDetectionTimeById(Long id)
    {
        return weekDetectionTimeMapper.deleteWeekDetectionTimeById(id);
    }
}
