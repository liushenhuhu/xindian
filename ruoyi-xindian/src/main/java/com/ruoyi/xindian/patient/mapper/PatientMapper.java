package com.ruoyi.xindian.patient.mapper;

import java.util.List;
import com.ruoyi.xindian.patient.domain.Patient;

/**
 * 患者Mapper接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface PatientMapper
{
    /**
     * 查询患者
     *
     * @param patientId 患者主键
     * @return 患者
     */
    public Patient selectPatientByPatientId(Long patientId);

    /**
     * 查询患者列表
     *
     * @param patient 患者
     * @return 患者集合
     */
    public List<Patient> selectPatientList(Patient patient);

    /**
     * 新增患者
     *
     * @param patient 患者
     * @return 结果
     */
    public int insertPatient(Patient patient);

    /**
     * 修改患者
     *
     * @param patient 患者
     * @return 结果
     */
    public int updatePatient(Patient patient);

    /**
     * 删除患者
     *
     * @param patientId 患者主键
     * @return 结果
     */
    public int deletePatientByPatientId(Long patientId);

    /**
     * 批量删除患者
     *
     * @param patientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientByPatientIds(Long[] patientIds);

    /**
     * 查询患者
     *
     * @param patientCode 患者身份证号
     * @return 患者
     */
    public Patient selectPatientByPatientCode(String patientCode);

    /**
     * 查询患者
     *
     * @param patientName 患者姓名
     * @return 患者
     */
    public Patient selectPatientByPatientName(String patientName);

    public void updateMonitoringStatus();

    public void updateMonitoringStatus2(String equipment);

    public Patient selectPatientByNameAndCode(Patient patient);
}
