package com.ruoyi.xindian.pdf.utils;

import lombok.Data;

import java.util.List;

@Data
public class ECGFragment {
    //发生时间
    private String ocTime;
    //发生事件
    private String event;
    //平均心率
    private float meanHr;
    //心电数据
    private List<Float> ecgData;
}
