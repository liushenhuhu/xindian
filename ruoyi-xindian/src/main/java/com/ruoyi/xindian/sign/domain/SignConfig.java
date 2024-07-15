package com.ruoyi.xindian.sign.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * config对象 sign_config
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
public class SignConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 签到天数 */
    @Excel(name = "签到天数")
    private Long signDay;

    /** 奖励明细1：现金，2：检测次数 */
    @Excel(name = "奖励明细1：现金，2：检测次数")
    private Long sCategory;

    /** 奖励数量 */
    @Excel(name = "奖励数量")
    private Long sNumber;

    /** 备注 */
    @Excel(name = "备注")
    private String sMark;

    /** 奖励方式:1:单次，2:循环 */
    @Excel(name = "奖励方式:1:单次，2:循环")
    private Long isSet;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSignDay(Long signDay) 
    {
        this.signDay = signDay;
    }

    public Long getSignDay() 
    {
        return signDay;
    }
    public void setsCategory(Long sCategory) 
    {
        this.sCategory = sCategory;
    }

    public Long getsCategory() 
    {
        return sCategory;
    }
    public void setsNumber(Long sNumber) 
    {
        this.sNumber = sNumber;
    }

    public Long getsNumber() 
    {
        return sNumber;
    }
    public void setsMark(String sMark) 
    {
        this.sMark = sMark;
    }

    public String getsMark() 
    {
        return sMark;
    }
    public void setIsSet(Long isSet) 
    {
        this.isSet = isSet;
    }

    public Long getIsSet() 
    {
        return isSet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("signDay", getSignDay())
            .append("sCategory", getsCategory())
            .append("sNumber", getsNumber())
            .append("sMark", getsMark())
            .append("isSet", getIsSet())
            .toString();
    }
}
