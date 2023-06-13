package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalSpecialRelation;

/**
 * 关系表Service接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface IHospitalSpecialRelationService 
{
    /**
     * 查询关系表
     * 
     * @param id 关系表主键
     * @return 关系表
     */
    public HospitalSpecialRelation selectHospitalSpecialRelationById(Long id);

    /**
     * 查询关系表列表
     * 
     * @param hospitalSpecialRelation 关系表
     * @return 关系表集合
     */
    public List<HospitalSpecialRelation> selectHospitalSpecialRelationList(HospitalSpecialRelation hospitalSpecialRelation);

    /**
     * 新增关系表
     * 
     * @param hospitalSpecialRelation 关系表
     * @return 结果
     */
    public int insertHospitalSpecialRelation(HospitalSpecialRelation hospitalSpecialRelation);

    /**
     * 修改关系表
     * 
     * @param hospitalSpecialRelation 关系表
     * @return 结果
     */
    public int updateHospitalSpecialRelation(HospitalSpecialRelation hospitalSpecialRelation);

    /**
     * 批量删除关系表
     * 
     * @param ids 需要删除的关系表主键集合
     * @return 结果
     */
    public int deleteHospitalSpecialRelationByIds(Long[] ids);

    /**
     * 删除关系表信息
     * 
     * @param id 关系表主键
     * @return 结果
     */
    public int deleteHospitalSpecialRelationById(Long id);
}
