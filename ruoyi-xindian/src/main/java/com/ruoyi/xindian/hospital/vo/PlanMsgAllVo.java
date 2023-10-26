package com.ruoyi.xindian.hospital.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PlanMsgAllVo {


    private String patientPhone;

    private String patientName;

    private String patientSex;


    private Integer patientAge;


    private String hospitalName;

    private String HospitalClinicName;

    private String doctorPhone;
    private String doctorName;


    private String professional;


    private BigDecimal chargePrice;


    private Long planId;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;


    private Integer slotId;


    /**
     * 分配开始时间
     */
    private String startTime;

    /**
     * 分配结束时间
     */
    private String endTime;

    private String accompanyPhone;

}
