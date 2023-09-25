package com.ruoyi.xindian.statistics.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName diagnose_doc
 */
@TableName(value ="diagnose_doc")
@Data
public class DiagnoseDoc implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer diagnoseId;

    /**
     * 报告表id
     */
    private Long reportId;

    /**
     * 医生手机号
     */
    private String doctorPhone;

    /**
     * 诊断类型（1：主动接诊，2：被动分配）
     */
    private String diagnoseType;

    /**
     * 诊断状态（1：完成诊断，2：未完成诊断）
     */
    private String diagnoseStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}