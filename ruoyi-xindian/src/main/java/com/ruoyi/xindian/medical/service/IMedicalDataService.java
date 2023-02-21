package com.ruoyi.xindian.medical.service;

import java.util.List;
import com.ruoyi.xindian.medical.domain.MedicalData;

/**
 * 病数据Service接口
 * 
 * @author hanhan
 * @date 2023-02-21
 */
public interface IMedicalDataService 
{
    /**
     * 查询病数据
     * 
     * @param medicalDataId 病数据主键
     * @return 病数据
     */
    public MedicalData selectMedicalDataByMedicalDataId(Long medicalDataId);

    /**
     * 查询病数据列表
     * 
     * @param medicalData 病数据
     * @return 病数据集合
     */
    public List<MedicalData> selectMedicalDataList(MedicalData medicalData);

    /**
     * 新增病数据
     * 
     * @param medicalData 病数据
     * @return 结果
     */
    public int insertMedicalData(MedicalData medicalData);

    /**
     * 修改病数据
     * 
     * @param medicalData 病数据
     * @return 结果
     */
    public int updateMedicalData(MedicalData medicalData);

    /**
     * 批量删除病数据
     * 
     * @param medicalDataIds 需要删除的病数据主键集合
     * @return 结果
     */
    public int deleteMedicalDataByMedicalDataIds(Long[] medicalDataIds);

    /**
     * 删除病数据信息
     * 
     * @param medicalDataId 病数据主键
     * @return 结果
     */
    public int deleteMedicalDataByMedicalDataId(Long medicalDataId);
}
