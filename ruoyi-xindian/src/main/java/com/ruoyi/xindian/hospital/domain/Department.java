package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 科室对象 department
 * 
 * @author hanhan
 * @date 2022-12-03
 */
public class Department extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long departmentId;

    /** 科室代号 */
    @Excel(name = "科室代号")
    private String departmentCode;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private String departmentName;

    /** 医院代号 */
    @Excel(name = "医院代号")
    private String hospitalCode;

    public void setDepartmentId(Long departmentId) 
    {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() 
    {
        return departmentId;
    }
    public void setDepartmentCode(String departmentCode) 
    {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentCode() 
    {
        return departmentCode;
    }
    public void setDepartmentName(String departmentName) 
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() 
    {
        return departmentName;
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
            .append("departmentId", getDepartmentId())
            .append("departmentCode", getDepartmentCode())
            .append("departmentName", getDepartmentName())
            .append("hospitalCode", getHospitalCode())
            .toString();
    }
}
