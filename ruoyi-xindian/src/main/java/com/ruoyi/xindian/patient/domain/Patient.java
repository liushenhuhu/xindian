package com.ruoyi.xindian.patient.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 患者对象 patient
 *
 * @author hanhan
 * @date 2022-08-15
 */
public class Patient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 患者id */
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

    /** 患者来源 */
    @Excel(name = "患者来源")
    private String patientSource;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 家属电话 */
    @Excel(name = "家属电话")
    private String familyPhone;

    /** 最近连接设备号 */
    @Excel(name = "最近连接设备号")
    private String equipmentId;

    /** 监测状态 */
    @Excel(name = "监测状态")
    private String monitoringStatus;

    /** 床位号 */
    @Excel(name = "床位号")
    private String bedNumber;

    /** 病历号 */
    @Excel(name = "病历号")
    private String caseHistoryNumber;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 医院名称 */
    @Excel(name = "医院代号")
    private String hospitalName;

    public void setPatientId(String patientId)
    {
        this.patientId = patientId;
    }

    public String getPatientId()
    {
        return patientId;
    }
    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getPatientName()
    {
        return patientName;
    }
    public void setPatientNumber(String patientNumber)
    {
        this.patientNumber = patientNumber;
    }

    public String getPatientNumber()
    {
        return patientNumber;
    }
    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }

    public String getPatientAge()
    {
        return patientAge;
    }
    public void setPatientSex(String patientSex)
    {
        this.patientSex = patientSex;
    }

    public String getPatientSex()
    {
        return patientSex;
    }
    public void setPatientSource(String patientSource)
    {
        this.patientSource = patientSource;
    }

    public String getPatientSource()
    {
        return patientSource;
    }
    public void setPatientPhone(String patientPhone)
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone()
    {
        return patientPhone;
    }
    public void setFamilyPhone(String familyPhone)
    {
        this.familyPhone = familyPhone;
    }

    public String getFamilyPhone()
    {
        return familyPhone;
    }
    public void setEquipmentId(String equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId()
    {
        return equipmentId;
    }
    public void setMonitoringStatus(String monitoringStatus)
    {
        this.monitoringStatus = monitoringStatus;
    }

    public String getMonitoringStatus()
    {
        return monitoringStatus;
    }
    public void setBedNumber(String bedNumber)
    {
        this.bedNumber = bedNumber;
    }

    public String getBedNumber()
    {
        return bedNumber;
    }
    public void setCaseHistoryNumber(String caseHistoryNumber)
    {
        this.caseHistoryNumber = caseHistoryNumber;
    }

    public String getCaseHistoryNumber()
    {
        return caseHistoryNumber;
    }
    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
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
            .append("patientId", getPatientId())
            .append("patientName", getPatientName())
            .append("patientNumber", getPatientNumber())
            .append("patientAge", getPatientAge())
            .append("patientSex", getPatientSex())
            .append("patientSource", getPatientSource())
            .append("patientPhone", getPatientPhone())
            .append("familyPhone", getFamilyPhone())
            .append("equipmentId", getEquipmentId())
            .append("monitoringStatus", getMonitoringStatus())
            .append("bedNumber", getBedNumber())
            .append("caseHistoryNumber", getCaseHistoryNumber())
            .append("hospitalCode", getHospitalCode())
            .append("hospitalName", getHospitalName())
            .toString();
    }
}
