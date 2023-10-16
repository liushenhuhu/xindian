package com.ruoyi.xindian.alert_log.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 静态12导心电数据
 * @TableName jecg_12
 */
@TableName(value ="jecg_12")
public class Jecg12 implements Serializable {
    /**
     * p_id
     */
    private String pId;

    /**
     * 标签
     */
    private String waveLabel;

    /**
     * 
     */
    private String beatLabel;


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
}