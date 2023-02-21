package com.ruoyi.xindian.patient_management.service.impl;

import java.util.List;

import com.ruoyi.xindian.patient_management.domain.SingleHistoryInfo;
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
public class PatientManagementServiceImpl implements IPatientManagementService {
    @Autowired
    private PatientManagementMapper patientManagementMapper;


    /**
     * 查询患者管理
     *
     * @param pId 患者管理主键
     * @return 患者管理
     */
    @Override
    public PatientManagement selectPatientManagementByPId(String pId) {
        return patientManagementMapper.selectPatientManagementByPId(pId);
    }

    /**
     * 查询患者管理列表
     *
     * @param patientManagement 患者管理
     * @return 患者管理
     */
    @Override
    public List<PatientManagement> selectPatientManagementList(PatientManagement patientManagement) {
        return patientManagementMapper.selectPatientManagementList(patientManagement);
    }

    @Override
    public List<PatientManagement> selectListOrderByTime(PatientManagement patientManagement) {
        return patientManagementMapper.selectListOrderByTime(patientManagement);
    }

    @Override
    public List<PatientManagement> selectPatientManagementListDECGsingle(PatientManagement patientManagement) {
        return patientManagementMapper.selectPatientManagementListDECGsingle(patientManagement);
    }

    @Override
    public List<PatientManagement> selectPatientManagementListJECG12(PatientManagement patientManagement) {
        return patientManagementMapper.selectPatientManagementListJECG12(patientManagement);
    }


    /**
     * 新增患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    @Override
    public int insertPatientManagement(PatientManagement patientManagement) {
        return patientManagementMapper.insertPatientManagement(patientManagement);
    }

    /**
     * 修改患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    @Override
    public int updatePatientManagement(PatientManagement patientManagement) {
        return patientManagementMapper.updatePatientManagement(patientManagement);
    }

    /**
     * 批量删除患者管理
     *
     * @param pIds 需要删除的患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPIds(String[] pIds) {
        return patientManagementMapper.deletePatientManagementByPIds(pIds);
    }

    /**
     * 删除患者管理信息
     *
     * @param pId 患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPId(String pId) {
        return patientManagementMapper.deletePatientManagementByPId(pId);
    }

    @Override
    public void updateStatusAll() {
        patientManagementMapper.updateStatusAll();
    }

    @Override
    public void updateStatus(String[] pIds) {
        patientManagementMapper.updateStatus(pIds);
    }

    @Override
    public List<SingleHistoryInfo> selectSingleHistoryInfoList(SingleHistoryInfo singleHistoryInfo) {
        return patientManagementMapper.selectSingleHistoryInfoList(singleHistoryInfo);
    }

}
