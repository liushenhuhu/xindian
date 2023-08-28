package com.ruoyi.xindian.vipPatient.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * vip用户对象 vip_patient
 *
 * @author ruoyi
 * @date 2023-07-06
 */
public class VipPatient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 检测次数 */
    @Excel(name = "检测次数")
    private Long vipNum;

    private Date createTime;
    private Long vipType;

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVipType() {
        return vipType;
    }

    public void setVipType(Long vipType) {
        this.vipType = vipType;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPatientPhone(String patientPhone)
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone()
    {
        return patientPhone;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setVipNum(Long vipNum)
    {
        this.vipNum = vipNum;
    }

    public Long getVipNum()
    {
        return vipNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("patientPhone", getPatientPhone())
                .append("endDate", getEndDate())
                .append("vipNum", getVipNum())
                .toString();
    }
}
