package com.ruoyi.xindian.alert_log.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedAno {
    private String userId;
    //    private List<String> pIdList;
    private String patientId;
}
