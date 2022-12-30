package com.ruoyi.xindian.ano.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * ano对象 ano_user
 * 
 * @author hanhan
 * @date 2022-12-30
 */
public class AnoUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long anoUserId;

    /** 账号id */
    @Excel(name = "账号id")
    private Long userId;

    /** 患者管理id */
    @Excel(name = "患者管理id")
    private String pId;

    public void setAnoUserId(Long anoUserId) 
    {
        this.anoUserId = anoUserId;
    }

    public Long getAnoUserId() 
    {
        return anoUserId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setpId(String pId) 
    {
        this.pId = pId;
    }

    public String getpId() 
    {
        return pId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("anoUserId", getAnoUserId())
            .append("userId", getUserId())
            .append("pId", getpId())
            .toString();
    }
}
