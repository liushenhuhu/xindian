package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalSpecialRelation;

/**
 * 关系表Mapper接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface HospitalSpecialRelationMapper 
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
     * 删除关系表
     * 
     * @param id 关系表主键
     * @return 结果
     */
    public int deleteHospitalSpecialRelationById(Long id);

    /**
     * 批量删除关系表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalSpecialRelationByIds(Long[] ids);
}
