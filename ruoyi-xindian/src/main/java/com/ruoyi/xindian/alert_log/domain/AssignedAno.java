package com.ruoyi.xindian.alert_log.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedAno {
    private String userId;
    private String pId;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
