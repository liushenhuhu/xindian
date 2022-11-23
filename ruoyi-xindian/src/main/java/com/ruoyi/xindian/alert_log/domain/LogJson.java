package com.ruoyi.xindian.alert_log.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogJson {
    private String id;
    private Data time;
    private String type;
    private List<LogLabel> splitLabels;
}
