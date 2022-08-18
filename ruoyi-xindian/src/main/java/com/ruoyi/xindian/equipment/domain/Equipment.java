package com.ruoyi.xindian.equipment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备对象 equipment
 *
 * @author hanhan
 * @date 2022-08-15
 */
public class Equipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备id */
    private String equipmentId;

    /** 设备号 */
    @Excel(name = "设备号")
    private String equipmentNumber;

    /** 设备版本号 */
    @Excel(name = "设备版本号")
    private String equipmentVersion;

    /** 连接时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "连接时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date connectionTime;

    /** 患者id */
    @Excel(name = "患者id")
    private String patientId;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String patientName;

    /** 患者身份证号 */
    @Excel(name = "患者身份证号")
    private String patientNumber;

    /** 患者年龄*/
    @Excel(name = "患者年龄 ")
    private String patientAge;

    /** 患者性别 */
    @Excel(name = "患者性别")
    private String patientSex;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String hospitalName;

    public void setEquipmentId(String equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId()
    {
        return equipmentId;
    }
    public void setEquipmentNumber(String equipmentNumber)
    {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentNumber()
    {
        return equipmentNumber;
    }
    public void setEquipmentVersion(String equipmentVersion)
    {
        this.equipmentVersion = equipmentVersion;
    }

    public String getEquipmentVersion()
    {
        return equipmentVersion;
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
    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("equipmentId", getEquipmentId())
                .append("equipmentNumber", getEquipmentNumber())
                .append("equipmentVersion", getEquipmentVersion())
                .append("connectionTime", getConnectionTime())
                .append("patientId", getPatientId())
                .append("patientName",getPatientName())
                .append("patientNumber",getPatientNumber())
                .append("patientAge",getPatientAge())
                .append("patientSex",getPatientSex())
                .append("patientPhone",getPatientPhone())
                .append("hospitalCode", getHospitalCode())
                .append("hospitalName", getHospitalName())
                .toString();
    }
}
