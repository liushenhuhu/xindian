package com.ruoyi.xindian.hospital.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出诊预约表对象 visit_appointment
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class VisitAppointment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约编号 */
    private Long id;

    /** 出诊编号 */
    @Excel(name = "出诊编号")
    private Long planId;

    /** 就诊卡号 */
    @Excel(name = "就诊卡号")
    private Long cardId;

    /** 账号编号 */
    @Excel(name = "账号编号")
    private Long accountId;

    /** 出诊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出诊时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date timePeriod;

    /** 预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成 */
    @Excel(name = "预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setCardId(Long cardId) 
    {
        this.cardId = cardId;
    }

    public Long getCardId() 
    {
        return cardId;
    }
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setTimePeriod(Date timePeriod) 
    {
        this.timePeriod = timePeriod;
    }

    public Date getTimePeriod() 
    {
        return timePeriod;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("cardId", getCardId())
            .append("accountId", getAccountId())
            .append("timePeriod", getTimePeriod())
            .append("status", getStatus())
            .toString();
    }
}
