package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.VisitPlanMapper;
import com.ruoyi.xindian.hospital.domain.VisitPlan;
import com.ruoyi.xindian.hospital.service.IVisitPlanService;

/**
 * 出诊信息表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class VisitPlanServiceImpl implements IVisitPlanService 
{
    @Autowired
    private VisitPlanMapper visitPlanMapper;

    /**
     * 查询出诊信息表
     * 
     * @param id 出诊信息表主键
     * @return 出诊信息表
     */
    @Override
    public VisitPlan selectVisitPlanById(Long id)
    {
        return visitPlanMapper.selectVisitPlanById(id);
    }

    /**
     * 查询出诊信息表列表
     * 
     * @param visitPlan 出诊信息表
     * @return 出诊信息表
     */
    @Override
    public List<VisitPlan> selectVisitPlanList(VisitPlan visitPlan)
    {
        return visitPlanMapper.selectVisitPlanList(visitPlan);
    }

    /**
     * 新增出诊信息表
     * 
     * @param visitPlan 出诊信息表
     * @return 结果
     */
    @Override
    public int insertVisitPlan(VisitPlan visitPlan)
    {
        return visitPlanMapper.insertVisitPlan(visitPlan);
    }

    /**
     * 修改出诊信息表
     * 
     * @param visitPlan 出诊信息表
     * @return 结果
     */
    @Override
    public int updateVisitPlan(VisitPlan visitPlan)
    {
        return visitPlanMapper.updateVisitPlan(visitPlan);
    }

    /**
     * 批量删除出诊信息表
     * 
     * @param ids 需要删除的出诊信息表主键
     * @return 结果
     */
    @Override
    public int deleteVisitPlanByIds(Long[] ids)
    {
        return visitPlanMapper.deleteVisitPlanByIds(ids);
    }

    /**
     * 删除出诊信息表信息
     * 
     * @param id 出诊信息表主键
     * @return 结果
     */
    @Override
    public int deleteVisitPlanById(Long id)
    {
        return visitPlanMapper.deleteVisitPlanById(id);
    }
}
