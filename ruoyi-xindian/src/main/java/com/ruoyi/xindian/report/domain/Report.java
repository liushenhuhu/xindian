package com.ruoyi.xindian.report.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报告对象 report
 *
 * @author hanhan
 * @date 2022-10-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报告id */
    private Long reportId;

    /** 动态/静态心电id */
    @Excel(name = "动态/静态心电id")
    private String pId;

    /** 诊断状态 */
    @Excel(name = "诊断状态")
    private Long diagnosisStatus;

    /** 诊断结论 */
    @Excel(name = "诊断结论")
    private String diagnosisConclusion;

    /** 诊断医生 */
    @Excel(name = "诊断医生")
    private String diagnosisDoctor;
    private String diagnosisDoctorAes;

    /** 报告时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报告时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportTime;

    /** 报告种类 */
    @Excel(name = "报告种类")
    private String reportType;


    /** 智能诊断 */
    @Excel(name = "智能诊断")
    private String intelligentDiagnosis;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String dPhone;
    private String dPhoneAes;

    /** 处置建议 */
    @Excel(name = "处置建议")
    private String handlingSuggestion;

    /** 保健建议 */
    @Excel(name = "保健建议")
    private String healthAdvice;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String pPhone;

    private String hospital;

    private String patientSymptom;

    /** 报告时间 */
    private String endReportTime;
    private String startReportTime;

    private Date startTime;

    private String startDateTime;

    private String ecgType;


    private String loginUserPhone;

    /**
     * 报告状态
     */
    private String reportNormal;


    /**
     * 拒绝理由
     */
    private String refuseText;

    private String logType;


    /**
     * 预警消息
     */
    private String warningText;
    public String getRefuseText() {
        return refuseText;
    }

    public void setRefuseText(String refuseText) {
        this.refuseText = refuseText;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public void setdPhone(String dPhone) { this.dPhone = dPhone;}

    public String getdPhone() {return dPhone;}


}
