package com.ruoyi.xindian.verify.controller;
import com.google.common.collect.Lists;

import java.nio.file.Files;
import java.util.HashMap;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.equipment.controller.EquipmentHeadingCodeController;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.verify.domain.SxDdReport;
import com.ruoyi.xindian.verify.domain.SxReport;
import com.ruoyi.xindian.verify.service.SxDdReportService;
import com.ruoyi.xindian.verify.vo.SxDdReportVO;
import com.ruoyi.xindian.verify.vo.SxReportVO;
import com.ruoyi.xindian.wx_pay.util.UniqueIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/ecg/report")
public class SxDdReportController {


    private static final Logger log = LoggerFactory.getLogger(SxDdReportController.class);
    @Value("${sx.url}")
    private String sxUrl;

    @Value("${ruoyi.profile}")
    private String uploadPath;

    @Value("${ruoyi.url}")
    private String urlPath;

    @Resource
    private SxDdReportService sxDdReportService;

    @Resource
    private EquipmentHeadingCodeController equipmentHeadingCodeController;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private IPatientService patientService;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private IPatientManagementService patientManagementService;

    @PostMapping("/addDdReport")
    public AjaxResult getReport(@Validated @RequestBody SxDdReportVO sxReportVO) throws Exception{

        SxDdReport sxDdReport = new SxDdReport();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(sxReportVO.getUploadStart());
        }catch (Exception e){
            return AjaxResult.error("请填写正确的时间格式");
        }
        BeanUtils.copyProperties( sxReportVO,sxDdReport);
        sxDdReport.setCreateTime(new Date());

        sxDdReportService.save(sxDdReport);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.runAsync(() ->{
            System.out.println("异步线程 =====> 开始上传善行心电贴数据 =====> " + new Date());
            try{
                uploadSX(sxDdReport);
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束上传善行心电贴数据 =====> " + new Date());
        },executorService);
        executorService.shutdown(); // 回收线程池



        return AjaxResult.success();
    }


    private void  uploadSX(SxDdReport sxDdReport){


        if (!sxDdReport.getSn().isEmpty() && sxDdReport.getSn().charAt(0) == 'C'){
                try{
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

                    Date parse = simpleDateFormat1.parse(sxDdReport.getBirthday());

                    String encrypt = aesUtils.encrypt(sxDdReport.getPhone());
                    String encrypt1 = aesUtils.encrypt(sxDdReport.getName());
                    Patient patient = patientService.selectPatientByPatientPhone(encrypt);
                    if (patient==null){
                        Patient patient1 = new Patient();
                        patient1.setBirthDay(parse);
                        patient1.setPatientName(encrypt1);
                        patient1.setPatientAge(DateUtil.getAge(parse)+"");
                        patient1.setPatientSex(sxDdReport.getSex());
                        patient1.setPatientPhone(encrypt);
                        patient1.setMonitoringStatus("0");
                        patientService.insertPatient(patient1);
                    }

                    PatientManagement patientManagement = new PatientManagement();
                    patientManagement.setpId("SX"+ UniqueIdGenerator.generateUniqueId());
                    patientManagement.setHospitalCode("29");
                    patientManagement.setEquipmentCode(sxDdReport.getSn());

                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                    patientManagement.setConnectionTime(simpleDateFormat2.parse(sxDdReport.getGatherStartTime()));
                    patientManagement.setBedNumber("0");
                    patientManagement.setCaseHistoryNumber("0");
                    patientManagement.setEcgType("DECGsingleSX");
                    patientManagement.setOnlineStatus("0");
                    patientManagement.setEcgLevel(2L);
                    patientManagement.setSamplingRate(250L);
                    patientManagement.setDetectionState("0");
                    patientManagement.setReportState("0");
                    patientManagement.setPatientPhone(encrypt);
                    patientManagementService.insertPatientManagement(patientManagement);

                    downloadPFD(sxDdReport,patientManagement.getpId());
                }catch (Exception e){
                    System.out.println(e);
                }

        }
    }


    public void downloadPFD(SxDdReport sxDdReport,String pId)  {
        try {
            HttpHeaders headers = new HttpHeaders(); //构建请求头
            String equipmentCodeAccessToken = equipmentHeadingCodeController.getEquipmentCodeAccess_token();
            headers.set("authorization","Bearer "+equipmentCodeAccessToken);
            //封装请求头
            HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
            String url = sxUrl+"/bmecg/third/ecg/zzdx/data/download?userId="+sxDdReport.getUserId()+"&fileName="+sxDdReport.getFileName()+"&sn="+sxDdReport.getSn();
            ResponseEntity<byte[]> response=null;
            try {
                response = restTemplate.exchange(url, HttpMethod.GET,formEntity, byte[].class);
            }catch (Exception e){
                redisTemplate.delete("EquipmentCodeAccess_token");
                System.out.println(e);
            }
            System.out.println("开始解压文件");

            if (response != null && response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // 保存下载的 ZIP 文件到临时文件
                File zipFile = File.createTempFile("temp", ".zip");
                try (FileOutputStream fos = new FileOutputStream(zipFile)) {
                    fos.write(response.getBody());
                }

                // 解压缩 ZIP 文件到指定目录
                String extractPath = "/repository/DECG_single/save/"; // 指定解压目录
                File extractDir = new File(extractPath);
                if (!extractDir.exists()) {
                    boolean created = extractDir.mkdirs(); // 创建目录及其父目录
                    if (created) {
                        System.out.println("目录已成功创建：" + extractDir.getAbsolutePath());
                    } else {
                        System.out.println("创建目录失败：" + extractPath);
                    }
                }
                System.out.println("目录已存在：" + extractDir.getAbsolutePath());
                extractZip(zipFile, extractPath,pId+".dat");
                // 删除临时 ZIP 文件
                if (zipFile.exists()) {
                    zipFile.delete();
                }


                // 检查文件是否成功上传
                boolean isSuccessful = isUploadSuccessful(extractDir, pId+".dat");
                if (isSuccessful) {
                    System.out.println("文件上传成功！");
                } else {
                    System.out.println("文件上传失败！");
                }
                // 返回成功或其他信息，这里应根据业务逻辑返回适当的 AjaxResult
            } else {
                // 处理下载失败的情况，这里可以根据实际需求进行异常处理或返回适当的信息
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean isUploadSuccessful(File extractDir, String expectedFileName) {
        File expectedFile = new File(extractDir, expectedFileName);
        return expectedFile.exists() && !expectedFile.isDirectory();
    }


    private void extractZip(File zipFile, String extractPath,String fileName) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile.toPath()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File entryFile = new File(extractPath, fileName);
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    try (FileOutputStream fos = new FileOutputStream(entryFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }



}
