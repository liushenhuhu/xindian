package com.ruoyi.xindian.mark_info.domain;

public class User {
    private String userId;

    //标签
    private String flag;
    //阈值
    private double threshold;
    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    public String getFlag() {
        return flag;
    }

    public double getThreshold() {
        return threshold;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }


    public String toString() {
        return "User{userId = " + userId + "}";
    }
}
