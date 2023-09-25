package com.ruoyi.xindian.statistics.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lixinlong
 * 2023/7/11 14:58
 */
public class Statistics extends BaseEntity {

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 医生电话
     */
    private String doctorPhone;

    /**
     * 月份
     */
    private String month;

    /**
     * 总诊断次数
     */
    private String count;


    /**
     * 总诊断时间
     */
    private String countTime;

    private String doctorMonth;

    private String reportType;

    /**
     * 年份
     */
    private String year;

    /**
     * 平均诊断时间
     */
    private String averageTime;


    private String startTime;

    private String endTime;


    /**
     * 主动接诊次数
     */
    private int diagnoseTypeZhuSUM;


    /**
     * 被动分配次数
     */
    private int diagnoseTypeBIESUM;


    /**
     * 分配但未诊断次数
     */
    private int diagnoseStatusWEISUM;


    public int getDiagnoseTypeZhuSUM() {
        return diagnoseTypeZhuSUM;
    }

    public void setDiagnoseTypeZhuSUM(int diagnoseTypeZhuSUM) {
        this.diagnoseTypeZhuSUM = diagnoseTypeZhuSUM;
    }

    public int getDiagnoseTypeBIESUM() {
        return diagnoseTypeBIESUM;
    }

    public void setDiagnoseTypeBIESUM(int diagnoseTypeBIESUM) {
        this.diagnoseTypeBIESUM = diagnoseTypeBIESUM;
    }

    public int getDiagnoseStatusWEISUM() {
        return diagnoseStatusWEISUM;
    }

    public void setDiagnoseStatusWEISUM(int diagnoseStatusWEISUM) {
        this.diagnoseStatusWEISUM = diagnoseStatusWEISUM;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    private String timeCount;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDoctorMonth() {
        return doctorMonth;
    }

    public void setDoctorMonth(String doctorMonth) {
        this.doctorMonth = doctorMonth;
    }

    public String getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(String timeCount) {
        this.timeCount = timeCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("doctorName", doctorName)
                .append("doctorPhone", doctorPhone)
                .append("month", month)
                .append("count", count)
                .append("doctorMonth", doctorMonth)
                .append("timeCount", timeCount)
                .toString();
    }
}
