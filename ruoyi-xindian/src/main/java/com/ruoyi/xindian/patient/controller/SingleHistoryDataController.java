package com.ruoyi.xindian.patient.controller;

import com.ruoyi.xindian.patient.service.SingleHistoryDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/single_history_data")
public class SingleHistoryDataController {


    @Resource
    private SingleHistoryDataService singleHistoryDataService;



}
