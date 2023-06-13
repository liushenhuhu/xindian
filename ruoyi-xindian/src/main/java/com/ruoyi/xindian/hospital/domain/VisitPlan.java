package com.ruoyi.xindian.hospital.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出诊信息表对象 visit_plan
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class VisitPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出诊编号 */
    private Long id;

    /** 医院编号 */
    @Excel(name = "医院编号")
    private Long hospitalId;

    /** 专科编号 */
    @Excel(name = "专科编号")
    private Long specialId;

    /** 门诊编号 */
    @Excel(name = "门诊编号")
    private Long outpatientId;

    /** 诊室编号 */
    @Excel(name = "诊室编号")
    private Long clinicId;

    /** 医生编号 */
    @Excel(name = "医生编号")
    private Long doctorId;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHospitalId(Long hospitalId) 
    {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId() 
    {
        return hospitalId;
    }
    public void setSpecialId(Long specialId) 
    {
        this.specialId = specialId;
    }

    public Long getSpecialId() 
    {
        return specialId;
    }
    public void setOutpatientId(Long outpatientId) 
    {
        this.outpatientId = outpatientId;
    }

    public Long getOutpatientId() 
    {
        return outpatientId;
    }
    public void setClinicId(Long clinicId) 
    {
        this.clinicId = clinicId;
    }

    public Long getClinicId() 
    {
        return clinicId;
    }
    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("hospitalId", getHospitalId())
            .append("specialId", getSpecialId())
            .append("outpatientId", getOutpatientId())
            .append("clinicId", getClinicId())
            .append("doctorId", getDoctorId())
            .append("time", getTime())
            .toString();
    }
}
