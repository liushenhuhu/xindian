package com.ruoyi.xindian.hospital.domain;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院门诊关系 对象 hospital_outpatient_relation
 *
 * @author ruoyi
 * @date 2023-10-19
 */
public class HospitalOutpatientRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关系编号 */
    private Long id;

    /** 医院编号 */
    @Excel(name = "医院编号")
    private Long hospitalId;

    /** 门诊编号 */
    @Excel(name = "门诊编号")
    private Long outpatientId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtCreate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtModified;


    private Long specialId;


    private Hospital hospital;


    private HospitalOutpatient hospitalOutpatient;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HospitalOutpatientRelation that = (HospitalOutpatientRelation) o;
        return Objects.equals(id, that.id) && Objects.equals(hospitalId, that.hospitalId) && Objects.equals(outpatientId, that.outpatientId) && Objects.equals(gmtCreate, that.gmtCreate) && Objects.equals(gmtModified, that.gmtModified) && Objects.equals(specialId, that.specialId) && Objects.equals(hospital, that.hospital) && Objects.equals(hospitalOutpatient, that.hospitalOutpatient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hospitalId, outpatientId, gmtCreate, gmtModified, specialId, hospital, hospitalOutpatient);
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public HospitalOutpatient getHospitalOutpatient() {
        return hospitalOutpatient;
    }

    public void setHospitalOutpatient(HospitalOutpatient hospitalOutpatient) {
        this.hospitalOutpatient = hospitalOutpatient;
    }

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
    public void setOutpatientId(Long outpatientId)
    {
        this.outpatientId = outpatientId;
    }

    public Long getOutpatientId()
    {
        return outpatientId;
    }
    public void setGmtCreate(Date gmtCreate)
    {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate()
    {
        return gmtCreate;
    }
    public void setGmtModified(Date gmtModified)
    {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified()
    {
        return gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("hospitalId", getHospitalId())
                .append("outpatientId", getOutpatientId())
                .append("gmtCreate", getGmtCreate())
                .append("gmtModified", getGmtModified())
                .toString();
    }
}
