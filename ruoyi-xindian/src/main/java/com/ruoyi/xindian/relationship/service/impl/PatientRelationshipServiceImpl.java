package com.ruoyi.xindian.relationship.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.relationship.mapper.PatientRelationshipMapper;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.service.IPatientRelationshipService;

/**
 * 患者关系表Service业务层处理
 *
 * @author chenpeng
 * @date 2023-06-30
 */
@Service
public class PatientRelationshipServiceImpl implements IPatientRelationshipService
{
    @Autowired
    private PatientRelationshipMapper patientRelationshipMapper;

    /**
     * 查询患者关系表
     *
     * @param id 患者关系表主键
     * @return 患者关系表
     */
    @Override
    public PatientRelationship selectPatientRelationshipById(Long id)
    {
        return patientRelationshipMapper.selectPatientRelationshipById(id);
    }

    /**
     * 查询患者关系表列表
     *
     * @param patientRelationship 患者关系表
     * @return 患者关系表
     */
    @Override
    public List<PatientRelationship> selectPatientRelationshipList(PatientRelationship patientRelationship)
    {
        return patientRelationshipMapper.selectPatientRelationshipList(patientRelationship);
    }

    /**
     * 新增患者关系表
     *
     * @param patientRelationship 患者关系表
     * @return 结果
     */
    @Override
    public int insertPatientRelationship(PatientRelationship patientRelationship)
    {
        return patientRelationshipMapper.insertPatientRelationship(patientRelationship);
    }

    /**
     * 修改患者关系表
     *
     * @param patientRelationship 患者关系表
     * @return 结果
     */
    @Override
    public int updatePatientRelationship(PatientRelationship patientRelationship)
    {
        return patientRelationshipMapper.updatePatientRelationship(patientRelationship);
    }

    /**
     * 批量删除患者关系表
     *
     * @param ids 需要删除的患者关系表主键
     * @return 结果
     */
    @Override
    public int deletePatientRelationshipByIds(Long[] ids)
    {
        return patientRelationshipMapper.deletePatientRelationshipByIds(ids);
    }

    /**
     * 删除患者关系表信息
     *
     * @param id 患者关系表主键
     * @return 结果
     */
    @Override
    public int deletePatientRelationshipById(Long id)
    {
        return patientRelationshipMapper.deletePatientRelationshipById(id);
    }
}

