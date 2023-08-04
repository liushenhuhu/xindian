package com.ruoyi.xindian.equipment.config;

import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class ExcelInsertConfig {


    @Resource
    private IEquipmentService equipmentService;


    @GetMapping("/excel")
    public void excelInsert() throws Exception {
        //设备数据导入 自己添加路径
        equipmentService.batchInsert("C:\\Users\\13401\\Desktop\\河南迈雅100+800+280+20SN码.郑州大学100台.(1).xlsx");

    }


}
