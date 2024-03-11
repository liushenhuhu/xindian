package com.ruoyi.xindian.appData.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;


/**
 * app相关数据对象 app_data
 * 
 * @author hanhan
 * @date 2022-10-06
 */
public class AppData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long appDataId;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String patientName;
    private String patientNameAes;

    /** 患者性别 */
    @Excel(name = "患者性别")
    private String patientSex;

    /** 患者年龄 */
    @Excel(name = "患者年龄")
    private String patientAge;

    /** 患者身份证号 */
    @Excel(name = "患者身份证号")
    private String patientCode;

    /** 患者手机号 */
    @Excel(name = "患者手机号")
    private String patientPhone;
    private String patientPhoneAes;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 患者出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "患者出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDay;

    /** 医生咨询次数*/
    private Long questionNum;

    /** 关系*/
    private String relation;

    private String hospital;

    private String familyPhone;

    private String familyName;


    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPatientNameAes() {
        return patientNameAes;
    }

    public void setPatientNameAes(String patientNameAes) {
        this.patientNameAes = patientNameAes;
    }

    public String getPatientPhoneAes() {
        return patientPhoneAes;
    }

    public void setPatientPhoneAes(String patientPhoneAes) {
        this.patientPhoneAes = patientPhoneAes;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setAppDataId(Long appDataId)
    {
        this.appDataId = appDataId;
    }

    public Long getAppDataId()
    {
        return appDataId;
    }

    public void setQuestionNum(Long questionNum)
    {
        this.questionNum = questionNum;
    }

    public Long getQuestionNum()
    {
        return questionNum;
    }
    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getPatientName()
    {
        return patientName;
    }
    public void setPatientSex(String patientSex)
    {
        this.patientSex = patientSex;
    }

    public String getPatientSex()
    {
        return patientSex;
    }
    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }

    public String getPatientAge()
    {
        return patientAge;
    }
    public void setPatientCode(String patientCode)
    {
        this.patientCode = patientCode;
    }

    public String getPatientCode()
    {
        return patientCode;
    }
    public void setPatientPhone(String patientPhone)
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone()
    {
        return patientPhone;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setBirthDay(Date birthDay)
    {
        this.birthDay = birthDay;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("appDataId", getAppDataId())
                .append("patientName", getPatientName())
                .append("patientSex", getPatientSex())
                .append("patientAge", getPatientAge())
                .append("patientCode", getPatientCode())
                .append("patientPhone", getPatientPhone())
                .append("userName", getUserName())
                .append("birthDay", getBirthDay())
                .toString();
    }
}
