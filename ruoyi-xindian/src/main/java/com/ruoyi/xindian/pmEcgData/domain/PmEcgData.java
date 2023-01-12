package com.ruoyi.xindian.pmEcgData.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心电数据对象 pm_ecg_data
 * 
 * @author ruoyi
 * @date 2023-01-12
 */
public class PmEcgData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String pId;

    private String ecgAnalysisData;

    public void setpId(String pId) 
    {
        this.pId = pId;
    }

    public String getpId() 
    {
        return pId;
    }
    public void setEcgAnalysisData(String ecgAnalysisData) 
    {
        this.ecgAnalysisData = ecgAnalysisData;
    }

    public String getEcgAnalysisData() 
    {
        return ecgAnalysisData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pId", getpId())
            .append("ecgAnalysisData", getEcgAnalysisData())
            .toString();
    }
}
