package com.ruoyi.xindian.medical.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 病史对象 medical_history
 * 
 * @author hanhan
 * @date 2023-02-21
 */
public class MedicalHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long medicalHistoryId;

    /** 身高 */
    @Excel(name = "身高")
    private Long height;

    /** 体重 */
    @Excel(name = "体重")
    private Long weight;

    /** 过往病史 */
    @Excel(name = "过往病史")
    private String pastMedicalHistory;

    /** 生活习惯 */
    @Excel(name = "生活习惯")
    private String livingHabit;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    public void setMedicalHistoryId(Long medicalHistoryId) 
    {
        this.medicalHistoryId = medicalHistoryId;
    }

    public Long getMedicalHistoryId() 
    {
        return medicalHistoryId;
    }
    public void setHeight(Long height) 
    {
        this.height = height;
    }

    public Long getHeight() 
    {
        return height;
    }
    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    public void setLivingHabit(String livingHabit)
    {
        this.livingHabit = livingHabit;
    }

    public String getLivingHabit() 
    {
        return livingHabit;
    }
    public void setPatientPhone(String patientPhone) 
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone() 
    {
        return patientPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("medicalHistoryId", getMedicalHistoryId())
            .append("height", getHeight())
            .append("weight", getWeight())
            .append("pastMedicalHistory", getPastMedicalHistory())
            .append("livingHabit", getLivingHabit())
            .append("patientPhone", getPatientPhone())
            .toString();
    }
}
