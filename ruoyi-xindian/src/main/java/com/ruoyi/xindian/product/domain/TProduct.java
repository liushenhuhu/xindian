package com.ruoyi.xindian.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品信息对象 t_product
 * 
 * @author chenpeng
 * @date 2023-07-04
 */
public class TProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商Bid */
    private Long productId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 价格(分) */
    @Excel(name = "价格(分)")
    private Long price;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String productDescription;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String productUrl;

    /** 商品折扣 */
    @Excel(name = "商品折扣")
    private String discount;

    /** 状态（1：上架，2：下架，3：缺货） */
    @Excel(name = "状态", readConverterExp = "1=：上架，2：下架，3：缺货")
    private String state;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private String productNum;

    /** 销量 */
    @Excel(name = "销量")
    private Long sales;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 商品类型
     */
    private String type;

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }
    public void setProductDescription(String productDescription) 
    {
        this.productDescription = productDescription;
    }

    public String getProductDescription() 
    {
        return productDescription;
    }
    public void setProductUrl(String productUrl) 
    {
        this.productUrl = productUrl;
    }

    public String getProductUrl() 
    {
        return productUrl;
    }
    public void setDiscount(String discount) 
    {
        this.discount = discount;
    }

    public String getDiscount() 
    {
        return discount;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setProductNum(String productNum) 
    {
        this.productNum = productNum;
    }

    public String getProductNum() 
    {
        return productNum;
    }
    public void setSales(Long sales) 
    {
        this.sales = sales;
    }

    public Long getSales() 
    {
        return sales;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("price", getPrice())
            .append("productDescription", getProductDescription())
            .append("productUrl", getProductUrl())
            .append("discount", getDiscount())
            .append("state", getState())
            .append("updateTime", getUpdateTime())
            .append("createTime", getCreateTime())
            .append("productNum", getProductNum())
            .append("sales", getSales())
            .toString();
    }
}
