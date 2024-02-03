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
    /**
     * 平均心率
     */
    private String hrMean;

    /**
     * P波振幅
     */
    private String pAmplitude;

    /**
     * R波振幅
     */
    private String rAmplitude;

    /**
     * T波振幅
     */
    private String tAmplitude;

    /**
     * P波时限
     */
    private String pTime;

    /**
     * QRS波时限
     */
    private String qrsInterval;

    /**
     * T波时限
     */
    private String tTime;

    /**
     * PR间期
     */
    private String prInterval;

    /**
     * QT间期
     */
    private String qtInterval;

    /**
     * QTc
     */
    private String qtc;

    /**
     * SDNN
     */
    private String sdnn;

    /**
     * RMSSD
     */
    private String rmssd;

    public String getHrMean() {
        return hrMean;
    }

    public void setHrMean(String hrMean) {
        this.hrMean = hrMean;
    }

    public String getpAmplitude() {
        return pAmplitude;
    }

    public void setpAmplitude(String pAmplitude) {
        this.pAmplitude = pAmplitude;
    }

    public String getrAmplitude() {
        return rAmplitude;
    }

    public void setrAmplitude(String rAmplitude) {
        this.rAmplitude = rAmplitude;
    }

    public String gettAmplitude() {
        return tAmplitude;
    }

    public void settAmplitude(String tAmplitude) {
        this.tAmplitude = tAmplitude;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getQrsInterval() {
        return qrsInterval;
    }

    public void setQrsInterval(String qrsInterval) {
        this.qrsInterval = qrsInterval;
    }

    public String gettTime() {
        return tTime;
    }

    public void settTime(String tTime) {
        this.tTime = tTime;
    }

    public String getPrInterval() {
        return prInterval;
    }

    public void setPrInterval(String prInterval) {
        this.prInterval = prInterval;
    }

    public String getQtInterval() {
        return qtInterval;
    }

    public void setQtInterval(String qtInterval) {
        this.qtInterval = qtInterval;
    }

    public String getQtc() {
        return qtc;
    }

    public void setQtc(String qtc) {
        this.qtc = qtc;
    }

    public String getSdnn() {
        return sdnn;
    }

    public void setSdnn(String sdnn) {
        this.sdnn = sdnn;
    }

    public String getRmssd() {
        return rmssd;
    }

    public void setRmssd(String rmssd) {
        this.rmssd = rmssd;
    }

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
