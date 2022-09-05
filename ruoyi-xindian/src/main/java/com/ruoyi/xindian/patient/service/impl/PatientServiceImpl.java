package com.ruoyi.xindian.patient.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;

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
    public List<Patient> selectPatientList(Patient patient)
    {
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
    public Patient selectPatientByPatientCode(String patientCode) {
        return patientMapper.selectPatientByPatientCode(patientCode);
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
    public Patient selectPatientByNameAndCode(Patient patient) {
        return patientMapper.selectPatientByNameAndCode(patient);
    }
}
