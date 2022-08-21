package com.ruoyi.xindian.equipment.domain;

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
public class Equipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备id */
    private Long equipmentId;

    /** 设备号 */
    @Excel(name = "设备号")
    private String equipmentNumber;

    /** 设备版本号 */
    @Excel(name = "设备版本号")
    private String equipmentVersion;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String equipmentStatus;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    public void setEquipmentId(Long equipmentId)
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId()
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("equipmentId", getEquipmentId())
                .append("equipmentNumber", getEquipmentNumber())
                .append("equipmentVersion", getEquipmentVersion())
                .append("equipmentStatus", getEquipmentStatus())
                .append("hospitalCode", getHospitalCode())
                .toString();
    }
}
