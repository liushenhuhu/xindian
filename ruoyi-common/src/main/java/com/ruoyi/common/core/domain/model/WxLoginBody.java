package com.ruoyi.common.core.domain.model;

public class WxLoginBody {

    /**
     * 临时登录凭证
     */
    private String code;
    /**
     * 偏移量
     */
    private String encryptedIv;
    /**
     * 加密数据
     */
    private String encryptedData;

    private String phone;


    public WxLoginBody() {
    }

    public WxLoginBody(String code, String encryptedIv, String getEncryptedData) {
        this.code = code;
        this.encryptedIv = encryptedIv;
        this.encryptedData = getEncryptedData;
    }


    /**
     * 获取
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取
     * @return encryptedIv
     */
    public String getEncryptedIv() {
        return encryptedIv;
    }

    /**
     * 设置
     * @param encryptedIv
     */
    public void setEncryptedIv(String encryptedIv) {
        this.encryptedIv = encryptedIv;
    }

    /**
     * 获取
     * @return getEncryptedData
     */
    public String getEncryptedData() {
        return encryptedData;
    }

    /**
     * 设置
     * @param getEncryptedData
     */
    public void setGetEncryptedData(String getEncryptedData) {
        this.encryptedData = getEncryptedData;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "WxLoginBody{code = " + code + ", encryptedIv = " + encryptedIv + ", getEncryptedData = " + encryptedData + "}";
    }
}
