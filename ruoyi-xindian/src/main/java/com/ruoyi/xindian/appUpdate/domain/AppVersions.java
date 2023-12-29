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
 * @TableName app_versions
 */
@TableName(value ="app_versions")
@Data
public class AppVersions implements Serializable {
    /**
     * 版本id
     */
    @TableId
    private Integer versionsId;

    /**
     * 版本号
     */
    private String versionsNumber;

    /**
     * 更新地址
     */
    private String url;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String type;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}