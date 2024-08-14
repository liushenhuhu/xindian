package com.ruoyi.xindian.patient.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName patient_login
 */
@TableName(value ="patient_login")
@Data
public class PatientLogin implements Serializable {
    /**
     * 登录id
     */
    @TableId(type = IdType.AUTO)
    private Integer loginId;

    /**
     * 登录人手机号
     */
    private String patientPhone;

    /**
     * 登录时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}