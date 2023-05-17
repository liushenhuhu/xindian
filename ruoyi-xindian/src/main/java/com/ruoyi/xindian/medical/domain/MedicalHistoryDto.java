package com.ruoyi.xindian.medical.domain;

public class MedicalHistoryDto extends MedicalHistory{
    private String userName;
    private String gender;

    public MedicalHistoryDto() {
    }

    public MedicalHistoryDto(String userName, String gender) {
        this.userName = userName;
        this.gender = gender;
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
}
