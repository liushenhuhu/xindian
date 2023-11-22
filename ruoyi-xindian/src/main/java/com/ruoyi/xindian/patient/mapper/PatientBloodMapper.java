package com.ruoyi.xindian.patient.mapper;

import com.ruoyi.xindian.patient.domain.PatientBlood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 13401
* @description 针对表【patient_blood】的数据库操作Mapper
* @createDate 2023-11-16 17:09:20
* @Entity com.ruoyi.xindian.patient.domain.PatientBlood
*/
public interface PatientBloodMapper extends BaseMapper<PatientBlood> {
    /**
     * 查询【患者血压】
     *
     * @param bloodId 【患者血压】主键
     * @return 【患者血压】
     */
    public PatientBlood selectPatientBloodByBloodId(String bloodId);

    /**
     * 查询【患者血压】列表
     *
     * @param patientBlood 【患者血压】
     * @return 【患者血压】集合
     */
    public List<PatientBlood> selectPatientBloodList(PatientBlood patientBlood);

    /**
     * 查询统计【患者血压】列表
     *
     * @param patientBlood 【患者血压】
     * @return 【患者血压】集合
     */
    public List<PatientBlood> selectPatientBloodStatistics(PatientBlood patientBlood);

    /**
     * 新增【患者血压】
     *
     * @param patientBlood 【患者血压】
     * @return 结果
     */
    public int insertPatientBlood(PatientBlood patientBlood);

    /**
     * 修改【患者血压】
     *
     * @param patientBlood 【患者血压】
     * @return 结果
     */
    public int updatePatientBlood(PatientBlood patientBlood);

    /**
     * 删除【患者血压】
     *
     * @param bloodId 【患者血压】主键
     * @return 结果
     */
    public int deletePatientBloodByBloodId(String bloodId);

    /**
     * 批量删除【患者血压】
     *
     * @param bloodIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientBloodByBloodIds(String[] bloodIds);
}




