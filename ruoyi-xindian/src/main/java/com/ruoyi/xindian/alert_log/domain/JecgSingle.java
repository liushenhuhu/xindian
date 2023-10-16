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
@Data
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
}