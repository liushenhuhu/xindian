package com.ruoyi.xindian.management.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.management.mapper.PatientManagementMapper;
import com.ruoyi.xindian.management.domain.PatientManagement;
import com.ruoyi.xindian.management.service.IPatientManagementService;

/**
 * 患者管理Service业务层处理
 * 
 * @author Han
 * @date 2022-07-22
 */
@Service
public class PatientManagementServiceImpl implements IPatientManagementService 
{
    @Autowired
    private PatientManagementMapper patientManagementMapper;

    /**
     * 查询患者管理
     * 
     * @param id 患者管理主键
     * @return 患者管理
     */
    @Override
    public PatientManagement selectPatientManagementById(String id)
    {
        return patientManagementMapper.selectPatientManagementById(id);
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
     * @param ids 需要删除的患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByIds(String[] ids)
    {
        return patientManagementMapper.deletePatientManagementByIds(ids);
    }

    /**
     * 删除患者管理信息
     * 
     * @param id 患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementById(String id)
    {
        return patientManagementMapper.deletePatientManagementById(id);
    }
}
