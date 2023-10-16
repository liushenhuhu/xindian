package com.ruoyi.xindian.alert_log.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.ruoyi.xindian.alert_log.service.JecgSingleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ecgData")
public class JEcgSingleController {



    @Resource
    private JecgSingleService jecgSingleService;



    /**
     * 再原有的数据上替换关键点数据
     */
    @PostMapping("/ecgBeatLabelAdd")
    public AjaxResult ecgBeatLabelAdd(JecgSingle jecgSingle){
        return AjaxResult.success(jecgSingleService.updateBeatLabel(jecgSingle)>0);
    }

    @GetMapping("/getEcgDataById")
    public AjaxResult getEcgDataById(String pId){
        return AjaxResult.success(jecgSingleService.selectById(pId));
    }

    @PostMapping("/ecgWaveLabelPut")
    public AjaxResult ecgWaveLabelPut(JecgSingle jecgSingle){
        return AjaxResult.success(jecgSingleService.updateWaveLabel(jecgSingle)>0);
    }



}
