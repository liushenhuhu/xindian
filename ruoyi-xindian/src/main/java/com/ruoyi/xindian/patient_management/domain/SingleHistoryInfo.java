package com.ruoyi.xindian.patient_management.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * info对象 single_history_info
 *
 * @author chenpeng
 * @date 2023-02-19
 */
public class SingleHistoryInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 历史统计id */
    private Long ecgId;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String patientPhone;

    /** 正常心电图 */
    @Excel(name = "正常心电图")
    private Long normalEcg;

    /** 房性早搏 */
    @Excel(name = "房性早搏")
    private Long apBeat;

    /** 室性早搏 */
    @Excel(name = "室性早搏")
    private Long pvBeat;

    /** 心房颤动 */
    @Excel(name = "心房颤动")
    private Long atrialFibrillation;

    /** 心房扑动 */
    @Excel(name = "心房扑动")
    private Long atrialFlutter;

    /** 完全性左束支传导阻滞 */
    @Excel(name = "完全性左束支传导阻滞")
    private Long leftBlock;

    /** 完全性右束支传导阻滞 */
    @Excel(name = "完全性右束支传导阻滞")
    private Long rightBlock;

    /** 心动过缓 */
    @Excel(name = "心动过缓")
    private Long bradycardia;

    /** 心动过速 */
    @Excel(name = "心动过速")
    private Long tachycardia;

    public void setEcgId(Long ecgId)
    {
        this.ecgId = ecgId;
    }

    public Long getEcgId()
    {
        return ecgId;
    }
    public void setPatientPhone(String patientPhone)
    {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone()
    {
        return patientPhone;
    }
    public void setNormalEcg(Long normalEcg)
    {
        this.normalEcg = normalEcg;
    }

    public Long getNormalEcg()
    {
        return normalEcg;
    }
    public void setApBeat(Long apBeat)
    {
        this.apBeat = apBeat;
    }

    public Long getApBeat()
    {
        return apBeat;
    }
    public void setPvBeat(Long pvBeat)
    {
        this.pvBeat = pvBeat;
    }

    public Long getPvBeat()
    {
        return pvBeat;
    }
    public void setAtrialFibrillation(Long atrialFibrillation)
    {
        this.atrialFibrillation = atrialFibrillation;
    }

    public Long getAtrialFibrillation()
    {
        return atrialFibrillation;
    }
    public void setAtrialFlutter(Long atrialFlutter)
    {
        this.atrialFlutter = atrialFlutter;
    }

    public Long getAtrialFlutter()
    {
        return atrialFlutter;
    }
    public void setLeftBlock(Long leftBlock)
    {
        this.leftBlock = leftBlock;
    }

    public Long getLeftBlock()
    {
        return leftBlock;
    }
    public void setRightBlock(Long rightBlock)
    {
        this.rightBlock = rightBlock;
    }

    public Long getRightBlock()
    {
        return rightBlock;
    }
    public void setBradycardia(Long bradycardia)
    {
        this.bradycardia = bradycardia;
    }

    public Long getBradycardia()
    {
        return bradycardia;
    }
    public void setTachycardia(Long tachycardia)
    {
        this.tachycardia = tachycardia;
    }

    public Long getTachycardia()
    {
        return tachycardia;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("ecgId", getEcgId())
                .append("patientPhone", getPatientPhone())
                .append("normalEcg", getNormalEcg())
                .append("apBeat", getApBeat())
                .append("pvBeat", getPvBeat())
                .append("atrialFibrillation", getAtrialFibrillation())
                .append("atrialFlutter", getAtrialFlutter())
                .append("leftBlock", getLeftBlock())
                .append("rightBlock", getRightBlock())
                .append("bradycardia", getBradycardia())
                .append("tachycardia", getTachycardia())
                .toString();
    }
}
