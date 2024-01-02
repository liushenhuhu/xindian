package com.ruoyi.xindian.patient.service;

import com.ruoyi.xindian.patient.domain.DoctorRelationPatient;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.patient.domain.Patient;

import java.util.List;

/**
* @author 13401
* @description 针对表【doctor_relation_patient】的数据库操作Service
* @createDate 2023-12-30 14:43:00
*/
public interface DoctorRelationPatientService extends IService<DoctorRelationPatient> {



    int insertDoctorRelationPatient(DoctorRelationPatient doctorRelationPatient);


    List<DoctorRelationPatient> selectDoctorRelationPatientList(DoctorRelationPatient doctorRelationPatient);






}
