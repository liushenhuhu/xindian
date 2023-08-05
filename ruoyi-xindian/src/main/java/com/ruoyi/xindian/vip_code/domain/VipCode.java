package com.ruoyi.xindian.vip_code.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName vip_code
 */
@TableName(value ="vip_code")
@Data
public class VipCode implements Serializable {
    /**
     * 激活码id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 激活码
     */
    private String code;

    /**
     * 
     */
    private Integer num;

    /**
     * 是否为vip（)
     */
    private Integer isVip;

    /**
     * 状态（0未使用，1激活）
     */
    private Integer state;

    /**
     * 激活码生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 激活码修改时间（激活时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date useTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}