package com.ruoyi.xindian.stLabel.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * ST标注对象 st_label
 * 
 * @author liuyi
 * @date 2024-01-26
 */
@Data
public class StLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志id */
    private String logId;

    /** 心电图数据 */
//    @Excel(name = "心电图数据")
    private byte[] ecgData;

    private List<List<Float>> ecgDataJson;
    /** 诊断结果 */
    @Excel(name = "诊断结果")
    private String diagnosticResult;

    /** 标注 标签 */
    @Excel(name = "标注 标签")
    private String labelJson;

    /** 是否标注 0：否 1：是 */
    @Excel(name = "是否标注 0：否 1：是")
    private Long isLabel;

    /** 标注时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "标注时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date labelTime;


}
