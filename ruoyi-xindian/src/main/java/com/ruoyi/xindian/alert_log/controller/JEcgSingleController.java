package com.ruoyi.xindian.alert_log.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.alert_log.domain.Jecg12;
import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.ruoyi.xindian.alert_log.service.Jecg12Service;
import com.ruoyi.xindian.alert_log.service.JecgSingleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ecgData")
public class JEcgSingleController {



    @Resource
    private JecgSingleService jecgSingleService;


    @Resource
    private Jecg12Service jecg12Service;

    /**
     * 再原有的数据上替换关键点数据
     */
    @PostMapping("/ecgBeatLabelAdd")
    public AjaxResult ecgBeatLabelAdd(JecgSingle jecgSingle){
        return AjaxResult.success(jecgSingleService.updateBeatLabel(jecgSingle)>0);
    }

    /**
     * 通过pID查询所有的关键点数据
     */
    @GetMapping("/getEcgDataById")
    public AjaxResult getEcgDataById(String pId){
        return AjaxResult.success(jecgSingleService.selectById(pId));
    }


    /**
     * 在原有的数据上修改标签数据
     * @param jecgSingle
     * @return
     */
    @PostMapping("/ecgWaveLabelPut")
    public AjaxResult ecgWaveLabelPut(JecgSingle jecgSingle){
        return AjaxResult.success(jecgSingleService.updateWaveLabel(jecgSingle)>0);
    }



    @GetMapping("/getJecg12ById")
    public AjaxResult getJecg12ById(String pId){
        return AjaxResult.success(jecg12Service.selectById(pId));
    }

    @PostMapping("/put12BeatLabel")
    public AjaxResult put12BeatLabel(Jecg12 jecg12){
        return AjaxResult.success(jecg12Service.updateBeatLabel(jecg12)>0);
    }
    @PostMapping("/put12WaveLabel")
    public AjaxResult put12WaveLabel(Jecg12 jecg12){
        return AjaxResult.success(jecg12Service.updateWaveLabel(jecg12)>0);
    }



}
