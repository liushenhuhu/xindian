package com.ruoyi.xindian.patient_management.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private List<String> equipmentCodeList = new ArrayList<>();

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
    @Excel(name = "心电种类")
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
     * 诊断医生电话
     */
    @Excel(name = "诊断医生电话")
    private String dPhone;


    private String pastMedicalHistory;

    private String livingHabit;

    private Date generationTime;

    private String ecgAnalysisData;

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

    /** 医生电话 */
//    @Excel(name = "医生电话")
    private String birthDay;

    /** 起搏器 */
    @Excel(name = "起搏器")
    private String pacemaker;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** 监护时长 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "监护时长", width = 30, dateFormat = "yyyy-MM-dd")
    private Date guardianshipDuration;

    private Long samplingRate;
    /**检测状态*/
    private String detectionState;

    /**结束采集时间*/
    private Date endTime;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 维度 */
    @Excel(name = "维度")
    private String latitude;

    private List<String> bindingDoctors = new ArrayList<>();

    /**报告时长*/
    private String timeDuration;

    private String reportState;

    private String endReportTime;
    private String patientSymptom;

    private String startTime;

    private Integer ecgIsLabel;


    private Integer rushState = 1;


    private String PatPhone;


    private String countTime;


    private String avgTime;

    /**
     * 报告状态
     */
    private String reportNormal;


    private String isSelect;



    private String logDataType;

    private Integer sxStatus;


    private Integer sxReportStatus;

    private Integer sxPayStatus;


    private PmEcgData pmEcgData;
    /**
     * 拒绝理由
     */
    private String refuseText;

    private Integer endValue;

    /** 身高 */
    @Excel(name = "身高")
    private Double height;

    /** 体重 */
    @Excel(name = "体重")
    private Double weight;


    private List<String> hospitalCodeList = new ArrayList<>();

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
