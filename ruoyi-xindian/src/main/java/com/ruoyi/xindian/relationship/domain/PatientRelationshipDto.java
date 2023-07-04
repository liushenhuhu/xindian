package com.ruoyi.xindian.relationship.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.xindian.appData.domain.AppData;
import lombok.Data;

import java.util.Date;

@Data
public class PatientRelationshipDto extends PatientRelationship {
    /** 患者性别 */
    @Excel(name = "患者性别")
    private String patientSex;

    /** 患者出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "患者出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDay;

    /** 身高 */
    @Excel(name = "身高")
    private Double height;

    /** 体重 */
    @Excel(name = "体重")
    private Double weight;

    /** 过往病史 */
    @Excel(name = "过往病史")
    private String pastMedicalHistory;

    /** 生活习惯 */
    @Excel(name = "生活习惯")
    private String livingHabit;


}
