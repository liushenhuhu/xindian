package com.ruoyi.xindian.verify.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sx_dd_report
 */
@TableName(value ="sx_dd_report")
@Data
public class SxDdReportVO implements Serializable {

    /**
     * 报告编号
     */
//    @NotNull(message = "报告编号不能为空")
//    private String orderId;

    /**
     * 设备号
     */
    @NotNull(message = "设备号不能为空")
    private String sn;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private String userId;

    /**
     * 上传开始时间
     */
    @NotNull(message = "上传开始时间不能为空")
    private String uploadStart;

    /**
     * 开始采集时间
     */
//    @NotNull(message = "上传结束时间不能为空")
    private String gatherStartTime;

    /**
     * 采集时长
     */
    private String gatherDuration;


    /**
     * 患者手机号
     */
    @NotNull(message = "用户手机号不能为空")
    private String phone;


    /**
     * 数据文件名
     */
    @NotNull(message = "数据文件名不能为空")
    private String fileName;

    /**
     * 用户名称
     */
    @NotNull(message = "用户名称不能为空")
    private String name;

    /**
     * 用户性别
     */
    @NotNull(message = "用户性别不能为空")
    private String sex;

    /**
     * 用户生日
     */
    @NotNull(message = "用户生日不能为空")
    private String birthday;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}