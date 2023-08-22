package com.ruoyi.xindian.equipment.config;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import org.aspectj.weaver.loadtime.Aj;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class ExcelInsertConfig {


    @Resource
    private IEquipmentService equipmentService;


    @GetMapping("/excel")
    public void excelInsert() throws Exception {
        //设备数据导入 自己添加路径
        equipmentService.batchInsert("C:\\Users\\13401\\Desktop\\河南迈雅100+800+280+20+200SN码.郑州大学100台.(2).xlsx");
//        equipmentService.batchSelect("C:\\Users\\13401\\Desktop\\河南迈雅100+800+280+20+200SN码.郑州大学100台.(1).xlsx");

    }


    @GetMapping("/select/{id}")
    public AjaxResult select(@PathVariable("id") Integer sum) throws Exception {
        return AjaxResult.success(equipmentService.batchSelect("C:\\Users\\13401\\Desktop\\河南迈雅100+800+280+20+200SN码.郑州大学100台.(2).xlsx",sum));
    }




}
