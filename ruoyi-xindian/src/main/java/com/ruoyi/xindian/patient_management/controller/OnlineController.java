package com.ruoyi.xindian.patient_management.controller;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.HashMap;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.equipment.controller.EquipmentController;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.OnlineParam;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 患者管理Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/getOnline")
public class OnlineController extends BaseController {

    @Autowired
    private PatientManagementController patientManagementController;

    @Autowired
    private EquipmentController equipmentController;

    @Autowired
    private IPatientManagementService patientManagementService;

    @Autowired
    private IHospitalService iHospitalService;

    @Autowired
    private TokenService tokenService;

    @Resource
    private SysUserMapper sysUserMapper;


    @Resource
    private IEquipmentService equipmentService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private AesUtils aesUtils;


    @Autowired
    private IPatientService patientService;



    @GetMapping("/updateAll")
    public AjaxResult updateAll(HttpServletRequest request) throws Exception {
        AjaxResult result1 = update1(request);
        AjaxResult result2 = update2(request);
        Map<String, Object> map = new HashMap<>();
        map.put("res1", result1);
        map.put("res2", result2);
        return AjaxResult.success(map);
    }
    @GetMapping("/{patientPhone}")
    public AjaxResult getOnlineStatus(@PathVariable String patientPhone,HttpServletRequest request) throws Exception {
        String encrypt = aesUtils.encrypt(patientPhone);
        update1(request);
        update2(request);
        PatientManagement patientManagement = new PatientManagement();
        patientManagement.setPatientPhone(encrypt);
        patientManagement.setOnlineStatus("1");
        List<PatientManagement> patientManagements = patientManagementService.selectPatientManagementListDECG12(patientManagement);

        if(patientManagements!=null && !patientManagements.isEmpty()){
            return getAjaxResult(patientManagements);
        }
        return getAjaxResult(patientPhone);
    }

    private AjaxResult getAjaxResult(List<PatientManagement> patientManagements) throws Exception {
        for (PatientManagement patientManagement : patientManagements){
            if (patientManagement.getPatientPhone()!=null&& !patientManagement.getPatientPhone().isEmpty()){
                patientManagement.setPatientPhone(aesUtils.decrypt(patientManagement.getPatientPhone()));
            }
            if (patientManagement.getPatientName()!=null&& !patientManagement.getPatientName().isEmpty()){
                patientManagement.setPatientName(aesUtils.decrypt(patientManagement.getPatientName()));
            }
        }

        return AjaxResult.success(patientManagements);
    }

    private AjaxResult getAjaxResult(@PathVariable String patientPhone) {
        Set<String> keys = redisTemplate.keys("getEquipmentCodeT15"+"!*");
        Iterator<String> iterator = null;
        if (keys != null) {
            iterator = keys.iterator();
        }
        if (iterator != null) {
            while (iterator.hasNext()){
                String next = iterator.next();
                if (next.contains(patientPhone)){

                    String code = "";
                    try{
                        String[] split = next.split("!");
                        String[] split1 = split[1].split("=");

                        code = split1[0];
                    }catch (Exception e){
                        return AjaxResult.error("无在线设备");
                    }
                    Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(code);
                    if (equipment==null){
                        return AjaxResult.error("无在线设备");
                    }
                    PatientManagement patientManagement1 = new PatientManagement();
                    patientManagement1.setEquipmentCode(code);
                    patientManagement1.setPatientPhone(patientPhone);
                    patientManagement1.setOnlineStatus("3");
                    patientManagement1.setEcgType(equipment.getEquipmentType());
                    return AjaxResult.success(patientManagement1);
                }
            }
        }
        return AjaxResult.error("无在线设备");
    }


    @GetMapping("/getSingleOr12")
    public AjaxResult getSingleOr12(PatientManagement patientManagement,HttpServletRequest request) throws Exception {
        String patientPhone = patientManagement.getPatientPhone();
        if (StringUtils.isNotEmpty(patientManagement.getPatientPhone())){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        update1(request);
        update2(request);
        patientManagement.setOnlineStatus("1");

        List<PatientManagement> patientManagements = patientManagementService.selectPatientManagementList(patientManagement);

        if(patientManagements!=null && !patientManagements.isEmpty()){
            return getAjaxResult(patientManagements);
        }

        if (patientManagement.getEcgType()!=null&&patientManagement.getEcgType().contains("DECG12")){
            return getAjaxResult(patientPhone);
        }
        return AjaxResult.error("无在线设备");

    }


    @GetMapping("/update1")
    public AjaxResult update1(HttpServletRequest request1) {
        LoginUser loginUser = tokenService.getLoginUser(request1);
        SysUser userInfo = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
        System.out.println("用户信息:"+userInfo);
        OnlineParam onlineParam = new OnlineParam();
        onlineParam.setHospName("所有");
//        if (userInfo!=null && userInfo.getDeptId() != null && userInfo.getDeptId() == 200) {
//            if (userInfo.getHospitalName()==null){
//                Hospital hospital = iHospitalService.selectHospitalByHospitalCode(userInfo.getHospitalCode());
//                onlineParam.setHospName(hospital.getHospitalName());
//            }
//        }

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
            e.printStackTrace();
            return AjaxResult.error("请求失败");
        }
        String responseEntityBody = responseEntity.getBody();
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        String splitData = splitData(responseEntityBody, "[", "]");
        String s = removeDoubleQuotes(splitData);
        String[] devList = s.split(",");
        String res="";
        res = equipmentController.updateEquipmentStatus(devList);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("devList", devList);
        resMap.put("res", res);
        return AjaxResult.success(resMap);
    }

    @GetMapping("/update2")
    public AjaxResult update2(HttpServletRequest request1) throws Exception {
        String[] pIdList = splitData(request1);
        String res = patientManagementController.updateStatus(pIdList);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("pIdList", pIdList);
        resMap.put("res", res);
        return AjaxResult.success(resMap);
    }

    @GetMapping("/getPatientOnlineStatus")
    public AjaxResult getOnlineStatus(HttpServletRequest request1) throws Exception {
        String[] pIdList = splitData(request1);
        patientService.updateMonitoringStatus();
        if (pIdList.length != 0) {
            patientManagementService.updateStatus(pIdList);
            for (String pId : pIdList) {
                PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);
                if (patientManagement != null) {
                    Patient patient = new Patient();
                    patient.setPatientName(patientManagement.getPatientName());
                    patient.setPatientPhone(patientManagement.getPatientPhone());
                    Patient patient1 = patientService.selectPatientByNameAndPhone(patient);
                    patient1.setMonitoringStatus("1");
                    patientService.updatePatient(patient1);
                }
            }
        }

        List<Patient> patients = patientService.selectPatientList(new Patient());
        redisTemplate.delete("patientList");
        redisTemplate.delete("patientListByTest:*");
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        // 将数据分批传入线程池进行异步插入操作
        CompletableFuture<Void>[] futures = new CompletableFuture[(int) Math.ceil((double) patients.size() / 1000)];
        for (int i = 0; i < patients.size(); i += 1000) {
            int endIndex = Math.min(i + 1000, patients.size());
            List<Patient> dataChunk = patients.subList(i, endIndex);
            int batchIndex = i / 1000;
            futures[batchIndex] = CompletableFuture.runAsync(() -> {
                try {
                    redisAdd(dataChunk);
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

    private String[] splitData(HttpServletRequest request1) {
        String url = "https://server.mindyard.cn:84/get_device2";
        LoginUser loginUser = tokenService.getLoginUser(request1);
        SysUser userInfo = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
        System.out.println("用户信息:" + userInfo);
        OnlineParam onlineParam = new OnlineParam("所有");
        if (userInfo != null && userInfo.getDeptId() != null && userInfo.getDeptId() == 200) {
            if (userInfo.getHospitalName() == null) {
                Hospital hospital = iHospitalService.selectHospitalByHospitalCode(userInfo.getHospitalCode());
                onlineParam.setHospName(hospital.getHospitalName());
            }
        }
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
            e.printStackTrace();
           throw new ServiceException("请求失败");
        }
        String responseEntityBody = responseEntity.getBody();
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        String splitData = splitData(responseEntityBody, "[", "]");
        String s = removeDoubleQuotes(splitData);
        return s.split(",");
    }



    private  void redisAdd(List<Patient> patients) throws Exception {
        if (patients!=null&& !patients.isEmpty()){
            for (Patient pat : patients) {
                if(pat.getBirthDay()!=null)
                    pat.setPatientAge(String.valueOf(DateUtil.getAge(pat.getBirthDay())));
                if(pat.getPatientSex().length()>1){
                    pat.setPatientSex(pat.getPatientSex().substring(0,1));
                }
                if(pat.getPatientPhone() != null){
                    pat.setPatientPhone(aesUtils.decrypt(pat.getPatientPhone()));
                }
                if(pat.getPatientName() != null){
                    pat.setPatientName(aesUtils.decrypt(pat.getPatientName()));
                }
                if (pat.getFamilyPhone()!=null&&!"".equals(pat.getFamilyPhone())){
                    pat.setFamilyPhone(aesUtils.decrypt(pat.getFamilyPhone()));
                }

                redisTemplate.opsForList().rightPush("patientList",pat);
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
