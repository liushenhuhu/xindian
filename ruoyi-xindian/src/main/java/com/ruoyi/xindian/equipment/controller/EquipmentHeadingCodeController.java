package com.ruoyi.xindian.equipment.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.equipment.domain.AccountsMsg;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.domain.EquipmentHeadingCode;
import com.ruoyi.xindian.equipment.mapper.EquipmentHeadingCodeMapper;
import com.ruoyi.xindian.equipment.service.AccountsMsgService;
import com.ruoyi.xindian.equipment.service.EquipmentHeadingCodeService;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.controller.OnlineController;
import com.ruoyi.xindian.patient_management.domain.OnlineParam;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/headingCode/headingCode")
public class EquipmentHeadingCodeController {



    @Resource
    private EquipmentHeadingCodeService equipmentHeadingCodeService;


    @Resource
    private IEquipmentService equipmentService;

    @Resource
    private IPatientService patientService;

    @Resource
    private WXPublicRequest wxPublicRequest;


    @Resource
    private AccountsMsgService accountsMsgService;

    @Resource
    private AesUtils aesUtils;


    @Resource
    private IMedicalHistoryService medicalHistoryService;

    @Resource
    private OnlineController onlineController;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 查询设备编号以及给管理员发送消息
     * @param code
     * @param phone
     * @return
     * @throws Exception
     */
    @GetMapping("/getEquipmentCode")
    public AjaxResult getEquipmentCode(String code, String phone, HttpServletRequest request) throws Exception {

        if (code.length()>17){
            code=code.substring(0,17);
        }
        String encrypt = aesUtils.encrypt(phone);
        EquipmentHeadingCode equipmentHeadingCode = new EquipmentHeadingCode();

        equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);
        if (equipmentHeadingCode==null){
           equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
           if (equipmentHeadingCode==null){
               return AjaxResult.error("识别码不存在");
           }
        }
        getCodeStatus(equipmentHeadingCode.getHeadingCode());
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
        if (equipment==null){
            return AjaxResult.error("SN码不存在");
        }
        Patient patient = patientService.selectPatientByPatientPhone(encrypt);
        if (patient==null){
            return AjaxResult.error("患者信息不存在");
        }
        if (equipment.getEquipmentStatus().equals("True")){
            return AjaxResult.error(207,"当前设备已被使用，请更换设备后重试");
        }


        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(encrypt);
        EquipmentHeadingCode finalEquipmentHeadingCode = equipmentHeadingCode;
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
            redisTemplate.delete("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone);
        }
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
            redisTemplate.delete("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone);
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.runAsync(() ->{
            System.out.println("异步线程 =====> 开始推送公众号消息 =====> " + new Date());
            try{
                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
                for (AccountsMsg c : accountsMsgs){
                    wxPublicRequest.sendEquipmentMsg(finalEquipmentHeadingCode.getEquipmentCode(), c.getOpenId(),
                            aesUtils.decrypt(patient.getPatientName()),aesUtils.decrypt(patient.getPatientPhone()),patient.getPatientSex()+"/高:"+medicalHistory.getHeight()+"/重"+medicalHistory.getWeight(),patient.getBirthDay());
                }
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束推送公众号消息 =====> " + new Date());
        },executorService);
        executorService.shutdown(); // 回收线程池

        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),5, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("getEquipmentCodeT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),30,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("getEquipmentCodeSF!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),12,TimeUnit.SECONDS);
        return AjaxResult.success();

    }


    /**
     * 患者解除绑定
     * @param code
     * @param phone
     * @return
     * @throws Exception
     */
    @GetMapping("/getGiveBackCode")
    public AjaxResult getGiveBackCode(String code,String phone) throws Exception {
        if (code.length()>17){
            code=code.substring(0,17);
        }
        String encrypt = aesUtils.encrypt(phone);
        EquipmentHeadingCode equipmentHeadingCode = new EquipmentHeadingCode();

        equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);
        if (equipmentHeadingCode==null){
            equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
            if (equipmentHeadingCode==null){
                return AjaxResult.error("识别码不存在");
            }
        }
        getCodeStatus(equipmentHeadingCode.getHeadingCode());
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
        if (equipment==null){
            return AjaxResult.error("SN码不存在");
        }
        Patient patient = patientService.selectPatientByPatientPhone(encrypt);
        if (patient==null){
            return AjaxResult.error("患者信息不存在");
        }

        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(encrypt);
        EquipmentHeadingCode finalEquipmentHeadingCode = equipmentHeadingCode;
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
            redisTemplate.delete("getEquipmentCodeAgainTwo");
        }
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
            redisTemplate.delete("getEquipmentCodeAgainT15");
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.runAsync(() ->{
            System.out.println("异步线程 =====> 开始推送公众号消息 =====> " + new Date());
            try{
                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
                for (AccountsMsg c : accountsMsgs){
                    wxPublicRequest.sendEquipmentMsg(finalEquipmentHeadingCode.getEquipmentCode(), c.getOpenId(),"换设备："+
                            aesUtils.decrypt(patient.getPatientName()),aesUtils.decrypt(patient.getPatientPhone()),patient.getPatientSex()+"/高:"+medicalHistory.getHeight()+"/重"+medicalHistory.getWeight(),patient.getBirthDay());
                }
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束推送公众号消息 =====> " + new Date());
        },executorService);
        executorService.shutdown(); // 回收线程池

        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),5, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("getEquipmentCodeT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),30,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("getEquipmentCodeSF!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),12,TimeUnit.SECONDS);
        return AjaxResult.success();
    }



    public void getCodeStatus(String code){
        OnlineParam onlineParam = new OnlineParam("所有");
        String url = "https://server.mindyard.cn:84/get_device";
//        String url = "http://202.102.249.124:84/get_device";
        //请求
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "zzu");
        headers.set("password", "zzu123");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OnlineParam> request = new HttpEntity<OnlineParam>(onlineParam, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, request, String.class);
        } catch (RestClientException e) {
            System.out.println(e);
        }
        String responseEntityBody = responseEntity.getBody();
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        String splitData = splitData(responseEntityBody, "[", "]");
        String s = removeDoubleQuotes(splitData);
        String[] devList = s.split(",");
        String Sn = "";

        for (String c :devList){
            if (c.equals(code)){
                Sn = c;
            }
        }
        if (StringUtils.isEmpty(Sn)){
            Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(code);
            if (equipment!=null&&equipment.getEquipmentStatus().equals("True")){
                equipment.setEquipmentStatus("False");
                equipmentService.updateStatusAndPatientPhoneNull(equipment);
            }
        }

    }

    public String splitData(String str, String strStart, String strEnd) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
        return tempStr;
    }

    public static String removeDoubleQuotes(String result) {
        //去掉" "号
        return result.replace("\"", "");
    }

}
