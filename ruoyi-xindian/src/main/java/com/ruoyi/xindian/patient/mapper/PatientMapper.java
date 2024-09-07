package com.ruoyi.xindian.patient.mapper;

import com.ruoyi.xindian.patient.domain.DoctorRelationPatient;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient_management.vo.Limit;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

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
    public int updatePatientAes(Patient patient);

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
     * @param patientPhone 患者身份证号
     * @return 患者
     */
    public Patient selectPatientByPatientPhone(String patientPhone);


    List<Patient> selectPatientListByPhoneList(@Param("patientPhones") String[] patientPhones);


    List<Patient> selectPatientByCode(@Param("equipmentCode") String equipmentCode);

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

    public int delPatientByPatientPhone(String patientPhone);


    int updateDetectionNumInt(@Param("patientPhone") String patientPhone);


    List<Patient> selectAlertLogListPatients(Limit limit);


    Patient selectPatientByPatientPhoneAndHospitalId(String patientPhone);


    List<Patient> selectByDoc(DoctorRelationPatient doctorRelationPatient);

    int updateStatusPhone(@Param("phone") String phone);
}
