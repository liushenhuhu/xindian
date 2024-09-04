package com.ruoyi.xindian.pdf.controller;

import com.google.gson.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.ruoyi.xindian.alert_log.service.JecgSingleService;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.pdf.domain.JECGSingnalData;
import com.ruoyi.xindian.pdf.service.IPdfDataService;
import com.ruoyi.xindian.pdf.utils.CreatePdf;
import com.ruoyi.xindian.pdf.utils.PdfGenerator;
import com.ruoyi.xindian.pdf.domain.ReportData;
import com.ruoyi.xindian.pdf.domain.WeekPdfData;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.pmEcgData.service.IPmEcgDataService;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.weekDetectionTime.domain.WeekDetectionTime;
import com.ruoyi.xindian.weekDetectionTime.service.IWeekDetectionTimeService;
import com.ruoyi.xindian.weekReport.domain.WeekReport;
import com.ruoyi.xindian.weekReport.service.IWeekReportService;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfBlendMode;
import com.spire.pdf.graphics.PdfTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pdf/jecgReport")
public class JECGReportController extends BaseController {

    @Value("${ruoyi.profile}")
    private String path;

    @Value("${ruoyi.url}")
    private String url;


    @Resource
    private IPatientManagementService patientManagementService;

    @Resource
    private IReportService reportService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private IMedicalHistoryService medicalHistoryService;

    @Resource
    private IPatientService patientService;

    @Resource
    private IPdfDataService pdfDataService;

    @Resource
    private IPmEcgDataService pmEcgDataService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private IWeekReportService weekReportService;

    @Resource
    private IWeekDetectionTimeService weekDetectionTimeService;

    @Resource
    private JecgSingleService jecgSingleService;

    @PostMapping("/getPdf")
    public AjaxResult getPdf(@RequestBody PatientManagement patientManagement, HttpServletResponse response) throws Exception {

        PatientManagement patientManagement1 = patientManagementService.selectPatientManagementByPId(patientManagement.getpId());
        if (patientManagement1 == null) {
            throw new ServiceException("数据不存在");
        }
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pid", patientManagement1.getpId());
        String substring = patientManagement1.getEcgType().substring(0, 5);
        String url = "";
        String analysis = "";
        int is = 0;
        if (substring.equals("JECGs")) {
            url = "https://screen.mindyard.cn:84/get_jecg_report";
            analysis = "静态30秒II导联+解析结果";
        } else {
            if (substring.equals("JECG1")) {
                paramsMap.put("ecgType", "12");
                analysis = "12导联+解析结果";
                is = 12;
            } else {
                paramsMap.put("ecgType", "4");
                analysis = "4导联+解析结果";
            }
            url = "https://screen.mindyard.cn:84/get_jecg_report_12";
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap, headers);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");


        HashMap<String, Map<String, Object>> sendMessageVo = null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        } catch (Exception e) {
            throw new ServiceException("pdf生成失败");
        }
        try {
            if (sendMessageVo != null) {
                Map<String, Object> result = sendMessageVo.get("result");
                Object reportDataPdf = result.get("reportDataPdf");
                Map<String, Object> ecgAnalysisData = (Map<String, Object>) result.get("ecg_analysis_data");
                // 使用Base64进行解码
                byte[] decodedData = Base64.getDecoder().decode(reportDataPdf.toString());
                String tempPath = "";
                if (is == 12) {
                    tempPath = "/template/jecgReport_12.pdf";
                } else {
                    tempPath = "/template/jecgReport.pdf";
                }

                PdfDocument pdf2 = new PdfDocument();
                pdf2.loadFromBytes(decodedData);
                String outPath1 = "";
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("name", patientManagement1.getPatientName() != null && !patientManagement1.getPatientName().isEmpty() ? aesUtils.decrypt(patientManagement1.getPatientName()) : "");
                dataMap.put("age", patientManagement1.getBirthDay() != null && !patientManagement1.getBirthDay().isEmpty() ? DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(patientManagement1.getBirthDay())) + "" : "0");
                dataMap.put("sex", patientManagement1.getPatientSex());
                dataMap.put("conclusion", patientManagement1.getIntelligentDiagnosis() != null && !patientManagement1.getIntelligentDiagnosis().isEmpty() ? patientManagement1.getIntelligentDiagnosis() : patientManagement1.getDiagnosisConclusion());
                dataMap.put("heart", ecgAnalysisData.get("平均心率").toString());
                dataMap.put("pId", patientManagement1.getpId());
                dataMap.put("analysis", analysis);
                dataMap.put("reportDate", sdf1.format(patientManagement1.getConnectionTime()));
                dataMap.put("newDate", sdf.format(new Date()));
                dataMap.put("doctor", patientManagement1.getDiagnosisDoctor() != null && !patientManagement1.getDiagnosisDoctor().isEmpty() ? aesUtils.decrypt(patientManagement1.getDiagnosisDoctor()) : "");
                dataMap.put("pr", ecgAnalysisData.get("PR间期").toString());
                dataMap.put("qrs", ecgAnalysisData.get("QRS波时限").toString());
                dataMap.put("qtqtc", ecgAnalysisData.get("QT间期").toString() + "/" + ecgAnalysisData.get("QTc").toString());
                dataMap.put("pqrst", ecgAnalysisData.get("P波时限").toString() + "/" + ecgAnalysisData.get("QRS波时限") + "/" + ecgAnalysisData.get("T波时限").toString());
//            dataMap.put("rv5sv1", ecgAnalysisData.get("P波时限").toString()+"/"+ecgAnalysisData.get("QRS波时限")+"/"+ecgAnalysisData.get("T波时限").toString());
                Map<String, Object> o = new HashMap<>();
                o.put("tempPath", tempPath);
                o.put("dataMap", dataMap);
                outPath1 = path + new Date().getTime() + "tempfile1.pdf";
                //生成模板1
                byte[] pdfByTemplate = CreatePdf.createPdfByTemplate(o, tempPath, outPath1);
                String s = MergePages(pdfByTemplate, pdf2, response, patientManagement1.getpId(), is);
                //生成完成后删除合并的pdf，保留合并后的pdf
                deleteFile(outPath1);
                return AjaxResult.success(s);
            }
            return AjaxResult.error("pdf数据解析异常");
        } catch (Exception e) {
            throw new ServiceException("pdf数据解析失败");
        }


    }

    /**
     * 删除指定文件
     *
     * @throws IOException
     */
    public void deleteFile(String pathName1) throws IOException {
        Path path1 = Paths.get(pathName1);
        boolean result1 = Files.deleteIfExists(path1);
        System.out.println(result1);
    }

    /*
     *  合并pdf
     * */
    public String MergePages(byte[] pdfByTemplate, PdfDocument pdf1, HttpServletResponse response, String pId, Integer type) throws IOException {
        PdfDocument pdf2 = new PdfDocument();
        pdf2.loadFromBytes(pdfByTemplate);

        //根据pdf1的第一页绘制模板
        PdfTemplate template = pdf1.getPages().get(0).createTemplate();


        //获取pdf2的第一页
        PdfPageBase page2 = pdf2.getPages().get(0);
        //设置透明度和重叠样式
        page2.getCanvas().setTransparency(1f, 1f, PdfBlendMode.Normal);
        //将pdf1的第一页内容写入pdf2的第二页中的指定位置
        if (type == 12) {
            page2.getCanvas().drawTemplate(template, new Point2D.Float(60, 175));
        } else {
            page2.getCanvas().drawTemplate(template, new Point2D.Float(60, 200));
        }

        String pdfName = pId + ".pdf";
        String pdfPath = path + "pdf/" + pdfName;
        //保存pdf2
        pdf2.saveToFile(pdfPath, FileFormat.PDF);
        pdf2.dispose();

        //从浏览器下载pdf
        /*writeBytes(pdfPath, response.getOutputStream());
        File file = new File(pdfPath);
        if (file.exists()) {
            DataOutputStream temps = new DataOutputStream(response.getOutputStream());
            DataInputStream in = new DataInputStream(new FileInputStream(pdfPath));
            byte[] b = new byte[2048];
            while ((in.read(b)) != -1) {
                temps.write(b);
                temps.flush();
            }
            in.close();
            temps.close();
        } else {
            logger.error("文件不存在!");
        }*/

        return url + "/pdf/" + pdfName;
    }

    @PostMapping("/createPdf")
    public AjaxResult createPdf(@RequestBody ReportData reportData) throws IOException, ParseException {
        PdfGenerator pdfGenerator = new PdfGenerator();
//        reportData = ReportData.getReportData();
        String write_dir = "/home/chenpeng/workspace/system/xindian/data/pdf/" + reportData.getPid() + ".pdf";
//        write_dir = "E:/test.pdf";
        pdfGenerator.createNewPdf(write_dir, reportData);
        return AjaxResult.success("ok");
    }

    @GetMapping("/creatWeekPdf")
    public AjaxResult creatWeekPdf(PatientManagement patientManagement) throws Exception {
        int flag = 0;
        String patientPhone = aesUtils.encrypt(patientManagement.getPatientPhone());
//        Report reportT = reportService.getRecentlyTimeByPhone(patientPhone);
//        WeekDetectionTime weekDetectionTime = new WeekDetectionTime();
//        weekDetectionTime.setPatientPhone(patientPhone);
//        List<WeekDetectionTime> weekDetectionTimes = weekDetectionTimeService.selectWeekDetectionTimeList(weekDetectionTime);
//        if (weekDetectionTimes != null && weekDetectionTimes.size() != 0 && weekDetectionTimes.get(0).getDetectionTime().getTime() >= reportT.getReportTime().getTime()) {
//            AjaxResult.error("未有新的检测数据，请检测之后再重新生成！");
//        }
//        if (weekDetectionTimes == null || weekDetectionTimes.size() == 0) flag = 1;
//        try {
        PdfGenerator pdfGenerator = new PdfGenerator();
//        if (patientManagement == null) {
//            return AjaxResult.error("参数不为能空！");
//        }
//        if (patientManagement.getPatientPhone() != null && !patientManagement.getPatientPhone().isEmpty()) {
//            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
//        }
//        if (patientManagement.getPatientPhone() == null || patientManagement.getPatientPhone().isEmpty()) {
//            return AjaxResult.error("手机号不完整，请稍后在试");
//        }
//        String patientPhone = aesUtils.encrypt(patientManagement.getPatientPhone());
        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(patientPhone);
        Patient patient = patientService.selectPatientByPatientPhone(patientPhone);
//        if (patient == null || medicalHistory == null) return AjaxResult.error("当前用户信息存在问题，请联系管理员。");
        String height = String.valueOf(medicalHistory.getHeight());
        String weight = String.valueOf(medicalHistory.getWeight());
        Date birthDay = patient.getBirthDay();
        String patientAge = String.valueOf(DateUtil.getAge(birthDay));
        String patientName = aesUtils.decrypt(patient.getPatientName());
        String gender = patient.getPatientSex();

        Report report = new Report();
        report.setPPhone(patientPhone);
        LocalDate now;
        LocalDate now_7;

        LocalDate last;
        LocalDate last_7;

        if (patientManagement.getReportTime() != null) {
            now = DateUtil.getLocalDate(patientManagement.getReportTime()).minusWeeks(1).with(DayOfWeek.SUNDAY);
            now_7 = DateUtil.getLocalDate(patientManagement.getReportTime()).minusWeeks(1).with(DayOfWeek.MONDAY);
            last = DateUtil.getLocalDate(patientManagement.getReportTime()).minusWeeks(2).with(DayOfWeek.SUNDAY);
            last_7 = DateUtil.getLocalDate(patientManagement.getReportTime()).minusWeeks(2).with(DayOfWeek.MONDAY);
            flag = 1;
        } else {
            now = LocalDate.now().minusWeeks(1).with(DayOfWeek.SUNDAY);
            now_7 = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);
            last = LocalDate.now().minusWeeks(2).with(DayOfWeek.SUNDAY);
            last_7 = LocalDate.now().minusWeeks(2).with(DayOfWeek.MONDAY);
        }
        // 格式化日期输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = now.format(formatter);
        String startDay = now_7.format(formatter);
        //本周
        report.setStartReportTime(startDay);
        report.setEndReportTime(today);
        report.setReportType("JECGsingleWL");
        List<Report> reports = reportService.selectReportList(report);
        //上周
        today = last.format(formatter);
        startDay = last_7.format(formatter);
        report.setStartReportTime(startDay);
        report.setEndReportTime(today);
        report.setReportType("JECGsingleWL");
        List<Report> last_reports = reportService.selectReportList(report);


        PmEcgData pmEcgData;
        WeekPdfData weekPdfData;
        float[] floats;
        LinkedList<WeekPdfData> weekPdfDataList = new LinkedList<>();
        LinkedList<WeekPdfData> last_weekPdfDataList = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Report rp : reports) {
            JECGSingnalData jecgSingnalData = pdfDataService.getJECGSingnalByPid(rp.getpId());
            if (jecgSingnalData == null)
                continue;
            JecgSingle jecgSingle = jecgSingleService.selectById(rp.getpId());
            //获取R波
            String beatLabel = jecgSingle.getBeatLabel();
            JsonObject jsonObject = JsonParser.parseString(beatLabel).getAsJsonObject();
            //R
            LinkedList<LinkedList<Integer>> linkedListR = new LinkedList<>();

            LinkedList<LinkedList<Integer>> linkedListFZ = new LinkedList<>();

            LinkedList<LinkedList<Integer>> linkedListSZ = new LinkedList<>();

            for (int i = 0; i < 10; i++) {
                JsonObject asJsonObject = jsonObject.getAsJsonObject(String.valueOf(i));
                if (asJsonObject != null) {
                    JsonArray list1 = asJsonObject.getAsJsonArray("Normal");
                    JsonArray list2 = asJsonObject.getAsJsonArray("FangZao");
                    JsonArray list3 = asJsonObject.getAsJsonArray("ShiZao");
                    LinkedList<Integer> rP = new LinkedList<>();
                    LinkedList<Integer> fz = new LinkedList<>();
                    LinkedList<Integer> sz = new LinkedList<>();
                    for (JsonElement jsonElement : list1) {
                        rP.add(jsonElement.getAsInt());
                    }
                    for (JsonElement jsonElement : list2) {
                        fz.add(jsonElement.getAsInt());
                    }
                    for (JsonElement jsonElement : list3) {
                        sz.add(jsonElement.getAsInt());
                    }
                    linkedListR.add(rP);
                    linkedListFZ.add(fz);
                    linkedListSZ.add(sz);
                } else {
                    break;
                }
            }
            floats = btyeToFloatList(jecgSingnalData.getEcgData());
            pmEcgData = pmEcgDataService.selectPmEcgDataByPId(rp.getpId());
            weekPdfData = new WeekPdfData();
            String intelligentDiagnosis = rp.getIntelligentDiagnosis();
            String[] split = intelligentDiagnosis.split("\\(");
            weekPdfData.setAiConclusion(split[0]);
            weekPdfData.setHr(pmEcgData.getHrMean());
            weekPdfData.setP(pmEcgData.getpAmplitude());
            weekPdfData.setQtc(pmEcgData.getQtc());
            weekPdfData.setRr(pmEcgData.getrAmplitude());
            weekPdfData.setHrv(pmEcgData.getRmssd());
            weekPdfData.setQrs(pmEcgData.getQrsInterval());
            weekPdfData.setEcgData(floats);
            weekPdfData.setDetectionTime(sdf.format(rp.getReportTime()));
            weekPdfData.setRList(linkedListR);
            weekPdfData.setFzList(linkedListFZ);
            weekPdfData.setSzList(linkedListSZ);
            weekPdfDataList.add(weekPdfData);
        }
//        //上周数据
//        for (Report last_report : last_reports) {
//            pmEcgData = pmEcgDataService.selectPmEcgDataByPId(last_report.getpId());
//            weekPdfData = new WeekPdfData();
//            weekPdfData.setAiConclusion(last_report.getIntelligentDiagnosis());
//            weekPdfData.setHr(pmEcgData.getHrMean());
//            weekPdfData.setP(pmEcgData.getpAmplitude());
//            weekPdfData.setQtc(pmEcgData.getQtc());
//            weekPdfData.setRr(pmEcgData.getrAmplitude());
//            weekPdfData.setHrv(pmEcgData.getRmssd());
//            weekPdfData.setQrs(pmEcgData.getQrsInterval());
//            weekPdfData.setDetectionTime(sdf.format(last_report.getReportTime()));
//            last_weekPdfDataList.add(weekPdfData);
//        }


        if (weekPdfDataList.isEmpty()) return new AjaxResult(202, "无检测数据！");
        //数据记录入库
        WeekReport weekReport = new WeekReport();
        weekReport.setPatientPhone(patientPhone);
        String nowTime = DateUtil.getNowTime();
        nowTime = nowTime.replace(" ", "");
        nowTime = nowTime.replace(":", "");
        nowTime = nowTime.replace("-", "");
        String weekId = nowTime;
        weekReport.setWeekid(patientManagement.getPatientPhone() + weekId);
        weekReport.setWeekpdftime(DateUtil.getDateByLocalDate(now_7));
        String write_dir = "/home/chenpeng/workspace/system/xindian/data/weekpdf/" + patientManagement.getPatientPhone() + "/" + weekReport.getWeekid() + ".pdf";
        File file = new File("/home/chenpeng/workspace/system/xindian/data/weekpdf/" + patientManagement.getPatientPhone());
        if (!file.exists()) file.mkdirs();
//        write_dir = "E:/test.pdf";
        String conclusion = pdfGenerator.createWeekPdf(write_dir, weekPdfDataList, patientName, gender, patientAge, height, weight, last_weekPdfDataList);
        weekReport.setDiagnosisConclusion(conclusion);
        weekReportService.insertWeekReport(weekReport);

//        weekDetectionTime.setDetectionTime(maxTime);
//        if (flag == 0) {
//            weekDetectionTime.setId(weekDetectionTimes.get(0).getId());
//            weekDetectionTimeService.updateWeekDetectionTime(weekDetectionTime);
//        } else {
//            weekDetectionTimeService.insertWeekDetectionTime(weekDetectionTime);
//        }
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf");
//            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
//            System.out.println("ok");
//            return new ResponseEntity<>(weekPdf, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            System.out.println(e.toString());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        ValueOperations<String, Object> phone = redisTemplate.opsForValue();
////        phone.set("weekpdf_" + patientManagement.getPatientPhone(), "1");
//        if (flag == 0) {
//            HashOperations<String, String, Object> phone = redisTemplate.opsForHash();
//            phone.put("week_pdf", patientManagement.getPatientPhone(), "1");
//        }
//        if (Boolean.TRUE.equals(redisTemplate.hasKey("week_pdf")))
//            phone.put("week_pdf", patientManagement.getPatientPhone(), "1");
//        else {
//            System.out.println(DateUtil.getToday());
//            phone.put("week_pdf", patientManagement.getPatientPhone(), "1");
//            redisTemplate.expire("week_pdf", Duration.ofSeconds(DateUtil.getToday()));
//        }
        return AjaxResult.success();
    }

    @GetMapping("/getPdfStatus")
    public AjaxResult getPdfStatus(PatientManagement patientManagement) {
        Boolean week_pdf = redisTemplate.opsForHash().hasKey("week_pdf", patientManagement.getPatientPhone());
//        return AjaxResult.success(redisTemplate.hasKey("weekpdf_" + patientManagement.getPatientPhone()));
        return AjaxResult.success(week_pdf);

    }

    private float[] btyeToFloatList(byte[] ecgData) {
        float[] floats = new float[ecgData.length / 2];
        for (int i = 0; i < ecgData.length; i += 2) {
            floats[i / 2] = byteToFloat(ecgData[i + 1], ecgData[i]);
        }
        return floats;
    }

    private float byteToFloat(byte b1, byte b2) {
        int halfPrecision = ((b1 & 0xFF) << 8) | (b2 & 0xFF);

        int sign = (halfPrecision >> 15) & 0x1;
        int exponent = (halfPrecision >> 10) & 0x1F;
        int fraction = halfPrecision & 0x3FF;

        if (exponent == 0) {
            if (fraction == 0) {
                // Zero
                return (sign == 1) ? -0.0f : 0.0f;
            } else {
                // Subnormal number
                return (float) ((sign == 1 ? -1 : 1) * Math.pow(2, -14) * (fraction / Math.pow(2, 10)));
            }
        } else if (exponent == 31) {
            if (fraction == 0) {
                // Infinity
                return (sign == 1) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            } else {
                // NaN
                return Float.NaN;
            }
        } else {
            // Normalized number
            return (float) ((sign == 1 ? -1 : 1) * Math.pow(2, exponent - 15) * (1 + fraction / Math.pow(2, 10)));
        }
    }
}
