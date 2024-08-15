package com.ruoyi.xindian.wSuryvey.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName w_survey_history
 */
@TableName(value ="w_survey_history")
public class WSurveyHistory implements Serializable {
    /**
     * 问卷历史信息
     */
    @TableId(type = IdType.AUTO)
    private Integer wSurveyHistoryId;

    /**
     * 问卷id
     */
    private Long wSurveyId;

    /**
     * 年份
     */
    private String particularYear;

    /**
     * 低密度脂蛋白胆固醇
     */
    private Double hLowAlbumen;

    /**
     * 甘油三酯
     */
    private Double hTriglyceride;

    /**
     * 高密度脂蛋白胆固醇
     */
    private Double hSystolicAlbumen;

    /**
     * 胆固醇
     */
    private Double hCholesterol;


    public Integer getwSurveyHistoryId() {
        return wSurveyHistoryId;
    }

    public void setwSurveyHistoryId(Integer wSurveyHistoryId) {
        this.wSurveyHistoryId = wSurveyHistoryId;
    }

    public Long getwSurveyId() {
        return wSurveyId;
    }

    public void setwSurveyId(Long wSurveyId) {
        this.wSurveyId = wSurveyId;
    }

    public String getParticularYear() {
        return particularYear;
    }

    public void setParticularYear(String particularYear) {
        this.particularYear = particularYear;
    }

    public Double gethLowAlbumen() {
        return hLowAlbumen;
    }

    public void sethLowAlbumen(Double hLowAlbumen) {
        this.hLowAlbumen = hLowAlbumen;
    }

    public Double gethTriglyceride() {
        return hTriglyceride;
    }

    public void sethTriglyceride(Double hTriglyceride) {
        this.hTriglyceride = hTriglyceride;
    }

    public Double gethSystolicAlbumen() {
        return hSystolicAlbumen;
    }

    public void sethSystolicAlbumen(Double hSystolicAlbumen) {
        this.hSystolicAlbumen = hSystolicAlbumen;
    }

    public Double gethCholesterol() {
        return hCholesterol;
    }

    public void sethCholesterol(Double hCholesterol) {
        this.hCholesterol = hCholesterol;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}