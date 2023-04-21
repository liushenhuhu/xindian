package com.ruoyi.xindian.equipment.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备对象 equipment
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Data
public class Equipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备id */
    private Long equipmentId;

    /** 设备号 */
    @Excel(name = "设备号")
    private String equipmentCode;

    private String equipmentName;

    /** 设备版本号 */
    @Excel(name = "设备版本号")
    private String equipmentVersion;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String equipmentStatus;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /** 科室代号 */
    @Excel(name = "科室代号")
    private String departmentCode;

    /** 设备种类（静态动态） */
    @Excel(name = "设备种类", readConverterExp = "静=态动态")
    private String equipmentType;

    /** 患者身份证 */
    @Excel(name = "患者身份证")
    private String patientPhone;

    /** 患者管理id */
    @Excel(name = "患者管理id")
    private String pId;

    /**开关机状态*/
    private String onOffStatus;
    /**
     *
     * @return
     */


    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public void setEquipmentId(Long equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId()
    {
        return equipmentId;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public void setEquipmentVersion(String equipmentVersion)
    {
        this.equipmentVersion = equipmentVersion;
    }

    public String getEquipmentVersion()
    {
        return equipmentVersion;
    }
    public void setEquipmentStatus(String equipmentStatus)
    {
        this.equipmentStatus = equipmentStatus;
    }

    public String getEquipmentStatus()
    {
        return equipmentStatus;
    }
    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("equipmentId", getEquipmentId())
                .append("equipmentCode", getEquipmentCode())
                .append("equipmentVersion", getEquipmentVersion())
                .append("equipmentStatus", getEquipmentStatus())
                .append("hospitalCode", getHospitalCode())
                .append("departmentCode", getDepartmentCode())
                .append("equipmentType",getEquipmentType())
                .append("patientPhone",getPatientPhone())
                .append("pId",getpId())
                .append("equipmentCode",getEquipmentName())
                .toString();
    }
}
