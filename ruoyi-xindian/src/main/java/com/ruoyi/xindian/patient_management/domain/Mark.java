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
    @Column(name="data")
    private String data;

    @Column(name="time")
    private String time;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pId", pId)
                .append("date", data)
                .append("time", time)
                .toString();
    }
}
