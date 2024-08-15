package com.ruoyi.xindian.verify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.equipment.controller.EquipmentHeadingCodeController;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.verify.controller.SxDdReportController;
import com.ruoyi.xindian.verify.domain.SxDdReport;
import com.ruoyi.xindian.verify.service.SxDdReportService;
import com.ruoyi.xindian.verify.mapper.SxDdReportMapper;
import com.ruoyi.xindian.wx_pay.util.UniqueIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
* @author w
* @description 针对表【sx_dd_report】的数据库操作Service实现
* @createDate 2024-07-25 12:05:07
*/
@Service
public class SxDdReportServiceImpl extends ServiceImpl<SxDdReportMapper, SxDdReport>
    implements SxDdReportService{

    @Resource
    private SxDdReportMapper sxDdReportMapper;

    @Resource
    private EquipmentHeadingCodeController equipmentHeadingCodeController;
    @Value("${sx.url}")
    private String sxUrl;

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

    /**
     * 查询善行单导心电贴
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 善行单导心电贴
     */
    @Override
    public SxDdReport selectSxDdReportBySxReportId(Long sxReportId)
    {
        return sxDdReportMapper.selectSxDdReportBySxReportId(sxReportId);
    }

    /**
     * 查询善行单导心电贴列表
     *
     * @param sxDdReport 善行单导心电贴
     * @return 善行单导心电贴
     */
    @Override
    public List<SxDdReport> selectSxDdReportList(SxDdReport sxDdReport)
    {
        return sxDdReportMapper.selectSxDdReportList(sxDdReport);
    }

    /**
     * 新增善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    @Override
    public int insertSxDdReport(SxDdReport sxDdReport)
    {
        sxDdReport.setCreateTime(DateUtils.getNowDate());
        return sxDdReportMapper.insertSxDdReport(sxDdReport);
    }

    /**
     * 修改善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    @Override
    public int updateSxDdReport(SxDdReport sxDdReport)
    {
        return sxDdReportMapper.updateSxDdReport(sxDdReport);
    }

    /**
     * 批量删除善行单导心电贴
     *
     * @param sxReportIds 需要删除的善行单导心电贴主键
     * @return 结果
     */
    @Override
    public int deleteSxDdReportBySxReportIds(Long[] sxReportIds)
    {
        return sxDdReportMapper.deleteSxDdReportBySxReportIds(sxReportIds);
    }

    /**
     * 删除善行单导心电贴信息
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 结果
     */
    @Override
    public int deleteSxDdReportBySxReportId(Long sxReportId)
    {
        return sxDdReportMapper.deleteSxDdReportBySxReportId(sxReportId);
    }

    @Override
    public int getFilePath(SxDdReport sxDdReport) {

        SxDdReport sxDdReport1 = selectSxDdReportBySxReportId(sxDdReport.getSxReportId());
        if (sxDdReport1==null){
            throw new RuntimeException("未查询到该报告");
        }
        uploadSX(sxDdReport1);
        return 1;
    }



    private void  uploadSX(SxDdReport sxDdReport){


        if (!sxDdReport.getSn().isEmpty() && sxDdReport.getSn().charAt(0) == 'C'){
            try{
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");


                String pId = "SX"+ UniqueIdGenerator.generateUniqueId();
                downloadPFD(sxDdReport,pId);

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
                patientManagement.setpId(pId);
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

                sxDdReport.setPId(pId);
                updateById(sxDdReport);

            }catch (Exception e){
                System.out.println(e);
            }

        }
    }


    public void downloadPFD(SxDdReport sxDdReport,String pId) throws Exception {

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
                throw new ServiceException("报告上传失败");
            }
            // 返回成功或其他信息，这里应根据业务逻辑返回适当的 AjaxResult
        } else {
            // 处理下载失败的情况，这里可以根据实际需求进行异常处理或返回适当的信息
            throw new ServiceException("报告上传失败");
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




