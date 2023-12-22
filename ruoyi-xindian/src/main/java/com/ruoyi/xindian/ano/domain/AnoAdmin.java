package com.ruoyi.xindian.ano.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName ano_admin
 */
@TableName(value ="ano_admin")
public class AnoAdmin implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer anoAdminId;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private String pId;


    private String ecgType;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public Integer getAnoAdminId() {
        return anoAdminId;
    }

    public void setAnoAdminId(Integer anoAdminId) {
        this.anoAdminId = anoAdminId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getEcgType() {
        return ecgType;
    }

    public void setEcgType(String ecgType) {
        this.ecgType = ecgType;
    }
}