package com.ruoyi.xindian.report.domain;

import lombok.Data;

import java.util.List;

@Data
public class ReportM extends Report {
    private String patientPhone;
    private String patientName;
    private String patientSex;
    private String patientAge;
    private List<String> medicalHistory;
}
