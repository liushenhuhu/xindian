package com.ruoyi.xindian.statistics.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.statistics.domain.DiagnoseDoc;
import com.ruoyi.xindian.statistics.service.DiagnoseDocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/diagnoseDoc")
public class DiagnoseDocController {


    @Resource
    private DiagnoseDocService diagnoseDocService;

    @PostMapping("/add")
    public AjaxResult add(DiagnoseDoc diagnoseDoc){
        return AjaxResult.success(diagnoseDocService.insertDiagnose(diagnoseDoc));
    }


    @PutMapping("/put")
    public AjaxResult put(DiagnoseDoc diagnoseDoc){
        return AjaxResult.success(diagnoseDocService.updateDiagnose(diagnoseDoc));
    }


}
