package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalOutpatientRelation;

/**
 * 医院门诊关系表Service接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface IHospitalOutpatientRelationService 
{
    /**
     * 查询医院门诊关系表
     * 
     * @param id 医院门诊关系表主键
     * @return 医院门诊关系表
     */
    public HospitalOutpatientRelation selectHospitalOutpatientRelationById(Long id);

    /**
     * 查询医院门诊关系表列表
     * 
     * @param hospitalOutpatientRelation 医院门诊关系表
     * @return 医院门诊关系表集合
     */
    public List<HospitalOutpatientRelation> selectHospitalOutpatientRelationList(HospitalOutpatientRelation hospitalOutpatientRelation);

    /**
     * 新增医院门诊关系表
     * 
     * @param hospitalOutpatientRelation 医院门诊关系表
     * @return 结果
     */
    public int insertHospitalOutpatientRelation(HospitalOutpatientRelation hospitalOutpatientRelation);

    /**
     * 修改医院门诊关系表
     * 
     * @param hospitalOutpatientRelation 医院门诊关系表
     * @return 结果
     */
    public int updateHospitalOutpatientRelation(HospitalOutpatientRelation hospitalOutpatientRelation);

    /**
     * 批量删除医院门诊关系表
     * 
     * @param ids 需要删除的医院门诊关系表主键集合
     * @return 结果
     */
    public int deleteHospitalOutpatientRelationByIds(Long[] ids);

    /**
     * 删除医院门诊关系表信息
     * 
     * @param id 医院门诊关系表主键
     * @return 结果
     */
    public int deleteHospitalOutpatientRelationById(Long id);
}
