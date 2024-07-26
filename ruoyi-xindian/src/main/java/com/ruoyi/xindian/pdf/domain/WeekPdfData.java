package com.ruoyi.xindian.pdf.domain;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class WeekPdfData {
    private String aiConclusion;
    private String detectionTime;
    private String hr;
    private String p;
    private String rr;
    private String qrs;
    private String qtc;
    private String hrv;
    private float[] ecgData;
    private LinkedList<LinkedList<Integer>> rList;
}
