package com.ruoyi.xindian.wSuryvey.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * wSuryvey对象 w_survey
 * 
 * @author chenpeng
 * @date 2024-07-18
 */
public class WSurvey extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 电话 */
    @Excel(name = "电话")
    private String patientPhone;

    /** 性别 */
    @Excel(name = "性别")
    private Integer gender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 腰围 */
    @Excel(name = "腰围")
    private Long waistline;

    /** 胆固醇 */
    @Excel(name = "胆固醇")
    private Long cholesterol;

    /** 高密度脂蛋白胆固醇 */
    @Excel(name = "高密度脂蛋白胆固醇")
    private Long systolicAlbumen;

    /** 舒张压 */
    @Excel(name = "舒张压")
    private Long diastolicBlood;

    /** 收缩压 */
    @Excel(name = "收缩压")
    private Long systolicBlood;

    /** 复用降压药 */
    @Excel(name = "复用降压药")
    private Integer hypotensiveDrugs;

    /** 是否糖尿病 */
    @Excel(name = "是否糖尿病")
    private Integer diabetes;

    /** 是否吸烟 */
    @Excel(name = "是否吸烟")
    private Long smoking;

    /** 是否心血管病家族史 */
    @Excel(name = "是否心血管病家族史")
    private Long fammilyCd;

    /** 是否脑卒中家族史 */
    @Excel(name = "是否脑卒中家族史")
    private Long fammilyCa;

    /** 低密度脂蛋白胆固醇 */
    @Excel(name = "低密度脂蛋白胆固醇")
    private Long lowAlbumen;

    /** 甘油三酯 */
    @Excel(name = "甘油三酯")
    private Long triglyceride;


    private List<WSurveyHistory> wSurveyHistories = new ArrayList<>();

    public Long getLowAlbumen() {
        return lowAlbumen;
    }

    public void setLowAlbumen(Long lowAlbumen) {
        this.lowAlbumen = lowAlbumen;
    }

    public Long getTriglyceride() {
        return triglyceride;
    }

    public void setTriglyceride(Long triglyceride) {
        this.triglyceride = triglyceride;
    }


    public List<WSurveyHistory> getwSurveyHistories() {
        return wSurveyHistories;
    }

    public void setwSurveyHistories(List<WSurveyHistory> wSurveyHistories) {
        this.wSurveyHistories = wSurveyHistories;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPatientPhone(String patientPhone) 
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone() 
    {
        return patientPhone;
    }
    public void setGender(Integer gender) 
    {
        this.gender = gender;
    }

    public Integer getGender() 
    {
        return gender;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setWaistline(Long waistline) 
    {
        this.waistline = waistline;
    }

    public Long getWaistline() 
    {
        return waistline;
    }
    public void setCholesterol(Long cholesterol) 
    {
        this.cholesterol = cholesterol;
    }

    public Long getCholesterol() 
    {
        return cholesterol;
    }
    public void setSystolicAlbumen(Long systolicAlbumen) 
    {
        this.systolicAlbumen = systolicAlbumen;
    }

    public Long getSystolicAlbumen() 
    {
        return systolicAlbumen;
    }
    public void setDiastolicBlood(Long diastolicBlood) 
    {
        this.diastolicBlood = diastolicBlood;
    }

    public Long getDiastolicBlood() 
    {
        return diastolicBlood;
    }
    public void setSystolicBlood(Long systolicBlood) 
    {
        this.systolicBlood = systolicBlood;
    }

    public Long getSystolicBlood() 
    {
        return systolicBlood;
    }
    public void setHypotensiveDrugs(Integer hypotensiveDrugs) 
    {
        this.hypotensiveDrugs = hypotensiveDrugs;
    }

    public Integer getHypotensiveDrugs() 
    {
        return hypotensiveDrugs;
    }
    public void setDiabetes(Integer diabetes) 
    {
        this.diabetes = diabetes;
    }

    public Integer getDiabetes() 
    {
        return diabetes;
    }
    public void setSmoking(Long smoking) 
    {
        this.smoking = smoking;
    }

    public Long getSmoking() 
    {
        return smoking;
    }
    public void setFammilyCd(Long fammilyCd) 
    {
        this.fammilyCd = fammilyCd;
    }

    public Long getFammilyCd() 
    {
        return fammilyCd;
    }
    public void setFammilyCa(Long fammilyCa) 
    {
        this.fammilyCa = fammilyCa;
    }

    public Long getFammilyCa() 
    {
        return fammilyCa;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("patientPhone", getPatientPhone())
            .append("gender", getGender())
            .append("age", getAge())
            .append("waistline", getWaistline())
            .append("cholesterol", getCholesterol())
            .append("systolicAlbumen", getSystolicAlbumen())
            .append("diastolicBlood", getDiastolicBlood())
            .append("systolicBlood", getSystolicBlood())
            .append("hypotensiveDrugs", getHypotensiveDrugs())
            .append("diabetes", getDiabetes())
            .append("smoking", getSmoking())
            .append("fammilyCd", getFammilyCd())
            .append("fammilyCa", getFammilyCa())
            .toString();
    }
}
