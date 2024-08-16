package com.ruoyi.xindian.set_app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 小程序设置
 * @TableName set_ecg_app
 */
@TableName(value ="set_ecg_app")
@Data
public class SetEcgApp implements Serializable {
    /**
     * 设置id
     */
    @TableId(type = IdType.AUTO)
    private Integer setId;

    /**
     * 用户电话
     */
    private String patientPhone;

    /**
     * 类型（根据不同的页面走）
     */
    private String type;

    /**
     * 状态（1=正常 0=禁用）
     */
    private String status;

    /**
     * 患者管理id
     */
    private String pId;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}