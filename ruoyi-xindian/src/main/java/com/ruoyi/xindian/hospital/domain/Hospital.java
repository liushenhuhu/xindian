package com.ruoyi.xindian.hospital.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院对象 hospital
 *
 * @author hanhan
 * @date 2022-08-20
 */
public class Hospital extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 医院id */
    private Long hospitalId;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String hospitalName;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 医院账号 */
    @Excel(name = "医院账号")
    private String hospitalAccount;

    /** 医院密码 */
    @Excel(name = "医院密码")
    private String hospitalPassword;

    /** 设备数量 */
    @Excel(name = "设备数量")
    private Long equipmentNumber;

    /** 患者总数 */
    @Excel(name = "患者总数")
    private Long patientNumber;

    /** 正在监测患者数 */
    @Excel(name = "正在监测患者数")
    private Long monitoringPatientNumber;

    /** 账号总数 */
    @Excel(name = "账号总数")
    private Long accountNumber;

    /** 首次收到心电数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首次收到心电数据时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstEcgTime;

    /** 是否开通数据统计 */
    @Excel(name = "是否开通数据统计")
    private String ifStatistics;

    private String price;

    public List<String> getHospitalCodeList() {
        return hospitalCodeList;
    }

    public void setHospitalCodeList(List<String> hospitalCodeList) {
        this.hospitalCodeList = hospitalCodeList;
    }

    private String img;


    private List<String> hospitalCodeList = new ArrayList<>();
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setHospitalId(Long hospitalId)
    {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId()
    {
        return hospitalId;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvince()
    {
        return province;
    }
    public void setHospitalName(String hospitalName)
    {
        this.hospitalName = hospitalName;
    }

    public String getHospitalName()
    {
        return hospitalName;
    }
    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
    }
    public void setHospitalAccount(String hospitalAccount)
    {
        this.hospitalAccount = hospitalAccount;
    }

    public String getHospitalAccount()
    {
        return hospitalAccount;
    }
    public void setHospitalPassword(String hospitalPassword)
    {
        this.hospitalPassword = hospitalPassword;
    }

    public String getHospitalPassword()
    {
        return hospitalPassword;
    }
    public void setEquipmentNumber(Long equipmentNumber)
    {
        this.equipmentNumber = equipmentNumber;
    }

    public Long getEquipmentNumber()
    {
        return equipmentNumber;
    }
    public void setPatientNumber(Long patientNumber)
    {
        this.patientNumber = patientNumber;
    }

    public Long getPatientNumber()
    {
        return patientNumber;
    }
    public void setMonitoringPatientNumber(Long monitoringPatientNumber)
    {
        this.monitoringPatientNumber = monitoringPatientNumber;
    }

    public Long getMonitoringPatientNumber()
    {
        return monitoringPatientNumber;
    }
    public void setAccountNumber(Long accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public Long getAccountNumber()
    {
        return accountNumber;
    }
    public void setFirstEcgTime(Date firstEcgTime)
    {
        this.firstEcgTime = firstEcgTime;
    }

    public Date getFirstEcgTime()
    {
        return firstEcgTime;
    }
    public void setIfStatistics(String ifStatistics)
    {
        this.ifStatistics = ifStatistics;
    }

    public String getIfStatistics()
    {
        return ifStatistics;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("hospitalId", getHospitalId())
                .append("province", getProvince())
                .append("hospitalName", getHospitalName())
                .append("hospitalCode", getHospitalCode())
                .append("hospitalAccount", getHospitalAccount())
                .append("hospitalPassword", getHospitalPassword())
                .append("equipmentNumber", getEquipmentNumber())
                .append("patientNumber", getPatientNumber())
                .append("monitoringPatientNumber", getMonitoringPatientNumber())
                .append("accountNumber", getAccountNumber())
                .append("firstEcgTime", getFirstEcgTime())
                .append("ifStatistics", getIfStatistics())
                .toString();
    }
}
