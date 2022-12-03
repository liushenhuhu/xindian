package com.ruoyi.xindian.patient_management.domain;

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
 * 患者管理对象 patient_management
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientManagement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 患者管理id
     */
    @Excel(name = "患者管理id")
    private String pId;

    /**
     * 患者身份证号
     */
    @Excel(name = "患者身份证号")
    private String patientCode;

    /**
     * 医院代号
     */
    @Excel(name = "医院代号")
    private String hospitalCode;

    /**
     * 设备号
     */
    @Excel(name = "设备号")
    private String equipmentCode;

    /**
     * 连接时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "连接时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date connectionTime;

    /**
     * 床位号
     */
    @Excel(name = "床位号")
    private String bedNumber;

    /**
     * 病历号
     */
    @Excel(name = "病历号")
    private String caseHistoryNumber;

    /**
     * 患者姓名
     */
    @Excel(name = "患者姓名")
    private String patientName;

    /**
     * 患者年龄
     */
    @Excel(name = "患者年龄 ")
    private String patientAge;

    /**
     * 患者性别
     */
    @Excel(name = "患者性别")
    private String patientSex;

    /**
     * 患者来源
     */
    @Excel(name = "患者来源")
    private String patientSource;

    /**
     * 患者电话
     */
    @Excel(name = "患者电话")
    private String patientPhone;

    /**
     * 家属电话
     */
    @Excel(name = "家属电话")
    private String familyPhone;

    /**
     * 监测状态
     */
    @Excel(name = "监测状态")
    private String monitoringStatus;

    /**
     * 医院名称
     */
    @Excel(name = "医院名称")
    private String hospitalName;

    /**
     * 心电种类（静态动态）
     */
    @Excel(name = "心电种类", readConverterExp = "静=态动态")
    private String ecgType;

    /**
     * 在线状态
     */
    @Excel(name = "在线状态")
    private String onlineStatus;

    /**
     * 智能诊断
     */
    @Excel(name = "智能诊断")
    private String intelligentDiagnosis;

    /**
     * 诊断状态
     */
    @Excel(name = "诊断状态")
    private Long diagnosisStatus;

    /**
     * 诊断结论
     */
    @Excel(name = "诊断结论")
    private String diagnosisConclusion;

    /**
     * 诊断医生
     */
    @Excel(name = "诊断医生")
    private String diagnosisDoctor;

    /**
     * 报告时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报告时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reportTime;

    /** 风险等级*/
    @Excel(name = "风险等级")
    private Long ecgLevel;

    /** 医生电话 */
    @Excel(name = "医生电话")
    private String doctorPhone;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
