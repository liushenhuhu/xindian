package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院专科表对象 hospital_special
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class HospitalSpecial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专科编号 */
    private Long id;

    /** 专科名称 */
    @Excel(name = "专科名称")
    private String name;

    /** 专科简介 */
    @Excel(name = "专科简介")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("description", getDescription())
            .toString();
    }
}
