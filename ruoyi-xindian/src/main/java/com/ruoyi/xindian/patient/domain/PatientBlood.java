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
 * @TableName patient_blood
 */
@TableName(value ="patient_blood")
@Data
public class PatientBlood implements Serializable {
    /**
     * 血压id
     */
    private String bloodId;

    /**
     * 患者手机号
     */
    private String patientPhone;

    /**
     * 高压
     */
    private String hp;

    /**
     * 低压
     */
    private String lp;

    /**
     * 脉搏
     */
    private String bp;

    /**
     * 症状
     */
    private String otherAymptom;

    /**
     * 行为
     */
    private String otherDo;

    /**
     * 上传时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * app根据日期查询开始时间
     */
    @TableField(exist = false)
    private String startTime;

    /**
     * app根据日期查询结束时间
     */
    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}