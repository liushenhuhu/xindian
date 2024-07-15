package com.ruoyi.xindian.patient_management.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ECGTendency implements Serializable {

    //心电检测次数
    private int count;
    //心率
    private List<Double> hr;
    //p波时限
    private List<Double> P;
    //PR间期
    private List<Double> PR;
    //QRS波群
    private List<Double> QRS;
    //QTC
    private List<Double> QTC;
    //SDNN
    private List<Double> SDNN;
    //RMSSD
    private List<Double> RMSSD;
}
