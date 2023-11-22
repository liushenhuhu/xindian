package com.ruoyi.xindian.patient.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.xindian.hospital.domain.Doctor;
import lombok.Data;

/**
 * 
 * @TableName patient_relation_doc
 */
@TableName(value ="patient_relation_doc")
@Data
public class PatientRelationDoc implements Serializable {
    /**
     * 患者关联医生id
     */
    private Integer relationId;

    /**
     * 患者手机号
     */
    private String patientPhone;

    /**
     * 医师电话
     */
    private String doctorPhone;

    /**
     * 创建时间
     */
    private Date createTime;

    private String boundStatus;

    @TableField(exist = false)
    private List<Patient> patients = new ArrayList<>();
    @TableField(exist = false)
    private List<Doctor> doctors = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}