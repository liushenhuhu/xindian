package com.ruoyi.xindian.wSuryvey.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class WSurveyDTO implements Serializable {

    /** $column.columnCmment */
    private Long id;

    /** 电话 */
    @Excel(name = "电话")
    private String patientPhone;

    /** 性别 */
    @Excel(name = "性别")
    private Integer gender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 腰围 */
    @Excel(name = "腰围")
    private Long waistline;

    /** 胆固醇 */
    @Excel(name = "胆固醇")
    private Long cholesterol;

    /** 高密度脂蛋白胆固醇 */
    @Excel(name = "高密度脂蛋白胆固醇")
    private Long systolicAlbumen;

    /** 舒张压 */
    @Excel(name = "舒张压")
    private Long diastolicBlood;

    /** 收缩压 */
    @Excel(name = "收缩压")
    private Long systolicBlood;

    /** 复用降压药 */
    @Excel(name = "复用降压药")
    private Integer hypotensiveDrugs;

    /** 是否糖尿病 */
    @Excel(name = "是否糖尿病")
    private Integer diabetes;

    /** 是否吸烟 */
    @Excel(name = "是否吸烟")
    private Long smoking;

    /** 是否心血管病家族史 */
    @Excel(name = "是否心血管病家族史")
    private Long fammilyCd;

    /** 是否脑卒中家族史 */
    @Excel(name = "是否脑卒中家族史")
    private Long fammilyCa;

    private String patientCode;

    private String addr;
}
