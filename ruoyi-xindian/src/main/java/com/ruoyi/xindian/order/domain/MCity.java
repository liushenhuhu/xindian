package com.ruoyi.xindian.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区表
 * @TableName m_city
 */
@TableName(value ="m_city")
@Data
public class MCity implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 城市id
     */
    private String regionId;

    /**
     * 城市名称
     */
    private String regionName;

    /**
     * 城市父类id
     */
    private String regionParentId;

    /**
     * 级别
     */
    private Integer regionLevel;


    @TableField(exist = false)
    private List<MCity> children = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}