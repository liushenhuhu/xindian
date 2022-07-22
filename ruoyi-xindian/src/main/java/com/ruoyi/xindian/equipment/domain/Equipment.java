package com.ruoyi.xindian.equipment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备管理对象 equipment
 * 
 * @author Han
 * @date 2022-07-22
 */
public class Equipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private String id;

    /** 主机序列号 */
    @Excel(name = "主机序列号")
    private String hostId;

    /** 主机MAC地址 */
    @Excel(name = "主机MAC地址")
    private String hostMac;

    /** 主机版本号 */
    @Excel(name = "主机版本号")
    private String hostVersion;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String hospitalName;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 连接时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "连接时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date connectionTime;

    /** 最近使用患者姓名 */
    @Excel(name = "最近使用患者姓名")
    private String recentlyUsername;

    /** 年龄 */
    @Excel(name = "年龄")
    private String age;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
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
    public void setHostVersion(String hostVersion) 
    {
        this.hostVersion = hostVersion;
    }

    public String getHostVersion() 
    {
        return hostVersion;
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
    public void setConnectionTime(Date connectionTime) 
    {
        this.connectionTime = connectionTime;
    }

    public Date getConnectionTime() 
    {
        return connectionTime;
    }
    public void setRecentlyUsername(String recentlyUsername) 
    {
        this.recentlyUsername = recentlyUsername;
    }

    public String getRecentlyUsername() 
    {
        return recentlyUsername;
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
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("hostId", getHostId())
            .append("hostMac", getHostMac())
            .append("hostVersion", getHostVersion())
            .append("hospitalName", getHospitalName())
            .append("hospitalCode", getHospitalCode())
            .append("connectionTime", getConnectionTime())
            .append("recentlyUsername", getRecentlyUsername())
            .append("age", getAge())
            .append("sex", getSex())
            .append("phone", getPhone())
            .toString();
    }
}
