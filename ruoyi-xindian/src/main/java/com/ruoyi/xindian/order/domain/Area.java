package com.ruoyi.xindian.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 地址区域表
 * @TableName area
 */
@TableName(value ="area")
@Data
public class Area implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 父级
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 级别 1 省 2 市 3 区 4 乡镇/街道
     */
    private Integer level;

    /**
     * 排序
     */
    private Object sort;

    /**
     * 状态 0 无效 1 有效
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}