package com.ruoyi.xindian.patient.mapper;

import java.util.List;
import com.ruoyi.xindian.patient.domain.PatientManagement;

/**
 * 患者管理Mapper接口
 *
 * @author hanhan
 * @date 2022-08-15
 */
public interface PatientManagementMapper
{
    /**
     * 查询患者管理
     *
     * @param patientId 患者管理主键
     * @return 患者管理
     */
    public PatientManagement selectPatientManagementByPatientId(String patientId);

    /**
     * 查询患者管理
     *
     * @param patientNumber 患者身份证号
     * @return 患者管理
     */
    public PatientManagement selectPatientManagementByPatientNumber(String patientNumber);

    /**
     * 查询患者管理列表
     *
     * @param patientManagement 患者管理
     * @return 患者管理集合
     */
    public List<PatientManagement> selectPatientManagementList(PatientManagement patientManagement);

    /**
     * 新增患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    public int insertPatientManagement(PatientManagement patientManagement);

    /**
     * 修改患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    public int updatePatientManagement(PatientManagement patientManagement);

    /**
     * 删除患者管理
     *
     * @param patientId 患者管理主键
     * @return 结果
     */
    public int deletePatientManagementByPatientId(String patientId);

    /**
     * 批量删除患者管理
     *
     * @param patientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientManagementByPatientIds(String[] patientIds);
}
