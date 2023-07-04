package com.ruoyi.xindian.relationship.domain;

import com.ruoyi.xindian.patient.domain.Patient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 患者关系表对象 patient_relationship
 *
 * @author chenpeng
 * @date 2023-06-30
 */
public class PatientRelationship extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关系id */
    private Long id;

    /** 父电话 */
    @Excel(name = "父电话")
    private String fatherPhone;

    /** 子电话 */
    @Excel(name = "子电话")
    private String sonPhone;

    /** 关系 */
    @Excel(name = "关系")
    private String relationship;
//    患者姓名
    private String relationshipPatientName;

    public String getRelationshipPatientName() {
        return relationshipPatientName;
    }

    public void setRelationshipPatientName(String relationshipPatientName) {
        this.relationshipPatientName = relationshipPatientName;
    }
//    public Patient getRelationshipPatient() {
//        return relationshipPatient;
//    }
//
//    public void setRelationshipPatient(Patient relationshipPatient) {
//        this.relationshipPatient = relationshipPatient;
//    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setFatherPhone(String fatherPhone)
    {
        this.fatherPhone = fatherPhone;
    }

    public String getFatherPhone()
    {
        return fatherPhone;
    }
    public void setSonPhone(String sonPhone)
    {
        this.sonPhone = sonPhone;
    }

    public String getSonPhone()
    {
        return sonPhone;
    }
    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }

    public String getRelationship()
    {
        return relationship;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fatherPhone", getFatherPhone())
                .append("sonPhone", getSonPhone())
                .append("relationship", getRelationship())
                .toString();
    }
}

