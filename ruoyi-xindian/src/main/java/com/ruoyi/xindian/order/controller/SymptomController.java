package com.ruoyi.xindian.order.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.order.service.SymptomTypeService;
import com.ruoyi.xindian.order.service.impl.SymptomTypeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/symptom")
public class SymptomController {


    @Resource
    private SymptomTypeService symptomTypeService;

    @GetMapping("/type")
    public AjaxResult type(){
        return AjaxResult.success(symptomTypeService.typeList());
    }

}
