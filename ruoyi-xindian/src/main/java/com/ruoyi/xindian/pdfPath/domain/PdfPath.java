package com.ruoyi.xindian.pdfPath.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * pdf保存路径对象 pdf_path
 * 
 * @author hanhan
 * @date 2022-10-29
 */
public class PdfPath extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long pathId;

    /** pId */
    @Excel(name = "pId")
    private String pId;

    /** 保存路径 */
    @Excel(name = "保存路径")
    private String pdfPath;

    /** 保存时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "保存时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date pathTime;

    public void setPathId(Long pathId) 
    {
        this.pathId = pathId;
    }

    public Long getPathId() 
    {
        return pathId;
    }
    public void setpId(String pId) 
    {
        this.pId = pId;
    }

    public String getpId() 
    {
        return pId;
    }
    public void setPdfPath(String pdfPath) 
    {
        this.pdfPath = pdfPath;
    }

    public String getPdfPath() 
    {
        return pdfPath;
    }
    public void setPathTime(Date pathTime) 
    {
        this.pathTime = pathTime;
    }

    public Date getPathTime() 
    {
        return pathTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pathId", getPathId())
            .append("pId", getpId())
            .append("pdfPath", getPdfPath())
            .append("pathTime", getPathTime())
            .toString();
    }
}
