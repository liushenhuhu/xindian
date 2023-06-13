package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalOutpatientRelationMapper;
import com.ruoyi.xindian.hospital.domain.HospitalOutpatientRelation;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientRelationService;

/**
 * 医院门诊关系表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class HospitalOutpatientRelationServiceImpl implements IHospitalOutpatientRelationService 
{
    @Autowired
    private HospitalOutpatientRelationMapper hospitalOutpatientRelationMapper;

    /**
     * 查询医院门诊关系表
     * 
     * @param id 医院门诊关系表主键
     * @return 医院门诊关系表
     */
    @Override
    public HospitalOutpatientRelation selectHospitalOutpatientRelationById(Long id)
    {
        return hospitalOutpatientRelationMapper.selectHospitalOutpatientRelationById(id);
    }

    /**
     * 查询医院门诊关系表列表
     * 
     * @param hospitalOutpatientRelation 医院门诊关系表
     * @return 医院门诊关系表
     */
    @Override
    public List<HospitalOutpatientRelation> selectHospitalOutpatientRelationList(HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        return hospitalOutpatientRelationMapper.selectHospitalOutpatientRelationList(hospitalOutpatientRelation);
    }

    /**
     * 新增医院门诊关系表
     * 
     * @param hospitalOutpatientRelation 医院门诊关系表
     * @return 结果
     */
    @Override
    public int insertHospitalOutpatientRelation(HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        return hospitalOutpatientRelationMapper.insertHospitalOutpatientRelation(hospitalOutpatientRelation);
    }

    /**
     * 修改医院门诊关系表
     * 
     * @param hospitalOutpatientRelation 医院门诊关系表
     * @return 结果
     */
    @Override
    public int updateHospitalOutpatientRelation(HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        return hospitalOutpatientRelationMapper.updateHospitalOutpatientRelation(hospitalOutpatientRelation);
    }

    /**
     * 批量删除医院门诊关系表
     * 
     * @param ids 需要删除的医院门诊关系表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalOutpatientRelationByIds(Long[] ids)
    {
        return hospitalOutpatientRelationMapper.deleteHospitalOutpatientRelationByIds(ids);
    }

    /**
     * 删除医院门诊关系表信息
     * 
     * @param id 医院门诊关系表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalOutpatientRelationById(Long id)
    {
        return hospitalOutpatientRelationMapper.deleteHospitalOutpatientRelationById(id);
    }
}
