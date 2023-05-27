package com.ruoyi.xindian.mark_info.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;
import java.util.Map;

public class MarkInfo extends BaseEntity {
    //日志id
    private String logId;
    //用户列表
    private String allLabel;

    private String allUsers;


    private String allDataLabel;


    private List<Map<String,String>> labelList;


    public MarkInfo() {
    }

    public MarkInfo(String logId, String allLabel, String allUsers, List<Map<String, String>> labelList) {
        this.logId = logId;
        this.allLabel = allLabel;
        this.allUsers = allUsers;
        this.labelList = labelList;
    }

    /**
     * 获取
     * @return logId
     */
    public String getLogId() {
        return logId;
    }

    /**
     * 设置
     * @param logId
     */
    public void setLogId(String logId) {
        this.logId = logId;
    }

    /**
     * 获取
     * @return allLabel
     */
    public String getAllLabel() {
        return allLabel;
    }

    /**
     * 设置
     * @param allLabel
     */
    public void setAllLabel(String allLabel) {
        this.allLabel = allLabel;
    }

    /**
     * 获取
     * @return allUsers
     */
    public String getAllUsers() {
        return allUsers;
    }

    /**
     * 设置
     * @param allUsers
     */
    public void setAllUsers(String allUsers) {
        this.allUsers = allUsers;
    }

    public String getAllDataLabel() {
        return allDataLabel;
    }

    public void setAllDataLabel(String allDataLabel) {
        this.allDataLabel = allDataLabel;
    }

    /**
     * 获取
     * @return labelList
     */
    public List<Map<String, String>> getLabelList() {
        return labelList;
    }

    /**
     * 设置
     * @param labelList
     */
    public void setLabelList(List<Map<String, String>> labelList) {
        this.labelList = labelList;
    }

    public String toString() {
        return "MarkInfo{logId = " + logId + ", allLabel = " + allLabel + ", allUsers = " + allUsers + ", labelList = " + labelList + "}";
    }
}
