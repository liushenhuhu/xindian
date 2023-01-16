package com.ruoyi.xindian.patient_management.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.patient_management.domain.AnalysisParam;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



/**
 * 患者管理Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController extends BaseController {

    @PostMapping("/analysis_decg")
    public Object analysis_decg(@RequestBody PatientManagement patientManagement) {
        AnalysisParam analysisParam = new AnalysisParam();
        analysisParam.setpId(patientManagement.getpId());
        analysisParam.setEcg_type(patientManagement.getEcgType());

        String url = "https://server.mindyard.cn:84/analysis_decg";
        //请求
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "zzu");
        headers.set("password", "zzu123");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AnalysisParam> request = new HttpEntity<AnalysisParam>(analysisParam, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, request, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return AjaxResult.error("请求失败");
        }

        return responseEntity;
    }

}
