package com.ruoyi.xindian.patient.service;

import com.ruoyi.xindian.patient.domain.PatientLogin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author w
* @description 针对表【patient_login】的数据库操作Service
* @createDate 2024-08-14 09:17:51
*/
public interface PatientLoginService extends IService<PatientLogin> {

    int addLoginLog(PatientLogin patientLogin) throws Exception;

    Map<String,Object> getCount(PatientLogin patientLogin);
}
