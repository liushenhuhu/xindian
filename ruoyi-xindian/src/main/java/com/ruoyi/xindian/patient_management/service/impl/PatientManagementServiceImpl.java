package com.ruoyi.xindian.patient_management.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.patient_management.mapper.PatientManagementMapper;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;

/**
 * 患者管理Service业务层处理
 * 
 * @author hanhan
 * @date 2022-08-20
 */
@Service
public class PatientManagementServiceImpl implements IPatientManagementService 
{
    @Autowired
    private PatientManagementMapper patientManagementMapper;

    /**
     * 查询患者管理
     * 
     * @param pId 患者管理主键
     * @return 患者管理
     */
    @Override
    public PatientManagement selectPatientManagementByPId(Long pId)
    {
        return patientManagementMapper.selectPatientManagementByPId(pId);
    }

    /**
     * 查询患者管理列表
     * 
     * @param patientManagement 患者管理
     * @return 患者管理
     */
    @Override
    public List<PatientManagement> selectPatientManagementList(PatientManagement patientManagement)
    {
        return patientManagementMapper.selectPatientManagementList(patientManagement);
    }

    /**
     * 新增患者管理
     * 
     * @param patientManagement 患者管理
     * @return 结果
     */
    @Override
    public int insertPatientManagement(PatientManagement patientManagement)
    {
        return patientManagementMapper.insertPatientManagement(patientManagement);
    }

    /**
     * 修改患者管理
     * 
     * @param patientManagement 患者管理
     * @return 结果
     */
    @Override
    public int updatePatientManagement(PatientManagement patientManagement)
    {
        return patientManagementMapper.updatePatientManagement(patientManagement);
    }

    /**
     * 批量删除患者管理
     * 
     * @param pIds 需要删除的患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPIds(Long[] pIds)
    {
        return patientManagementMapper.deletePatientManagementByPIds(pIds);
    }

    /**
     * 删除患者管理信息
     * 
     * @param pId 患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPId(Long pId)
    {
        return patientManagementMapper.deletePatientManagementByPId(pId);
    }
}
