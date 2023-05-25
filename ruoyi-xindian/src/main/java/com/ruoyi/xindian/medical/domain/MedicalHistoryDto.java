package com.ruoyi.xindian.medical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MedicalHistoryDto extends MedicalHistory{
    private String userName;
    private String gender;

    private String m_data;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    public MedicalHistoryDto() {
    }

    public MedicalHistoryDto(String userName, String gender) {
        this.userName = userName;
        this.gender = gender;
    }

    public MedicalHistoryDto(String userName, String gender, String m_data) {
        this.userName = userName;
        this.gender = gender;
        this.m_data = m_data;
    }

    public MedicalHistoryDto(String userName, String gender, String m_data, Date birthDay) {
        this.userName = userName;
        this.gender = gender;
        this.m_data = m_data;
        this.birthDay = birthDay;
    }


    /**
     * 获取
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return "MedicalHistoryDto{userName = " + userName + ", gender = " + gender + "}";
    }

    /**
     * 获取
     * @return m_data
     */
    public String getM_data() {
        return m_data;
    }

    /**
     * 设置
     * @param m_data
     */
    public void setM_data(String m_data) {
        this.m_data = m_data;
    }

    /**
     * 获取
     * @return birthDay
     */
    public Date getBirthDay() {
        return birthDay;
    }

    /**
     * 设置
     * @param birthDay
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}
