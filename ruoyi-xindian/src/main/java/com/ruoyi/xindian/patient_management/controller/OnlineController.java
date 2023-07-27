package com.ruoyi.xindian.patient_management.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.equipment.controller.EquipmentController;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.*;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @GetMapping("/updateAll")
    public AjaxResult updateAll(HttpServletRequest request) {
        AjaxResult result1 = update1(request);
        AjaxResult result2 = update2(request);
        Map<String, Object> map = new HashMap<>();
        map.put("res1", result1);
        map.put("res2", result2);
        return AjaxResult.success(map);
    }
    @GetMapping("/{patientPhone}")
    public AjaxResult getOnlineStatus(@PathVariable String patientPhone,HttpServletRequest request) {
        update1(request);
        update2(request);
        PatientManagement patientManagement = new PatientManagement();
        patientManagement.setPatientPhone(patientPhone);
        patientManagement.setOnlineStatus("1");
        List<PatientManagement> patientManagements = patientManagementService.selectPatientManagementListDECG12(patientManagement);
        if(patientManagements!=null && patientManagements.size()!=0){
            return AjaxResult.success(patientManagements.get(0));
        }
        return AjaxResult.error("无在线设备");
    }


    @GetMapping("/update1")
    public AjaxResult update1(HttpServletRequest request1) {
        LoginUser loginUser = tokenService.getLoginUser(request1);
        SysUser userInfo = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
        System.out.println("用户信息:"+userInfo);
        OnlineParam onlineParam = new OnlineParam("所有");
        if (userInfo!=null && userInfo.getDeptId() != null && userInfo.getDeptId() == 200) {
            if (userInfo.getHospitalName()==null){
                Hospital hospital = iHospitalService.selectHospitalByHospitalCode(userInfo.getHospitalCode());
                onlineParam.setHospName(hospital.getHospitalName());
            }
        }

        String url = "https://server.mindyard.cn:84/get_device";
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
    public AjaxResult update2(HttpServletRequest request1) {
//        String url = "http://219.155.7.235:5003/get_device2";
        String url = "https://server.mindyard.cn:84/get_device2";

        LoginUser loginUser = tokenService.getLoginUser(request1);
        SysUser userInfo = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
        System.out.println("用户信息:"+userInfo);
        OnlineParam onlineParam = new OnlineParam("所有");
        if (userInfo!=null && userInfo.getDeptId() != null && userInfo.getDeptId() == 200) {
            if (userInfo.getHospitalName()==null){
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
            return AjaxResult.error("请求失败");
        }
        String responseEntityBody = responseEntity.getBody();
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        String splitData = splitData(responseEntityBody, "[", "]");
        String s = removeDoubleQuotes(splitData);
        String[] pIdList = s.split(",");
        String res = patientManagementController.updateStatus(pIdList);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("pIdList", pIdList);
        resMap.put("res", res);
        return AjaxResult.success(resMap);
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
