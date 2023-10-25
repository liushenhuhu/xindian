package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.VisitAppointment;
import org.apache.ibatis.annotations.Param;

/**
 * 出诊预约表Mapper接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface VisitAppointmentMapper 
{
    /**
     * 查询出诊预约表
     * 
     * @param id 出诊预约表主键
     * @return 出诊预约表
     */
    public VisitAppointment selectVisitAppointmentById(Long id);

    /**
     * 查询出诊预约表列表
     * 
     * @param visitAppointment 出诊预约表
     * @return 出诊预约表集合
     */
    public List<VisitAppointment> selectVisitAppointmentList(VisitAppointment visitAppointment);

    /**
     * 新增出诊预约表
     * 
     * @param visitAppointment 出诊预约表
     * @return 结果
     */
    public int insertVisitAppointment(VisitAppointment visitAppointment);

    /**
     * 修改出诊预约表
     * 
     * @param visitAppointment 出诊预约表
     * @return 结果
     */
    public int updateVisitAppointment(VisitAppointment visitAppointment);

    /**
     * 删除出诊预约表
     * 
     * @param id 出诊预约表主键
     * @return 结果
     */
    public int deleteVisitAppointmentById(Long id);

    /**
     * 批量删除出诊预约表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisitAppointmentByIds(Long[] ids);


    List<VisitAppointment> selectByPlanIdVisitAppointments(@Param("planId") Long planId);
}
