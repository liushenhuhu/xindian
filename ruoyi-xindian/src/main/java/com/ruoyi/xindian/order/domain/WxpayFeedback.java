package com.ruoyi.xindian.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * 
 * @TableName wxpay_feedback
 */
@TableName(value ="wxpay_feedback")
@Data
public class WxpayFeedback implements Serializable {
    /**
     * 反馈信息id
     */
    @TableId
    @Excel(name = "反馈信息id")
    private Integer feedbackiId;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private String orderId;

    /**
     * 反馈原因
     */
    @Excel(name = "反馈原因")
    private String feedbackCause;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 反馈信息状态
     */
    @Excel(name = "反馈信息状态")
    private String status;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}