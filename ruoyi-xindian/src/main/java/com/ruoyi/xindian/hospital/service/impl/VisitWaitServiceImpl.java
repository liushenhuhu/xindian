package com.ruoyi.xindian.hospital.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.hospital.domain.VisitAllocation;
import com.ruoyi.xindian.hospital.domain.VisitAppointment;
import com.ruoyi.xindian.hospital.domain.VisitWait;
import com.ruoyi.xindian.hospital.mapper.VisitAllocationMapper;
import com.ruoyi.xindian.hospital.mapper.VisitAppointmentMapper;
import com.ruoyi.xindian.hospital.service.VisitWaitService;
import com.ruoyi.xindian.hospital.mapper.VisitWaitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【visit_wait(门诊候诊)】的数据库操作Service实现
* @createDate 2023-10-27 15:26:31
*/
@Service
public class VisitWaitServiceImpl extends ServiceImpl<VisitWaitMapper, VisitWait>
    implements VisitWaitService{

    @Resource
    private VisitWaitMapper visitWaitMapper;

    @Resource
    private VisitAppointmentMapper visitAppointmentMapper;

    /**
     * 查询门诊候诊
     *
     * @param waitId 门诊候诊主键
     * @return 门诊候诊
     */
    @Override
    public VisitWait selectVisitWaitByWaitId(Long waitId)
    {
        return visitWaitMapper.selectVisitWaitByWaitId(waitId);
    }

    /**
     * 查询门诊候诊列表
     *
     * @param visitWait 门诊候诊
     * @return 门诊候诊
     */
    @Override
    public List<VisitWait> selectVisitWaitList(VisitWait visitWait)
    {
        return visitWaitMapper.selectVisitWaitList(visitWait);
    }

    /**
     * 新增门诊候诊
     *
     * @param visitWait 门诊候诊
     * @return 结果
     */
    @Override
    public int insertVisitWait(VisitWait visitWait)
    {
        visitWait.setCreateTime(DateUtils.getNowDate());
        return visitWaitMapper.insertVisitWait(visitWait);
    }

    /**
     * 修改门诊候诊
     *
     * @param visitWait 门诊候诊
     * @return 结果
     */
    @Override
    public int updateVisitWait(VisitWait visitWait)
    {
        visitWait.setUpdateTime(DateUtils.getNowDate());
        return visitWaitMapper.updateVisitWait(visitWait);
    }

    /**
     * 批量删除门诊候诊
     *
     * @param waitIds 需要删除的门诊候诊主键
     * @return 结果
     */
    @Override
    public int deleteVisitWaitByWaitIds(Long[] waitIds)
    {
        return visitWaitMapper.deleteVisitWaitByWaitIds(waitIds);
    }

    /**
     * 删除门诊候诊信息
     *
     * @param waitId 门诊候诊主键
     * @return 结果
     */
    @Override
    public int deleteVisitWaitByWaitId(Long waitId)
    {
        return visitWaitMapper.deleteVisitWaitByWaitId(waitId);
    }

    @Override
    public int insertVisit(String appointmentId) {
        VisitAppointment visitAppointment = visitAppointmentMapper.selectVisitAppointmentById(appointmentId);

        if (visitAppointment==null){
            throw new ServiceException("预约不存在");
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

    @Override
    public int getAccomplishStatus(VisitWait visitWait) {

        VisitAppointment visitAppointment = new VisitAppointment();

        visitAppointment.setAppointmentId(visitWait.getAppointmentId());
        visitAppointment.setStatus(visitWait.getStatus());

        int i = visitAppointmentMapper.updateVisitAppointment(visitAppointment);
        if (i>0){
            int waitId = visitWaitMapper.deleteVisitWaitByWaitId(visitWait.getWaitId());
            if (waitId>0){
                return waitId;
            }
            return 0;
        }
        return 0;
    }

}




