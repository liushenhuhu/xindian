package com.ruoyi.xindian.rotograph.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 轮播广告对象 rotograph
 * 
 * @author chenpeng
 * @date 2023-03-14
 */
@Data
public class Rotograph extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识 */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 不同端标识(医生，用户) */
    @Excel(name = "不同端标识(医生，用户)")
    private String position;

    /** 排序 */
    @Excel(name = "排序")
    private String linkId;

    /** 摘要 */
    @Excel(name = "摘要")
    private String abstracted;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 显示位置 */
    @Excel(name = "显示位置")
    private String showPosition;

    /** 图片位置 */
    @Excel(name = "图片位置")
    private String imageUrl;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    /** 删除标志位 */
    @Excel(name = "删除标志位")
    private Integer deleteflag;

    /**上传的图片文件*/
    private MultipartFile file;

}
