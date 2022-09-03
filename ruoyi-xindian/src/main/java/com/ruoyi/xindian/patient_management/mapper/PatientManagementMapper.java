package com.ruoyi.xindian.patient_management.mapper;

import java.util.List;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;

/**
 * 患者管理Mapper接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface PatientManagementMapper
{
    /**
     * 查询患者管理
     *
     * @param pId 患者管理主键
     * @return 患者管理
     */
    public PatientManagement selectPatientManagementByPId(String pId);

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
     * @param pId 患者管理主键
     * @return 结果
     */
    public int deletePatientManagementByPId(String pId);

    /**
     * 批量删除患者管理
     *
     * @param pIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientManagementByPIds(String[] pIds);

    public void updateStatusAll();

    public void updateStatus(String[] pIds);
}
