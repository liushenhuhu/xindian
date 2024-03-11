package com.ruoyi.xindian.equipment.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName equipment_heading_code
 */
@TableName(value ="equipment_heading_code")
@Data
public class EquipmentHeadingCode implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer codeId;

    /**
     * 识别码
     */
    private String headingCode;

    /**
     * sn码
     */
    private String equipmentCode;


    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}