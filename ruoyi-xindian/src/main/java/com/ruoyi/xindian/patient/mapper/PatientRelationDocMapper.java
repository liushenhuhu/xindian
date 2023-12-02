package com.ruoyi.xindian.patient.mapper;

import com.ruoyi.xindian.patient.domain.PatientRelationDoc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 13401
* @description 针对表【patient_relation_doc】的数据库操作Mapper
* @createDate 2023-11-11 14:43:18
* @Entity com.ruoyi.xindian.patient.domain.PatientRelationDoc
*/
public interface PatientRelationDocMapper extends BaseMapper<PatientRelationDoc> {



    List<PatientRelationDoc> selectAllList(PatientRelationDoc patientRelationDoc);

    List<PatientRelationDoc> selectAllDocList(PatientRelationDoc patientRelationDoc);


}




