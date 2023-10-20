package com.ruoyi.xindian.hospital.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院专科 对象 hospital_special
 *
 * @author ruoyi
 * @date 2023-10-19
 */
public class HospitalSpecial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专科编号 */
    private Long specialId;

    /** 专科名称 */
    @Excel(name = "专科名称")
    private String specialName;

    /** 专科简介 */
    @Excel(name = "专科简介")
    private String description;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtCreate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtModified;

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
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
