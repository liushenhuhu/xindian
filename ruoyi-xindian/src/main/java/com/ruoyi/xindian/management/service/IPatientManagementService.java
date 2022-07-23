package com.ruoyi.xindian.management.service;

import java.util.List;
import com.ruoyi.xindian.management.domain.PatientManagement;

/**
 * 患者管理Service接口
 * 
 * @author Han
 * @date 2022-07-22
 */
public interface IPatientManagementService 
{
    /**
     * 查询患者管理
     * 
     * @param id 患者管理主键
     * @return 患者管理
     */
    public PatientManagement selectPatientManagementById(Long id);

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
     * 批量删除患者管理
     * 
     * @param ids 需要删除的患者管理主键集合
     * @return 结果
     */
    public int deletePatientManagementByIds(Long[] ids);

    /**
     * 删除患者管理信息
     * 
     * @param id 患者管理主键
     * @return 结果
     */
    public int deletePatientManagementById(Long id);
}
