package com.ruoyi.xindian.jecg_large_screen.vo;

import lombok.Data;

import java.util.Date;


public class RightBottomVo {

    private String countName;

    private String gatewayno;

    private Date createTime;

    private String terminalno;



    private String alertdetail;

    private String data;

    private String pId;

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public String getGatewayno() {
        return gatewayno;
    }

    public void setGatewayno(String gatewayno) {
        this.gatewayno = gatewayno;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTerminalno() {
        return terminalno;
    }

    public void setTerminalno(String terminalno) {
        this.terminalno = terminalno;
    }


    public String getAlertdetail() {
        return alertdetail;
    }

    public void setAlertdetail(String alertdetail) {
        this.alertdetail = alertdetail;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
