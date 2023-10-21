package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.VisitPlan;

/**
 * 出诊信息表Service接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface IVisitPlanService 
{
    /**
     * 查询出诊信息表
     * 
     * @param id 出诊信息表主键
     * @return 出诊信息表
     */
    public VisitPlan selectVisitPlanById(Long id);

    /**
     * 查询出诊信息表列表
     *
     * @param visitPlan 出诊信息表
     * @return 出诊信息表集合
     */
    public List<VisitPlan> selectVisitPlanWebList(VisitPlan visitPlan);

    /**
     * 查询出诊信息表列表
     * 
     * @param visitPlan 出诊信息表
     * @return 出诊信息表集合
     */
    public List<VisitPlan> selectVisitPlanList(VisitPlan visitPlan);

    /**
     * 新增出诊信息表
     * 
     * @param visitPlan 出诊信息表
     * @return 结果
     */
    public int insertVisitPlan(VisitPlan visitPlan);

    /**
     * 修改出诊信息表
     * 
     * @param visitPlan 出诊信息表
     * @return 结果
     */
    public int updateVisitPlan(VisitPlan visitPlan);

    /**
     * 批量删除出诊信息表
     * 
     * @param ids 需要删除的出诊信息表主键集合
     * @return 结果
     */
    public int deleteVisitPlanByIds(Long[] ids);

    /**
     * 删除出诊信息表信息
     * 
     * @param id 出诊信息表主键
     * @return 结果
     */
    public int deleteVisitPlanById(Long id);
}
