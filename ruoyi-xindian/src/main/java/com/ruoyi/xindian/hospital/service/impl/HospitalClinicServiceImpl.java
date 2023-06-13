package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalClinicMapper;
import com.ruoyi.xindian.hospital.domain.HospitalClinic;
import com.ruoyi.xindian.hospital.service.IHospitalClinicService;

/**
 * 诊室信息表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class HospitalClinicServiceImpl implements IHospitalClinicService 
{
    @Autowired
    private HospitalClinicMapper hospitalClinicMapper;

    /**
     * 查询诊室信息表
     * 
     * @param id 诊室信息表主键
     * @return 诊室信息表
     */
    @Override
    public HospitalClinic selectHospitalClinicById(Long id)
    {
        return hospitalClinicMapper.selectHospitalClinicById(id);
    }

    /**
     * 查询诊室信息表列表
     * 
     * @param hospitalClinic 诊室信息表
     * @return 诊室信息表
     */
    @Override
    public List<HospitalClinic> selectHospitalClinicList(HospitalClinic hospitalClinic)
    {
        return hospitalClinicMapper.selectHospitalClinicList(hospitalClinic);
    }

    /**
     * 新增诊室信息表
     * 
     * @param hospitalClinic 诊室信息表
     * @return 结果
     */
    @Override
    public int insertHospitalClinic(HospitalClinic hospitalClinic)
    {
        return hospitalClinicMapper.insertHospitalClinic(hospitalClinic);
    }

    /**
     * 修改诊室信息表
     * 
     * @param hospitalClinic 诊室信息表
     * @return 结果
     */
    @Override
    public int updateHospitalClinic(HospitalClinic hospitalClinic)
    {
        return hospitalClinicMapper.updateHospitalClinic(hospitalClinic);
    }

    /**
     * 批量删除诊室信息表
     * 
     * @param ids 需要删除的诊室信息表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalClinicByIds(Long[] ids)
    {
        return hospitalClinicMapper.deleteHospitalClinicByIds(ids);
    }

    /**
     * 删除诊室信息表信息
     * 
     * @param id 诊室信息表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalClinicById(Long id)
    {
        return hospitalClinicMapper.deleteHospitalClinicById(id);
    }
}
