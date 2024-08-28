package com.ruoyi.xindian.slideshow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName app_slideshow
 */
@TableName(value ="app_slideshow")
@Data
public class AppSlideshow implements Serializable {
    /**
     * 轮播图id
     */
    @TableId(type = IdType.AUTO)
    private Integer slideshowId;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 是否跳转（0=跳转，1=跳转）
     */
    private String isSkip;

    /**
     * 跳转路径
     */
    private String path;

    /**
     * 轮播图状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}