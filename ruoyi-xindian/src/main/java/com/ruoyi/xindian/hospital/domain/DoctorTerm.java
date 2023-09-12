package com.ruoyi.xindian.hospital.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName doctor_term
 */
@TableName(value ="doctor_term")
@Data
public class DoctorTerm implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long termId;

    /**
     * 医生电话
     */
    private String doctorPhone;

    /**
     * 术语标签
     */
    private String termText;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}