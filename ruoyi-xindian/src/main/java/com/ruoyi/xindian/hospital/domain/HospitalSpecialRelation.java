package com.ruoyi.xindian.hospital.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 hospital_special_relation
 *
 * @author ruoyi
 * @date 2023-10-19
 */
public class HospitalSpecialRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关系编号 */
    private Long id;

    /** 医院编号 */
    @Excel(name = "医院编号")
    private Long hospitalId;

    /** 专科编号 */
    @Excel(name = "专科编号")
    private Long specialId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtCreate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtModified;

    private Hospital hospital;


    private HospitalSpecial hospitalSpecial;


    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public HospitalSpecial getHospitalSpecial() {
        return hospitalSpecial;
    }

    public void setHospitalSpecial(HospitalSpecial hospitalSpecial) {
        this.hospitalSpecial = hospitalSpecial;
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
    public void setSpecialId(Long specialId)
    {
        this.specialId = specialId;
    }

    public Long getSpecialId()
    {
        return specialId;
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
                .append("specialId", getSpecialId())
                .append("gmtCreate", getGmtCreate())
                .append("gmtModified", getGmtModified())
                .toString();
    }
}
