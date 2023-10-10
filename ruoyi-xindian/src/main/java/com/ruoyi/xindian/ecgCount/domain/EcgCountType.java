package com.ruoyi.xindian.ecgCount.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName ecg_count_type
 */
@TableName(value ="ecg_count_type")
@Data
public class EcgCountType implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private String label;

    /**
     * 
     */
    private String value;

    /**
     * 
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}