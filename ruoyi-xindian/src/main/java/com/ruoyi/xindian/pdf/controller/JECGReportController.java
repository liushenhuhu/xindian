package com.ruoyi.xindian.pdf.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.ruoyi.xindian.alert_log.service.JecgSingleService;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.pdf.domain.JECGSingnalData;
import com.ruoyi.xindian.pdf.domain.ReportData;
import com.ruoyi.xindian.pdf.domain.WeekPdfData;
import com.ruoyi.xindian.pdf.service.IPdfDataService;
import com.ruoyi.xindian.pdf.utils.CreatePdf;
import com.ruoyi.xindian.pdf.utils.JecgPdfCreateUtil;
import com.ruoyi.xindian.pdf.utils.PdfGenerator;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.pmEcgData.service.IPmEcgDataService;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.weekDetectionTime.service.IWeekDetectionTimeService;
import com.ruoyi.xindian.weekReport.domain.WeekReport;
import com.ruoyi.xindian.weekReport.service.IWeekReportService;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfBlendMode;
import com.spire.pdf.graphics.PdfTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private IDoctorService doctorService;

    @Resource
    private IWeekDetectionTimeService weekDetectionTimeService;

    @Resource
    private JecgSingleService jecgSingleService;

    @Resource
    private JecgPdfCreateUtil jecgPdfCreateUtil;

    @PostMapping("/getPdf")
    public AjaxResult getPdf(@RequestBody PatientManagement patientManagement, HttpServletResponse response) throws Exception {

        String pdfName = patientManagement.getpId() + "_ecg.pdf";
        String pdfPath = path + "pdf/" + pdfName;
        try {
            PatientManagement patientManagement1 = patientManagementService.selectPatientManagementByPId(patientManagement.getpId());
            if (patientManagement1 == null) {
                throw new ServiceException("数据不存在");
            }



            HttpHeaders headers = new HttpHeaders(); //构建请求头
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("user","zzu");
            headers.set("password","zzu123");
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("pid", patientManagement1.getpId());
            String substring = patientManagement1.getEcgType().substring(0, 5);
            String url1 = "";
            String analysis = "";
            int is = 0;
            if (substring.equals("JECGs")) {
                url1 = "https://screen.mindyard.cn:84/get_jecg_single_web";
                analysis = "静态30秒II导联+解析结果";
            } else {
                if (substring.equals("JECG1")) {
                    paramsMap.put("ecgType", "12");
                    analysis = "12导联+解析结果";
                    is = 12;
                } else {
                    paramsMap.put("ecgType", "4");
                    analysis = "4导联+解析结果";
                    is = 4;
                }
                url1 = "https://screen.mindyard.cn:84/get_jecg_12";
            }
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap, headers);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");


            HashMap<String, Map<String, Object>> sendMessageVo = null;
            try {
                sendMessageVo = restTemplate.postForObject(url1, request, HashMap.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("pdf生成失败");
            }

            Map<String, Object> map1 = sendMessageVo.get("result");
            Object I = map1.get("I");
            Object II = map1.get("II");
            Object III = map1.get("III");
            Object aVR = map1.get("aVR");
            Object aVL = map1.get("aVL");
            Object aVF = map1.get("aVF");
            Object V1 = map1.get("V1");
            Object V2 = map1.get("V2");
            Object V3 = map1.get("V3");
            Object v4 = map1.get("V4");
            Object v5 = map1.get("V5");
            Object v6 = map1.get("V6");



            Map<String,Object> ecgAnalysisData = (Map<String, Object>) map1.get("ecg_analysis_data");

            Map<String,Object> map = new HashMap<>();
            map.put("patientName",patientManagement1.getPatientName() != null && !patientManagement1.getPatientName().isEmpty() ? aesUtils.decrypt(patientManagement1.getPatientName()) : "");
            map.put("pId",patientManagement1.getpId());
            map.put("sex",patientManagement1.getPatientSex());
            map.put("age",patientManagement1.getBirthDay() != null && !patientManagement1.getBirthDay().isEmpty() ? DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(patientManagement1.getBirthDay())) + "" :patientManagement1.getPatientAge()!=null ? patientManagement1.getPatientAge(): "0");
            map.put("source",patientManagement1.getHospitalName());
            map.put("reportTime",sdf.format(patientManagement1.getConnectionTime()));
            map.put("getDataTime",sdf2.format(patientManagement1.getReportTime()));
            map.put("heart", ecgAnalysisData.get("平均心率").toString());
            map.put("conclusion",patientManagement1.getDiagnosisConclusion() != null && !patientManagement1.getDiagnosisConclusion().isEmpty() ? patientManagement1.getDiagnosisConclusion() : patientManagement1.getIntelligentDiagnosis());
            map.put("pr",ecgAnalysisData.get("PR间期").toString());
            map.put("qtqtc",ecgAnalysisData.get("QT间期").toString() + "/" + ecgAnalysisData.get("QTc").toString());
            map.put("ecgType",patientManagement1.getEcgType());
            map.put("qrs",ecgAnalysisData.get("QRS波时限").toString());
            map.put("status",patientManagement1.getDiagnosisStatus());
            map.put("reportType",analysis);
            if (patientManagement1.getDiagnosisStatus()!=null&&patientManagement1.getDiagnosisStatus()==1){
                Doctor doctor = doctorService.selectDoctorByDoctorPhone(patientManagement1.getDPhone());
                if (doctor!=null&& StringUtils.isNotEmpty(doctor.getDzVisa())){
                    map.put("doctorPhoto",url+"/"+doctor.getDzVisa());
                }
            }
            Map<String,Double[]> ecgDataMap = new HashMap<>();


            if (substring.equals("JECGs")) {
                ecgDataMap.put("II",getDouble(II));


            } else {
                if (substring.equals("JECG1")) {
                    ecgDataMap.put("I",Arrays.copyOfRange(getDouble(I),0,250));
                    ecgDataMap.put("II",Arrays.copyOfRange(getDouble(II),0,250));
                    ecgDataMap.put("III",Arrays.copyOfRange(getDouble(III),0,250));
                    ecgDataMap.put("aVR",Arrays.copyOfRange(getDouble(aVR),0,250));
                    ecgDataMap.put("aVL",Arrays.copyOfRange(getDouble(aVL),0,250));
                    ecgDataMap.put("aVF",Arrays.copyOfRange(getDouble(aVF),0,250));
                    ecgDataMap.put("V1",Arrays.copyOfRange(getDouble(V1),0,250));
                    ecgDataMap.put("V2",Arrays.copyOfRange(getDouble(V2),0,250));
                    ecgDataMap.put("V3",Arrays.copyOfRange(getDouble(V3),0,250));
                    ecgDataMap.put("V4",Arrays.copyOfRange(getDouble(v4),0,250));
                    ecgDataMap.put("V5",Arrays.copyOfRange(getDouble(v5),0,250));
                    ecgDataMap.put("V6",Arrays.copyOfRange(getDouble(v6),0,250));
                    ecgDataMap.put("All_II",getDouble(II));
                } else {
                    ecgDataMap.put("II",getDouble(II));
                    ecgDataMap.put("V2",getDouble(V2));
                    ecgDataMap.put("V4",getDouble(v4));
                    ecgDataMap.put("V6",getDouble(v6));
                }
            }
            jecgPdfCreateUtil.createPdf(map,ecgDataMap,pdfPath);
        }catch (Exception e){

            e.printStackTrace();

        }

        return AjaxResult.success(url+"/pdf/"+pdfName);
    }





    public Double[] getDouble(Object o) {
        if (o instanceof List<?>) { // 使用 instanceof 检查类型
            try {
                List<?> list = (List<?>) o; // 先进行通用类型转换
                return list.stream()
                        .filter(Double.class::isInstance) // 过滤出 Double 类型
                        .map(Double.class::cast) // 转换为 Double
                        .toArray(Double[]::new); // 转换为数组
            } catch (Exception e) {
                // 可以记录异常日志或处理
            }
        }
        return null; // 如果不是 List 或发生异常，返回 null
    }



//    public AjaxResult getPdf(@RequestBody PatientManagement patientManagement, HttpServletResponse response) throws Exception {
//
//        PatientManagement patientManagement1 = patientManagementService.selectPatientManagementByPId(patientManagement.getpId());
//        if (patientManagement1 == null) {
//            throw new ServiceException("数据不存在");
//        }
//        String pdfName = patientManagement.getpId() + "_ecg.pdf";
//        String pdfPath = path + "pdf/" + pdfName;
//        File file = new File(pdfPath);
//        // 检查文件是否存在
//        if (file.exists()) {
//            return AjaxResult.success(updateReport(patientManagement1));
//        }
//
//
//        HttpHeaders headers = new HttpHeaders(); //构建请求头
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        Map<String, Object> paramsMap = new HashMap<>();
//        paramsMap.put("pid", patientManagement1.getpId());
//        String substring = patientManagement1.getEcgType().substring(0, 5);
//        String url = "";
//        String analysis = "";
//        int is = 0;
//        if (substring.equals("JECGs")) {
//            url = "https://screen.mindyard.cn:84/get_jecg_report";
//            analysis = "静态30秒II导联+解析结果";
//        } else {
//            if (substring.equals("JECG1")) {
//                paramsMap.put("ecgType", "12");
//                analysis = "12导联+解析结果";
//                is = 12;
//            } else {
//                paramsMap.put("ecgType", "4");
//                analysis = "4导联+解析结果";
//                is = 4;
//            }
//            url = "https://screen.mindyard.cn:84/get_jecg_report_12";
//        }
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap, headers);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//
//
//        HashMap<String, Map<String, Object>> sendMessageVo = null;
//        try {
//            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServiceException("pdf生成失败");
//        }
//        try {
//            if (sendMessageVo != null) {
//                Map<String, Object> result = sendMessageVo.get("result");
//                Object reportDataPdf = result.get("reportDataPdf");
//                Map<String, Object> ecgAnalysisData = (Map<String, Object>) result.get("ecg_analysis_data");
//                // 使用Base64进行解码
//                byte[] decodedData = Base64.getDecoder().decode(reportDataPdf.toString());
//                String tempPath = "";
//                if (is == 12||is ==4) {
//                    tempPath = "/template/jecgReport_12.pdf";
//                } else {
//                    tempPath = "/template/jecgReport.pdf";
//                }
//
//                PdfDocument pdf2 = new PdfDocument();
//                pdf2.loadFromBytes(decodedData);
//
//                String text1 = "(本报告由互联网医疗与健康服务河南省协同创新中心人工智能平台自动生成, 未经临床验证, 仅供参考, 请根据医生诊断进一步确认.)";
//                String outPath1 = "";
//                Map<String, String> dataMap = new HashMap<>();
//                dataMap.put("name", patientManagement1.getPatientName() != null && !patientManagement1.getPatientName().isEmpty() ? aesUtils.decrypt(patientManagement1.getPatientName()) : "");
//                dataMap.put("tongzhi",patientManagement1.getDiagnosisStatus() != null && patientManagement1.getDiagnosisStatus()!=1L?text1 : "");
//                dataMap.put("age", patientManagement1.getBirthDay() != null && !patientManagement1.getBirthDay().isEmpty() ? DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(patientManagement1.getBirthDay())) + "" :patientManagement1.getPatientAge()!=null ? patientManagement1.getPatientAge(): "0");
//                dataMap.put("sex", patientManagement1.getPatientSex());
//                dataMap.put("conclusion", patientManagement1.getDiagnosisConclusion() != null && !patientManagement1.getDiagnosisConclusion().isEmpty() ? patientManagement1.getDiagnosisConclusion() : patientManagement1.getIntelligentDiagnosis());
//                dataMap.put("heart", ecgAnalysisData.get("平均心率").toString());
//                dataMap.put("pId", patientManagement1.getpId());
//                dataMap.put("analysis", analysis);
//                dataMap.put("administrative", patientManagement1.getHospitalName());
//                dataMap.put("reportDate", sdf1.format(patientManagement1.getConnectionTime()));
//                dataMap.put("newDate", sdf.format(new Date()));
////                dataMap.put("doctor", patientManagement1.getDiagnosisDoctor() != null && !patientManagement1.getDiagnosisDoctor().isEmpty() ? aesUtils.decrypt(patientManagement1.getDiagnosisDoctor()) : "");
//                dataMap.put("pr", ecgAnalysisData.get("PR间期").toString());
//                dataMap.put("qrs", ecgAnalysisData.get("QRS波时限").toString());
//                dataMap.put("qtqtc", ecgAnalysisData.get("QT间期").toString() + "/" + ecgAnalysisData.get("QTc").toString());
//                dataMap.put("pqrst", ecgAnalysisData.get("P波时限").toString() + "/" + ecgAnalysisData.get("QRS波时限") + "/" + ecgAnalysisData.get("T波时限").toString());
//                if (is!=0){
//                    dataMap.put("RV_SV", ecgAnalysisData.get("RV5_SV1")!=null&&ecgAnalysisData.get("SV1_mv")!=null? subtractBigDecimals(ecgAnalysisData.get("RV5_SV1").toString(), ecgAnalysisData.get("SV1_mv").toString()) +"/" + ecgAnalysisData.get("SV1_mv").toString():"0/0");
//                    dataMap.put("RV+SV", ecgAnalysisData.get("RV5_SV1")!=null?ecgAnalysisData.get("RV5_SV1").toString():"0");
//                };
//                Map<String, Object> o = new HashMap<>();
//                o.put("tempPath", tempPath);
//                o.put("dataMap", dataMap);
//                outPath1 = path + new Date().getTime() + "tempfile1.pdf";
//                //生成模板1
//                byte[] pdfByTemplate = CreatePdf.createPdfByTemplate(o, tempPath, outPath1);
//                String s = MergePages(pdfByTemplate, pdf2, response, patientManagement1.getpId(), is);
//                //生成完成后删除合并的pdf，保留合并后的pdf
//                deleteFile(outPath1);
//                if (patientManagement1.getDiagnosisStatus()!=null&&patientManagement1.getDiagnosisStatus()==1){
//                    addDoctor(patientManagement1);
//                }
//                return AjaxResult.success(s);
//            }
//            return AjaxResult.error("pdf数据解析异常");
//        } catch (Exception e) {
//            throw new ServiceException("pdf数据解析失败");
//        }
//
//
//    }


    public static BigDecimal addBigDecimals(String num1, String num2) {
        // 创建 BigDecimal 对象
        BigDecimal bd1 = new BigDecimal(num1);
        BigDecimal bd2 = new BigDecimal(num2);

        // 执行加法操作
        return bd1.add(bd2);
    }

    public static BigDecimal subtractBigDecimals(String num1, String num2) {
        try {
            // 创建 BigDecimal 对象并执行减法操作
            BigDecimal bd1 = new BigDecimal(num1);
            BigDecimal bd2 = new BigDecimal(num2);
            return bd1.subtract(bd2);
        }catch (Exception e){
            return new BigDecimal("0");
        }
    }

    /**
     * 添加医生签名
     * @param patientManagement
     * @throws FileNotFoundException
     */
    public void addDoctor(PatientManagement patientManagement) throws FileNotFoundException {
        String src = "/home/chenpeng/workspace/system/xindian/uploadPath/pdf/"+patientManagement.getpId()+"_ecg.pdf";
        String dest = "/home/chenpeng/workspace/system/xindian/uploadPath/pdf/"+patientManagement.getpId()+"_md.pdf";
        try {
            PdfReader reader = new PdfReader(src);
            PdfWriter writer = new PdfWriter(dest);
            // 使用PdfDocument和Document来添加内容
            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader,writer);
            Document document = new Document(pdfDoc);
            Doctor doctor = doctorService.selectDoctorByDoctorPhone(patientManagement.getDPhone());
            //添加电子签
            if (doctor!=null&&doctor.getDzVisa() != null) {
                String wdir = "/home/chenpeng/workspace/system/xindian/uploadPath" + doctor.getDzVisa();
                ImageData imageData = ImageDataFactory.create(wdir);
                if (patientManagement.getEcgType()!=null&&patientManagement.getEcgType().contains("JECG12")){
                    Image image = new Image(imageData)
                            .setFixedPosition(698, 30)
                            .scaleToFit(100, 100)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setRotationAngle(Math.toRadians(90));
                    document.add(image);
                }else {
                    Image image = new Image(imageData)
                            .setFixedPosition(698, 60)
                            .scaleToFit(100, 100)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setRotationAngle(Math.toRadians(90));
                    document.add(image);
                }
            }
            pdfDoc.close();
            document.close();

            pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(new PdfReader(dest), new PdfWriter(src));
            pdfDoc.close();
            Files.delete(Paths.get(dest));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String updateReport(PatientManagement patientManagement1) throws IOException {

        String src = "/home/chenpeng/workspace/system/xindian/uploadPath/pdf/"+patientManagement1.getpId()+"_ecg.pdf";
        String dest = "/home/chenpeng/workspace/system/xindian/uploadPath/pdf/"+patientManagement1.getpId()+"_md.pdf";
        String dests = "/home/chenpeng/workspace/system/xindian/uploadPath/pdf/"+patientManagement1.getpId()+"_mdt.pdf";
        try  {
            PdfReader reader = new PdfReader(src);
            PdfWriter writer = new PdfWriter(dest);
            // 使用PdfDocument和Document来添加内容
            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader, writer);
            Document document = new Document(pdfDoc);
//                PdfFont font = PdfFontFactory.createFont("./ruoyi-xindian/src/main/java/com/ruoyi/xindian/pdf/utils/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
            PdfFont font = PdfFontFactory.createFont("/home/chenpeng/workspace/system/xindian/ttf/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
            // 指定要覆盖的区域
            Rectangle rect = new Rectangle(687,433, 120, 100);
//            // 设置填充颜色为白色
            canvas.setFillColor(ColorConstants.WHITE);
            canvas.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight());
            canvas.fill();

            if (patientManagement1.getDiagnosisStatus()!=null&&patientManagement1.getDiagnosisStatus()==1){
                if (patientManagement1.getEcgType()!=null&&patientManagement1.getEcgType().contains("JECG12")){
                    Rectangle rect1 = new Rectangle(75,68, 700, 20);
                    canvas.setFillColor(ColorConstants.WHITE);
                    canvas.rectangle(rect1.getLeft(), rect1.getBottom(), rect1.getWidth(), rect1.getHeight());
                    canvas.fill();
                }else {
                    Rectangle rect1 = new Rectangle(75,95, 700, 20);
                    canvas.setFillColor(ColorConstants.WHITE);
                    canvas.rectangle(rect1.getLeft(), rect1.getBottom(), rect1.getWidth(), rect1.getHeight());
                    canvas.fill();
                }
            }





            pdfDoc.close();
            document.close();

            PdfReader reader1 = new PdfReader(dest);
            PdfWriter writer1 = new PdfWriter(dests);

            com.itextpdf.kernel.pdf.PdfDocument pdfDoc1 = new com.itextpdf.kernel.pdf.PdfDocument(reader1, writer1);
            Canvas canvas1 = new Canvas(new PdfCanvas(pdfDoc1.getFirstPage()), pdfDoc, rect);
            // 设置文本内容
            String text = patientManagement1.getDiagnosisConclusion() != null && !patientManagement1.getDiagnosisConclusion().isEmpty() ? patientManagement1.getDiagnosisConclusion() : patientManagement1.getIntelligentDiagnosis();

            Text title = new Text(text).setFont(font).setFontSize(9);

            Paragraph p = new Paragraph().add(title);
            canvas1.add(p);
            canvas1.close();
            pdfDoc1.close();
            pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(new PdfReader(dests), new PdfWriter(src));
            pdfDoc.close();

            Files.delete(Paths.get(dests));
            Files.delete(Paths.get(dest));
            if (patientManagement1.getDiagnosisStatus()!=null&&patientManagement1.getDiagnosisStatus()==1){
                addDoctor(patientManagement1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String pdfName = patientManagement1.getpId() + "_ecg.pdf";
        return url + "/pdf/" + pdfName;
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
        if (type == 12||type == 4) {
            page2.getCanvas().drawTemplate(template, new Point2D.Float(60, 170));
        } else {
            page2.getCanvas().drawTemplate(template, new Point2D.Float(60, 180));
        }

        String pdfName = pId + "_ecg.pdf";
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
