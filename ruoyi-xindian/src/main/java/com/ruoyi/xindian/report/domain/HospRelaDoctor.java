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
public class HospRelaDoctor implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHospCode() {
        return hospCode;
    }

    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
    }

    public String getdPhoneAes() {
        return dPhoneAes;
    }

    public void setdPhoneAes(String dPhoneAes) {
        this.dPhoneAes = dPhoneAes;
    }

    public String getdNameAes() {
        return dNameAes;
    }

    public void setdNameAes(String dNameAes) {
        this.dNameAes = dNameAes;
    }
}