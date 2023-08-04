package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.mapper.VipPatientMapper;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.wx_pay.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 患者Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Service
public class PatientServiceImpl implements IPatientService
{
    @Autowired
    private PatientMapper patientMapper;


    @Autowired
    private VipPatientMapper vipPatientMapper;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    @Resource
    private HospitalMapper hospitalMapper;

    @Resource
    private SysUserMapper sysUserMapper;
    /**
     * 查询患者
     *
     * @param patientId 患者主键
     * @return 患者
     */
    @Override
    public Patient selectPatientByPatientId(Long patientId)
    {
        return patientMapper.selectPatientByPatientId(patientId);
    }

    /**
     * 查询患者列表
     *
     * @param patient 患者
     * @return 患者
     */
    @Override
    public List<Patient> selectPatientList(Patient patient) {

        return patientMapper.selectPatientList(patient);
    }

    /**
     * 新增患者
     *
     * @param patient 患者
     * @return 结果
     */
    @Override
    public int insertPatient(Patient patient)
    {
        return patientMapper.insertPatient(patient);
    }

    /**
     * 修改患者
     *
     * @param patient 患者
     * @return 结果
     */
    @Override
    public int updatePatient(Patient patient)
    {
        return patientMapper.updatePatient(patient);
    }

    /**
     * 批量删除患者
     *
     * @param patientIds 需要删除的患者主键
     * @return 结果
     */
    @Override
    public int deletePatientByPatientIds(Long[] patientIds)
    {
        return patientMapper.deletePatientByPatientIds(patientIds);
    }

    /**
     * 删除患者信息
     *
     * @param patientId 患者主键
     * @return 结果
     */
    @Override
    public int deletePatientByPatientId(Long patientId)
    {
        return patientMapper.deletePatientByPatientId(patientId);
    }

    @Override
    public int deletePatientByPatientPhone(String patientPhone) {
        return patientMapper.delPatientByPatientPhone(patientPhone);
    }


    @Override
    public Patient selectPatientByPatientPhone(String patientPhone) {
        return patientMapper.selectPatientByPatientPhone(patientPhone);
    }

    @Override
    public Patient selectPatientByPatientName(String patientName) {
        return patientMapper.selectPatientByPatientName(patientName);
    }

    @Override
    public void updateMonitoringStatus() {
        patientMapper.updateMonitoringStatus();
    }

    @Override
    public void updateMonitoringStatus2(String equipment) {
        patientMapper.updateMonitoringStatus2(equipment);
    }

    @Override
    public Patient selectPatientByNameAndPhone(Patient patient) {
        return patientMapper.selectPatientByNameAndPhone(patient);
    }

    @Transactional
    @Override
    public Boolean detectionNumSubtract(String patientPhone) {
        VipPatient vipPhone = vipPatientMapper.selectPhone(patientPhone);

        if (vipPhone!=null){
                     // 设置库存减一的更新操作
            vipPatientMapper.updateVipNumInt(vipPhone.getId());
        }else {
            sysUserMapper.updateDetectionNumInt(patientPhone);
        }
        return true;
    }
}
