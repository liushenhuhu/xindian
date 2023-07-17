package com.ruoyi.xindian.statistics.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lixinlong
 * 2023/7/11 14:58
 */
public class Statistics extends BaseEntity {

    private String doctorName;
    private String doctorPhone;

    private String month;

    private String count;

    private String doctorMonth;

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
