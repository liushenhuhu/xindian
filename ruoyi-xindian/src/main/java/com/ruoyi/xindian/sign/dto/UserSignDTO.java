package com.ruoyi.xindian.sign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserSignDTO implements Serializable {

    //采集次数
    private Long countAll;
    //今日采集次数
    private Long countToday;
    /** 累积签到天数 */
    private Long seriesDays;
    /** 连续签到天数 */
    private Long continuityDays;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastSign;

}
