package com.ruoyi.xindian.patient.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.patient.domain.PatientLogin;
import com.ruoyi.xindian.patient.service.PatientLoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 患者登录日志
 */
@RestController
@RequestMapping("/patient/login")
public class PatientLoginController {





    @Resource
    private PatientLoginService patientLoginService;


    /**
     * 记录登录日志
     * @param patientLogin
     * @return
     */
    @PostMapping("/loginLog")
    public AjaxResult loginLog(@RequestBody PatientLogin patientLogin) throws Exception {
        return AjaxResult.success(patientLoginService.addLoginLog(patientLogin));
    }

    @GetMapping("/getCount")
    public AjaxResult getCount(PatientLogin patientLogin) {
        return AjaxResult.success(patientLoginService.getCount(patientLogin));
    }


}
