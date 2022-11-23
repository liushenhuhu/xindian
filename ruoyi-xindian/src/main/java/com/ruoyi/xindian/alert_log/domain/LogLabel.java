package com.ruoyi.xindian.alert_log.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogLabel {
    private String lead;
    private String start;
    private String end;
    private String labelType;
}
