package com.ruoyi.xindian.sign.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * sign对象 user_sign
 *
 * @author chenpeng
 * @date 2024-06-25
 */
public class UserSign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 电话 */
    @Excel(name = "电话")
    private String patientPhone;

    /** 累积签到天数 */
    @Excel(name = "累积签到天数")
    private Long seriesDays;

    /** 连续签到天数 */
    @Excel(name = "连续签到天数")
    private Long continuityDays;

    /** 最后一次签到时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后一次签到时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastSign;

    /** 可补签次数 */
    @Excel(name = "可补签次数")
    private Long signNums;

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
    public void setSeriesDays(Long seriesDays)
    {
        this.seriesDays = seriesDays;
    }

    public Long getSeriesDays()
    {
        return seriesDays;
    }
    public void setContinuityDays(Long continuityDays)
    {
        this.continuityDays = continuityDays;
    }

    public Long getContinuityDays()
    {
        return continuityDays;
    }
    public void setLastSign(Date lastSign)
    {
        this.lastSign = lastSign;
    }

    public Date getLastSign()
    {
        return lastSign;
    }
    public void setSignNums(Long signNums)
    {
        this.signNums = signNums;
    }

    public Long getSignNums()
    {
        return signNums;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("patientPhone", getPatientPhone())
                .append("seriesDays", getSeriesDays())
                .append("continuityDays", getContinuityDays())
                .append("lastSign", getLastSign())
                .append("signNums", getSignNums())
                .toString();
    }
}
