package com.ruoyi.xindian.callLog.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【通话记录】对象 call_log
 * 
 * @author ruoyi
 * @date 2023-09-05
 */
public class CallLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 患者id */
    @Excel(name = "患者id")
    private String pId;

    /** 打给谁（患者、家属、医生） */
    @Excel(name = "打给谁", readConverterExp = "患=者、家属、医生")
    private String role;
    /** 谁打的（登录的用户） */
    @Excel(name = "谁打的")
    private String userName;
    /** 打电话时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss")
    @Excel(name = "打电话时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 通话时长 */
    @Excel(name = "通话时长")
    private String duration;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setpId(String pId) 
    {
        this.pId = pId;
    }

    public String getpId() 
    {
        return pId;
    }
    public void setRole(String role) 
    {
        this.role = role;
    }

    public String getRole() 
    {
        return role;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setDuration(String duration) 
    {
        this.duration = duration;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDuration()
    {
        return duration;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pId", getpId())
            .append("role", getRole())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("duration", getDuration())
            .toString();
    }
}
