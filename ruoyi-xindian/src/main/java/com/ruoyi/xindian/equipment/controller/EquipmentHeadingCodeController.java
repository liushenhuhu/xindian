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
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/headingCode/headingCode")
public class EquipmentHeadingCodeController {



    @Value("${sx.url}")
    private String sxUrl;


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
    private RestTemplate restTemplate;

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @Resource
    private IPatientManagementService patientManagementService;
//
//    /**
//     * 查询设备编号以及给管理员发送消息
//     * @param code
//     * @param phone
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/getEquipmentCode")
//    public AjaxResult getEquipmentCode(String code, String phone, HttpServletRequest request) throws Exception {
//
//        if (code.length()>17){
//            code=code.substring(0,17);
//        }
//        String encrypt = aesUtils.encrypt(phone);
//        EquipmentHeadingCode equipmentHeadingCode = new EquipmentHeadingCode();
//
//        equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);
//        if (equipmentHeadingCode==null){
//           equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
//           if (equipmentHeadingCode==null){
//               return AjaxResult.error("识别码不存在");
//           }
//        }
//        getCodeStatus(equipmentHeadingCode.getHeadingCode());
//        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
//        if (equipment==null){
//            return AjaxResult.error("SN码不存在");
//        }
//        Patient patient = patientService.selectPatientByPatientPhone(encrypt);
//        if (patient==null){
//            return AjaxResult.error("患者信息不存在");
//        }
//        if (equipment.getEquipmentStatus().equals("True")){
//            return AjaxResult.error(207,"当前设备已被使用，请更换设备后重试");
//        }
//
//
//        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(encrypt);
//        EquipmentHeadingCode finalEquipmentHeadingCode = equipmentHeadingCode;
//        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
//            redisTemplate.delete("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone);
//        }
//        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
//            redisTemplate.delete("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone);
//        }
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        CompletableFuture.runAsync(() ->{
//            System.out.println("异步线程 =====> 开始推送公众号消息 =====> " + new Date());
//            try{
//                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
//                for (AccountsMsg c : accountsMsgs){
//                    wxPublicRequest.sendEquipmentMsgNew(finalEquipmentHeadingCode.getEquipmentCode(),c.getOpenId(),
//                            aesUtils.decrypt(patient.getPatientName())+"/"+patient.getPatientSex(),aesUtils.decrypt(patient.getPatientPhone()),medicalHistory.getHeight()+"/"+medicalHistory.getWeight(),patient.getBirthDay());
//                }
//            }catch (Exception e){
//                System.out.println(e);
//            }
//            System.out.println("异步线程 =====> 结束推送公众号消息 =====> " + new Date());
//        },executorService);
//        executorService.shutdown(); // 回收线程池
//
//        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),5, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("getEquipmentCodeT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),30,TimeUnit.MINUTES);
//        redisTemplate.opsForValue().set("getEquipmentCodeSF!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),12,TimeUnit.SECONDS);
//        return AjaxResult.success();
//
//    }
//



    /**
     * 查询设备编号是否存在数据库中
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/getEquipmentExist")
    public AjaxResult getEquipmentExist(String code) throws Exception {
        if (code == null || code.isEmpty()) {
            return AjaxResult.success(false);
        }
        if (code.length() > 17) {
            code = code.substring(0, 17);
        }
        EquipmentHeadingCode equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);

        if (equipmentHeadingCode == null) {
            equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
        }
        if (equipmentHeadingCode == null) {
            Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(code);
            return AjaxResult.success(equipment != null);
        } else {
            Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
            return AjaxResult.success(equipment != null);
        }
    }






    /**
     * 查询设备编号以及给管理员发送消息
     * @param code
     * @param phone
     * @return
     * @throws Exception
     */
    @GetMapping("/getEquipmentCode")
    public AjaxResult getSXUserAdd(String code, String phone, HttpServletRequest request) throws Exception {

        if (code == null || code.isEmpty()) {
            return AjaxResult.error("识别码不存在");
        }
        if (phone == null || phone.isEmpty()) {
            return AjaxResult.error("手机号码不存在");
        }
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
        String sxUserId = getSXUserId(patient);
        if (sxUserId==null){
            return AjaxResult.error("请求错误");
        }
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userId",sxUserId);
        paramsMap.put("mac", equipment.getEquipmentCode());

        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(paramsMap,headers);

        String url = sxUrl+"/bmecg/third/report/zzdx/bindDevice";
        HashMap<String,String> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request1, HashMap.class);
        }catch (Exception e){
            System.out.println(e);
        }
        EquipmentHeadingCode finalEquipmentHeadingCode = equipmentHeadingCode;
        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),5, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("getEquipmentCodeT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),30,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("getEquipmentCodeSF!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),12,TimeUnit.SECONDS);
        if (sendMessageVo!=null){
            if (sendMessageVo.get("resultCode").equals("200")){
                return AjaxResult.success("绑定成功,请等待2-3分钟后重新打开");
            }else {
                return AjaxResult.error(sendMessageVo.get("resultMsg"));
            }
        }
        return AjaxResult.success("申请绑定成功");
    }




    /**
     * 获取患者的userId
     * @param patient
     * @return
     * @throws Exception
     */
    public String getSXUserId(Patient patient) throws Exception {
        if (patient==null){
            return null;
        }
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(patient.getPatientPhone());

        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");

        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name",aesUtils.decrypt(patient.getPatientName()) );
        paramsMap.put("sex",patient.getPatientSex() );
        try {
            paramsMap.put("birthday",simpleDateFormat.format(patient.getBirthDay()) );
        }catch (Exception e){
            paramsMap.put("birthday","2000-12-12");
        }
        paramsMap.put("phone",aesUtils.decrypt(patient.getPatientPhone()) );
        paramsMap.put("height",medicalHistory.getHeight() );
        paramsMap.put("weight",medicalHistory.getWeight() );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);

        String url = sxUrl+"/p/third/userSync/"+"ZZDX";
        HashMap<String,Map<String,Object>> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        }catch (Exception e){
            System.out.println(e);
        }
        Map<String,Object> resultData = sendMessageVo.get("resultData");
        if (resultData!=null){
            return resultData.get("uid").toString();
        }

        return null;
    }


    /**
     * 下载PDF
     * @param orderId
     * @return
     * @throws Exception
     */
    @GetMapping("/downloadPFD")
    public AjaxResult downloadPFD(String orderId) throws Exception {
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
        String url = sxUrl+"/bmecg/third/report/download?orderId="+orderId;
        ResponseEntity<byte[]> sendMessageVo=null;
        try {
             sendMessageVo = restTemplate.exchange(url, HttpMethod.GET,formEntity, byte[].class);
        }catch (Exception e){
            System.out.println(e);
        }
        fileToBytes(sendMessageVo.getBody(),"D:\\Users\\Downloads\\","test.pdf");
        return null;
    }


    /**
     * 提交报告到诊断平台
     * @param phone
     * @param pId
     * @return
     * @throws Exception
     */
    public Boolean addSXReport(String phone,String pId) throws Exception {

        Patient patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(phone));

        String sxUserId = getSXUserId(patient);

        LinkedHashMap<String, Object> sxDateList = getSXDateList(sxUserId, pId);

        if (sxDateList==null){
            return false;
        }

        try {
            String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
            HttpHeaders headers = new HttpHeaders(); //构建请求头
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("authorization","Bearer "+equipmentCodeAccessToken);
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("userId",sxDateList.get("userId"));
            paramsMap.put("fileName",sxDateList.get("fileName"));
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
            String url = sxUrl+"/bmecg/third/report/zzdx/sendNotify";
            HashMap<String,Map<String,Object>> sendMessageVo=null;
            try {
                sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
            }catch (Exception e){
                System.out.println(e);
                return false;
            }
            if (sendMessageVo!=null){
                if (sendMessageVo.get("resultCode").equals("200")){
                    return true;
                }else {
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }






    /**
     * 通过userId获取用户检测列表
     * @param userId
     * @return
     * @throws Exception
     */
    public LinkedHashMap<String,Object> getSXDateList(String userId,String pId) throws Exception {
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
        String url = sxUrl+"/bmecg/third/report/zzdx/dataList?userId="+userId+"&page="+1+"&size="+100;
        ResponseEntity<Object> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.exchange(url, HttpMethod.GET,formEntity, Object.class);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        try {
            PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);

            if (patientManagement==null){
                return null;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            LinkedHashMap<String,Object> body =(LinkedHashMap<String, Object>) sendMessageVo.getBody();

            LinkedHashMap<String,Object> resultData =(LinkedHashMap<String, Object>) body.get("resultData");

            List<LinkedHashMap<String,Object>> datas =(List<LinkedHashMap<String, Object>>) resultData.get("datas");

            String format = simpleDateFormat.format(patientManagement.getConnectionTime());

            for(LinkedHashMap<String,Object> data:datas){
                String startTime =data.get("startTime").toString();
                if (simpleDateFormat.format(simpleDateFormat.parse(startTime)).equals(format)){
                    return data;
                }
            }

            return null;

        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }


    /**
     * 将Byte数组转换成文件
     * @param bytes byte数组
     * @param filePath 文件路径  如 D:\\Users\\Downloads\\
     * @param fileName  文件名
     */
    public  void fileToBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(filePath + fileName);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取善行的验证token
     * @return
     * @throws Exception
     */
    private String getEquipmentCodeAccess_token() throws Exception {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("EquipmentCodeAccess_token"))){
            return redisTemplate.opsForValue().get("EquipmentCodeAccess_token");
        }
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("password","PSEIF363T" );
        paramsMap.put("appKey","s_zwecg" );
        paramsMap.put("uid","80000261" );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);

        String url = sxUrl+"/p/thirdLogin/"+"ZZDX";
        HashMap<String,Map<String,Object>> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        }catch (Exception e){
            System.out.println(e);
        }
        Map<String,Object> resultData = sendMessageVo.get("resultData");
        if (resultData!=null){
            String accessToken = resultData.get("access_token").toString();
            redisTemplate.opsForValue().set("EquipmentCodeAccess_token",accessToken,1, TimeUnit.HOURS);
            return accessToken;
        }
        return null;
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
                            aesUtils.decrypt(patient.getPatientName())+"/"+patient.getPatientSex(),aesUtils.decrypt(patient.getPatientPhone()),medicalHistory.getHeight()+"/"+medicalHistory.getWeight(),patient.getBirthDay());
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
