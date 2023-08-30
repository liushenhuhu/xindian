package com.ruoyi.xindian.patient_management.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author lixinlong
 * 2023/8/29 10:39
 */
@Data
@Measurement(name = "ecg_mark")
public class Mark {

    @Column(name="pId",tag=true)
    private String pId;
    @Column(name="level")
    private String level;
    @Column(name="xArr")
    private String xArr;
    @Column(name="yArr")
    private String yArr;
    @Column(name="types")
    private String types;
    @Column(name="date")
    private String date;

    @Column(name="time")
    private String time;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getxArr() {
        return xArr;
    }

    public void setxArr(String xArr) {
        this.xArr = xArr;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getyArr() {
        return yArr;
    }

    public void setyArr(String yArr) {
        this.yArr = yArr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pId", pId)
                .append("level", level)
                .append("xArr", xArr)
                .append("yArr", yArr)
                .append("types", types)
                .append("date", date)
                .append("time", time)
                .toString();
    }
}
