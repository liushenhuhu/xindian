package com.ruoyi.xindian.medical.service;

import com.ruoyi.xindian.medical.domain.MedicalHistory;

import java.util.List;

/**
 * 病史Service接口
 * 
 * @author hanhan
 * @date 2023-02-21
 */
public interface IMedicalHistoryService 
{
    /**
     * 查询病史
     * 
     * @param medicalHistoryId 病史主键
     * @return 病史
     */
    public MedicalHistory selectMedicalHistoryByMedicalHistoryId(Long medicalHistoryId);

    public MedicalHistory selectMedicalHistoryByPatientPhone(String patientPhone);

    /**
     * 查询病史列表
     * 
     * @param medicalHistory 病史
     * @return 病史集合
     */
    public List<MedicalHistory> selectMedicalHistoryList(MedicalHistory medicalHistory);

    /**
     * 新增病史
     * 
     * @param medicalHistory 病史
     * @return 结果
     */
    public int insertMedicalHistory(MedicalHistory medicalHistory);

    /**
     * 修改病史
     * 
     * @param medicalHistory 病史
     * @return 结果
     */
    public int updateMedicalHistory(MedicalHistory medicalHistory);

    /**
     * 批量删除病史
     * 
     * @param medicalHistoryIds 需要删除的病史主键集合
     * @return 结果
     */
    public int deleteMedicalHistoryByMedicalHistoryIds(Long[] medicalHistoryIds);

    /**
     * 删除病史信息
     * 
     * @param medicalHistoryId 病史主键
     * @return 结果
     */
    public int deleteMedicalHistoryByMedicalHistoryId(Long medicalHistoryId);

    int deleteMedicalHistoryByPatientPhone(String patientPhone);
}
