package com.ruoyi.xindian.report.config;

import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.xindian.statistics.domain.DiagnoseDoc;
import com.ruoyi.xindian.statistics.service.DiagnoseDocService;
import com.ruoyi.xindian.util.ReportUtil;
import com.ruoyi.xindian.util.StrUtil;
import com.ruoyi.xindian.util.WxUtil;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class WxMsgRunConfig {



    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private IPatientManagementService patientManagementService;

    @Autowired
    private IDoctorService doctorService;


    @Autowired
    private WXPublicRequest wxPublicRequest;

    @Autowired
    private IReportService reportService;

    @Resource
    private DiagnoseDocService diagnoseDocService;

    @Autowired
    private AesUtils aesUtils;

    private final LocalTime start = LocalTime.of(8, 0); // 8:00 AM
    private final LocalTime end = LocalTime.of(18, 0); // 6:00 PM


    /**
     * 患者提交心电图，加入抢单模式
     * @param pid
     * @param doctorList
     */
    public void redisAdd(String pid, List<Doctor> doctorList){
        LocalTime now = LocalTime.now();

        redisTemplate.opsForList().leftPushAll("DocList"+pid,doctorList);
        if (now.isAfter(start) && now.isBefore(end)) {

            redisTemplate.opsForValue().set("reportPT:"+pid,pid,10, TimeUnit.MINUTES);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            CompletableFuture.runAsync(() ->{
                System.out.println("异步线程 =====> 开始推送公众号消息 =====> " + new Date());
                try{
                    wxPublicRequest.dockerMsg();
                }catch (Exception e){
                    System.out.println(e);
                }
                System.out.println("异步线程 =====> 结束推送公众号消息 =====> " + new Date());
            },executorService);
            executorService.shutdown(); // 回收线程池
        }else {
            LocalDateTime nowTime = LocalDateTime.now();
            LocalDateTime tomorrow8AM = LocalDateTime.of(nowTime.toLocalDate().plusDays(1), nowTime.toLocalTime().withHour(8));
            long minutes = nowTime.until(tomorrow8AM, ChronoUnit.MINUTES);
            Random random = new Random();
            int randomNumber = random.nextInt(31);
            redisTemplate.opsForValue().set("reportPT:"+pid,pid,minutes+randomNumber, TimeUnit.MINUTES);
        }

    }

    /**
     * 判断当前患者是否已经被抢单
     * @param pId
     */
    public void redisTOrAdd(String pId) throws Exception {
        Report report2 = reportService.selectReportByPId(pId);
        LocalTime now = LocalTime.now();

        if (report2.getDiagnosisStatus()!=2){
            redisTemplate.delete("DocList"+pId);
            redisTemplate.delete("reportPT:"+pId);
            System.out.println("订单已诊断");
        }else {
            if (report2.getdPhone()!=null&&!"".equals(report2.getdPhone())){
//            redisTemplate.opsForValue().set("reportDT:"+pId,pId,30, TimeUnit.MINUTES);
                System.out.println("订单已被抢走");
            }else {
                if (now.isAfter(start) && now.isBefore(end)) {

                    DiagnoseDoc diagnoseDoc = new DiagnoseDoc();
                    diagnoseDoc.setReportId(report2.getReportId());


                    if (!Boolean.TRUE.equals(redisTemplate.hasKey("DocList"+pId))){
                        List<Doctor> doctors = doctorService.selectIsDoc();
                        int rand = StrUtil.randomInt(doctors.size());
                        Doctor doctor = doctors.get(rand);
                        String dPhone= doctor.getDoctorPhone();
                        report2.setDiagnosisDoctorAes(aesUtils.decrypt(doctor.getDoctorName()));
                        report2.setDPhoneAes(aesUtils.decrypt(doctor.getDoctorPhone()));
                        report2.setdPhone(dPhone);
                        diagnoseDoc.setDoctorPhone(dPhone);
                        report2.setReportTime(new Date());
                        report2.setStartTime(new Date());
                        report2.setDiagnosisDoctor(doctor.getDoctorName());
                        WxUtil.send(aesUtils.decrypt(dPhone));
                        redisTemplate.opsForList().leftPushAll("DocList"+pId,doctors);
                    }else {
                        List<Object> doctors = redisTemplate.opsForList().range("DocList"+pId, 0, -1);
                        if (doctors!=null&&doctors.size()>0){
                            List<Doctor> doctorList = new ArrayList<>();
                            for (Object c : doctors){
                                doctorList.addAll((List<Doctor>) c);
                            }
                            int rand = StrUtil.randomInt(doctorList.size());
                            Doctor doctor =doctorList.get(rand);
                            String dPhone= doctor.getDoctorPhone();
                            report2.setdPhone(dPhone);
                            diagnoseDoc.setDoctorPhone(dPhone);
                            report2.setDiagnosisDoctorAes(aesUtils.decrypt(doctor.getDoctorName()));
                            report2.setDPhoneAes(aesUtils.decrypt(doctor.getDoctorPhone()));
                            report2.setReportTime(new Date());
                            report2.setStartTime(new Date());
                            report2.setDiagnosisDoctor(doctor.getDoctorName());
                            WxUtil.send(aesUtils.decrypt(dPhone));
                        }
                    }

                    diagnoseDoc.setDiagnoseType("2");
                    diagnoseDocService.insertDiagnose(diagnoseDoc);

                    reportService.updateReport(report2);
                    redisTemplate.opsForValue().set("reportDT:"+pId,pId,30, TimeUnit.MINUTES);
                }else {
                    LocalDateTime nowTime = LocalDateTime.now();
                    LocalDateTime tomorrow8AM = LocalDateTime.of(nowTime.toLocalDate().plusDays(1), nowTime.toLocalTime().withHour(8));
                    long minutes = nowTime.until(tomorrow8AM, ChronoUnit.MINUTES);
                    Random random = new Random();
                    int randomNumber = random.nextInt(31);
                    redisTemplate.opsForValue().set("reportPT:"+pId,pId,minutes+randomNumber, TimeUnit.MINUTES);
//                redisTemplate.opsForValue().set("reportPT:"+pId,pId,1, TimeUnit.MINUTES);
                }
            }
        }

    }

    /**
     * 判断当前患者是否已经诊断完，没有的话，换时间
     * @param pId
     */
    public void redisDTTime(String pId) throws Exception {
        Report report2 = reportService.selectReportByPId(pId);
        LocalTime now = LocalTime.now();

        if (report2.getDiagnosisStatus()!=2){
            redisTemplate.delete("DocList"+pId);
        }else {
            report2.setReportTime(new Date());
            report2.setStartTime(new Date());
            if (now.isAfter(start) && now.isBefore(end)) {

                DiagnoseDoc diagnoseDoc1 = new DiagnoseDoc();
                diagnoseDoc1.setReportId(report2.getReportId());
                diagnoseDoc1.setDoctorPhone(report2.getdPhone());
                diagnoseDoc1.setDiagnoseStatus("2");
                diagnoseDocService.updateDiagnose(diagnoseDoc1);

                DiagnoseDoc diagnoseDoc = new DiagnoseDoc();
                diagnoseDoc.setReportId(report2.getReportId());
                if (!Boolean.TRUE.equals(redisTemplate.hasKey("DocList"+pId))){
                    Doctor doctor2 = doctorService.selectDoctorByDoctorPhone(report2.getdPhone());
                    Doctor doctor1 = new Doctor();
                    doctor1.getHospitalNameList().add(doctor2.getHospital());
                    List<Doctor> doctors = doctorService.selectUserDoc(doctor1);
                    int rand = StrUtil.randomInt(doctors.size());
                    Doctor doctor = doctors.get(rand);
                    String dPhone= doctor.getDoctorPhone();
                    WxUtil.send(aesUtils.decrypt(dPhone));
                    report2.setdPhone(dPhone);
                    diagnoseDoc.setDoctorPhone(dPhone);
                    report2.setDiagnosisDoctorAes(aesUtils.decrypt(doctor.getDoctorName()));
                    report2.setDPhoneAes(aesUtils.decrypt(doctor.getDoctorPhone()));
                    report2.setDiagnosisDoctor(doctor.getDoctorName());
                    redisTemplate.opsForList().leftPushAll("DocList"+pId,doctors);
                }else {
                    List<Object> doctors = redisTemplate.opsForList().range("DocList"+pId, 0, -1);
                    if (doctors!=null&&doctors.size()>0){
                        List<Doctor> doctorList = new ArrayList<>();
                        for (Object c : doctors){
                            doctorList.addAll((List<Doctor>) c);
                        }
                        int rand = StrUtil.randomInt(doctorList.size());
                        Doctor doctor =doctorList.get(rand);
                        String dPhone= doctor.getDoctorPhone();
                        report2.setdPhone(dPhone);
                        diagnoseDoc.setDoctorPhone(dPhone);
                        report2.setDiagnosisDoctorAes(aesUtils.decrypt(doctor.getDoctorName()));
                        report2.setDPhoneAes(aesUtils.decrypt(doctor.getDoctorPhone()));
                        report2.setDiagnosisDoctor(doctor.getDoctorName());
                        WxUtil.send(aesUtils.decrypt(dPhone));
                    }
                }
                diagnoseDoc.setDiagnoseType("2");
                diagnoseDocService.insertDiagnose(diagnoseDoc);

            }else {
                //如果半夜提交报告，则直接延迟到第二天
                LocalDateTime nowTime = LocalDateTime.now();
                LocalDateTime tomorrow8AM = LocalDateTime.of(nowTime.toLocalDate().plusDays(1), nowTime.toLocalTime().withHour(8));
                long minutes = nowTime.until(tomorrow8AM, ChronoUnit.MINUTES);
                Random random = new Random();
                int randomNumber = random.nextInt(31);
                redisTemplate.opsForValue().set("reportDT:"+pId,pId,minutes+randomNumber, TimeUnit.MINUTES);
            }
            reportService.updateReport(report2);

        }

    }


    /**
     * 抢单成功，加30分钟
     * @param pId
     * @param doctorList
     */
    public void redisDTStart(String pId, List<Doctor> doctorList){
        redisTemplate.delete("DocList"+pId);
        List<Doctor> doctors = new ArrayList<>(doctorList);
        redisTemplate.opsForList().leftPushAll("DocList"+pId,doctors);
        redisTemplate.opsForValue().set("reportDT:"+pId,pId,30, TimeUnit.MINUTES);
    }


    /**
     * 项目启动后，运行，判断项目断开期间是否有抢单的订单未被医生抢到
     */
    public void reportItemT() throws Exception {

        List<PatientManagement> patientManagements = patientManagementService.selectPatientManagementList();
        List<PatientManagement> patientManagements1 = patientManagementService.selectPatientManagementList1();

        if (patientManagements!=null&&patientManagements.size()>0){

            for (PatientManagement c:patientManagements){

                Report report = reportService.selectReportByPId(c.getpId());
                LocalTime now = LocalTime.now();
                if (now.isAfter(start) && now.isBefore(end)) {

                    if (!Boolean.TRUE.equals(redisTemplate.hasKey("DocList"+c.getpId()))){
                        List<Doctor> doctors = doctorService.selectIsDoc();
                        int rand = StrUtil.randomInt(doctors.size());
                        Doctor doctor =doctors.get(rand);
                        String dPhone= doctor.getDoctorPhone();
                        report.setdPhone(dPhone);
                        report.setDiagnosisDoctor(doctor.getDoctorName());
                        WxUtil.send(aesUtils.decrypt(dPhone));
                        redisTemplate.opsForList().leftPushAll("DocList"+c.getpId(),doctors);
                    }else {
                        List<Object> doctors = redisTemplate.opsForList().range("DocList"+c.getpId(), 0, -1);
                        if (doctors!=null&&doctors.size()>0){
                            List<Doctor> doctorList = new ArrayList<>();
                            for (Object b : doctors){
                                doctorList.addAll((List<Doctor>) b);
                            }
                            int rand = StrUtil.randomInt(doctorList.size());
                            Doctor doctor =doctorList.get(rand);
                            String dPhone= doctor.getDoctorPhone();
                            report.setdPhone(dPhone);
                            report.setDiagnosisDoctor(doctor.getDoctorName());
                            WxUtil.send(aesUtils.decrypt(dPhone));
                        }

                    }

                    reportService.updateReport(report);
                    redisTemplate.opsForValue().set("reportDT:"+c.getpId(),c.getpId(),30, TimeUnit.MINUTES);
                }else {
                    redisTemplate.opsForValue().set("reportPT:"+c.getpId(),c.getpId(),1, TimeUnit.HOURS);
//                    redisTemplate.opsForValue().set("reportPT:"+c.getpId(),c.getpId(),1, TimeUnit.MINUTES);
                }

            }


        }

        if (patientManagements1!=null&&patientManagements1.size()>0){
            for (PatientManagement c:patientManagements1){
                Report report = reportService.selectReportByPId("DocList"+c.getpId());
                if (Boolean.TRUE.equals(redisTemplate.hasKey("reportDT"+report.getpId()))){
                    redisTemplate.delete("reportDT"+report.getpId());
                }
                redisTemplate.opsForValue().set("reportDT:"+c.getpId(),c.getpId(),30, TimeUnit.MINUTES);

            }


        }

    }


}
