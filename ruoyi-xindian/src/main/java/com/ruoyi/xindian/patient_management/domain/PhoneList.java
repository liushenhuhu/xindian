package com.ruoyi.xindian.patient_management.domain;

import lombok.Data;

@Data

public class PhoneList {
    //患者手机号
    private String patientPhone;
    //家人手机号
    private String familyPhone;

    //医生手机号
    private String doctorPhone;
}
