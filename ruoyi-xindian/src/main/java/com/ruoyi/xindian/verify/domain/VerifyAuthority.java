package com.ruoyi.xindian.verify.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName verify_authority
 */
@TableName(value ="verify_authority")
@Data
public class VerifyAuthority implements Serializable {
    /**
     * 验证编号
     */
    @TableId
    private Integer verifyId;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 验证密码
     */
    private String accountPwd;

    /**
     * 状态（0：正常，1：禁用）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}