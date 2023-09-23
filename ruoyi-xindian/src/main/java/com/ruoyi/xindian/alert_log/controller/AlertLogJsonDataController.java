package com.ruoyi.xindian.alert_log.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.alert_log.domain.AlertLogJsonData;
import com.ruoyi.xindian.alert_log.service.AlertLogJsonDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/alertLog_jsonData/alertLog_jsonData")
public class AlertLogJsonDataController {




    @Resource
    private AlertLogJsonDataService alertLogJsonDataService;


    /**
     * 添加或修改标注数据
     * @param alertLogJsonData
     * @return
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AlertLogJsonData alertLogJsonData){
        return AjaxResult.success(alertLogJsonDataService.updateOrInsertAlertLogJsonData(alertLogJsonData));
    }

    @GetMapping("/getJsonData")
    public AjaxResult getJsonData(AlertLogJsonData alertLogJsonData){
        return AjaxResult.success(alertLogJsonDataService.selectJsonData(alertLogJsonData));
    }


}
