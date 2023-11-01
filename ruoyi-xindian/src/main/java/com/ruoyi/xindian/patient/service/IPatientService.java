package com.ruoyi.xindian.patient.service;

import com.ruoyi.xindian.patient.domain.Patient;

import java.util.List;

/**
 * 患者Service接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface IPatientService
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
     * 批量删除患者
     *
     * @param patientIds 需要删除的患者主键集合
     * @return 结果
     */
    public int deletePatientByPatientIds(Long[] patientIds);

    /**
     * 删除患者信息
     *
     * @param patientId 患者主键
     * @return 结果
     */
    public int deletePatientByPatientId(Long patientId);


    /**
     * 删除患者信息(手机号)
     *
     * @param patientPhone 患者主键
     * @return 结果
     */
    public int deletePatientByPatientPhone(String patientPhone);

    /**
     * 查询患者
     *
     * @return 患者
     */
    public Patient selectPatientByPatientPhone(String patientPhone);

    /**
     * 查询患者
     *
     * @param patientName 患者姓名
     * @return 患者
     */
    public Patient selectPatientByPatientName(String patientName);

    public void updateMonitoringStatus();

    public void updateMonitoringStatus2(String equipment);

    public Patient selectPatientByNameAndPhone(Patient patient);


    Boolean detectionNumSubtract(String patientPhone );
    Boolean detectionNumAdd(String patientPhone );
}
