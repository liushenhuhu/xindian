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
@Data
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
    private String beatLabe;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}