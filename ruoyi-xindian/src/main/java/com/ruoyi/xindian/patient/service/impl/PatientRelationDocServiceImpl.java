package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.domain.PatientRelationDoc;
import com.ruoyi.xindian.patient.service.PatientRelationDocService;
import com.ruoyi.xindian.patient.mapper.PatientRelationDocMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 13401
* @description 针对表【patient_relation_doc】的数据库操作Service实现
* @createDate 2023-11-11 14:43:19
*/
@Service
public class PatientRelationDocServiceImpl extends ServiceImpl<PatientRelationDocMapper, PatientRelationDoc>
    implements PatientRelationDocService{


    @Resource
    private PatientRelationDocMapper patientRelationDocMapper;

    @Resource
    private AesUtils aesUtils;

    @Override
    public List<PatientRelationDoc> selectAllList(PatientRelationDoc patientRelationDoc) throws Exception {
        getPatientEncrypt(patientRelationDoc);
        List<PatientRelationDoc> patientRelationDocList = new ArrayList<>();
        if (!StringUtils.isAnyEmpty(patientRelationDoc.getDoctorPhone(),patientRelationDoc.getPatientPhone())){
            return new ArrayList<>();
        }
        if (StringUtils.isNotEmpty(patientRelationDoc.getDoctorPhone())){
            patientRelationDocList = selectPatient(patientRelationDoc);
        }else if (StringUtils.isNotEmpty(patientRelationDoc.getPatientPhone())){
            patientRelationDocList = selectDoc(patientRelationDoc);
        }

        return patientRelationDocList;
    }


    private List<PatientRelationDoc> selectPatient(PatientRelationDoc patientRelationDoc){
        return patientRelationDocMapper.selectAllList(patientRelationDoc).stream().peek(p->{
            try {
                getPatientDecrypt(p);
            } catch (Exception e) {
                throw new ServiceException("数据错误");
            }
            p.getPatients().forEach(d->
                    {
                        try {
                            getPatient(d);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    );

        }).collect(Collectors.toList());
    }

    private List<PatientRelationDoc> selectDoc(PatientRelationDoc patientRelationDoc){
        return patientRelationDocMapper.selectAllDocList(patientRelationDoc).stream().peek(p->{
            try {
                getPatientDecrypt(p);
            } catch (Exception e) {
                throw new ServiceException("数据错误");
            }
            p.getDoctors().forEach(d->
                    {
                        try {
                            getDocDecrypt(d);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

        }).collect(Collectors.toList());
    }

    private void getPatient(Patient patient) throws Exception {
        if (patient!=null){

            if (StringUtils.isNotEmpty(patient.getPatientName())){
                patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
            }
            if (StringUtils.isNotEmpty(patient.getPatientPhone())){
                patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
            }
            if (StringUtils.isNotEmpty(patient.getDoctorPhone())){
                patient.setDoctorPhone(aesUtils.decrypt(patient.getDoctorPhone()));
            }
        }

    }


    private void getDocDecrypt(Doctor doctor) throws Exception {
        if (doctor!=null){
            if(!StringUtils.isEmpty(doctor.getDoctorName())){
                doctor.setDoctorName(aesUtils.decrypt(doctor.getDoctorName()));
            }
            if(!StringUtils.isEmpty(doctor.getDoctorPhone())){
                doctor.setDoctorPhone(aesUtils.decrypt(doctor.getDoctorPhone()));
            }
        }
    }

    private void getPatientEncrypt(PatientRelationDoc patientRelationDoc) throws Exception {
        if (patientRelationDoc!=null){

            if (StringUtils.isNotEmpty(patientRelationDoc.getPatientPhone())){
                patientRelationDoc.setPatientPhone(aesUtils.encrypt(patientRelationDoc.getPatientPhone()));
            }
            if (StringUtils.isNotEmpty(patientRelationDoc.getDoctorPhone())){
                patientRelationDoc.setDoctorPhone(aesUtils.encrypt(patientRelationDoc.getDoctorPhone()));
            }
        }

    }
    private void getPatientDecrypt(PatientRelationDoc patientRelationDoc) throws Exception {
        if (patientRelationDoc!=null){

            if (StringUtils.isNotEmpty(patientRelationDoc.getPatientPhone())){
                patientRelationDoc.setPatientPhone(aesUtils.decrypt(patientRelationDoc.getPatientPhone()));
            }
            if (StringUtils.isNotEmpty(patientRelationDoc.getDoctorPhone())){
                patientRelationDoc.setDoctorPhone(aesUtils.decrypt(patientRelationDoc.getDoctorPhone()));
            }
        }

    }

    @Override
    public Boolean insertPatientRelationDoc(PatientRelationDoc patientRelationDoc) throws Exception {
        getPatientEncrypt(patientRelationDoc);

        //判断患者是否重复绑定相同的医师
        List<PatientRelationDoc> docs = patientRelationDocMapper.selectList(new QueryWrapper<PatientRelationDoc>().eq("patient_phone", patientRelationDoc.getPatientPhone()).eq("doctor_phone", patientRelationDoc.getDoctorPhone()));
        if (docs!=null&& !docs.isEmpty()){

            throw new ServiceException("数据已在处理，请勿重复绑定");
        }
        patientRelationDoc.setCreateTime(new Date());
        return patientRelationDocMapper.insert(patientRelationDoc)>0;
    }

    @Override
    public Boolean delPatientRelationDoc(PatientRelationDoc patientRelationDoc) throws Exception {
        getPatientEncrypt(patientRelationDoc);
        return patientRelationDocMapper.delete(new QueryWrapper<PatientRelationDoc>().eq("patient_phone",patientRelationDoc.getPatientPhone()).eq("doctor_phone",patientRelationDoc.getDoctorPhone()))>0;
    }

    @Override
    public Boolean updatePatientRelation(PatientRelationDoc patientRelationDoc) {
        return patientRelationDocMapper.update(patientRelationDoc,new QueryWrapper<PatientRelationDoc>().eq("relation_id",patientRelationDoc.getRelationId()))>0;
    }
}




