package com.ruoyi.xindian.verify.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sx_dd_report
 */
@TableName(value ="sx_dd_report")
@Data
public class SxDdReport implements Serializable {
    /**
     * 善行生成报告id
     */
    @TableId(type = IdType.AUTO)
    private Long sxReportId;

    /**
     * 报告编号
     */
    private String orderId;

    /**
     * 设备号
     */
    private String sn;

    /**
     * 患者的id,唯一标识
     */
    private String userId;

    /**
     * 上传开始时间
     */
    private String uploadStart;

    /**
     * 开始采集时间
     */
    private String gatherStartTime;

    /**
     * 患者手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 下载url文件地址
     */
    private String pdfUrl;

    /**
     * 数据文件名
     */
    private String fileName;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户生日
     */
    private String birthday;

    /**
     * 采集时长
     */
    private String gatherDuration;

    private String pId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}