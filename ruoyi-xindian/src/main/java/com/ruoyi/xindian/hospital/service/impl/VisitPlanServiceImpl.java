package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.hospital.domain.VisitAllocation;
import com.ruoyi.xindian.hospital.mapper.VisitAllocationMapper;
import com.ruoyi.xindian.hospital.vo.PlanMsgAllVo;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.VisitPlanMapper;
import com.ruoyi.xindian.hospital.domain.VisitPlan;
import com.ruoyi.xindian.hospital.service.IVisitPlanService;

import javax.annotation.Resource;

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


    @Resource
    private PatientMapper patientMapper;



    @Resource
    private VisitAllocationMapper visitAllocationMapper;

    @Resource
    private AesUtils aesUtils;

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

    @Override
    public List<VisitPlan> selectVisitPlanWebList(VisitPlan visitPlan) {
        return visitPlanMapper.selectVisitPlanWebList(visitPlan);
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

    @Override
    public PlanMsgAllVo getPlanMsgAll(PlanMsgAllVo planMsgAllVo) throws Exception {


        if (StringUtils.isNotEmpty(planMsgAllVo.getPatientPhone())){
            planMsgAllVo.setPatientPhone(aesUtils.encrypt(planMsgAllVo.getPatientPhone()));
        }

        Patient patient = patientMapper.selectPatientByPatientPhone(planMsgAllVo.getPatientPhone());

        if (patient==null){
            throw new ServiceException("患者信息不存在");
        }
        BeanUtils.copyProperties(patient,planMsgAllVo);

        planMsgAllVo.setPatientAge(DateUtil.getAge(patient.getBirthDay()));
        VisitPlan visitPlan = visitPlanMapper.selectVisitPlanByIdAll(planMsgAllVo.getPlanId());


        planMsgAllVo.setHospitalName(visitPlan.getHospital().getHospitalName());
        planMsgAllVo.setHospitalClinicName(visitPlan.getHospitalOutpatient().getOutpatientName());
        planMsgAllVo.setDoctorName(visitPlan.getDoctor().getDoctorName());
        planMsgAllVo.setDoctorPhone(visitPlan.getDoctor().getDoctorPhone());
        planMsgAllVo.setProfessional(visitPlan.getDoctor().getProfessional());
        planMsgAllVo.setChargePrice(visitPlan.getDoctor().getChargePrice());
        planMsgAllVo.setDay(visitPlan.getDay());


        VisitAllocation visitAllocation = visitAllocationMapper.selectOne(new QueryWrapper<VisitAllocation>().eq("slot_id", planMsgAllVo.getSlotId()));
        if (visitAllocation==null){
            throw new ServiceException("排班编号不存在");
        }
        BeanUtils.copyProperties(visitAllocation,planMsgAllVo);


        if (StringUtils.isNotEmpty(planMsgAllVo.getPatientPhone())){
            planMsgAllVo.setPatientPhone(aesUtils.decrypt(planMsgAllVo.getPatientPhone()));
        }
        if (StringUtils.isNotEmpty(planMsgAllVo.getPatientName())){
            planMsgAllVo.setPatientName(aesUtils.decrypt(planMsgAllVo.getPatientName()));
        }
        if (StringUtils.isNotEmpty(planMsgAllVo.getDoctorName())){
            planMsgAllVo.setDoctorName(aesUtils.decrypt(planMsgAllVo.getDoctorName()));
        }
        if (StringUtils.isNotEmpty(planMsgAllVo.getDoctorPhone())){
            planMsgAllVo.setDoctorPhone(aesUtils.decrypt(planMsgAllVo.getDoctorPhone()));
        }

        return planMsgAllVo;
    }
}
