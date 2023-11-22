package com.ruoyi.xindian.patient.service;

import com.ruoyi.xindian.patient.domain.PatientBlood;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【patient_blood】的数据库操作Service
* @createDate 2023-11-16 17:09:20
*/
public interface PatientBloodService extends IService<PatientBlood> {
    /**
     * 查询【患者血压】
     *
     * @param bloodId 【患者血压】主键
     * @return 【患者血压】
     */
    public PatientBlood selectPatientBloodByBloodId(String bloodId) throws Exception;

    /**
     * 查询【患者血压】列表
     *
     * @param patientBlood 【患者血压】
     * @return 【患者血压】集合
     */
    public List<PatientBlood> selectPatientBloodList(PatientBlood patientBlood) throws Exception;



    List<PatientBlood> selectPatientBloodStatistics(PatientBlood patientBlood) throws Exception;

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
     * 批量删除【患者血压】
     *
     * @param bloodIds 需要删除的【患者血压】主键集合
     * @return 结果
     */
    public int deletePatientBloodByBloodIds(String[] bloodIds);

    /**
     * 删除【患者血压】信息
     *
     * @param bloodId 【患者血压】主键
     * @return 结果
     */
    public int deletePatientBloodByBloodId(String bloodId);
}
