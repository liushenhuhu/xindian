package com.ruoyi.xindian.management.mapper;

import java.util.List;
import com.ruoyi.xindian.management.domain.PatientManagement;

/**
 * 患者管理Mapper接口
 * 
 * @author Han
 * @date 2022-07-22
 */
public interface PatientManagementMapper 
{
    /**
     * 查询患者管理
     * 
     * @param id 患者管理主键
     * @return 患者管理
     */
    public PatientManagement selectPatientManagementById(String id);

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
     * @param id 患者管理主键
     * @return 结果
     */
    public int deletePatientManagementById(String id);

    /**
     * 批量删除患者管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientManagementByIds(String[] ids);
}
