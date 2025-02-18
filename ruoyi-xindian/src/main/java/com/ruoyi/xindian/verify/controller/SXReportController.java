package com.ruoyi.xindian.verify.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.FileUploadUtils;
import com.ruoyi.xindian.util.WxUtil;
import com.ruoyi.xindian.verify.domain.SxReport;
import com.ruoyi.xindian.verify.service.SxReportService;
import com.ruoyi.xindian.verify.vo.SxReportVO;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 上传报告信息
 */
@RestController
@RequestMapping("/ecg/report")
public class SXReportController {


    @Resource
    private SxReportService sxReportService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AesUtils aesUtils;


    @Resource
    private IPatientService patientService;

    @Resource
    private WXPublicRequest wxPublicRequest;


    @Resource
    private IPatientManagementService patientManagementService;

    @Resource
    private FileUploadUtils fileUploadUtils;

    @Resource
    private ISysUserService sysUserService;
    /**
     * 上传报告信息
     * @param sxReportVO
     * @return
     * @throws Exception
     */
    @PostMapping("/addReport")
    public AjaxResult getReport(@RequestBody SxReportVO sxReportVO) throws Exception{

        if (StringUtils.isAnyEmpty(sxReportVO.getSn(), sxReportVO.getOrderId(), sxReportVO.getPatientPhone(), sxReportVO.getUserId(),sxReportVO.getStartTime())){
            return AjaxResult.error("请填写完整参数");
        }

        SxReport sxReport = new SxReport();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(sxReportVO.getStartTime());
        }catch (Exception e){
            return AjaxResult.error("请填写正确的时间格式");
        }
        BeanUtils.copyProperties( sxReportVO,sxReport);
        sxReport.setCreateTime(new Date());
        if (StringUtils.isNotEmpty(sxReport.getPatientPhone())){
            sxReport.setPatientPhone(aesUtils.encrypt(sxReport.getPatientPhone()));
        }
        sxReport.setUploadStart(sxReportVO.getStartTime());
        if (sxReportService.insertSxReport(sxReport) > 0){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            CompletableFuture.runAsync(() ->{
                System.out.println("异步线程 =====> 开始推送报告生成成功消息 =====> " + new Date());
                try{
                    Patient patient = patientService.selectPatientByPatientPhone(sxReport.getPatientPhone());
//                    if (StringUtils.isNotEmpty(sxReport.getOrderId())){
//                        HttpHeaders headers = new HttpHeaders(); //构建请求头
//                        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
//                        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
//                        //封装请求头
//                        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
//                        String url = "https://api3.benefm.com/bmecg/third/report/download?orderId="+sxReport.getOrderId();
//                        ResponseEntity<byte[]> sendMessageVo=null;
//                        try {
//                            sendMessageVo = restTemplate.exchange(url, HttpMethod.GET,formEntity, byte[].class);
//                        }catch (Exception e){
//                            System.out.println(e);
//                        }
//                        String s = fileUploadUtils.uploadPDFUrl(sendMessageVo.getBody(), "sx", aesUtils.decrypt(sxReport.getPatientPhone()));
//                        if (s!=null){
//                            sxReport.setPdfUrl(s);
//                            sxReportService.updateSxReport(sxReport);
//                        }
//                    }
                    if (patient!=null){
                        if (!Boolean.TRUE.equals(redisTemplate.hasKey("SX_report:"+patient.getPatientPhone()))){
                            redisTemplate.opsForValue().set("SX_report:" + patient.getPatientPhone(), "1", 5, TimeUnit.MINUTES);
                            if (sxReport.getPatientPhone()!=null&&aesUtils.decrypt(sxReport.getPatientPhone()).length()>11){
                                sxReport.setPatientPhone(aesUtils.encrypt(aesUtils.decrypt(sxReport.getPatientPhone()).substring(0,11)));
                            }
                            SysUser sysUser = sysUserService.selectUserByPhone(sxReport.getPatientPhone());
                            if (sysUser!=null){
                                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                                String time = "";
                                try {
                                    time= simpleDate.format(simpleDate.parse(sxReportVO.getStartTime()));
                                }catch (Exception e){
                                    time = sxReportVO.getStartTime().substring(0,10);
                                }

                                wxPublicRequest.SXEquipmentMsg(sysUser.getOpenId(),aesUtils.decrypt(patient.getPatientName()),aesUtils.decrypt(sxReport.getPatientPhone()),time);
                            }
                        }
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
                System.out.println("异步线程 =====> 结束报告生成成功消息 =====> " + new Date());
            },executorService);
            executorService.shutdown(); // 回收线程池
            return AjaxResult.success("success");
        }
        return AjaxResult.error();
    }




    @GetMapping("/getSX_PDFByPId")
    public AjaxResult getReportByPId(String pId) throws Exception {

        if (StringUtils.isEmpty(pId)){
            return AjaxResult.success(null);
        }
        PatientManagement management = patientManagementService.selectPatientManagementByPId(pId);
        if (management==null){
            return AjaxResult.success(null);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SxReport sxReport = new SxReport();
            sxReport.setPatientPhone(management.getPatientPhone());
            String format = simpleDateFormat.format(management.getConnectionTime());
            sxReport.setUploadStart(format);
            List<SxReport> reportList = sxReportService.getReportList(sxReport);
            if (reportList==null|| reportList.isEmpty()){
                return AjaxResult.success(null);
            }
            SxReport sxReport1 = reportList.get(0);
            if (sxReport1==null){
                return AjaxResult.success(null);
            }
            if (StringUtils.isNotEmpty(sxReport1.getPdfUrl())){
                return AjaxResult.success(sxReport1.getPdfUrl());
            }
            String equipmentCodeAccessToken = getEquipmentCodeAccess_token();



            HttpHeaders headers = new HttpHeaders(); //构建请求头

            headers.set("authorization","Bearer "+equipmentCodeAccessToken);
            //封装请求头
            HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
            String url = "https://api3.benefm.com/bmecg/third/report/download?orderId="+sxReport1.getOrderId();
            ResponseEntity<byte[]> sendMessageVo=null;
            try {
                sendMessageVo = restTemplate.exchange(url, HttpMethod.GET,formEntity, byte[].class);
            }catch (Exception e){
                System.out.println(e);
            }
            String pdfUrl =null;
            try {
                pdfUrl = fileUploadUtils.uploadPDFUrl(sendMessageVo.getBody(), "sx", aesUtils.decrypt(sxReport1.getPatientPhone()));
            }catch (Exception e){
                return AjaxResult.success(null);
            }
            if (pdfUrl!=null){
                sxReport1.setPdfUrl(pdfUrl);
                sxReportService.updateSxReport(sxReport1);
                return AjaxResult.success(pdfUrl);
            }
            return AjaxResult.success(null);
        }catch (Exception e){
            return AjaxResult.success(null);
        }
    }


    @GetMapping("/downloadPFD")
    public AjaxResult downloadPFD(SxReport sxReport) throws Exception {

        if (StringUtils.isEmpty(sxReport.getPatientPhone())){
            return AjaxResult.error("请填写完整参数");
        }
        sxReport.setPatientPhone(aesUtils.encrypt(sxReport.getPatientPhone()));

        List<SxReport> reportList = sxReportService.getReportList(sxReport);

        if (reportList.isEmpty()){
            return AjaxResult.error("没有找到该报告");
        }
        SxReport sxReport1 = reportList.get(0);
        if (sxReport1==null){
            return AjaxResult.error("没有找到该报告");
        }
        if (StringUtils.isNotEmpty(sxReport1.getPdfUrl())){
            return AjaxResult.success(sxReport1.getPdfUrl());
        }

        HttpHeaders headers = new HttpHeaders(); //构建请求头
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
        String url = "https://api3.benefm.com/bmecg/third/report/download?orderId="+sxReport1.getOrderId();
        ResponseEntity<byte[]> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.exchange(url, HttpMethod.GET,formEntity, byte[].class);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println(sendMessageVo.getBody().length);
        if (sendMessageVo.getBody().length<500){
            redisTemplate.delete("EquipmentCodeAccess_token");
            HttpHeaders headers1 = new HttpHeaders(); //构建请求头
            String equipmentCodeAccessToken1 = getEquipmentCodeAccess_token();
            headers1.set("authorization","Bearer "+equipmentCodeAccessToken1);
            //封装请求头
            HttpEntity<MultiValueMap<String, Object>> formEntity1 = new HttpEntity<MultiValueMap<String, Object>>(headers1);
            String url1 = "https://api3.benefm.com/bmecg/third/report/download?orderId="+sxReport1.getOrderId();
            try {
                sendMessageVo = restTemplate.exchange(url1, HttpMethod.GET,formEntity1, byte[].class);
            }catch (Exception e){
                System.out.println(e);
            }
        }
        String pdfUrl =null;
        try {
            pdfUrl = fileUploadUtils.uploadPDFUrl(sendMessageVo.getBody(), "sx", aesUtils.decrypt(sxReport1.getPatientPhone()));
        }catch (Exception e){
            return AjaxResult.error("报告下载错误");
        }

        if (pdfUrl!=null){
            sxReport1.setPdfUrl(pdfUrl);
            sxReportService.updateSxReport(sxReport1);
            return AjaxResult.success(pdfUrl);
        }
        return AjaxResult.error("没有找到该报告");
    }





    private String getEquipmentCodeAccess_token() throws Exception {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("EquipmentCodeAccess_token"))){
            return redisTemplate.opsForValue().get("EquipmentCodeAccess_token");
        }
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("password","PSEIF363T" );
        paramsMap.put("appKey","s_zwecg" );
        paramsMap.put("uid","80000261" );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);

        String url = "https://api3.benefm.com/p/thirdLogin/"+"ZZDX";
        HashMap<String,Map<String,Object>> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        }catch (Exception e){
            System.out.println(e);
        }
        Map<String,Object> resultData = sendMessageVo.get("resultData");
        if (resultData!=null){
            String accessToken = resultData.get("access_token").toString();
            redisTemplate.opsForValue().set("EquipmentCodeAccess_token",accessToken,20, TimeUnit.MINUTES);
            return accessToken;
        }
        return null;
    }


}
