package com.ruoyi.xindian.management.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 患者管理对象 patient_management
 *
 * @author Han
 * @date 2022-07-22
 */
public class PatientManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 年龄
 */
    @Excel(name = "年龄 ")
    private String age;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 来源 */
    @Excel(name = "来源")
    private String source;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 主机发放时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "主机发放时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hostReleaseTime;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String hospitalName;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 主机序列号 */
    @Excel(name = "主机序列号")
    private String hostId;

    /** 主机mac地址 */
    @Excel(name = "主机mac地址")
    private String hostMac;

    /** 监测状态 */
    @Excel(name = "监测状态")
    private String monitoringStatus;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setAge(String age)
    {
        this.age = age;
    }

    public String getAge()
    {
        return age;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setSource(String source)
    {
        this.source = source;
    }

    public String getSource()
    {
        return source;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setHostReleaseTime(Date hostReleaseTime)
    {
        this.hostReleaseTime = hostReleaseTime;
    }

    public Date getHostReleaseTime()
    {
        return hostReleaseTime;
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
    public void setHostId(String hostId)
    {
        this.hostId = hostId;
    }

    public String getHostId()
    {
        return hostId;
    }
    public void setHostMac(String hostMac)
    {
        this.hostMac = hostMac;
    }

    public String getHostMac()
    {
        return hostMac;
    }
    public void setMonitoringStatus(String monitoringStatus)
    {
        this.monitoringStatus = monitoringStatus;
    }

    public String getMonitoringStatus()
    {
        return monitoringStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("age", getAge())
            .append("sex", getSex())
            .append("source", getSource())
            .append("phone", getPhone())
            .append("hostReleaseTime", getHostReleaseTime())
            .append("hospitalName", getHospitalName())
            .append("hospitalCode", getHospitalCode())
            .append("hostId", getHostId())
            .append("hostMac", getHostMac())
            .append("monitoringStatus", getMonitoringStatus())
            .toString();
    }
}
