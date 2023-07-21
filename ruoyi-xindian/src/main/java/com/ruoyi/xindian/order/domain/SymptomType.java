package com.ruoyi.xindian.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName symptom_type
 */
@TableName(value ="symptom_type")
@Data
public class SymptomType implements Serializable {
    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 心电症状类型
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}