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
 * @TableName single_history_data
 */
@TableName(value ="single_history_data")
@Data
public class SingleHistoryData implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Integer ecgId;

    /**
     * 患者关联id
     */
    private String pId;

    /**
     * 患者手机号
     */
    private String patientPhone;

    /**
     * 发生时间
     */
    private Date createTime;

    /**
     * 正常心电图
     */
    private Integer normalEcg;

    /**
     * 房性早搏
     */
    private Integer apBeat;

    /**
     * 室性早搏
     */
    private Integer pvBeat;

    /**
     * 心房颤动
     */
    private Integer atrialFibrillation;

    /**
     * 心房扑动
     */
    private Integer atrialFlutter;

    /**
     * 完全性左束支传导阻滞
     */
    private Integer leftBlock;

    /**
     * 完全性右束支传导阻滞
     */
    private Integer rightBlock;

    /**
     * 心动过缓
     */
    private Integer bradycardia;

    /**
     * 心动过速
     */
    private Integer tachycardia;


    @TableField(exist = false)
    private String startTime;


    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}