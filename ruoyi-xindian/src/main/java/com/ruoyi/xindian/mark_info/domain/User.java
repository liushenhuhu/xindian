package com.ruoyi.xindian.mark_info.domain;

public class User {
    private String userId;

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

    /**
     * 设置
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "User{userId = " + userId + "}";
    }
}
