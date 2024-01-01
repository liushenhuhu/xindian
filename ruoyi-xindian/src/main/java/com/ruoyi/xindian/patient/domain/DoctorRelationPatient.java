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
 * @TableName doctor_relation_patient
 */
@TableName(value ="doctor_relation_patient")
@Data
public class DoctorRelationPatient implements Serializable {
    /**
     * 
     */
    @TableId(value = "relation_id", type = IdType.AUTO)
    private Integer relationId;

    /**
     * 医师电话
     */
    private String doctorPhone;

    /**
     * 患者电话
     */
    private String patientPhone;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}