package com.ruoyi.xindian.appUpdate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName app_version
 */
@TableName(value ="app_version")
@Data
public class AppVersion implements Serializable {
    /**
     * 版本id
     */
    @TableId
    private Integer versionId;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 版本号
     */
    private String versionCode;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * wgt下载地址
     */
    private String wgtUrl;

    /**
     * 是否强制更新（0：否，1：是）
     */
    private Integer forceUpdate;

    /**
     * 是否静默更新(0：否，1：是)
     */
    private Integer isSilently;

    /**
     * 更新内容
     */
    private String versionDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 类型(1:急诊科，2：迈雅云APP，3：医生端APP)
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}