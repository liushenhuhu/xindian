package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient.domain.PatientLogin;
import com.ruoyi.xindian.patient.service.PatientLoginService;
import com.ruoyi.xindian.patient.mapper.PatientLoginMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public int addLoginLog(PatientLogin patientLogin) throws Exception {
        if (StringUtils.isEmpty(patientLogin.getPatientPhone())) {
            return 0;
        }
        if (StringUtils.isNotEmpty(patientLogin.getPatientPhone())) {
            patientLogin.setPatientPhone(aesUtils.encrypt(patientLogin.getPatientPhone()));
        }
        if (Boolean.TRUE.equals(redisTemplate.hasKey("patient_login:"+patientLogin.getPatientPhone()))) {
            return 0;
        }
        patientLogin.setCreateTime(new Date());
        int insert = patientLoginMapper.insert(patientLogin);
        // 获取当前时间
        LocalTime now = LocalTime.now();
        // 定义晚上11点59分的时间
        LocalTime targetTime = LocalTime.of(23, 59);
        // 计算当前时间与目标时间之间的时间差
        Duration duration = Duration.between(now, targetTime);
        // 获取剩余的分钟数
        long minutesUntilTarget = duration.toMinutes();
        if (minutesUntilTarget>0){
            redisTemplate.opsForValue().set("patient_login:" + patientLogin.getPatientPhone(), "1",minutesUntilTarget, TimeUnit.MINUTES );
        }
        return insert;
    }
}




