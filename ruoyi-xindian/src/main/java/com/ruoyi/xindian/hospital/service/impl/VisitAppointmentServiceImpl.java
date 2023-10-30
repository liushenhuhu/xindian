package com.ruoyi.xindian.hospital.service.impl;
import java.util.Date;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.hospital.domain.VisitAllocation;
import com.ruoyi.xindian.hospital.domain.VisitPlan;
import com.ruoyi.xindian.hospital.domain.VisitWait;
import com.ruoyi.xindian.hospital.mapper.VisitAllocationMapper;
import com.ruoyi.xindian.hospital.mapper.VisitPlanMapper;
import com.ruoyi.xindian.hospital.mapper.VisitWaitMapper;
import com.ruoyi.xindian.hospital.vo.PlanMsgAllVo;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.VisitAppointmentMapper;
import com.ruoyi.xindian.hospital.domain.VisitAppointment;
import com.ruoyi.xindian.hospital.service.IVisitAppointmentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 出诊预约表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class VisitAppointmentServiceImpl implements IVisitAppointmentService 
{
    @Resource
    private VisitAppointmentMapper visitAppointmentMapper;

    @Resource
    private VisitPlanMapper visitPlanMapper;

    @Resource
    private PatientMapper patientMapper;



    @Resource
    private VisitAllocationMapper visitAllocationMapper;
    @Resource
    private AesUtils aesUtils;



    @Resource
    private TokenService tokenService;


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private VisitWaitMapper visitWaitMapper;

    /**
     * 查询出诊预约表
     * 
     * @param id 出诊预约表主键
     * @return 出诊预约表
     */
    @Override
    public VisitAppointment selectVisitAppointmentById(String id)
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
    public int deleteVisitAppointmentByIds(String[] ids)
    {
        return visitAppointmentMapper.deleteVisitAppointmentByIds(ids);
    }

    /**
     * 删除出诊预约表信息
     * 
     * @param appointmentId 出诊预约表主键
     * @return 结果
     */
    @Override
    public int deleteVisitAppointmentById(String appointmentId)
    {
        return visitAppointmentMapper.deleteVisitAppointmentById(appointmentId);
    }

    @Override
    public List<VisitAppointment> selectByPlanId(Long planId) {
        return visitAppointmentMapper.selectByPlanIdVisitAppointments(planId);
    }

    @Override
    public VisitAppointment addVisitAppointment(PlanMsgAllVo planMsgAllVo, HttpServletRequest request) throws Exception {


        //获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);

        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());

        VisitAppointment visitAppointment = new VisitAppointment();
        //判断患者信息是否存在
        Patient patient = patientMapper.selectPatientByPatientPhone(aesUtils.encrypt(planMsgAllVo.getPatientPhone()));
        if (patient==null){
            throw new ServiceException("患者信息不存在");
        }

        visitAppointment.setPatientPhone(patient.getPatientPhone());

        //判断排班信息是否存在
        VisitPlan visitPlan = visitPlanMapper.selectVisitPlanByIdAll(planMsgAllVo.getPlanId());
        if (visitPlan==null){
            throw new ServiceException("排班信息不存在");
        }

        VisitAppointment visitAppointment2 = new VisitAppointment();
        visitAppointment2.setPatientPhone(patient.getPatientPhone());
        visitAppointment2.setPlanId(planMsgAllVo.getPlanId());

        List<VisitAppointment> visitAppointments1 = visitAppointmentMapper.selectVisitAppointmentList(visitAppointment2);


        for (VisitAppointment v : visitAppointments1){

            if (v.getStatus()!=2){
                throw new ServiceException("今日已在"+aesUtils.decrypt(v.getDoctorName())+"医生预约了"+v.getPeriodStart()+"-"+v.getPeriodEnd()+"时段，不能重复预约");
            }
        }

        //判断预约的时间的在不在当前医生的判断时间里
        VisitAllocation visitAllocation = visitAllocationMapper.selectOne(new QueryWrapper<VisitAllocation>().eq("slot_id", planMsgAllVo.getSlotId()).eq(visitPlan.getTime()!=3,"category",visitPlan.getTime()));
        if (visitAllocation==null){
            throw new ServiceException("排班信息不存在");
        }

        //判断当前时间段是否被预约
        List<VisitAppointment> visitAppointments = visitAppointmentMapper.selectByPlanIdVisitAppointments(planMsgAllVo.getPlanId());

        for (VisitAppointment visitAppointment1 : visitAppointments){
            if (visitAppointment1.getTimePeriod().equals(planMsgAllVo.getSlotId())&&visitAppointment1.getStatus()!=2){
                throw new ServiceException("当前时间段已被人预约");
            }
        }

        //判断当前预约的时间的是否过了当天的预约时间（进行预约当天的）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (visitPlan.getDay()!=null&&sdf.parse(sdf.format(date)).compareTo(sdf.parse(sdf.format(visitPlan.getDay())))==0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date parse1 = simpleDateFormat.parse(simpleDateFormat.format(date));
            Date parse = simpleDateFormat.parse(visitAllocation.getStartTime());
            if (parse1.compareTo(parse)>0){
                throw new ServiceException("当前时间段已过预约时间");
            }

        }


        visitAppointment.setAppointmentId(OrderNoUtils.getNo());
        visitAppointment.setPlanId(planMsgAllVo.getPlanId());

        visitAppointment.setAccompanyPhone(planMsgAllVo.getAccompanyPhone());
        visitAppointment.setPeriodStart(visitAllocation.getStartTime());
        visitAppointment.setStatus(0L);
        visitAppointment.setPayStatus("0");
        visitAppointment.setPeriodEnd(visitAllocation.getEndTime());
        visitAppointment.setSpecialName(visitPlan.getHospitalSpecial().getSpecialName());
        visitAppointment.setOutpatientName(visitPlan.getHospitalOutpatient().getOutpatientName());
        visitAppointment.setClinicAddress(visitPlan.getHospitalClinic().getAddress());
        visitAppointment.setHospitalId(visitPlan.getHospital().getHospitalId());
        visitAppointment.setDoctorName(visitPlan.getDoctor().getDoctorName());
        visitAppointment.setDoctorPhone(visitPlan.getDoctor().getDoctorPhone());
        visitAppointment.setPeriodDay(sdf.parse(sdf.format(visitPlan.getDay())));
        visitAppointment.setPayPrice(visitPlan.getDoctor().getChargePrice());
        visitAppointment.setOutpatientType("预约挂号");
        visitAppointment.setDoctorProfessional(visitPlan.getDoctor().getProfessional());
        visitAppointment.setTimePeriod(planMsgAllVo.getSlotId());
        visitAppointment.setTitle("门诊预约挂号");
        visitAppointment.setOrderNo(OrderNoUtils.getOrderNo());
        visitAppointment.setSearchValue("");
        visitAppointment.setCreateTime(date);
        visitAppointment.setUpdateTime(date);
        visitAppointment.setOpenId(sysUser.getOpenId());
        int i = visitAppointmentMapper.insertVisitAppointment(visitAppointment);
        if (i>0){
            return visitAppointment;
        }
        throw new ServiceException("添加失败");
    }

    @Override
    public VisitAppointment putVisitAppointment(PlanMsgAllVo planMsgAllVo, HttpServletRequest request) throws Exception {

        VisitAppointment visitAppointment = new VisitAppointment();
        visitAppointment.setAppointmentId(planMsgAllVo.getAppointmentId());
        //判断患者信息是否存在
        Patient patient = patientMapper.selectPatientByPatientPhone(aesUtils.encrypt(planMsgAllVo.getPatientPhone()));
        if (patient==null){
            throw new ServiceException("患者信息不存在");
        }

        visitAppointment.setPatientPhone(patient.getPatientPhone());

        //判断排班信息是否存在
        VisitPlan visitPlan = visitPlanMapper.selectVisitPlanByIdAll(planMsgAllVo.getPlanId());
        if (visitPlan==null){
            throw new ServiceException("排班信息不存在");
        }
        //判断预约的时间的在不在当前医生的判断时间里
        VisitAllocation visitAllocation = visitAllocationMapper.selectOne(new QueryWrapper<VisitAllocation>().eq("slot_id", planMsgAllVo.getSlotId()).eq(visitPlan.getTime()!=3,"category",visitPlan.getTime()));
        if (visitAllocation==null){
            throw new ServiceException("排班信息不存在");
        }

        //判断当前时间段是否被预约
        List<VisitAppointment> visitAppointments = visitAppointmentMapper.selectByPlanIdVisitAppointments(planMsgAllVo.getPlanId());

        for (VisitAppointment visitAppointment1 : visitAppointments){
            if (visitAppointment1.getTimePeriod().equals(planMsgAllVo.getSlotId())&&visitAppointment1.getStatus()!=2){
                throw new ServiceException("当前时间段已被人预约");
            }
        }

        //判断当前预约的时间的是否过了当天的预约时间（进行预约当天的）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (visitPlan.getDay()!=null&&sdf.parse(sdf.format(date)).compareTo(sdf.parse(sdf.format(visitPlan.getDay())))==0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date parse1 = simpleDateFormat.parse(simpleDateFormat.format(date));
            Date parse = simpleDateFormat.parse(visitAllocation.getStartTime());
            if (parse1.compareTo(parse)>0){
                throw new ServiceException("当前时间段已过预约时间");
            }

        }
        visitAppointment.setPlanId(planMsgAllVo.getPlanId());
        visitAppointment.setAccompanyPhone(planMsgAllVo.getAccompanyPhone());
        visitAppointment.setPeriodStart(visitAllocation.getStartTime());
        visitAppointment.setPeriodEnd(visitAllocation.getEndTime());
        visitAppointment.setSpecialName(visitPlan.getHospitalSpecial().getSpecialName());
        visitAppointment.setOutpatientName(visitPlan.getHospitalOutpatient().getOutpatientName());
        visitAppointment.setClinicAddress(visitPlan.getHospitalClinic().getAddress());
        visitAppointment.setHospitalId(visitPlan.getHospital().getHospitalId());
        visitAppointment.setDoctorName(visitPlan.getDoctor().getDoctorName());
        visitAppointment.setDoctorPhone(visitPlan.getDoctor().getDoctorPhone());
        visitAppointment.setPeriodDay(sdf.parse(sdf.format(visitPlan.getDay())));
        visitAppointment.setPayPrice(visitPlan.getDoctor().getChargePrice());
        visitAppointment.setDoctorProfessional(visitPlan.getDoctor().getProfessional());
        visitAppointment.setTimePeriod(planMsgAllVo.getSlotId());
        visitAppointment.setUpdateTime(date);

        int i = visitAppointmentMapper.updateVisitAppointment(visitAppointment);

        if (i>0){
            return visitAppointment;
        }
        throw new ServiceException("修改失败");
    }

    @Override
    public VisitAppointment getVisitAppointmentOrderNo(String orderNo) {
        VisitAppointment visitAppointmentOrderNo = visitAppointmentMapper.getVisitAppointmentOrderNo(orderNo);
        if (visitAppointmentOrderNo==null){
            return null;
        }
        return visitAppointmentOrderNo;
    }

    @Override
    public void updateVisitAppointmentOrderStatus(String orderNo,String type) {
        visitAppointmentMapper.updateVisitAppointmentOrderStatus(orderNo,type);
    }

    @Override
    public void updateVisitAppointmentStatus(String id, String type) {
        visitAppointmentMapper.updateVisitAppointmentStatus(id, type);
    }

    @Override
    public int visitSignIn(String appointmentId) throws ParseException {

        VisitAppointment visitAppointment = visitAppointmentMapper.selectVisitAppointmentById(appointmentId);
        if (visitAppointment==null){
            throw new ServiceException("预约不存在");
        }
        //判断当前预约的时间的是否过了当天的预约时间（进行预约当天的）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (visitAppointment.getPeriodDay()!=null&&sdf.parse(sdf.format(date)).compareTo(sdf.parse(sdf.format(visitAppointment.getPeriodDay())))==0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date parse1 = simpleDateFormat.parse(simpleDateFormat.format(date));
            Date parse = simpleDateFormat.parse(visitAppointment.getPeriodEnd());
            Date parse2 = simpleDateFormat.parse(visitAppointment.getPeriodStart());


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse2);
            calendar.add(Calendar.MINUTE, -20);
            Date timeBefore20Minutes = calendar.getTime();



            if (parse1.compareTo(parse)>0){
                VisitAppointment visitAppointment1 = new VisitAppointment();
                visitAppointment1.setStatus(3L);
                visitAppointment1.setAppointmentId(appointmentId);
                visitAppointmentMapper.updateVisitAppointment(visitAppointment1);
                throw new ServiceException("当前时间段已过预约时间,无法签到");
            }else if (parse1.compareTo(timeBefore20Minutes)<0){
                throw new ServiceException("现在未到开始时间，请稍后再试");
            }

        }else {
            throw new ServiceException("请在预约当天时间段前20分钟内点击签到");
        }


        VisitWait visitWait = new VisitWait();
        visitWait.setAppointmentId(visitAppointment.getAppointmentId());
        visitWait.setHospitalId(visitAppointment.getHospitalId());
        visitWait.setDoctorPhone(visitAppointment.getDoctorPhone());
        visitWait.setPatientPhone(visitAppointment.getPatientPhone());
        visitWait.setSlotId(visitAppointment.getTimePeriod());
        visitWait.setCreateTime(new Date());
        visitWait.setUpdateTime(new Date());

        return visitWaitMapper.insertVisitWait(visitWait);
    }

}
