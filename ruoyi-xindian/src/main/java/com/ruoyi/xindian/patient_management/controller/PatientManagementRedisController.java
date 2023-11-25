package com.ruoyi.xindian.patient_management.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 将单导还是12的名称查询出来，后存入redis,以及做数据更新
 */
@RestController
@RequestMapping("/patient_management/patient_management_redis")
public class PatientManagementRedisController {

    @Autowired
    private IPatientManagementService patientManagementService;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Resource
    private AesUtils aesUtils;

    @GetMapping("/redisAddData")
    public AjaxResult redisPutPatientManagement(String ecgType) throws Exception {
        if (StringUtils.isEmpty(ecgType)){
            return AjaxResult.success();
        }

        PatientManagement patientManagement = new PatientManagement();
        patientManagement.setEcgType(ecgType);

        List<PatientManagement> patientManagements = patientManagementService.selectPatientManagementList(patientManagement);

        redisTemplate.delete("patientManagement"+ecgType);
        String key = "patientManagementByName"+ecgType;
        Set<String> keys = redisTemplate.keys(key+":*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }


        int count = 500;

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        // 将数据分批传入线程池进行异步插入操作
        CompletableFuture<Void>[] futures = new CompletableFuture[(int) Math.ceil((double) patientManagements.size() / count)];
        for (int i = 0; i < patientManagements.size(); i += count) {
            int endIndex = Math.min(i + count, patientManagements.size());
            List<PatientManagement> dataChunk = patientManagements.subList(i, endIndex);
            int batchIndex = i / count;
            futures[batchIndex] = CompletableFuture.runAsync(() -> {
                try {
                    redisAddPatientManagement(dataChunk,ecgType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executorService);
        }
        // 等待所有异步操作完成
        CompletableFuture.allOf(futures).join();
        // 关闭线程池
        executorService.shutdown();
        return AjaxResult.success();
    }

    private  void redisAddPatientManagement(List<PatientManagement> patients,String ecgType) throws Exception {
        if (patients!=null&& !patients.isEmpty()){
            for (PatientManagement management : patients) {
                if(DateUtil.isValidDate(management.getBirthDay())){
                    try {
                        management.setPatientAge(String.valueOf(DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(management.getBirthDay()))));
                    } catch (ParseException e) {
                        System.out.println(1);
                    }
                }
                if (management.getPatientPhone()!=null&& !management.getPatientPhone().isEmpty()){
                    management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));
                }
                if (management.getPatientName()!=null&& !management.getPatientName().isEmpty()){
                    management.setPatientName(aesUtils.decrypt(management.getPatientName()));
                }
                if (management.getDiagnosisDoctor()!=null&& !management.getDiagnosisDoctor().isEmpty()){
                    management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
                }
                if (management.getFamilyPhone()!=null&& !management.getFamilyPhone().isEmpty()){
                    management.setFamilyPhone(aesUtils.decrypt(management.getFamilyPhone()));
                }
                if (StringUtils.isNotEmpty(management.getDoctorPhone())){
                    management.setDoctorPhone(aesUtils.decrypt(management.getDoctorPhone()));
                }
                if (StringUtils.isNotEmpty(management.getDPhone())){
                    management.setDPhone(aesUtils.decrypt(management.getDPhone()));
                }
                redisTemplate.opsForList().rightPush("patientManagement"+ecgType,management);
            }
        }
    }




}
