package com.ruoyi.xindian.shipAddress.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lixinlong
 * 2023/7/4 10:29
 */
public class ShipAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;


    private Long addressId;

    /**
     * 用户id
     */
    private Long userId;
    @Excel(name = "用户电话")
    private String patientPhone;
    @Excel(name = "用户姓名")
    private String patientName;
    @Excel(name = "街道地址")
    private String streetAddress;
    @Excel(name = "街道")
    private String street;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "省份")
    private String state;
    @Excel(name = "县")
    private String country;
    @Excel(name = "邮政编码")
    private String postalCode;
    @Excel(name = "默认地址标识符")
    private Long defaultFlag;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    private String street;

    public Long getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 删除标记位置
     */
    private Long delFlag;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Long defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("addressId", addressId)
                .append("userId", userId)
                .append("patientPhone", patientPhone)
                .append("patientName", patientName)
                .append("streetAddress", streetAddress)
                .append("street", street)
                .append("city", city)
                .append("state", state)
                .append("country", country)
                .append("postalCode", postalCode)
                .append("defaultFlag", defaultFlag)
                .append("delFlag", delFlag)
                .toString();
    }
}
