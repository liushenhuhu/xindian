package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient.domain.PatientLogin;
import com.ruoyi.xindian.patient.service.PatientLoginService;
import com.ruoyi.xindian.patient.mapper.PatientLoginMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author w
* @description 针对表【patient_login】的数据库操作Service实现
* @createDate 2024-08-14 09:17:51
*/
@Service
public class PatientLoginServiceImpl extends ServiceImpl<PatientLoginMapper, PatientLogin>
    implements PatientLoginService{

    @Resource
    private PatientLoginMapper patientLoginMapper;


    @Resource
    private AesUtils aesUtils;

    @Override
    public int addLoginLog(PatientLogin patientLogin) throws Exception {
        if (StringUtils.isEmpty(patientLogin.getPatientPhone())) {
            return 0;
        }

        if (StringUtils.isNotEmpty(patientLogin.getPatientPhone())) {
            patientLogin.setPatientPhone(aesUtils.encrypt(patientLogin.getPatientPhone()));
        }
        patientLogin.setCreateTime(new Date());
        return patientLoginMapper.insert(patientLogin);
    }
}




