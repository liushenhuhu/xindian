package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医生对象 doctor
 * 
 * @author hanhan
 * @date 2022-12-03
 */
public class Doctor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long doctorId;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorName;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String doctorPhone;

    /** 科室代号 */
    @Excel(name = "科室代号")
    private String departmentCode;

    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setDoctorName(String doctorName) 
    {
        this.doctorName = doctorName;
    }

    public String getDoctorName() 
    {
        return doctorName;
    }
    public void setDoctorPhone(String doctorPhone) 
    {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorPhone() 
    {
        return doctorPhone;
    }
    public void setDepartmentCode(String departmentCode) 
    {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentCode() 
    {
        return departmentCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("doctorId", getDoctorId())
            .append("doctorName", getDoctorName())
            .append("doctorPhone", getDoctorPhone())
            .append("departmentCode", getDepartmentCode())
            .toString();
    }
}
