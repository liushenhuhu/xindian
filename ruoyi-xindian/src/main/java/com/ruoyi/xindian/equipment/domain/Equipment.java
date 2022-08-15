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
 * @author hanhan
 * @date 2022-08-14
 */
public class Equipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备号 */
    private String equipmentId;

    /** 设备版本号 */
    @Excel(name = "设备版本号")
    private String equipmentVersion;

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

    /** 患者ID */
    @Excel(name = "患者ID")
    private String patientId;

    public void setEquipmentId(String equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId() 
    {
        return equipmentId;
    }
    public void setEquipmentVersion(String equipmentVersion) 
    {
        this.equipmentVersion = equipmentVersion;
    }

    public String getEquipmentVersion() 
    {
        return equipmentVersion;
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
    public void setPatientId(String patientId) 
    {
        this.patientId = patientId;
    }

    public String getPatientId() 
    {
        return patientId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("equipmentId", getEquipmentId())
            .append("equipmentVersion", getEquipmentVersion())
            .append("hospitalName", getHospitalName())
            .append("hospitalCode", getHospitalCode())
            .append("connectionTime", getConnectionTime())
            .append("patientId", getPatientId())
            .toString();
    }
}
