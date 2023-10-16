package com.ruoyi.xindian.alert_log.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 静态心电数据
 * @TableName jecg_single
 */
@TableName(value ="jecg_single")
public class JecgSingle implements Serializable {
    /**
     * p_id
     */
    private String pId;

    /**
     * 标签
     */
    private String waveLabel;

    /**
     * 关键点标注
     */
    private String beatLabel;

    @TableField(exist = false)
    private String field;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getWaveLabel() {
        return waveLabel;
    }

    public void setWaveLabel(String waveLabel) {
        this.waveLabel = waveLabel;
    }

    public String getBeatLabel() {
        return beatLabel;
    }

    public void setBeatLabel(String beatLabel) {
        this.beatLabel = beatLabel;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}