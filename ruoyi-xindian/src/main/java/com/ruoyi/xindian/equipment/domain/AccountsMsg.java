package com.ruoyi.xindian.equipment.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName accounts_msg
 */
@TableName(value ="accounts_msg")
@Data
public class AccountsMsg implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 公众号消息推送管理员openID
     */
    private String openId;

    /**
     * 管理员名称
     */
    private String adminName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}