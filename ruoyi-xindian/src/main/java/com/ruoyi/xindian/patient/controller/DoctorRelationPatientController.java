package com.ruoyi.xindian.patient.controller;

import com.ruoyi.xindian.patient.service.DoctorRelationPatientService;
import com.ruoyi.xindian.patient.service.IPatientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/patient/doctorRelationPatient")
public class DoctorRelationPatientController {



    @Resource
    private DoctorRelationPatientService doctorRelationPatientService;


    @Resource
    private IPatientService patientService;




}
