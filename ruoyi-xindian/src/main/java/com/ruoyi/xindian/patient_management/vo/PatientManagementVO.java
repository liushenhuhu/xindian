package com.ruoyi.xindian.patient_management.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class PatientManagementVO {



    @Excel(name = "患者ID")
    private String pId;



    @Excel(name = "设备号")
    private String equipmentCode;



    @Excel(name = "提交时间" ,dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date connectionTime;




    @Excel(name = "心博数据")
    private String beatLabel;
}
