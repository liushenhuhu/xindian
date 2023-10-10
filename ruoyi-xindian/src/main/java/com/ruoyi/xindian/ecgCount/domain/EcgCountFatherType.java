package com.ruoyi.xindian.ecgCount.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName ecg_count_father_type
 */
@TableName(value ="ecg_count_father_type")
@Data
public class EcgCountFatherType implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 父级类型
     */
    private String fatherType;

    /**
     * 子集类型
     */
    private String childValue;

    @TableField(exist = false)
    private EcgCountType ecgCountType;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}