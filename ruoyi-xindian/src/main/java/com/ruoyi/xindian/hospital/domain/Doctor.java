package com.ruoyi.xindian.hospital.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生对象 doctor
 * 
 * @author hanhan
 * @date 2022-12-03
 */
public class Doctor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long doctorId;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorName;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String doctorPhone;

    /** 科室代号 */
    @Excel(name = "科室代号")
    private String departmentCode;

    @Excel(name = "科室")
    private String departmentName;

    /** 擅长 */
    @Excel(name = "擅长")
    private String skill;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    /** 服务内容 */
    @Excel(name = "服务内容")
    private String serviceContent;

    /** 适用人群 */
    @Excel(name = "适用人群")
    private String applicablePopulation;

    /** 收费价格 */
    @Excel(name = "收费价格")
    private BigDecimal chargePrice;

    /** 在线状态 */
    @Excel(name = "在线状态")
    private String onlineState;

    /** 医院 */
    @Excel(name = "医院")
    private String hospital;

    private String hospitalCode;
    /** 头像地址 */
    @Excel(name = "头像地址")
    private String img;

    @Excel(name = "职称")
    private String professional;

    private String equipmentList;

    private String openId;

    private String unionId;

    /**
     * 用于判断是否是正规医生，还是测试医生
     */
    private String isDoc;

    /** 门诊编号 */
    private Long outpatientId;

    /** 所属专科 */
    @Excel(name = "所属专科")
    private Long specialId;


    private String accountStatus;


    private Long userId;
    /**
     * 电子签地址
     */
    private String dzVisa;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDzVisa() {
        return dzVisa;
    }

    public void setDzVisa(String dzVisa) {
        this.dzVisa = dzVisa;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getOutpatientId() {
        return outpatientId;
    }

    public void setOutpatientId(Long outpatientId) {
        this.outpatientId = outpatientId;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getIsDoc() {
        return isDoc;
    }

    public void setIsDoc(String isDoc) {
        this.isDoc = isDoc;
    }

    private List<String> hospitalNameList = new ArrayList<>();

    public List<String> getHospitalNameList() {
        return hospitalNameList;
    }

    public void setHospitalNameList(List<String> hospitalNameList) {
        this.hospitalNameList = hospitalNameList;
    }

    public String getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(String equipmentList) {
        this.equipmentList = equipmentList;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setDoctorId(Long doctorId)
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId()
    {
        return doctorId;
    }
    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }

    public String getDoctorName()
    {
        return doctorName;
    }
    public void setDoctorPhone(String doctorPhone)
    {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorPhone()
    {
        return doctorPhone;
    }
    public void setDepartmentCode(String departmentCode)
    {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentCode()
    {
        return departmentCode;
    }

    public void setSkill(String skill)
    {
        this.skill = skill;
    }

    public String getSkill()
    {
        return skill;
    }
    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getIntroduction()
    {
        return introduction;
    }
    public void setServiceContent(String serviceContent)
    {
        this.serviceContent = serviceContent;
    }

    public String getServiceContent()
    {
        return serviceContent;
    }
    public void setApplicablePopulation(String applicablePopulation)
    {
        this.applicablePopulation = applicablePopulation;
    }

    public String getApplicablePopulation()
    {
        return applicablePopulation;
    }


    public BigDecimal getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(BigDecimal chargePrice) {
        this.chargePrice = chargePrice;
    }

    public void setOnlineState(String onlineState)
    {
        this.onlineState = onlineState;
    }

    public String getOnlineState()
    {
        return onlineState;
    }

    public void setHospital(String hospital)
    {
        this.hospital = hospital;
    }

    public String getHospital()
    {
        return hospital;
    }
    public void setImg(String img)
    {
        this.img = img;
    }

    public String getImg()
    {
        return img;
    }
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setProfessional(String professional)
    {
        this.professional = professional;
    }

    public String getProfessional()
    {
        return professional;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("doctorId", getDoctorId())
                .append("doctorName", getDoctorName())
                .append("doctorPhone", getDoctorPhone())
                .append("departmentCode", getDepartmentCode())
                .append("skill", getSkill())
                .append("introduction", getIntroduction())
                .append("serviceContent", getServiceContent())
                .append("applicablePopulation", getApplicablePopulation())
                .append("chargePrice", getChargePrice())
                .append("onlineState", getOnlineState())
                .append("hospital", getHospital())
                .append("img", getImg())
                .append("departmentName", getDepartmentName())
                .append("openId", getOpenId())
                .toString();
    }
}
