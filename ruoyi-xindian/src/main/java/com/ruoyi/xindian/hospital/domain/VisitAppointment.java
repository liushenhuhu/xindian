package com.ruoyi.xindian.hospital.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 挂号预约对象 visit_appointment
 *
 * @author ruoyi
 * @date 2023-10-24
 */
public class VisitAppointment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约编号 */
    private String appointmentId;

    /** 出诊编号 */
    @Excel(name = "出诊编号")
    private Long planId;

    /** 患者手机号 */
    @Excel(name = "患者手机号")
    private String patientPhone;

    /** 陪诊人手机号 */
    @Excel(name = "陪诊人手机号")
    private String accompanyPhone;

    /** 出诊开始时间 */
    @Excel(name = "出诊开始时间")
    private String periodStart;

    /** 预约状态 0：待就诊，1：未按时就诊，2：取消预约挂号，3：已就诊 */
    @Excel(name = "预约状态 0：待就诊，3：未按时就诊，2：取消预约挂号，1：已就诊")
    private Long status;

    /** 支付状态（0：未支付，1，已支付，2：已退款） */
    @Excel(name = "支付状态", readConverterExp = "0=：未支付，1，已支付，2：已退款")
    private String payStatus;

    /** 出诊结束时间 */
    @Excel(name = "出诊结束时间")
    private String periodEnd;

    /** 专科名称 */
    @Excel(name = "专科名称")
    private String specialName;

    /** 门诊名称 */
    @Excel(name = "门诊名称")
    private String outpatientName;

    /** 科室地址 */
    @Excel(name = "科室地址")
    private String clinicAddress;

    /** 医院id */
    @Excel(name = "医院id")
    private Long hospitalId;

    /** 医生名称 */
    @Excel(name = "医生名称")
    private String doctorName;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String doctorPhone;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date periodDay;

    /** 挂号支付金额 */
    @Excel(name = "挂号支付金额")
    private BigDecimal payPrice;

    /** 就诊类型 */
    @Excel(name = "就诊类型")
    private String outpatientType;

    /** 医生职称 */
    @Excel(name = "医生职称")
    private String doctorProfessional;

    /** 出诊时间 */
    @Excel(name = "出诊时间")
    private Integer timePeriod;

    /** 预约支付标题 */
    @Excel(name = "预约支付标题")
    private String title;

    /** 商户id */
    @Excel(name = "商户id")
    private String orderNo;


    private String openId;


    private String patientName;

    private String patientSex;


    private Integer patientAge;

    private String hospitalName;

    private Date birthBay;

    public Date getBirthBay() {
        return birthBay;
    }

    public void setBirthBay(Date birthBay) {
        this.birthBay = birthBay;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setPlanId(Long planId)
    {
        this.planId = planId;
    }

    public Long getPlanId()
    {
        return planId;
    }
    public void setPatientPhone(String patientPhone)
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone()
    {
        return patientPhone;
    }
    public void setAccompanyPhone(String accompanyPhone)
    {
        this.accompanyPhone = accompanyPhone;
    }

    public String getAccompanyPhone()
    {
        return accompanyPhone;
    }
    public void setPeriodStart(String periodStart)
    {
        this.periodStart = periodStart;
    }

    public String getPeriodStart()
    {
        return periodStart;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }

    public String getPayStatus()
    {
        return payStatus;
    }
    public void setPeriodEnd(String periodEnd)
    {
        this.periodEnd = periodEnd;
    }

    public String getPeriodEnd()
    {
        return periodEnd;
    }
    public void setSpecialName(String specialName)
    {
        this.specialName = specialName;
    }

    public String getSpecialName()
    {
        return specialName;
    }
    public void setOutpatientName(String outpatientName)
    {
        this.outpatientName = outpatientName;
    }

    public String getOutpatientName()
    {
        return outpatientName;
    }
    public void setClinicAddress(String clinicAddress)
    {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicAddress()
    {
        return clinicAddress;
    }
    public void setHospitalId(Long hospitalId)
    {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId()
    {
        return hospitalId;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public void setPeriodDay(Date periodDay)
    {
        this.periodDay = periodDay;
    }

    public Date getPeriodDay()
    {
        return periodDay;
    }
    public void setPayPrice(BigDecimal payPrice)
    {
        this.payPrice = payPrice;
    }

    public BigDecimal getPayPrice()
    {
        return payPrice;
    }
    public void setOutpatientType(String outpatientType)
    {
        this.outpatientType = outpatientType;
    }

    public String getOutpatientType()
    {
        return outpatientType;
    }
    public void setDoctorProfessional(String doctorProfessional)
    {
        this.doctorProfessional = doctorProfessional;
    }

    public String getDoctorProfessional()
    {
        return doctorProfessional;
    }

    public Integer getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(Integer timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("appointmentId", getAppointmentId())
                .append("planId", getPlanId())
                .append("patientPhone", getPatientPhone())
                .append("accompanyPhone", getAccompanyPhone())
                .append("periodStart", getPeriodStart())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("payStatus", getPayStatus())
                .append("periodEnd", getPeriodEnd())
                .append("specialName", getSpecialName())
                .append("outpatientName", getOutpatientName())
                .append("clinicAddress", getClinicAddress())
                .append("hospitalId", getHospitalId())
                .append("periodDay", getPeriodDay())
                .append("payPrice", getPayPrice())
                .append("outpatientType", getOutpatientType())
                .append("doctorProfessional", getDoctorProfessional())
                .append("timePeriod", getTimePeriod())
                .append("title", getTitle())
                .append("orderNo", getOrderNo())
                .toString();
    }
}
