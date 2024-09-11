package com.ruoyi.xindian.report.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName hosp_rela_doctor
 */
@TableName(value ="hosp_rela_doctor")
@Data
public class HospRelaDoctor implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 医院
     */
    private String hospCode;

    /**
     * 医生电话
     */
    private String dPhoneAes;

    /**
     * 医生姓名
     */
    private String dNameAes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}