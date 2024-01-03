package com.ruoyi.xindian.sx.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.PhoneCheckUtils;
import com.ruoyi.xindian.util.WxUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class NoteController {


    @Resource
    private IPatientManagementService patientManagementService;


    @Resource
    private RedisTemplate<String,String> redisTemplate;


    private final Lock lock = new ReentrantLock();


    @Resource
    private IPatientService patientService;

    @Resource
    private AesUtils aesUtils;

    /**
     * 预警消息通知
     * @param logType 预警类型
     * @param pId 患者管理id
     * @return
     * @throws Exception
     */
    @GetMapping("/earlyLogMsg")
    public AjaxResult earlyLogMsg(String pId, String logType) throws Exception{


        lock.lock();
        try {

            if (Boolean.TRUE.equals(redisTemplate.hasKey("earlyLogMsg:" + pId + logType))){
                return AjaxResult.success();
            }

            PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);
            if (patientManagement==null){
                return AjaxResult.error("报告不存在");
            }
            if (StringUtils.isEmpty(patientManagement.getPatientPhone())){
                return AjaxResult.error("报告不存在");
            }

            String decryptPhone = aesUtils.decrypt(patientManagement.getPatientPhone());

            if (StringUtils.isEmpty(logType)){
                return AjaxResult.error("预警类型不能为空");
            }
            boolean b = decryptPhone(decryptPhone);
            if (!b){
                return AjaxResult.error("手机格式不对");
            }


            //给患者发消息
            WxUtil.sendSubmitSXLog(decryptPhone.substring(0,11));

            //给患者家人发消息
            Patient patient = patientService.selectPatientByPatientPhone(patientManagement.getPatientPhone());

            if (patient!=null){
                String familyPhone = patient.getFamilyPhone();
                if (StringUtils.isNotEmpty(familyPhone)){
                    String decryptFamilyPhone = aesUtils.decrypt(familyPhone);
                    boolean b1 = decryptPhone(decryptFamilyPhone);
                    if (b1){
                        if (!decryptPhone.substring(0,11).equals(decryptFamilyPhone.substring(0,11))){
                            //给患者家属发消息
                            WxUtil.sendSubmitSXLog(decryptFamilyPhone.substring(0,11));
                        }

                    }
                }
            }


            redisTemplate.opsForValue().set("earlyLogMsg:"+pId+logType,pId,1, TimeUnit.HOURS);

            return AjaxResult.success();

        }finally {
            lock.unlock();
        }
    }


    /**
     * 手机号格式校验
     * @param phone
     * @return
     */
    private boolean decryptPhone(String phone){

        if (phone.length()>11){
            phone = phone.substring(0,11);
        }
        boolean phoneLegal = PhoneCheckUtils.isPhoneLegal(phone);
        if (!phoneLegal){
            return false;
        }
        return true;
    }





}
