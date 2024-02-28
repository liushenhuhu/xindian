package com.ruoyi.xindian.patient_management.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName doc_report_msg
 */
@TableName(value ="doc_report_msg")
@Data
public class DocReportMsg implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 公众号消息推送医师openID
     */
    private String openId;

    /**
     * 医师名称
     */
    private String adminName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}