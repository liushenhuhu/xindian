package com.ruoyi.common.core.domain.model;

public class WxOpenId {

    /**
     * 医生电话
     */
    private String doctorPhone;
    /**
     * openid
     */
    private String openId;

    public WxOpenId() {
    }

    public WxOpenId(String doctorPhone, String openId) {
        this.doctorPhone = doctorPhone;
        this.openId = openId;
    }

    /**
     * 获取
     * @return doctorPhone
     */
    public String getDoctorPhone() {
        return doctorPhone;
    }

    /**
     * 设置
     * @param doctorPhone
     */
    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    /**
     * 获取
     * @return openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String toString() {
        return "WxOpenId{doctorPhone = " + doctorPhone + ", openId = " + openId + "}";
    }
}
