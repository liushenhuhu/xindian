package com.ruoyi.xindian.disease_explains.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 心电图报告解析说明
 * @TableName disease_explains
 */
@TableName(value ="disease_explains")
@Data
public class DiseaseExplains implements Serializable {
    /**
     * 疾病id
     */
    @TableId(type = IdType.AUTO)
    private Integer diseaseId;

    /**
     * 疾病名称
     */
    private String diseaseName;

    /**
     * 解释
     */
    private String explains;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}