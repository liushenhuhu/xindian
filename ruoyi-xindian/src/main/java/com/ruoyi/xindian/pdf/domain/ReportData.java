package com.ruoyi.xindian.pdf.domain;


import com.ruoyi.xindian.pdf.utils.ECGFragment;
import lombok.Data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 报告信息类
 */
@Data
public class ReportData {
    //////////
    //基本信息
    //////////
    //患者姓名
    private String patientName;
    //性别
    private String gender;
    //年龄
    private String age;
    //病历号
    private String pid;
    //科室
    private String department;
    //病床
    private String hospitalBed;
    //临床诊断
    private String clinicalDiagnosis;
    //用药
    private String medicate;
    //心电检测时间
    private String detectionTime;
    //分析时长
    private String analysisTime;
    //////////
    //心率
    //////////
    //总心搏数
    private String beatCount;
    //平均心率
    private String meanHr;
    //最快心率
    private String maxHr;
    //最慢心率
    private String minHr;
    //停搏次数
    private String cardiacArrestcount;
    //最长停搏时间
    private String cardiacArrestTime;
    //////////
    //房颤、房扑事件
    //////////
    //房扑、房颤总阵数
    private String afCount;
    //总时长
    private String afTime;
    //最快
    private String maxAf;
    //最慢
    private String minAf;
    //最长持续时间
    private String afLongTime;
    //////////
    //房性事件
    //////////
    //房性心搏总数
    private String abCount;
    //成对房早
    private String pairAb;
    //二联律总阵数
    private String abBigeminy;
    //三联律总阵数
    private String abTrigeminy;
    //房速总阵数
    private String atCount;
    //最快房速心搏数
    private String maxAtCount;
    //最快房速心率
    private String maxAt;
    //最长持续时间
    private String atLongTime;
    //////////
    //室性事件
    //////////
    //室性心搏总数
    private String vbCount;
    //成对室早
    private String pairVb;
    //二联律总阵数
    private String vbBigeminy;
    //三联律总阵数
    private String vbTrigeminy;
    //室速总阵数
    private String vtCount;
    //最快室速心搏数
    private String maxVtCount;
    //最快室速心率
    private String maxVt;
    //最长持续时间
    private String vtLongTime;
    //////////
    //交界性事件
    //////////
    //交界性心搏总数
    private String borderLineCount;
    //成对交界性早搏
    private String pairborderLine;
    //二联律总阵数
    private String borderLineBigeminy;
    //三联律总阵数
    private String borderLineTrigeminy;
    //逸搏心搏总数
    private String escape;
    //////////
    //心率变异性
    //////////
    //SDNN
    private String sdnn;
    //rmssd
    private String rmssd;
    //nn20
    private String nn20;
    //pnn20
    private String pnn20;
    //nn50
    private String nn50;
    //pnn50
    private String pnn50;
    //////////
    //结论
    //////////
    //结论
    private String conclusion;
    //签名
    private String signature;
    //报告日期
    private String reportTime;
    //////////
    //心率失常
    //////////
    //心率趋势,每分钟的平均心率
    private List<Float> hr;
    //室性早搏
    private List<Integer> vbList;
    //室上性早搏
    private List<Integer> superVbList;
    //长间歇
    private List<Integer> longPauseList;
    //房颤、房扑
    private List<Integer> afList;
    //统计表
    // 时间 平均心率 最慢心率 最快心率 室早 成对室早 室速 室性二联律 室性三联律 室上性早搏 成对室上性早搏 室上速 室上性二联律 室上性三联律 长间歇
    private List<List<String>> statisticalTable;
    //////////
    //趋势波形
    //////////
    //ST段
    private List<Float> stList;
    //////////
    //心率变异性
    //////////
    //HRV散点图 坐标点
    private List<LinkedList<Float>> hrvScatterList;
    //Lorenz散点图 坐标点
    private List<LinkedList<Float>> lorenzScatterList;
    //////////
    //NN间期直方图
    //////////
    //NN间期直方图
    private List<Integer> nnList;
    //平均NN
    private String meanNN;
    //////////
    //心电片段
    //////////
    //心电片段
    private List<ECGFragment> ecgFrag;

    public static ReportData getReportData() {
        ReportData reportData = new ReportData();
        reportData.setPatientName("陈鹏");
        reportData.setGender("男");
        reportData.setAge("24");
        reportData.setPid("123456");
        reportData.setDepartment("101");
        reportData.setHospitalBed("102");
        reportData.setClinicalDiagnosis("测试报告临床诊断");
        reportData.setMedicate("测试用药");
        reportData.setDetectionTime("2024-07-04 12:12:12 - 2024-07-05 12:12:12");
        reportData.setAnalysisTime("19小时9分钟44秒");
        reportData.setBeatCount("10");
        reportData.setMeanHr("10f");
        reportData.setMaxHr("10f");
        reportData.setMinHr("10f");
        reportData.setCardiacArrestcount("10");
        reportData.setCardiacArrestTime("10秒");
        reportData.setAfCount("10");
        reportData.setAfTime("10秒");
        reportData.setMaxAf("78");
        reportData.setMinAf("77");
        reportData.setAfLongTime("10秒");
        reportData.setAbCount("10");
        reportData.setPairAb("10");
        reportData.setAbBigeminy("10");
        reportData.setAbTrigeminy("10");
        reportData.setAtCount("10");
        reportData.setMaxAt("100");
        reportData.setAtLongTime("10秒");
        reportData.setVbCount("10");
        reportData.setPairVb("10");
        reportData.setVbBigeminy("10");
        reportData.setVtCount("10");
        reportData.setMaxVtCount("10");
        reportData.setMaxVt("10");
        reportData.setVtLongTime("10秒");
        reportData.setBorderLineCount("10");
        reportData.setPairborderLine("10");
        reportData.setBorderLineBigeminy("10");
        reportData.setBorderLineTrigeminy("10");
        reportData.setEscape("10");
        reportData.setSdnn("10");
        reportData.setRmssd("10");
        reportData.setNn20("10");
        reportData.setPnn20("10");
        reportData.setNn50("10");
        reportData.setPnn50("10");
        reportData.setConclusion("窦性心律\n心率变异性 SDNN 185ms");
        reportData.setSignature("");
        reportData.setReportTime("2024-07-05 08:54:14");
        Random random = new Random();
        LinkedList<Float> hrList = new LinkedList<>();
        for (int i = 0; i < 24 * 60; i++) {
            hrList.add((float) (random.nextInt(90) + 60));
        }
        LinkedList<Integer> vbList = new LinkedList<>(Arrays.asList(5, 10, 40, 20, 4, 0, 3, 5, 9, 10, 61, 12, 43, 1, 2, 4, 8, 2, 1, 5, 3, 0, 0, 9));
        LinkedList<Integer> superVbList = new LinkedList<>(Arrays.asList(1, 4, 6, 2, 4, 0, 3, 5, 9, 10, 4, 12, 6, 1, 2, 4, 8, 2, 1, 5, 3, 0, 0, 9));
        LinkedList<Integer> longPauseList = new LinkedList<>(Arrays.asList(5, 10, 1, 1, 4, 0, 3, 5, 9, 10, 4, 12, 12, 1, 2, 4, 8, 2, 1, 5, 3, 0, 0, 9));
        LinkedList<Integer> afList = new LinkedList<>(Arrays.asList(60, 12, 55, 20, 4, 77, 45, 78, 90, 10, 61, 12, 43, 23, 2, 4, 8, 2, 1, 5, 3, 22, 33, 59));
        reportData.setHr(hrList);
        reportData.setVbList(vbList);
        reportData.setSuperVbList(superVbList);
        reportData.setLongPauseList(longPauseList);
        reportData.setAfList(afList);
        LinkedList<List<String>> statisticalTable = new LinkedList<>();
        LinkedList<String> pstatisticalTable = new LinkedList<>(Arrays.asList(
                "12:12-14:12", "12", "13", "14", "13", "11", "12", "41", "21", "31", "16", "18", "10", "12", "13"
        ));
        statisticalTable.add(pstatisticalTable);
        statisticalTable.add(pstatisticalTable);
        statisticalTable.add(pstatisticalTable);
        statisticalTable.add(pstatisticalTable);
        statisticalTable.add(pstatisticalTable);
        reportData.setStatisticalTable(statisticalTable);

        LinkedList<Integer> nnList = new LinkedList<>(Arrays.asList(60, 12, 55, 20, 4, 77, 45, 78, 90, 10, 61, 12, 43, 23, 2, 4, 8, 2, 1, 5, 3, 22, 33, 59));
        reportData.setNnList(nnList);
        reportData.setMeanNN("724");

        LinkedList<Float> ecgData = new LinkedList<>(Arrays.asList(-0.094f, -0.065f, -0.041f, -0.061f, -0.052f, -0.03f, 0.0f, -0.026f, 0.004f, 0.015f, 0.087f, 0.068f, 0.05f, -0.006f, -0.008f, -0.032f, -0.03f, -0.046f, -0.046f, -0.035f, -0.017f, -0.039f, -0.021f, 0.037f, 0.14f, 0.32f, 0.709f, 0.129f, -0.727f, -0.217f, -0.032f, 0.004f, 0.039f, 0.054f, 0.054f, 0.098f, 0.116f, 0.114f, 0.145f, 0.131f, 0.134f, 0.186f, 0.232f, 0.285f, 0.342f, 0.419f, 0.522f, 0.617f, 0.674f, 0.718f, 0.67f, 0.549f, 0.34f, 0.103f, -0.096f, -0.241f, -0.329f, -0.375f, -0.355f, -0.371f, -0.36f, -0.327f, -0.325f, -0.287f, -0.276f, -0.263f, -0.21f, -0.215f, -0.206f, -0.186f, -0.184f, -0.191f, -0.145f, -0.147f, -0.112f, -0.118f, -0.127f, -0.127f, -0.136f, -0.14f, -0.127f, -0.153f, -0.118f, -0.127f, -0.107f, -0.112f, -0.116f, -0.12f, -0.114f, -0.109f, -0.103f, -0.107f, -0.085f, -0.098f, -0.081f, -0.079f, -0.074f, -0.079f, -0.068f, -0.05f, -0.052f, -0.048f, 0.008f, 0.017f, 0.061f, 0.039f, 0.054f, 0.035f, -0.008f, -0.061f, -0.03f, -0.041f, -0.024f, -0.032f, -0.026f, -0.024f, -0.026f, 0.072f, 0.09f, 0.463f, 0.626f, -0.054f, -0.494f, 0.0f, 0.048f, 0.039f, 0.052f, 0.068f, 0.065f, 0.054f, 0.063f, 0.09f, 0.134f, 0.142f, 0.169f, 0.191f, 0.199f, 0.259f, 0.318f, 0.404f, 0.494f, 0.617f, 0.703f, 0.672f, 0.643f, 0.492f, 0.274f, 0.03f, -0.166f, -0.29f, -0.34f, -0.358f, -0.362f, -0.364f, -0.347f, -0.349f, -0.305f, -0.303f, -0.281f, -0.265f, -0.235f, -0.21f, -0.197f, -0.217f, -0.173f, -0.182f, -0.158f, -0.156f, -0.16f, -0.166f, -0.131f, -0.149f, -0.156f, -0.147f, -0.145f, -0.129f, -0.136f, -0.149f, -0.136f, -0.145f, -0.12f, -0.09f, -0.094f, -0.105f, -0.085f, -0.116f, -0.09f, -0.101f, -0.087f, -0.094f, -0.048f, -0.068f, -0.074f, -0.05f, -0.035f, -0.019f, 0.006f, 0.054f, 0.061f, -0.008f, 0.021f, -0.008f, -0.052f, -0.072f, -0.05f, -0.074f, -0.065f, -0.068f, -0.05f, -0.068f, 0.057f, 0.054f, 0.446f, 0.61f, -0.039f, -0.577f, -0.017f, 0.002f, 0.019f, 0.032f, 0.07f, 0.046f, 0.085f, 0.07f, 0.098f, 0.123f, 0.127f, 0.12f, 0.188f, 0.219f, 0.261f, 0.32f, 0.406f, 0.507f, 0.593f, 0.659f, 0.7f, 0.639f, 0.465f, 0.237f, 0.021f, -0.158f, -0.274f, -0.342f, -0.349f, -0.373f, -0.36f, -0.362f, -0.307f, -0.312f, -0.327f, -0.283f, -0.281f, -0.268f, -0.226f, -0.215f, -0.204f, -0.18f, -0.191f, -0.171f, -0.16f, -0.142f, -0.153f, -0.164f, -0.162f, -0.147f, -0.153f, -0.182f, -0.151f, -0.164f, -0.129f, -0.171f, -0.153f, -0.12f, -0.114f, -0.103f, -0.103f, -0.105f, -0.079f, -0.09f, -0.063f, -0.092f, -0.087f, -0.037f, 0.006f, 0.013f, 0.07f, 0.004f, 0.019f, -0.03f, -0.046f, -0.063f, -0.037f, -0.054f, -0.052f, -0.057f, -0.021f, -0.01f, 0.074f, 0.118f, 0.509f, 0.533f, -0.208f, -0.505f, -0.074f, 0.026f, 0.05f, 0.028f, 0.048f, 0.043f, 0.074f, 0.068f, 0.101f, 0.107f, 0.145f, 0.162f, 0.195f, 0.23f, 0.279f, 0.336f, 0.461f, 0.553f, 0.643f, 0.705f, 0.718f, 0.635f, 0.45f, 0.206f, -0.017f, -0.16f, -0.276f, -0.329f, -0.355f, -0.342f, -0.351f, -0.349f, -0.342f, -0.322f, -0.296f, -0.29f, -0.252f, -0.241f, -0.226f, -0.226f, -0.206f, -0.18f, -0.173f, -0.184f, -0.158f, -0.153f, -0.158f, -0.131f, -0.149f, -0.166f, -0.125f, -0.123f, -0.118f, -0.123f, -0.107f, -0.094f, -0.076f, -0.118f, -0.105f, -0.114f, -0.101f, -0.083f, -0.065f, -0.092f, -0.048f, -0.074f, -0.039f, -0.052f, -0.015f, 0.019f, 0.039f, 0.05f, 0.048f, 0.028f, -0.013f, -0.043f, -0.041f, -0.057f, -0.048f, -0.065f, -0.037f, -0.052f, -0.03f, 0.07f, 0.076f, 0.494f, 0.45f, -0.375f, -0.362f, -0.03f, 0.026f, 0.028f, 0.061f, 0.063f, 0.094f, 0.085f, 0.083f, 0.105f, 0.125f, 0.136f, 0.175f, 0.18f, 0.239f, 0.307f, 0.366f, 0.459f, 0.553f, 0.635f, 0.698f, 0.672f, 0.621f, 0.435f, 0.202f, -0.026f, -0.16f, -0.272f, -0.305f, -0.344f, -0.338f, -0.336f, -0.318f, -0.336f, -0.274f, -0.272f, -0.272f, -0.276f, -0.241f, -0.215f, -0.21f, -0.204f, -0.169f, -0.175f, -0.145f, -0.191f, -0.129f, -0.164f, -0.195f, -0.213f, -0.158f, -0.16f, -0.191f, -0.162f, -0.147f, -0.224f, -0.006f, -0.114f, 0.046f, 0.0f, 0.039f, 0.043f, -0.054f, -0.032f, -0.072f, -0.017f, -0.083f, -0.03f, -0.008f, -0.039f, 0.037f, -0.07f, 0.107f, 0.063f, 0.024f, 0.037f, -0.041f, -0.068f, -0.063f, -0.026f, -0.048f, -0.037f, -0.063f, -0.083f, 0.123f, 0.074f, 0.395f, 0.47f, -0.226f, -0.476f, 0.0f, 0.048f, 0.063f, 0.035f, 0.085f, 0.107f, 0.123f, 0.123f, 0.14f, 0.116f, 0.14f, 0.173f, 0.184f, 0.204f, 0.283f, 0.369f, 0.432f, 0.516f, 0.61f, 0.667f, 0.674f, 0.624f, 0.435f, 0.202f, -0.032f, -0.204f, -0.279f, -0.333f, -0.38f, -0.404f, -0.404f, -0.38f, -0.369f, -0.369f, -0.371f, -0.318f, -0.281f, -0.285f, -0.279f, -0.252f, -0.204f, -0.237f, -0.21f, -0.184f, -0.177f, -0.177f, -0.18f, -0.149f, -0.142f, -0.166f, -0.147f, -0.158f, -0.156f, -0.147f, -0.131f, -0.12f, -0.118f, -0.12f, -0.14f, -0.134f, -0.116f, -0.081f, -0.09f, -0.085f, -0.087f, -0.074f, -0.037f, -0.021f, 0.017f, -0.004f, -0.01f, -0.03f, -0.026f, -0.068f, -0.057f, -0.057f, -0.072f, -0.09f, -0.076f, -0.07f, -0.057f, 0.085f, 0.153f, 0.586f, 0.369f, -0.58f, -0.432f, -0.032f, -0.015f, -0.015f, 0.008f, -0.008f, 0.068f, 0.072f, 0.09f, 0.085f, 0.101f, 0.134f, 0.164f, 0.193f, 0.246f, 0.318f, 0.377f, 0.459f, 0.573f, 0.635f, 0.685f, 0.709f, 0.584f, 0.382f, 0.171f, -0.024f, -0.147f, -0.25f, -0.301f, -0.305f, -0.279f, -0.301f, -0.272f, -0.261f, -0.257f, -0.246f, -0.241f, -0.237f, -0.206f, -0.184f, -0.173f, -0.171f, -0.142f, -0.151f, -0.142f, -0.145f, -0.123f, -0.12f, -0.171f, -0.173f, -0.118f, -0.12f, -0.138f, -0.138f, -0.136f, -0.138f, -0.098f, -0.112f, -0.101f, -0.14f, -0.09f, -0.046f, 0.032f, 0.035f, 0.043f, 0.008f, 0.026f, -0.024f, -0.063f, -0.094f, -0.096f, -0.107f, -0.087f, -0.037f, -0.041f, -0.074f, 0.024f, -0.019f, 0.191f, 0.678f, 0.05f, -0.628f, -0.092f, 0.01f, 0.032f, 0.05f, 0.059f, 0.068f, 0.063f, 0.074f, 0.114f, 0.136f, 0.149f, 0.16f, 0.199f, 0.217f, 0.248f, 0.349f, 0.424f, 0.511f, 0.599f, 0.678f, 0.689f, 0.628f, 0.457f, 0.239f, -0.013f, -0.14f, -0.263f, -0.333f, -0.353f, -0.366f, -0.347f, -0.347f, -0.316f, -0.309f, -0.29f, -0.23f, -0.241f, -0.219f, -0.197f, -0.197f, -0.173f, -0.175f, -0.166f, -0.14f, -0.142f, -0.136f, -0.123f, -0.134f, -0.114f, -0.131f, -0.123f, -0.134f, -0.092f, -0.101f, -0.112f, -0.109f, -0.101f, -0.109f, -0.109f, -0.085f, -0.081f, -0.087f, -0.085f, -0.098f, -0.043f, 0.002f, 0.037f, 0.037f, -0.006f, -0.002f, -0.03f, -0.035f, -0.061f, -0.081f, -0.076f, -0.081f, -0.074f, -0.081f, -0.041f, 0.07f, -0.002f, 0.454f, 0.441f, -0.316f, -0.485f, -0.059f, 0.002f, -0.004f, 0.006f, 0.052f, 0.087f, 0.085f, 0.094f, 0.105f, 0.116f, 0.151f, 0.162f, 0.202f, 0.228f, 0.27f, 0.358f, 0.443f, 0.555f, 0.652f, 0.683f, 0.696f, 0.571f, 0.406f, 0.175f, -0.041f, -0.215f, -0.316f, -0.34f, -0.351f, -0.364f, -0.353f, -0.349f, -0.325f, -0.274f, -0.27f, -0.276f, -0.265f, -0.248f, -0.243f, -0.202f, -0.162f, -0.195f, -0.175f, -0.177f, -0.134f, -0.142f, -0.166f, -0.182f, -0.188f, -0.136f, -0.059f, -0.054f, -0.072f, -0.09f, -0.065f, -0.068f, -0.101f, -0.041f, -0.09f, -0.008f, -0.054f, -0.063f, -0.074f, -0.046f, -0.019f, -0.013f, -0.048f, -0.019f, 0.057f, 0.098f, 0.054f, 0.026f, 0.015f, -0.041f, -0.043f, -0.07f, -0.07f, -0.081f, -0.07f, -0.054f, -0.068f, -0.052f, 0.039f, 0.004f, 0.404f, 0.522f, -0.129f, -0.542f, -0.035f, 0.013f, 0.024f, 0.013f, 0.061f, 0.063f, 0.068f, 0.074f, 0.087f, 0.098f, 0.12f, 0.145f, 0.184f, 0.206f, 0.268f, 0.336f, 0.406f, 0.505f, 0.604f, 0.637f, 0.659f, 0.602f, 0.417f, 0.193f, -0.041f, -0.197f, -0.303f, -0.382f, -0.373f, -0.393f, -0.371f, -0.369f, -0.366f, -0.353f, -0.316f, -0.309f, -0.298f, -0.294f, -0.263f, -0.25f, -0.204f, -0.202f, -0.182f, -0.197f, -0.184f, -0.156f, -0.175f, -0.171f, -0.171f, -0.171f, -0.175f, -0.173f, -0.156f, -0.171f, -0.153f, -0.16f, -0.149f, -0.123f, -0.109f, -0.101f, -0.112f, -0.105f, -0.079f, -0.105f, -0.098f, -0.07f, -0.083f, -0.061f, -0.039f, 0.017f, 0.035f, 0.035f, -0.01f, -0.01f, -0.024f, -0.037f, -0.074f, -0.035f, -0.061f, -0.061f, -0.046f, -0.032f, -0.039f, 0.021f, 0.101f, 0.318f, 0.76f, 0.206f, -0.736f, -0.265f, -0.046f, 0.008f, 0.006f, 0.026f, 0.05f, 0.037f, 0.063f, 0.079f, 0.094f, 0.12f, 0.149f, 0.173f, 0.217f, 0.252f, 0.312f, 0.397f, 0.5f, 0.595f, 0.665f, 0.683f, 0.67f, 0.544f, 0.307f, 0.098f, -0.105f, -0.219f, -0.301f, -0.331f, -0.369f, -0.349f, -0.36f, -0.329f, -0.294f, -0.285f, -0.287f, -0.252f, -0.257f, -0.217f, -0.219f, -0.195f, -0.18f, -0.166f, -0.151f, -0.14f, -0.138f, -0.136f, -0.136f, -0.134f, -0.142f, -0.142f, -0.134f, -0.105f, -0.094f, -0.096f, -0.09f, -0.087f, -0.083f, -0.083f, -0.065f, -0.063f, -0.061f, -0.081f, -0.05f, -0.013f, -0.01f, 0.026f, 0.0f, -0.008f, 0.002f, -0.039f, -0.052f, -0.07f, -0.057f, -0.054f, -0.046f, -0.037f, -0.035f, 0.006f, 0.116f, 0.114f, 0.606f, 0.465f, -0.461f, -0.408f, -0.03f, 0.024f, 0.046f));
        ECGFragment ecgFragment1 = new ECGFragment();
        ecgFragment1.setOcTime("2024-07-04 15:59:34");
        ecgFragment1.setEvent("最快心率");
        ecgFragment1.setMeanHr(120);
        ecgFragment1.setEcgData(ecgData);
        ECGFragment ecgFragment2 = new ECGFragment();
        ecgFragment2.setOcTime("2024-07-04 15:59:34");
        ecgFragment2.setEvent("最慢心率");
        ecgFragment2.setMeanHr(60);
        ecgFragment2.setEcgData(ecgData);
        ECGFragment ecgFragment3 = new ECGFragment();
        ecgFragment3.setOcTime("2024-07-04 15:59:34");
        ecgFragment3.setEvent("房性早搏");
        ecgFragment3.setMeanHr(78);
        ecgFragment3.setEcgData(ecgData);
        ECGFragment ecgFragment4 = new ECGFragment();
        ecgFragment4.setOcTime("2024-07-04 15:59:34");
        ecgFragment4.setEvent("心房颤动");
        ecgFragment4.setMeanHr(89);
        ecgFragment4.setEcgData(ecgData);
        LinkedList<ECGFragment> ecgFragments = new LinkedList<>();
        ecgFragments.add(ecgFragment1);
        ecgFragments.add(ecgFragment2);
        ecgFragments.add(ecgFragment3);
        ecgFragments.add(ecgFragment4);
        reportData.setEcgFrag(ecgFragments);
        return reportData;
    }
}
