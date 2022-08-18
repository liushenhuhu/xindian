package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;

/**
 * 医院Service业务层处理
 * 
 * @author hanhan
 * @date 2022-08-15
 */
@Service
public class HospitalServiceImpl implements IHospitalService 
{
    @Autowired
    private HospitalMapper hospitalMapper;

    /**
     * 查询医院
     * 
     * @param hospitalId 医院主键
     * @return 医院
     */
    @Override
    public Hospital selectHospitalByHospitalId(String hospitalId)
    {
        return hospitalMapper.selectHospitalByHospitalId(hospitalId);
    }

    /**
     * 查询医院列表
     * 
     * @param hospital 医院
     * @return 医院
     */
    @Override
    public List<Hospital> selectHospitalList(Hospital hospital)
    {
        return hospitalMapper.selectHospitalList(hospital);
    }

    /**
     * 新增医院
     * 
     * @param hospital 医院
     * @return 结果
     */
    @Override
    public int insertHospital(Hospital hospital)
    {
        return hospitalMapper.insertHospital(hospital);
    }

    /**
     * 修改医院
     * 
     * @param hospital 医院
     * @return 结果
     */
    @Override
    public int updateHospital(Hospital hospital)
    {
        return hospitalMapper.updateHospital(hospital);
    }

    /**
     * 批量删除医院
     * 
     * @param hospitalIds 需要删除的医院主键
     * @return 结果
     */
    @Override
    public int deleteHospitalByHospitalIds(String[] hospitalIds)
    {
        return hospitalMapper.deleteHospitalByHospitalIds(hospitalIds);
    }

    /**
     * 删除医院信息
     * 
     * @param hospitalId 医院主键
     * @return 结果
     */
    @Override
    public int deleteHospitalByHospitalId(String hospitalId)
    {
        return hospitalMapper.deleteHospitalByHospitalId(hospitalId);
    }
}
