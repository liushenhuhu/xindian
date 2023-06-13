package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出诊黑名单对象 visit_blacklist
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class VisitBlacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 名单编号 */
    private Long id;

    /** 就诊卡号 */
    @Excel(name = "就诊卡号")
    private Long cardId;

    /** 禁封状态 1：生效，2：已解封 */
    @Excel(name = "禁封状态 1：生效，2：已解封")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCardId(Long cardId) 
    {
        this.cardId = cardId;
    }

    public Long getCardId() 
    {
        return cardId;
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
            .append("cardId", getCardId())
            .append("status", getStatus())
            .toString();
    }
}
