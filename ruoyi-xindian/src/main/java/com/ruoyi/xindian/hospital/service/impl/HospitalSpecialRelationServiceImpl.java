package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalSpecialRelationMapper;
import com.ruoyi.xindian.hospital.domain.HospitalSpecialRelation;
import com.ruoyi.xindian.hospital.service.IHospitalSpecialRelationService;

/**
 * 关系表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class HospitalSpecialRelationServiceImpl implements IHospitalSpecialRelationService 
{
    @Autowired
    private HospitalSpecialRelationMapper hospitalSpecialRelationMapper;

    /**
     * 查询关系表
     * 
     * @param id 关系表主键
     * @return 关系表
     */
    @Override
    public HospitalSpecialRelation selectHospitalSpecialRelationById(Long id)
    {
        return hospitalSpecialRelationMapper.selectHospitalSpecialRelationById(id);
    }

    /**
     * 查询关系表列表
     * 
     * @param hospitalSpecialRelation 关系表
     * @return 关系表
     */
    @Override
    public List<HospitalSpecialRelation> selectHospitalSpecialRelationList(HospitalSpecialRelation hospitalSpecialRelation)
    {
        return hospitalSpecialRelationMapper.selectHospitalSpecialRelationList(hospitalSpecialRelation);
    }

    /**
     * 新增关系表
     * 
     * @param hospitalSpecialRelation 关系表
     * @return 结果
     */
    @Override
    public int insertHospitalSpecialRelation(HospitalSpecialRelation hospitalSpecialRelation)
    {
        return hospitalSpecialRelationMapper.insertHospitalSpecialRelation(hospitalSpecialRelation);
    }

    /**
     * 修改关系表
     * 
     * @param hospitalSpecialRelation 关系表
     * @return 结果
     */
    @Override
    public int updateHospitalSpecialRelation(HospitalSpecialRelation hospitalSpecialRelation)
    {
        return hospitalSpecialRelationMapper.updateHospitalSpecialRelation(hospitalSpecialRelation);
    }

    /**
     * 批量删除关系表
     * 
     * @param ids 需要删除的关系表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSpecialRelationByIds(Long[] ids)
    {
        return hospitalSpecialRelationMapper.deleteHospitalSpecialRelationByIds(ids);
    }

    /**
     * 删除关系表信息
     * 
     * @param id 关系表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSpecialRelationById(Long id)
    {
        return hospitalSpecialRelationMapper.deleteHospitalSpecialRelationById(id);
    }
}
