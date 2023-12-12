package com.ruoyi.xindian.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getUrlOne() {
        return urlOne;
    }

    public void setUrlOne(String urlOne) {
        this.urlOne = urlOne;
    }

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 价格(分) */
    @Excel(name = "价格(分)")
    private BigDecimal price;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String productDescription;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String productUrl;

    /** 商品折扣 */
    @Excel(name = "商品折扣")
    private BigDecimal discount;

    /** 状态（1：上架，2：下架，3：缺货） */
    @Excel(name = "状态", readConverterExp = "1=：上架，2：下架，3：缺货")
    private String state;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private String productNum;

    /** 销量 */
    @Excel(name = "销量")
    private Long sales;


    private String video;

    /**
     * 商品类型
     */
    private String type;


    private String urlOne;

    /**
     * 服务次数
     */
    private String frequency;

    /**
     * 是否是vip服务
     */
    private String isVip;

    private Integer delFlag;

    /**
     * 图片集合
     * @return
     */
    private List<String>  stringImg;


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public List<String> getStringImg() {
        return stringImg;
    }

    public void setStringImg(List<String> stringImg) {
        this.stringImg = stringImg;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TProduct{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", productDescription='" + productDescription + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", discount=" + discount +
                ", state='" + state + '\'' +
                ", productNum='" + productNum + '\'' +
                ", sales=" + sales +
                ", type='" + type + '\'' +
                '}';
    }
}
