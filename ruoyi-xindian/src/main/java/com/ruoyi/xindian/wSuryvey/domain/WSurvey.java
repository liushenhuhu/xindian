package com.ruoyi.xindian.wSuryvey.domain;

import lombok.Data;
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
@Data
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
    private String waistline;

    /** 胆固醇 */
    @Excel(name = "胆固醇")
    private String cholesterol;

    /** 高密度脂蛋白胆固醇 */
    @Excel(name = "高密度脂蛋白胆固醇")
    private String systolicAlbumen;

    /** 舒张压 */
    @Excel(name = "舒张压")
    private String diastolicBlood;

    /** 收缩压 */
    @Excel(name = "收缩压")
    private String systolicBlood;

    /** 复用降压药 */
    @Excel(name = "服用降压药")
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
    private String lowAlbumen;

    /** 甘油三酯 */
    @Excel(name = "甘油三酯")
    private String triglyceride;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String patientCode;


    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    private List<WSurveyHistory> wSurveyHistories = new ArrayList<>();

    public List<WSurveyHistory> getwSurveyHistories() {
        return wSurveyHistories;
    }
    public void setwSurveyHistories(List<WSurveyHistory> wSurveyHistories) {
        this.wSurveyHistories = wSurveyHistories;
    }


}
