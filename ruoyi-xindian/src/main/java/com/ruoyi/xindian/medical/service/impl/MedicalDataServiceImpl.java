package com.ruoyi.xindian.medical.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.medical.mapper.MedicalDataMapper;
import com.ruoyi.xindian.medical.domain.MedicalData;
import com.ruoyi.xindian.medical.service.IMedicalDataService;

/**
 * 病数据Service业务层处理
 * 
 * @author hanhan
 * @date 2023-02-21
 */
@Service
public class MedicalDataServiceImpl implements IMedicalDataService 
{
    @Autowired
    private MedicalDataMapper medicalDataMapper;

    /**
     * 查询病数据
     * 
     * @param medicalDataId 病数据主键
     * @return 病数据
     */
    @Override
    public MedicalData selectMedicalDataByMedicalDataId(Long medicalDataId)
    {
        return medicalDataMapper.selectMedicalDataByMedicalDataId(medicalDataId);
    }

    /**
     * 查询病数据列表
     * 
     * @param medicalData 病数据
     * @return 病数据
     */
    @Override
    public List<MedicalData> selectMedicalDataList(MedicalData medicalData)
    {
        return medicalDataMapper.selectMedicalDataList(medicalData);
    }

    /**
     * 新增病数据
     * 
     * @param medicalData 病数据
     * @return 结果
     */
    @Override
    public int insertMedicalData(MedicalData medicalData)
    {
        return medicalDataMapper.insertMedicalData(medicalData);
    }

    /**
     * 修改病数据
     * 
     * @param medicalData 病数据
     * @return 结果
     */
    @Override
    public int updateMedicalData(MedicalData medicalData)
    {
        return medicalDataMapper.updateMedicalData(medicalData);
    }

    /**
     * 批量删除病数据
     * 
     * @param medicalDataIds 需要删除的病数据主键
     * @return 结果
     */
    @Override
    public int deleteMedicalDataByMedicalDataIds(Long[] medicalDataIds)
    {
        return medicalDataMapper.deleteMedicalDataByMedicalDataIds(medicalDataIds);
    }

    /**
     * 删除病数据信息
     * 
     * @param medicalDataId 病数据主键
     * @return 结果
     */
    @Override
    public int deleteMedicalDataByMedicalDataId(Long medicalDataId)
    {
        return medicalDataMapper.deleteMedicalDataByMedicalDataId(medicalDataId);
    }
}
