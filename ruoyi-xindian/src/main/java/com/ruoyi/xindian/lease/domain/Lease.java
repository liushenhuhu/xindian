package com.ruoyi.xindian.lease.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName lease
 */
@TableName(value ="lease")
@Data
public class Lease implements Serializable {
    /**
     * 租赁编号
     */
    @TableId(type = IdType.AUTO)
    private Long leaseId;

    /**
     * 租赁用户名称
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String identityCard;

    /**
     * 单位
     */
    private String unitLeader;

    /**
     * 位置
     */
    private String place;

    /**
     * 是否归还（0=未归还，1=已归还）
     */
    private String giveBack;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
    @TableField(exist = false)
    private List<LeaseDetails> details = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}