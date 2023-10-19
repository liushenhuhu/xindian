package com.ruoyi.xindian.hospital.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.hospital.domain.VisitAllocation;
import com.ruoyi.xindian.hospital.service.VisitAllocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/visitAllocation")
public class VisitAllocationController {




    @Resource
    private VisitAllocationService visitAllocationService;


    @GetMapping("/addTime")
    private AjaxResult  addTime(){

        return AjaxResult.success(visitAllocationService.addRedis());
    }








}
