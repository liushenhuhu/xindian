package com.ruoyi.xindian.sign.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * sign对象 user_sign_log
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
public class UserSignLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 签到奖励内容 */
    @Excel(name = "签到奖励内容")
    private String signReward;

    /** 签到时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签到时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signTime;

    /** 签到类型1=签到2=补签 */
    @Excel(name = "签到类型1=签到2=补签")
    private String signType;

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
    public void setSignReward(String signReward) 
    {
        this.signReward = signReward;
    }

    public String getSignReward() 
    {
        return signReward;
    }
    public void setSignTime(Date signTime) 
    {
        this.signTime = signTime;
    }

    public Date getSignTime() 
    {
        return signTime;
    }
    public void setSignType(String signType) 
    {
        this.signType = signType;
    }

    public String getSignType() 
    {
        return signType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("patientPhone", getPatientPhone())
            .append("signReward", getSignReward())
            .append("signTime", getSignTime())
            .append("signType", getSignType())
            .append("createTime", getCreateTime())
            .toString();
    }
}
