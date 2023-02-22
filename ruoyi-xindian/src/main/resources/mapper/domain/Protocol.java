package com.ruoyi.xindian.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 协议对象 protocol
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
public class Protocol extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 协议id */
    private Long protocolId;

    /** 协议名字 */
    @Excel(name = "协议名字")
    private String protocolName;

    /** 协议版本 */
    @Excel(name = "协议版本")
    private Long protocolVersion;

    /** 协议内容 */
    @Excel(name = "协议内容")
    private String protocolContent;

    public void setProtocolId(Long protocolId) 
    {
        this.protocolId = protocolId;
    }

    public Long getProtocolId() 
    {
        return protocolId;
    }
    public void setProtocolName(String protocolName) 
    {
        this.protocolName = protocolName;
    }

    public String getProtocolName() 
    {
        return protocolName;
    }
    public void setProtocolVersion(Long protocolVersion) 
    {
        this.protocolVersion = protocolVersion;
    }

    public Long getProtocolVersion() 
    {
        return protocolVersion;
    }
    public void setProtocolContent(String protocolContent) 
    {
        this.protocolContent = protocolContent;
    }

    public String getProtocolContent() 
    {
        return protocolContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("protocolId", getProtocolId())
            .append("protocolName", getProtocolName())
            .append("protocolVersion", getProtocolVersion())
            .append("protocolContent", getProtocolContent())
            .toString();
    }
}
