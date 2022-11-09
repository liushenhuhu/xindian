package com.ruoyi.xindian.patient_management.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.equipment.controller.EquipmentController;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.patient_management.domain.OnlineParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @PostMapping("/update1")
    @ResponseBody
    public AjaxResult update1(@RequestBody OnlineParam onlineParam) {
        String url = "http://219.155.7.235:5003/get_device";
        //LinkedMultiValueMap一个键对应多个值，对应format-data的传入类型
        LinkedMultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        //入参
        request.add("hospName", onlineParam.getHospName());
        request.add("ts", "0");
        //请求
        RestTemplate restTemplate = new RestTemplate();

        // 设置restTemplate编码为utf-8
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

//        String result = restTemplate.postForObject(url, request, String.class);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);



        String responseEntityBody = responseEntity.getBody();
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        String splitData = splitData(responseEntityBody, "[", "]");
        String[] devList = splitData.split(",");
        String res = equipmentController.updateEquipmentStatus(devList);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("devList", devList);
        resMap.put("res", res);
        return AjaxResult.success(resMap);
    }

    @GetMapping("/update2")
    public AjaxResult update2() {
        String url = "http://219.155.7.235:5003/get_device2";
        //LinkedMultiValueMap一个键对应多个值，对应format-data的传入类型
        LinkedMultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        //入参
        request.set("ts", "0");
        //请求
        RestTemplate restTemplate = new RestTemplate();
/*        String result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);*/
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        String responseEntityBody = responseEntity.getBody();
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());

        String splitData = splitData(responseEntityBody, "[", "]");
        String[] pIdList = splitData.split(",");
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

}
