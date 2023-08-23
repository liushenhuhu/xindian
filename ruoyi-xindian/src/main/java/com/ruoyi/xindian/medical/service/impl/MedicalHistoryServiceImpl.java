package com.ruoyi.xindian.medical.service.impl;

import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.mapper.MedicalHistoryMapper;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 病史Service业务层处理
 * 
 * @author hanhan
 * @date 2023-02-21
 */
@Service
public class MedicalHistoryServiceImpl implements IMedicalHistoryService 
{
    @Autowired
    private MedicalHistoryMapper medicalHistoryMapper;

    @Resource
    private AesUtils aesUtils;

    /**
     * 查询病史
     * 
     * @param medicalHistoryId 病史主键
     * @return 病史
     */
    @Override
    public MedicalHistory selectMedicalHistoryByMedicalHistoryId(Long medicalHistoryId)
    {
        return medicalHistoryMapper.selectMedicalHistoryByMedicalHistoryId(medicalHistoryId);
    }

    @Override
    public MedicalHistory selectMedicalHistoryByPatientPhone(String patientPhone) {
        return medicalHistoryMapper.selectMedicalHistoryByPatientPhone(patientPhone);
    }

    /**
     * 查询病史列表
     * 
     * @param medicalHistory 病史
     * @return 病史
     */
    @Override
    public List<MedicalHistory> selectMedicalHistoryList(MedicalHistory medicalHistory)
    {
        return medicalHistoryMapper.selectMedicalHistoryList(medicalHistory);
    }

    /**
     * 新增病史
     * 
     * @param medicalHistory 病史
     * @return 结果
     */
    @Override
    public int insertMedicalHistory(MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory.getPatientPhone()!=null&&!"".equals(medicalHistory.getPatientPhone())){
            medicalHistory.setPatientPhoneAes(aesUtils.decrypt(medicalHistory.getPatientPhone()));
        }
        return medicalHistoryMapper.insertMedicalHistory(medicalHistory);
    }

    /**
     * 修改病史
     * 
     * @param medicalHistory 病史
     * @return 结果
     */
    @Override
    public int updateMedicalHistory(MedicalHistory medicalHistory)
    {
        return medicalHistoryMapper.updateMedicalHistory(medicalHistory);
    }

    /**
     * 批量删除病史
     * 
     * @param medicalHistoryIds 需要删除的病史主键
     * @return 结果
     */
    @Override
    public int deleteMedicalHistoryByMedicalHistoryIds(Long[] medicalHistoryIds)
    {
        return medicalHistoryMapper.deleteMedicalHistoryByMedicalHistoryIds(medicalHistoryIds);
    }

    /**
     * 删除病史信息
     * 
     * @param medicalHistoryId 病史主键
     * @return 结果
     */
    @Override
    public int deleteMedicalHistoryByMedicalHistoryId(Long medicalHistoryId)
    {
        return medicalHistoryMapper.deleteMedicalHistoryByMedicalHistoryId(medicalHistoryId);
    }

    @Override
    public int deleteMedicalHistoryByPatientPhone(String patientPhone) {
        return medicalHistoryMapper.deleteMedicalHistoryByPatientPhone(patientPhone);
    }
}
