package com.ruoyi.xindian.dataLabby.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 order
 * 
 * @author chenpeng
 * @date 2023-06-07
 */
public class dataLabby extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Long orderId;

    /** 报告id */
    @Excel(name = "报告id")
    private Long reportId;

    /** 医院id */
    @Excel(name = "医院id")
    private Long hospitalId;

    /** 删除标志位 */
    private Long delFlag;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setReportId(Long reportId) 
    {
        this.reportId = reportId;
    }

    public Long getReportId() 
    {
        return reportId;
    }
    public void setHospitalId(Long hospitalId) 
    {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId() 
    {
        return hospitalId;
    }
    public void setDelFlag(Long delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Long getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("reportId", getReportId())
            .append("hospitalId", getHospitalId())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
