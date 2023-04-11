package com.ruoyi.xindian.message.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息存储对象 message
 * 
 * @author chenpeng
 * @date 2023-04-11
 */
public class Message extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date putTime;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String msgData;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setPutTime(Date putTime) 
    {
        this.putTime = putTime;
    }

    public Date getPutTime() 
    {
        return putTime;
    }
    public void setMsgData(String msgData) 
    {
        this.msgData = msgData;
    }

    public String getMsgData() 
    {
        return msgData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("phone", getPhone())
            .append("putTime", getPutTime())
            .append("msgData", getMsgData())
            .toString();
    }
}
