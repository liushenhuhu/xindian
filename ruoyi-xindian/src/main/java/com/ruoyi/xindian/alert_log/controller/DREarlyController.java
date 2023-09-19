package com.ruoyi.xindian.alert_log.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.alert_log.service.IAlertLogService;
import com.ruoyi.xindian.patient.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/DREarly")
public class DREarlyController {


    @Autowired
    private IAlertLogService alertLogService;

    @Resource
    private AesUtils aesUtils;

    @GetMapping("/dr12OrSingle")
    public AjaxResult dr12List(AlertLog alertLog) throws Exception {
        Patient alertLogs = alertLogService.select12List(alertLog);
        if (StringUtils.isNotEmpty(alertLogs.getPatientName())){
            alertLogs.setPatientName(aesUtils.decrypt(alertLogs.getPatientName()));
        }
        if (StringUtils.isNotEmpty(alertLogs.getPatientPhone())){
            alertLogs.setPatientPhone(aesUtils.decrypt(alertLogs.getPatientPhone()));
        }
        if (StringUtils.isNotEmpty(alertLogs.getFamilyPhone())){
            alertLogs.setFamilyPhone(aesUtils.decrypt(alertLogs.getFamilyPhone()));
        }
        return AjaxResult.success(alertLogs);
    }


    @GetMapping("/earlyAll")
    public AjaxResult earlyAll(AlertLog alertLog){
        List<AlertLog> alertLogs = alertLogService.selectEarly(alertLog);
        return AjaxResult.success(alertLogs);
    }


}
