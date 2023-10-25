package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.VisitAppointmentMapper;
import com.ruoyi.xindian.hospital.domain.VisitAppointment;
import com.ruoyi.xindian.hospital.service.IVisitAppointmentService;

/**
 * 出诊预约表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class VisitAppointmentServiceImpl implements IVisitAppointmentService 
{
    @Autowired
    private VisitAppointmentMapper visitAppointmentMapper;

    /**
     * 查询出诊预约表
     * 
     * @param id 出诊预约表主键
     * @return 出诊预约表
     */
    @Override
    public VisitAppointment selectVisitAppointmentById(Long id)
    {
        return visitAppointmentMapper.selectVisitAppointmentById(id);
    }

    /**
     * 查询出诊预约表列表
     * 
     * @param visitAppointment 出诊预约表
     * @return 出诊预约表
     */
    @Override
    public List<VisitAppointment> selectVisitAppointmentList(VisitAppointment visitAppointment)
    {
        return visitAppointmentMapper.selectVisitAppointmentList(visitAppointment);
    }

    /**
     * 新增出诊预约表
     * 
     * @param visitAppointment 出诊预约表
     * @return 结果
     */
    @Override
    public int insertVisitAppointment(VisitAppointment visitAppointment)
    {
        return visitAppointmentMapper.insertVisitAppointment(visitAppointment);
    }

    /**
     * 修改出诊预约表
     * 
     * @param visitAppointment 出诊预约表
     * @return 结果
     */
    @Override
    public int updateVisitAppointment(VisitAppointment visitAppointment)
    {
        return visitAppointmentMapper.updateVisitAppointment(visitAppointment);
    }

    /**
     * 批量删除出诊预约表
     * 
     * @param ids 需要删除的出诊预约表主键
     * @return 结果
     */
    @Override
    public int deleteVisitAppointmentByIds(Long[] ids)
    {
        return visitAppointmentMapper.deleteVisitAppointmentByIds(ids);
    }

    /**
     * 删除出诊预约表信息
     * 
     * @param id 出诊预约表主键
     * @return 结果
     */
    @Override
    public int deleteVisitAppointmentById(Long id)
    {
        return visitAppointmentMapper.deleteVisitAppointmentById(id);
    }

    @Override
    public List<VisitAppointment> selectByPlanId(Long planId) {
        return visitAppointmentMapper.selectByPlanIdVisitAppointments(planId);
    }
}
