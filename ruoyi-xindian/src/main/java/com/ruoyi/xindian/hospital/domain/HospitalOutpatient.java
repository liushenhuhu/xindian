package com.ruoyi.xindian.hospital.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院门诊 对象 hospital_outpatient
 *
 * @author ruoyi
 * @date 2023-10-19
 */
public class HospitalOutpatient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 门诊编号 */
    private Long outpatientId;

    /** 所属专科 */
    @Excel(name = "所属专科")
    private Long specialId;


    /** 门诊名称 */
    @Excel(name = "门诊名称")
    private String outpatientName;


    /**
     * 专科类
     */
    private HospitalSpecial hospitalSpecial;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtCreate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtModified;


    public HospitalSpecial getHospitalSpecial() {
        return hospitalSpecial;
    }

    public void setHospitalSpecial(HospitalSpecial hospitalSpecial) {
        this.hospitalSpecial = hospitalSpecial;
    }

    public Long getOutpatientId() {
        return outpatientId;
    }

    public void setOutpatientId(Long outpatientId) {
        this.outpatientId = outpatientId;
    }

    public String getOutpatientName() {
        return outpatientName;
    }

    public void setOutpatientName(String outpatientName) {
        this.outpatientName = outpatientName;
    }

    public void setSpecialId(Long specialId) {
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


}
