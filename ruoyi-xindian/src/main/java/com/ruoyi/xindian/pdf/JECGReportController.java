package com.ruoyi.xindian.pdf;

import com.itextpdf.text.Rectangle;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.pdf.utils.CreatePdf;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.xindian.util.DateUtil;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfBlendMode;
import com.spire.pdf.graphics.PdfTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdf/jecgReport")
public class JECGReportController {

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


    @PostMapping("/getPdf")
    public AjaxResult getPdf(@RequestBody PatientManagement patientManagement, HttpServletResponse response) throws Exception {

        PatientManagement patientManagement1 = patientManagementService.selectPatientManagementByPId(patientManagement.getpId());
        if (patientManagement1==null){
            throw new ServiceException("数据不存在");
        }
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pid",patientManagement1.getpId());
        String substring = patientManagement1.getEcgType().substring(0, 5);
        String url = "";
        String analysis = "";
        int is = 0;
        if (substring.equals("JECGs")){
            url = "https://screen.mindyard.cn:84/get_jecg_report";
            analysis = "静态30秒II导联+解析结果";
        }else {
            if (substring.equals("JECG1")){
                paramsMap.put("ecgType","12");
                analysis = "12导联+解析结果";
                is=12;
            }else {
                paramsMap.put("ecgType","4");
                analysis = "4导联+解析结果";
            }
            url = "https://screen.mindyard.cn:84/get_jecg_report_12";
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");



        HashMap<String,Map<String,Object>> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        }catch (Exception e){
            throw new ServiceException("pdf生成失败");
        }
        try {
            if(sendMessageVo!=null){
                Map<String, Object> result = sendMessageVo.get("result");
                Object reportDataPdf = result.get("reportDataPdf");
                Map<String, Object> ecgAnalysisData =(Map<String, Object>) result.get("ecg_analysis_data");
                // 使用Base64进行解码
                byte[] decodedData = Base64.getDecoder().decode(reportDataPdf.toString());
                String tempPath = "";
                if (is==12){
                    tempPath = "/template/jecgReport_12.pdf";
                }else {
                    tempPath = "/template/jecgReport.pdf";
                }

                PdfDocument pdf2 = new PdfDocument();
                pdf2.loadFromBytes(decodedData);
                String outPath1 = "";
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("name", patientManagement1.getPatientName()!=null&& !patientManagement1.getPatientName().isEmpty() ?aesUtils.decrypt(patientManagement1.getPatientName()):"");
                dataMap.put("age", patientManagement1.getBirthDay()!=null&& !patientManagement1.getBirthDay().isEmpty() ? DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(patientManagement1.getBirthDay()))+"" :"0");
                dataMap.put("sex", patientManagement1.getPatientSex());
                dataMap.put("conclusion", patientManagement1.getIntelligentDiagnosis()!=null&& !patientManagement1.getIntelligentDiagnosis().isEmpty() ?patientManagement1.getIntelligentDiagnosis():patientManagement1.getDiagnosisConclusion());
                dataMap.put("heart", ecgAnalysisData.get("平均心率").toString());
                dataMap.put("pId", patientManagement1.getpId());
                dataMap.put("analysis", analysis);
                dataMap.put("reportDate", sdf1.format(patientManagement1.getConnectionTime()));
                dataMap.put("newDate", sdf.format(new Date()));
                dataMap.put("doctor", patientManagement1.getDiagnosisDoctor()!=null&& !patientManagement1.getDiagnosisDoctor().isEmpty() ?aesUtils.decrypt(patientManagement1.getDiagnosisDoctor()):"");
                dataMap.put("pr", ecgAnalysisData.get("PR间期").toString());
                dataMap.put("qrs", ecgAnalysisData.get("QRS波时限").toString());
                dataMap.put("qtqtc", ecgAnalysisData.get("QT间期").toString()+"/"+ecgAnalysisData.get("QTc").toString());
                dataMap.put("pqrst", ecgAnalysisData.get("P波时限").toString()+"/"+ecgAnalysisData.get("QRS波时限")+"/"+ecgAnalysisData.get("T波时限").toString());
//            dataMap.put("rv5sv1", ecgAnalysisData.get("P波时限").toString()+"/"+ecgAnalysisData.get("QRS波时限")+"/"+ecgAnalysisData.get("T波时限").toString());
                Map<String, Object> o = new HashMap<>();
                o.put("tempPath", tempPath);
                o.put("dataMap", dataMap);
                outPath1 = path+new Date().getTime()+"tempfile1.pdf";
                //生成模板1
                byte[] pdfByTemplate = CreatePdf.createPdfByTemplate(o, tempPath, outPath1);
                String s = MergePages(pdfByTemplate, pdf2, response, patientManagement1.getpId(), is);
                //生成完成后删除合并的pdf，保留合并后的pdf
                deleteFile(outPath1);
                return AjaxResult.success(s);
            }
            return AjaxResult.error("pdf数据解析异常");
        }catch (Exception e){
            throw new ServiceException("pdf数据解析失败");
        }


    }

    /**
     * 删除指定文件
     * @throws IOException
     */
    public void deleteFile(String pathName1) throws IOException {
        Path path1 = Paths.get(pathName1);
        boolean result1 = Files.deleteIfExists(path1);
        System.out.println(result1 );
    }
    /*
     *  合并pdf
     * */
    public String MergePages( byte[] pdfByTemplate,PdfDocument pdf1 ,HttpServletResponse response, String pId,Integer type) throws IOException {
        PdfDocument pdf2 = new PdfDocument();
        pdf2.loadFromBytes(pdfByTemplate);

        //根据pdf1的第一页绘制模板
        PdfTemplate template = pdf1.getPages().get(0).createTemplate();


        //获取pdf2的第一页
        PdfPageBase page2 = pdf2.getPages().get(0);
        //设置透明度和重叠样式
        page2.getCanvas().setTransparency(1f,1f, PdfBlendMode.Normal);
        //将pdf1的第一页内容写入pdf2的第二页中的指定位置
        if (type==12){
            page2.getCanvas().drawTemplate(template,new Point2D.Float(60,175));
        }else {
            page2.getCanvas().drawTemplate(template,new Point2D.Float(60,200));
        }

        String pdfName = pId+".pdf";
        String pdfPath = path+"pdf/"+pdfName;
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

        return url+"/pdf/"+pdfName;
    }



}
