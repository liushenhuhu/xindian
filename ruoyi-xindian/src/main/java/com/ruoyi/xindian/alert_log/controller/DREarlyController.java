package com.ruoyi.xindian.alert_log.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.alert_log.service.IAlertLogService;
import com.ruoyi.xindian.patient.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/DREarly")
public class DREarlyController {


    @Autowired
    private IAlertLogService alertLogService;

    @GetMapping("/dr12OrSingle")
    public AjaxResult dr12List(AlertLog alertLog){
        Patient alertLogs = alertLogService.select12List(alertLog);
        return AjaxResult.success(alertLogs);
    }


    @GetMapping("/earlyAll")
    public AjaxResult earlyAll(AlertLog alertLog){
        List<AlertLog> alertLogs = alertLogService.selectEarly(alertLog);
        return AjaxResult.success(alertLogs);
    }


}
