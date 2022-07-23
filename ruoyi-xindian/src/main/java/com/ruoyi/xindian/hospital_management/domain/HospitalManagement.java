package com.ruoyi.xindian.hospital_management.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院管理对象 hospital_management
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
public class HospitalManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String provience;

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

    /** 首次收到心电数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首次收到心电数据时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstEcgTime;

    /** 主机数量 */
    @Excel(name = "主机数量")
    private Long hostNumber;

    /** 患者总数 */
    @Excel(name = "患者总数")
    private Long patientNumber;

    /** 正在监测患者数 */
    @Excel(name = "正在监测患者数")
    private Long monitoringPatientNumber;

    /** 账号总数 */
    @Excel(name = "账号总数")
    private Long accountNumber;

    /** 是否开通数据统计 */
    @Excel(name = "是否开通数据统计")
    private String state;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProvience(String provience) 
    {
        this.provience = provience;
    }

    public String getProvience() 
    {
        return provience;
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
    public void setFirstEcgTime(Date firstEcgTime) 
    {
        this.firstEcgTime = firstEcgTime;
    }

    public Date getFirstEcgTime() 
    {
        return firstEcgTime;
    }
    public void setHostNumber(Long hostNumber) 
    {
        this.hostNumber = hostNumber;
    }

    public Long getHostNumber() 
    {
        return hostNumber;
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
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("provience", getProvience())
            .append("hospitalName", getHospitalName())
            .append("hospitalCode", getHospitalCode())
            .append("hospitalAccount", getHospitalAccount())
            .append("hospitalPassword", getHospitalPassword())
            .append("firstEcgTime", getFirstEcgTime())
            .append("hostNumber", getHostNumber())
            .append("patientNumber", getPatientNumber())
            .append("monitoringPatientNumber", getMonitoringPatientNumber())
            .append("accountNumber", getAccountNumber())
            .append("state", getState())
            .toString();
    }
}
