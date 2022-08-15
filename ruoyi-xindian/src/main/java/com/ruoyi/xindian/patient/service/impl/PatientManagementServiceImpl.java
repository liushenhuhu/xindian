package com.ruoyi.xindian.patient.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.patient.mapper.PatientManagementMapper;
import com.ruoyi.xindian.patient.domain.PatientManagement;
import com.ruoyi.xindian.patient.service.IPatientManagementService;

/**
 * 患者管理Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-15
 */
@Service
public class PatientManagementServiceImpl implements IPatientManagementService
{
    @Autowired
    private PatientManagementMapper patientManagementMapper;

    /**
     * 查询患者管理
     *
     * @param patientId 患者管理主键
     * @return 患者管理
     */
    @Override
    public PatientManagement selectPatientManagementByPatientId(String patientId)
    {
        return patientManagementMapper.selectPatientManagementByPatientId(patientId);
    }

    /**
     * 按身份证号查询患者管理
     *
     * @param patientNumber 患者身份证号
     * @return 患者管理
     */
    @Override
    public PatientManagement selectPatientManagementByPatientNumber(String patientNumber) {
        return patientManagementMapper.selectPatientManagementByPatientNumber(patientNumber);
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
     * @param patientIds 需要删除的患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPatientIds(String[] patientIds)
    {
        return patientManagementMapper.deletePatientManagementByPatientIds(patientIds);
    }

    /**
     * 删除患者管理信息
     *
     * @param patientId 患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPatientId(String patientId)
    {
        return patientManagementMapper.deletePatientManagementByPatientId(patientId);
    }
}
