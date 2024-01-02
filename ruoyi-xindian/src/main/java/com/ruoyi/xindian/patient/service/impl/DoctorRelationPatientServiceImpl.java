package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.patient.domain.DoctorRelationPatient;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.DoctorRelationPatientService;
import com.ruoyi.xindian.patient.mapper.DoctorRelationPatientMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 13401
* @description 针对表【doctor_relation_patient】的数据库操作Service实现
* @createDate 2023-12-30 14:43:00
*/
@Service
public class DoctorRelationPatientServiceImpl extends ServiceImpl<DoctorRelationPatientMapper, DoctorRelationPatient>
    implements DoctorRelationPatientService{


    @Resource
    private DoctorRelationPatientMapper doctorRelationPatientMapper;
    @Override
    public int insertDoctorRelationPatient(DoctorRelationPatient doctorRelationPatient) {
        doctorRelationPatient.setCreateTime(new Date());
        return doctorRelationPatientMapper.insert(doctorRelationPatient);
    }

    @Override
    public List<DoctorRelationPatient> selectDoctorRelationPatientList(DoctorRelationPatient doctorRelationPatient) {
        return doctorRelationPatientMapper.selectList(new QueryWrapper<DoctorRelationPatient>()
                .eq(doctorRelationPatient.getDoctorPhone()!= null, "doctor_phone",
                        doctorRelationPatient.getDoctorPhone()).eq(doctorRelationPatient.getPatientPhone()!= null, "patient_phone",doctorRelationPatient.getPatientPhone()));
    }


}




