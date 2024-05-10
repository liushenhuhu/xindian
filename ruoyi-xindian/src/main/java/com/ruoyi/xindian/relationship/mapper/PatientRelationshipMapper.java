package com.ruoyi.xindian.relationship.mapper;

import java.util.List;

import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import org.apache.ibatis.annotations.Param;

/**
 * 患者关系表Mapper接口
 *
 * @author chenpeng
 * @date 2023-06-30
 */
public interface PatientRelationshipMapper
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
    public List<PatientRelationship> selectPatientRelationshipListLimit(Limit limit);

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
     * 删除患者关系表
     *
     * @param id 患者关系表主键
     * @return 结果
     */
    public int deletePatientRelationshipById(Long id);

    /**
     * 批量删除患者关系表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientRelationshipByIds(Long[] ids);

    PatientRelationship selectFatherPhonePatientRelationship(@Param("phone") String phone);


    List<String> selectToAlertOpenids(@Param("fatherPhone") String fatherPhone);
}
