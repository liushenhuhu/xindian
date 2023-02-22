package com.ruoyi.xindian.medical.mapper;

import com.ruoyi.xindian.medical.domain.MedicalHistory;

import java.util.List;

/**
 * 病史Mapper接口
 * 
 * @author hanhan
 * @date 2023-02-21
 */
public interface MedicalHistoryMapper 
{
    /**
     * 查询病史
     * 
     * @param medicalHistoryId 病史主键
     * @return 病史
     */
    public MedicalHistory selectMedicalHistoryByMedicalHistoryId(Long medicalHistoryId);

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
     * 删除病史
     * 
     * @param medicalHistoryId 病史主键
     * @return 结果
     */
    public int deleteMedicalHistoryByMedicalHistoryId(Long medicalHistoryId);

    /**
     * 批量删除病史
     * 
     * @param medicalHistoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedicalHistoryByMedicalHistoryIds(Long[] medicalHistoryIds);

    MedicalHistory selectMedicalHistoryByPatientPhone(String patientPhone);

    int deleteMedicalHistoryByPatientPhone(String patientPhone);
}
