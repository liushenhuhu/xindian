package com.ruoyi.xindian.patient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.patient.domain.PatientRelationDoc;

import java.util.List;

/**
* @author 13401
* @description 针对表【patient_relation_doc】的数据库操作Service
* @createDate 2023-11-11 14:43:19
*/
public interface PatientRelationDocService extends IService<PatientRelationDoc> {


    /**
     * 根据条件查找
     * @param patientRelationDoc
     * @return
     */
    List<PatientRelationDoc> selectAllList(PatientRelationDoc patientRelationDoc) throws Exception;





    /**
     * 新增患者和医师关系
     * @param patientRelationDoc
     * @return
     */
    Boolean insertPatientRelationDoc(PatientRelationDoc patientRelationDoc) throws Exception;


    /**
     * 解除关系
     * @return
     */
    Boolean delPatientRelationDoc(PatientRelationDoc patientRelationDoc) throws Exception;



    Boolean updatePatientRelation(PatientRelationDoc patientRelationDoc);

}
