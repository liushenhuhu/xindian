package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关系表对象 hospital_special_relation
 * 
 * @author chenpeng
 * @date 2023-06-12
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("hospitalId", getHospitalId())
            .append("specialId", getSpecialId())
            .toString();
    }
}
