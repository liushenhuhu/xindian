package com.ruoyi.xindian.patient_management.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class AnalysisParam {
    private String pId;
    private String ecg_type;


    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getEcg_type() {
        return ecg_type;
    }

    public void setEcg_type(String ecg_type) {
        this.ecg_type = ecg_type;
    }
}
