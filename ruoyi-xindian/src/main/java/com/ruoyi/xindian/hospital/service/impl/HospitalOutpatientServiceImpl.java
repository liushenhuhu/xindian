package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalOutpatientMapper;
import com.ruoyi.xindian.hospital.domain.HospitalOutpatient;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientService;

/**
 * 医院门诊表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class HospitalOutpatientServiceImpl implements IHospitalOutpatientService 
{
    @Autowired
    private HospitalOutpatientMapper hospitalOutpatientMapper;

    /**
     * 查询医院门诊表
     * 
     * @param id 医院门诊表主键
     * @return 医院门诊表
     */
    @Override
    public HospitalOutpatient selectHospitalOutpatientById(Long id)
    {
        return hospitalOutpatientMapper.selectHospitalOutpatientById(id);
    }

    /**
     * 查询医院门诊表列表
     * 
     * @param hospitalOutpatient 医院门诊表
     * @return 医院门诊表
     */
    @Override
    public List<HospitalOutpatient> selectHospitalOutpatientList(HospitalOutpatient hospitalOutpatient)
    {
        return hospitalOutpatientMapper.selectHospitalOutpatientList(hospitalOutpatient);
    }

    /**
     * 新增医院门诊表
     * 
     * @param hospitalOutpatient 医院门诊表
     * @return 结果
     */
    @Override
    public int insertHospitalOutpatient(HospitalOutpatient hospitalOutpatient)
    {
        return hospitalOutpatientMapper.insertHospitalOutpatient(hospitalOutpatient);
    }

    /**
     * 修改医院门诊表
     * 
     * @param hospitalOutpatient 医院门诊表
     * @return 结果
     */
    @Override
    public int updateHospitalOutpatient(HospitalOutpatient hospitalOutpatient)
    {
        return hospitalOutpatientMapper.updateHospitalOutpatient(hospitalOutpatient);
    }

    /**
     * 批量删除医院门诊表
     * 
     * @param ids 需要删除的医院门诊表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalOutpatientByIds(Long[] ids)
    {
        return hospitalOutpatientMapper.deleteHospitalOutpatientByIds(ids);
    }

    /**
     * 删除医院门诊表信息
     * 
     * @param id 医院门诊表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalOutpatientById(Long id)
    {
        return hospitalOutpatientMapper.deleteHospitalOutpatientById(id);
    }
}
