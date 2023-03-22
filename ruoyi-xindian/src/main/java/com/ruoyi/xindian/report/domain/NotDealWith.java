package com.ruoyi.xindian.report.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 *  Not_Deal_with
 *
 * @author ruoyi
 * @date 2023-03-22
 */
@Data
public class NotDealWith extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识 */
    private Long id;

    /** 拒绝诊断时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拒绝诊断时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date refuseTime;

    /** 患者ID */
    @Excel(name = "患者ID")
    private String pid;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String doctorPhone;

    /** 拒绝理由 */
    @Excel(name = "拒绝理由")
    private String refuseReason;
}

