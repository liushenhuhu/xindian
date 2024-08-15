package com.ruoyi.xindian.set_app.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.set_app.domain.SetEcgApp;
import com.ruoyi.xindian.set_app.service.SetEcgAppService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/set/app")
public class SetAppController extends BaseController {




    @Resource
    private SetEcgAppService setEcgAppService;


    @GetMapping("/getSet")
    public AjaxResult getSet(SetEcgApp setEcgApp){

        return AjaxResult.success(setEcgAppService.selectSetEcgAppListByPId(setEcgApp));
    }


    @PostMapping("/addSet")
    public AjaxResult addSet(@RequestBody SetEcgApp setEcgApp){

        return toAjax(setEcgAppService.insertSetEcgApp(setEcgApp));
    }




}
