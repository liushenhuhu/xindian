package com.ruoyi.xindian.medical.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 病数据对象 medical_data
 * 
 * @author hanhan
 * @date 2023-02-21
 */
public class MedicalData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long medicalDataId;

    /** 病名称 */
    @Excel(name = "病名称")
    private String medicalName;

    /** 病代号 */
    @Excel(name = "病代号")
    private Long medicalCode;

    public void setMedicalDataId(Long medicalDataId) 
    {
        this.medicalDataId = medicalDataId;
    }

    public Long getMedicalDataId() 
    {
        return medicalDataId;
    }
    public void setMedicalName(String medicalName) 
    {
        this.medicalName = medicalName;
    }

    public String getMedicalName() 
    {
        return medicalName;
    }
    public void setMedicalCode(Long medicalCode) 
    {
        this.medicalCode = medicalCode;
    }

    public Long getMedicalCode() 
    {
        return medicalCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("medicalDataId", getMedicalDataId())
            .append("medicalName", getMedicalName())
            .append("medicalCode", getMedicalCode())
            .toString();
    }
}
