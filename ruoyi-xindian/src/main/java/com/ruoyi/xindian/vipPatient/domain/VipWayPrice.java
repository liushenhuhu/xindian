package com.ruoyi.xindian.vipPatient.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 vip_way_price
 *
 * @author ruoyi
 * @date 2023-07-06
 */
public class VipWayPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** vip方式  1：包年 2：单次 */
    @Excel(name = "vip方式  1：包年 2：单次")
    private Long way;

    /** 价钱(分) */
    @Excel(name = "价钱(分)")
    private String price;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setWay(Long way)
    {
        this.way = way;
    }

    public Long getWay()
    {
        return way;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getPrice()
    {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("way", getWay())
                .append("price", getPrice())
                .toString();
    }
}

