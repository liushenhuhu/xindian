package com.ruoyi.xindian.relationship.service;

import java.util.List;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;

/**
 * 患者关系表Service接口
 *
 * @author chenpeng
 * @date 2023-06-30
 */
public interface IPatientRelationshipService
{
    /**
     * 查询患者关系表
     *
     * @param id 患者关系表主键
     * @return 患者关系表
     */
    public PatientRelationship selectPatientRelationshipById(Long id);

    /**
     * 查询患者关系表列表
     *
     * @param patientRelationship 患者关系表
     * @return 患者关系表集合
     */
    public List<PatientRelationship> selectPatientRelationshipList(PatientRelationship patientRelationship);

    /**
     * 新增患者关系表
     *
     * @param patientRelationship 患者关系表
     * @return 结果
     */
    public int insertPatientRelationship(PatientRelationship patientRelationship);

    /**
     * 修改患者关系表
     *
     * @param patientRelationship 患者关系表
     * @return 结果
     */
    public int updatePatientRelationship(PatientRelationship patientRelationship);

    /**
     * 批量删除患者关系表
     *
     * @param ids 需要删除的患者关系表主键集合
     * @return 结果
     */
    public int deletePatientRelationshipByIds(Long[] ids);

    /**
     * 删除患者关系表信息
     *
     * @param id 患者关系表主键
     * @return 结果
     */
    public int deletePatientRelationshipById(Long id);
}

