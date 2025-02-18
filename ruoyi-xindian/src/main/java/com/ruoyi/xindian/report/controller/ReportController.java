package com.ruoyi.xindian.report.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.dataLabby.domain.dataLabby;
import com.ruoyi.xindian.dataLabby.service.IDataLabbyService;
import com.ruoyi.xindian.detection.domain.Detection;
import com.ruoyi.xindian.detection.service.IDetectionService;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.fw_log.mapper.FwLogMapper;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.DoctorTerm;
import com.ruoyi.xindian.hospital.service.DoctorTermService;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.medical.domain.MedicalData;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalDataService;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient.service.PatientRelationDocService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.patient_management.vo.PInfoVO;
import com.ruoyi.xindian.relationship.mapper.PatientRelationshipMapper;
import com.ruoyi.xindian.report.config.WxMsgRunConfig;
import com.ruoyi.xindian.report.domain.NotDealWith;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.domain.ReportM;
import com.ruoyi.xindian.report.service.INotDealWithService;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.xindian.statistics.domain.DiagnoseDoc;
import com.ruoyi.xindian.statistics.service.DiagnoseDocService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.util.WxUtil;
import com.ruoyi.xindian.vipPatient.controller.VipPatientController;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.weekReport.domain.WeekReport;
import com.ruoyi.xindian.weekReport.service.IWeekReportService;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 报告Controller
 *
 * @author hanhan
 * @date 2022-10-05
 */
@RestController
@RequestMapping("/report/report")
public class ReportController extends BaseController {


    @Resource
    private VipPatientController vipPatientController;


    @Resource
    private DoctorTermService doctorTermService;

    @Resource
    private PatientRelationshipMapper patientRelationshipMapper;

    @Autowired
    private WXPublicRequest wxPublicRequest;

    @Autowired
    private IReportService reportService;
    @Autowired
    private IPatientService patientService;

    @Autowired
    private IPatientManagementService patientManagementService;

    @Autowired
    private IMedicalHistoryService medicalHistoryService;

    @Autowired
    private IMedicalDataService medicalDataService;

    @Autowired
    private INotDealWithService notDealWithService;

    @Autowired
    private IDataLabbyService dataLabbyService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IDetectionService detectionService;


    @Resource
    private TokenService tokenService;

    @Autowired
    private AesUtils aesUtils;
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private IVipPatientService vipPatientService;

    @Resource
    private WxMsgRunConfig wxMsgRunConfig;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Resource
    private DiagnoseDocService diagnoseDocService;

    @Resource
    private FwLogMapper fwLogMapper;

    @Resource
    private PatientRelationDocService patientRelationDocService;

    @Resource
    private IWeekReportService weekReportService;

    /**
     * 查询报告列表
     */
//    @PreAuthorize("@ss.hasPermi('report:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(Report report) throws Exception {
        int isDoctor = 0;
        if (report.getdPhone() != null && !"".equals(report.getdPhone())) {
            report.setdPhone(aesUtils.encrypt(report.getdPhone()));
            isDoctor = 1;
        }
        if (report.getPPhone() != null && !"".equals(report.getPPhone())) {
            report.setPPhone(aesUtils.encrypt(report.getPPhone()));
        }
        List<Report> list;
        startPage();
        list = reportService.selectReportList(report);

        ArrayList<ReportM> resList = new ArrayList<>();

        PatientManagement patientManagement;
        Patient patient;
        Date birthDay;
        MedicalHistory medicalHistory;
        MedicalData medicalData = new MedicalData();
        //病种
        ArrayList<String> medical;
        //病种和id映射
        HashMap<String, String> medicalHashMap = new HashMap<>();
        List<MedicalData> medicalData1 = medicalDataService.selectMedicalDataList(medicalData);
        for (MedicalData data : medicalData1) {
            medicalHashMap.put(data.getMedicalCode().toString(), data.getMedicalName());
        }
        ReportM reportM;
        for (Report r : list) {
            medical = new ArrayList<>();
            reportM = new ReportM();
            patientManagement = patientManagementService.selectPatientManagementByPId(r.getpId());
            if (r.getPPhone() != null && !"".equals(r.getPPhone())) {
                r.setPPhone(aesUtils.decrypt(r.getPPhone()));
            }
            if (r.getdPhone() != null && !"".equals(r.getdPhone())) {
                r.setdPhone(aesUtils.decrypt(r.getdPhone()));
            }
            if (r.getDiagnosisDoctor() != null && !"".equals(r.getDiagnosisDoctor())) {
                r.setDiagnosisDoctor(aesUtils.decrypt(r.getDiagnosisDoctor()));
            }
            BeanUtils.copyProperties(r, reportM);
            if (patientManagement != null) {
                medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(patientManagement.getPatientPhone());
                if (medicalHistory != null && medicalHistory.getPastMedicalHistory() != null) {
                    String[] split = medicalHistory.getPastMedicalHistory().split(",");
                    for (String s : split) {
                        if (medicalHashMap.get(s) == null) {
                            medical.add("无");
                            break;
                        }
                        medical.add(medicalHashMap.get(s));
                    }
                    reportM.setMedicalHistory(medical);
                } else {
                    medical.add("无");
                    reportM.setMedicalHistory(medical);
                }

                patient = patientService.selectPatientByPatientPhone(patientManagement.getPatientPhone());
                if (patientManagement.getPatientPhone() != null && !"".equals(patientManagement.getPatientPhone())) {
                    patientManagement.setPatientPhone(aesUtils.decrypt(patientManagement.getPatientPhone()));
                }
                if (patientManagement.getPatientName() != null && !"".equals(patientManagement.getPatientName())) {
                    patientManagement.setPatientName(aesUtils.decrypt(patientManagement.getPatientName()));
                }

                if (patientManagement.getDiagnosisDoctor() != null && !"".equals(patientManagement.getDiagnosisDoctor())) {
                    patientManagement.setDiagnosisDoctor(aesUtils.decrypt(patientManagement.getDiagnosisDoctor()));
                }
                if (patientManagement.getDoctorPhone() != null && !"".equals(patientManagement.getDoctorPhone())) {
                    patientManagement.setDoctorPhone(aesUtils.decrypt(patientManagement.getDoctorPhone()));
                }
                birthDay = patient.getBirthDay();
                if (birthDay != null)
                    reportM.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
                else {
                    reportM.setPatientAge(patient.getPatientAge());
                }
                reportM.setPatientPhone(patientManagement.getPatientPhone());
                reportM.setPatientName(aesUtils.decrypt(patient.getPatientName()));
                reportM.setPatientSex(patient.getPatientSex());
            }
            reportM.setWeekReport(false);
            resList.add(reportM);
        }
        if (isDoctor == 1) {
            WeekReport weekReport = new WeekReport();
            weekReport.setDoctorPhone(aesUtils.decrypt(report.getdPhone()));
            if (report.getPPhone() != null && !"".equals(report.getPPhone())) {
                weekReport.setPatientPhone(aesUtils.decrypt(report.getPPhone()));
            }
            weekReport.setDiagnosisStatus(Math.toIntExact(report.getDiagnosisStatus()));
            //未诊断才返回周报数据
            if (weekReport.getDiagnosisStatus() != 1) {
                List<WeekReport> weekReports = weekReportService.selectWeekReportList(weekReport);
                for (WeekReport wr : weekReports) {
                    reportM = new ReportM();
                    patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(wr.getPatientPhone()));

                    reportM.setPPhone(wr.getPatientPhone());
                    reportM.setdPhone(wr.getDoctorPhone());
                    reportM.setDiagnosisStatus(report.getDiagnosisStatus());
                    reportM.setDiagnosisConclusion(wr.getDiagnosisConclusion());
                    reportM.setReportTime(wr.getWeekpdftime());
                    reportM.setWeekReport(true);
                    reportM.setpId(wr.getWeekid());
                    reportM.setPatientName(aesUtils.decrypt(patient.getPatientName()));
                    reportM.setPatientSex(patient.getPatientSex());
                    birthDay = patient.getBirthDay();
                    if (birthDay != null)
                        reportM.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
                    else {
                        reportM.setPatientAge(patient.getPatientAge());
                    }
                    resList.add(reportM);
                }
            }
        }
        return getTable(resList, new PageInfo(resList).getTotal());
    }


    @GetMapping("/web/list")
    public TableDataInfo getWebList(Report report) throws Exception {

        if (report.getdPhone() != null && !"".equals(report.getdPhone())) {
            report.setdPhone(aesUtils.encrypt(report.getdPhone()));
        }

        if (report.getPPhone() != null && !report.getPPhone().isEmpty()) {
            report.setPPhone(aesUtils.encrypt(report.getPPhone()));
        }
        Long userId = getUserId();
        if (!SysUser.isAdmin(userId)) {
            SysUser sysUser = sysUserMapper.selectUserById(userId);
            report.setdPhone(sysUser.getPhonenumber());
        }
        List<Report> list;
        startPage();
        list = reportService.selectReportList(report);

        ArrayList<ReportM> resList = new ArrayList<>();

        PatientManagement patientManagement;
        Patient patient;
        Date birthDay;
        ReportM reportM;
        for (Report r : list) {
            reportM = new ReportM();
            patientManagement = patientManagementService.selectPatientManagementByPId(r.getpId());
            if (r.getPPhone() != null && !"".equals(r.getPPhone())) {
                r.setPPhone(aesUtils.decrypt(r.getPPhone()));
            }
            if (r.getdPhone() != null && !"".equals(r.getdPhone())) {
                r.setdPhone(aesUtils.decrypt(r.getdPhone()));
            }
            if (r.getDiagnosisDoctor() != null && !"".equals(r.getDiagnosisDoctor())) {
                r.setDiagnosisDoctor(aesUtils.decrypt(r.getDiagnosisDoctor()));
            }
            BeanUtils.copyProperties(r, reportM);
            if (patientManagement != null) {
                patient = patientService.selectPatientByPatientPhone(patientManagement.getPatientPhone());
                if (patientManagement.getPatientPhone() != null && !"".equals(patientManagement.getPatientPhone())) {
                    patientManagement.setPatientPhone(aesUtils.decrypt(patientManagement.getPatientPhone()));
                }
                if (patientManagement.getPatientName() != null && !"".equals(patientManagement.getPatientName())) {
                    patientManagement.setPatientName(aesUtils.decrypt(patientManagement.getPatientName()));
                }

                if (patientManagement.getDiagnosisDoctor() != null && !"".equals(patientManagement.getDiagnosisDoctor())) {
                    patientManagement.setDiagnosisDoctor(aesUtils.decrypt(patientManagement.getDiagnosisDoctor()));
                }
                if (patientManagement.getDoctorPhone() != null && !"".equals(patientManagement.getDoctorPhone())) {
                    patientManagement.setDoctorPhone(aesUtils.decrypt(patientManagement.getDoctorPhone()));
                }
                birthDay = patient.getBirthDay();
                if (birthDay != null)
                    reportM.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
                else {
                    reportM.setPatientAge(patient.getPatientAge());
                }
                reportM.setPatientPhone(patientManagement.getPatientPhone());
                reportM.setPatientName(aesUtils.decrypt(patient.getPatientName()));
                reportM.setPatientSex(patient.getPatientSex());
            }
            reportM.setWeekReport(false);
            resList.add(reportM);
        }


        WeekReport weekReport = new WeekReport();
        if (!SysUser.isAdmin(getUserId())) {
            weekReport.setDoctorPhone(aesUtils.decrypt(getUsername()));
        } else {
            weekReport.setDoctorPhone(aesUtils.decrypt(report.getdPhone()));
        }

        if (report.getPPhone() != null && !report.getPPhone().isEmpty()) {
            weekReport.setPatientPhone(aesUtils.decrypt(report.getPPhone()));
        }
        weekReport.setDiagnosisStatus(Math.toIntExact(report.getDiagnosisStatus()));
        //未诊断才返回周报数据
        if (weekReport.getDiagnosisStatus() != 1) {
            List<WeekReport> weekReports = weekReportService.selectWeekReportList(weekReport);
            for (WeekReport wr : weekReports) {
                reportM = new ReportM();
                patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(wr.getPatientPhone()));
                Doctor doctor = doctorService.selectDoctorByDoctorPhone(aesUtils.encrypt(wr.getDoctorPhone()));
                reportM.setPPhone(wr.getPatientPhone());
                reportM.setdPhone(wr.getDoctorPhone());
                reportM.setDiagnosisStatus(report.getDiagnosisStatus());
                if (doctor != null) {
                    reportM.setDiagnosisDoctor(aesUtils.decrypt(doctor.getDoctorName()));
                }
                reportM.setReportType("week");
                reportM.setReportId(wr.getId());
                reportM.setDiagnosisConclusion(wr.getDiagnosisConclusion());
                reportM.setReportTime(wr.getWeekpdftime());
                reportM.setWeekReport(true);
                reportM.setpId(wr.getWeekid());
                if (patient != null) {
                    reportM.setPatientName(aesUtils.decrypt(patient.getPatientName()));
                    reportM.setPatientSex(patient.getPatientSex());
                    birthDay = patient.getBirthDay();
                    if (birthDay != null)
                        reportM.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
                    else {
                        reportM.setPatientAge(patient.getPatientAge());
                    }
                }

                resList.add(reportM);
            }

        }
        return getTable(resList, new PageInfo(resList).getTotal());
    }

    /**
     * 导出报告列表
     */
//    @PreAuthorize("@ss.hasPermi('report:report:export')")
    @Log(title = "报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Report report) {
        List<Report> list = reportService.selectReportList(report);
        ExcelUtil<Report> util = new ExcelUtil<Report>(Report.class);
        util.exportExcel(response, list, "报告数据");
    }

    /**
     * 获取报告详细信息
     */
//    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/{reportId}")
    public AjaxResult getInfo(@PathVariable("reportId") Long reportId) throws Exception {
        Report report = reportService.selectReportByReportId(reportId);
        if (report == null) {
            return AjaxResult.error("报告不存在");
        }
        if (report.getPPhone() != null && !"".equals(report.getPPhone())) {
            report.setPPhone(aesUtils.decrypt(report.getPPhone()));
        }
        return AjaxResult.success(report);
    }

    /**
     * 获取报告详细信息
     */
//    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/getInfoByPid/{pId}")
    public AjaxResult getInfoByPid(@PathVariable("pId") String pId) throws Exception {
        Report report = reportService.selectReportByPId(pId);
        if (report == null) {
            return AjaxResult.error("报告不存在");
        }
        if (report.getPPhone() != null && !"".equals(report.getPPhone())) {
            report.setPPhone(aesUtils.decrypt(report.getPPhone()));
        }
        if (report.getDiagnosisDoctor() != null && !"".equals(report.getDiagnosisDoctor())) {
            report.setDiagnosisDoctor(aesUtils.decrypt(report.getDiagnosisDoctor()));
        }
        if (report.getdPhone() != null && !"".equals(report.getdPhone())) {
            report.setdPhone(aesUtils.decrypt(report.getdPhone()));
        }
        return AjaxResult.success(report);
    }

    /**
     * 新增报告
     */
//    @PreAuthorize("@ss.hasPermi('report:report:add')")
    @Log(title = "报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Report rep) throws Exception {
        if (rep.getPPhone() != null && !"".equals(rep.getPPhone())) {
            rep.setPPhone(aesUtils.encrypt(rep.getPPhone()));
        }
        if (rep.getdPhone() != null && !"".equals(rep.getdPhone())) {
            rep.setdPhone(aesUtils.encrypt(rep.getdPhone()));
        }
        if (StringUtils.isNotEmpty(rep.getDiagnosisDoctor())) {
            rep.setDiagnosisDoctor(aesUtils.encrypt(rep.getDiagnosisDoctor()));
        }
        return toAjax(reportService.insertReport(rep));
    }


    /**
     * 修改报告
     */
    @Log(title = "报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Report report, HttpServletRequest request) throws Exception {

        LoginUser loginUser1 = tokenService.getLoginUser(request);
        Long userId = loginUser1.getUser().getUserId();
        if (Boolean.TRUE.equals(redisTemplate.hasKey("reportPutAdd" + userId))) {
            return AjaxResult.error("请勿重复提交");
        }
        redisTemplate.opsForValue().set("reportPutAdd" + userId, String.valueOf(userId), 5, TimeUnit.SECONDS);

        LoginUser loginUser = SecurityUtils.getLoginUser();
        String s = report.getpId();
        Report report1 = reportService.selectReportByPId(s);
        if (report1 == null) {
            return AjaxResult.error("报告不存在");
        }
        //相关数据加密
        if (report.getPPhone() != null && !"".equals(report.getPPhone())) {
            report.setPPhone(aesUtils.encrypt(report.getPPhone()));
        }
        if (report.getdPhone() != null && !"".equals(report.getdPhone())) {
            report.setdPhone(aesUtils.encrypt(report.getdPhone()));
        }
        if (StringUtils.isNotEmpty(report.getLoginUserPhone())) {
            report.setLoginUserPhone(aesUtils.encrypt(report.getLoginUserPhone()));
        }

        //web端判断填写医生是否在该平台，没有则直接返回
        if (StringUtils.isNotEmpty(report.getdPhone())) {
            Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(report.getdPhone());
            if (doctor1 == null) {
                return AjaxResult.error("当前医生不存在");
            }
            report.setDiagnosisDoctor(doctor1.getDoctorName());
        }
        List<Doctor> doctors = null;
        //当前报告信息，判断报告状态

        if (!SysUser.isAdmin(loginUser.getUser().getUserId())) {
            if (report1.getDiagnosisStatus() == 1) {
                return AjaxResult.error("该数据已被诊断");
            }
        }

        report.setReportId(report1.getReportId());
        Date date = new Date();
        report.setReportTime(date);
        report.setReportNormal("1");
        //获取医生信息
        Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(report1.getdPhone());
        //获取患者信息
        Patient patient = patientService.selectPatientByPatientPhone(report1.getPPhone());

        if (patient.getPatientName() != null && !"".equals(patient.getPatientName())) {
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }

        SysUser sysUser1 = sysUserMapper.selectUserById(loginUser.getUser().getUserId());

        //报告状态判断  2 提交判断 ， 3拒接判断 ， 1，医生诊断完成
        if (report.getDiagnosisStatus() == 2) {

            //判断用户是否存在服务次数
            VipPatient vipPhone = vipPatientService.findVipPhone(sysUser1.getPhonenumber());
            if (vipPhone == null) {
                if (sysUser1.getDetectionNum() == 0) {
                    return AjaxResult.error("用户服务次数不足");
                }
            }
            if (vipPhone != null && vipPhone.getVipNum() == 0) {
                return AjaxResult.error("用户服务次数不足");
            }
            //选择医院加入公共抢单
            if (report.getHospital() != null) {
                Doctor doctor = new Doctor();
                doctor.getHospitalNameList().add(report.getHospital());
                doctor.setAccountStatus("0");
                doctors = doctorService.selectDoctorListNot(doctor);
                if (doctors != null && !doctors.isEmpty()) {
                    //患者提交报告，通过微信公众号推送提醒消息
                    if (report.getpId() != null && !"".equals(report.getpId())) {
                        wxMsgRunConfig.redisAdd(report.getpId(), doctors);
                    }
                } else {
                    return AjaxResult.error("当前医院平台无医生");
                }
            } else {
                return AjaxResult.error("请先选择医院医院");
            }
            //咨询医生次数减一
            patientService.detectionNumSubtract(sysUser1.getPhonenumber());
            report.setStartTime(new Date());
            int i = reportService.updateReport(report);
            //记录患者的报告服务次数使用
            reportPut(loginUser.getUser().getPhonenumber(), "咨询医生减少服务次数", "2", request);
            return toAjax(i);


        } else if (report.getDiagnosisStatus() == 3) { //拒绝逻辑
            //记录医生拒绝判断的原因
            Detection detection = new Detection();
            detection.setDetectionPid(report1.getpId());
            List<Detection> detections = detectionService.selectDetectionList(detection);
            if (detections != null && !detections.isEmpty()) {
                detectionService.deleteDetectionByDetectionId(detections.get(detections.size() - 1).getDetectionId());
            }
            NotDealWith notDealWith = new NotDealWith();
            notDealWith.setPid(s);
            notDealWith.setDoctorPhone(report.getdPhone());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            notDealWith.setDoctorPhoneAes(report.getdPhone());
            notDealWith.setRefuseTime(calendar.getTime());
            notDealWith.setRefuseReason(report.getRefuseText());
            notDealWithService.insertNotDealWith(notDealWith);
            if (doctor1 == null) {
                return AjaxResult.error("医生不存在");
            }
            if (StringUtils.isNotEmpty(report1.getLoginUserPhone())) {
                patientService.detectionNumAdd(report1.getLoginUserPhone());
                reportPut(loginUser.getUser().getPhonenumber(), "咨询医生诊断异常数据，次数返回", "1", request);
            }
            //医生拒绝判断小程序消息推送通知用户通知
            //如果报告属于患者家人，则通过发送短信的方式去
            if (StringUtils.isNotEmpty(report1.getLoginUserPhone())) {
                WxMsgPut(report1.getLoginUserPhone(), doctor1.getHospital(), patient.getPatientName(),report1);
            }
            redisTemplate.delete("DocList" + report.getpId());
            return toAjax(reportService.updateReportNull(report));
        } else if (report.getDiagnosisStatus() == 1) {//医生诊断
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //记录报告的的状态
            if (StringUtils.isNotEmpty(report.getStartDateTime())) {

                Date parse = simpleDateFormat.parse(report.getStartDateTime());
                long time = parse.getTime();
                long time1 = report1.getStartTime().getTime();
                long dateTime = 2400000;
                if (time - time1 > dateTime) {
                    report.setStartTime(parse);
                }
                if (report1.getdPhone() != null && report1.getdPhone().equals(report.getdPhone())) {
                    DiagnoseDoc diagnoseDoc1 = new DiagnoseDoc();
                    diagnoseDoc1.setReportId(report1.getReportId());
                    diagnoseDoc1.setDoctorPhone(report.getdPhone());
                    diagnoseDoc1.setDiagnoseStatus("1");
                    diagnoseDocService.updateDiagnose(diagnoseDoc1);
                } else {
                    DiagnoseDoc diagnoseDoc1 = new DiagnoseDoc();
                    diagnoseDoc1.setReportId(report1.getReportId());
                    diagnoseDoc1.setDoctorPhone(report.getdPhone());
                    diagnoseDoc1.setDiagnoseType("1");
                    diagnoseDoc1.setDiagnoseStatus("1");
                    diagnoseDocService.insertDiagnose(diagnoseDoc1);
                }
            } else {
                Date date1 = new Date();
                long time = date1.getTime();
                long time1 = report1.getStartTime().getTime();
                long dateTime = 2400000;
                if (time - time1 > dateTime) {
                    report.setStartTime(date1);
                }
                DiagnoseDoc diagnoseDoc1 = new DiagnoseDoc();
                diagnoseDoc1.setReportId(report1.getReportId());
                diagnoseDoc1.setDoctorPhone(report1.getdPhone());
                diagnoseDoc1.setDiagnoseStatus("1");
                diagnoseDocService.updateDiagnose(diagnoseDoc1);
            }
            //判断当医生诊断出现干扰大，的时候，将次数重新返还给用户
            if (StringUtils.isNotEmpty(report.getDiagnosisConclusion())) {
                if (report.getDiagnosisConclusion().contains("重新")) {
                    report.setReportNormal("2");
                    if (StringUtils.isNotEmpty(report1.getLoginUserPhone())) {
                        patientService.detectionNumAdd(report1.getLoginUserPhone());
                        reportPut(loginUser.getUser().getPhonenumber(), "咨询医生诊断异常数据，次数返回", "1", request);
                    }
                }
            }
            //医生提交诊断报告小程序消息推送通知用户通知
            //如果报告属于患者家人，则通过发送短信的方式去
            reportService.updateReport(report);
            redisTemplate.delete("DocList" + report.getpId());
            if (StringUtils.isNotEmpty(report1.getLoginUserPhone())) {
                WxMsgPut(report1.getLoginUserPhone(), doctor1.getHospital(), patient.getPatientName(),report1);
            }
        }
        return toAjax(1);
    }

    public String getAddress(String ip) {
        String json_result = null;
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6006&format=json&query=" + ip;
        HttpResponse res = HttpRequest.get(url).execute();
        JSONObject resJson = JSONObject.parseObject(res.body());
        JSONArray resArr = JSONArray.parseArray(resJson.getString("data"));
        resJson = JSONObject.parseObject("" + resArr.get(0));

        return resJson.getString("location");
    }

    /**
     * 患者提交报告到大厅
     */
    @PutMapping("/dataLabby")
    public AjaxResult dataLabby(@RequestBody Report report) {
        Long hospitalId = Long.valueOf(report.getHospital());
        Long s = report.getReportId();
        dataLabby dataLabby = new dataLabby();
        dataLabby.setReportId(s);
        dataLabby.setHospitalId(hospitalId);
        report.setDiagnosisStatus(2L);
        reportService.updateReport(report);
        return AjaxResult.success(dataLabbyService.insertOrder(dataLabby));
    }


    /**
     * 删除报告
     */
//    @PreAuthorize("@ss.hasPermi('report:report:remove')")
    @Log(title = "报告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{reportIds}")
    public AjaxResult remove(@PathVariable Long[] reportIds) {
        return toAjax(reportService.deleteReportByReportIds(reportIds));
    }

    /**
     * 常用术语
     */
    @GetMapping("/getCommonTerms")
    public AjaxResult getCommonTerms(HttpServletRequest request) {
        //总述
        String[] normal;
        //技术条件
        String[] heart_rhythm;
        //窦性心律及心律失常
        String[] arrhythmia;
        //室上性心律失常
        String[] premature_escape;
        //室上性快速性心律失常
        String[] flutter;
        //室性心律失常
        String[] ST_T_change;
        //室性快速性心律失常
        String[] atrioventricular_hypertrophy;
        //房室传导
        String[] myocardial_infarction;
        //心室内及心房内传导
        String[] conduction_block;
        //电轴与电压
        String[] critical_value;
        //心脏肥厚或增大
        String[] other;
        //选择性建议
        String[] ad;
        //ST段、T波和U波
        String[] st;
        String[] xg;
        String[] qbq;

        //返回map
        LinkedHashMap<String, String[]> resMap = new LinkedHashMap<>();
        normal = "正常心电图,余心电图正常,异常心电图,无法判读的心电图".split(",");
        heart_rhythm = "肢体导联反接,胸导联错接,导联脱落,右胸导联,伪差,低质量数据,后壁导联".split(",");
        arrhythmia = "窦性心律,窦性心动过速,窦性心动过缓,窦性心律不齐,游走性窦性心律,Ⅰ型窦房阻滞,Ⅱ型窦房阻滞,窦性停搏,不确定的室上性心律".split(",");
        premature_escape = "房性期前收缩(早搏),逆传的心房激动,房性期前收缩(早搏)未下传,游走性房性心律,房性心律,多源性房性心律,交界性期前收缩 (早博),交界性逸搏,交界性心律,加速性交界性心律,室上性心律,室上性心搏,非窦性心动过缓".split(",");
        flutter = "心房颤动,心房扑动,单源性房性心动过速,多源性房性心动过速,交界性心动过速,室上性心动过速,窄QRS心动过速".split(",");
        ST_T_change = "室性期前收缩(早搏),室性融合波,室性逸搏,室性自主心律,加速性室性自主心律,分支性室性心律,室性并行心律,无脉性电活动,心脏停搏".split(",");
        atrioventricular_hypertrophy = "室性心动过速,非持续性室性心动过速,双向性室性心动过速,多形性室性心动过速,尖端扭转型室性心动过速,心室颤动,分支性室性心动过速,宽QRS心动过速".split(",");
        myocardial_infarction = "短PR间期,房室传导比N:D,PR间期延长,一度房室阻滞,二度I型房室阻滞,二度II型房室阻滞,2:1 房室阻滞,房室阻滞，不等比传导,高度房室阻滞,三度房室阻滞,房室分离".split(",");
        conduction_block = "室上性搏动伴差异性传导,左前分支阻滞,左后分支阻滞,左束支阻滞,不完全性右束支阻滞,右束支阻滞,室内传导延迟,心室预激,右心房传导异常,左心房传导异常,Epsilon波".split(",");
        critical_value = "电轴右偏,电轴左偏,电轴极度右偏,不确定电轴,电交替,低电压,肢体导联低电压,胸导联低电压,胸导联R波递增不良,P波电轴异常".split(",");
        other = "左心房增大,右心房增大,左心室肥厚,右心室肥厚,双心室肥厚".split(",");
        ad = "建议到上级医院复查, 建议做动态心电图, 建议做12导联常规心电图, 信号存在干扰，请重新采集".split(",");
        st =  "ST改变,ST-T改变,T波异常,QT间期延长,短QT间期,U波增高,U波倒置,心室肥厚所致的ST-T改变,J波,早复极".split(",");
        xg =   "前壁心肌梗死,下壁心肌梗死,后壁心肌梗死,侧壁心肌梗死,前间壁心肌梗死,广泛前壁心肌梗死,心肌梗死合并左束支阻滞,右心室心肌梗死,异常Q波".split(",");
        qbq =   "心房起搏/心律,心室起搏/心律,非右心室心尖部心室起搏,心房感知心室起搏/心律,房室双腔起搏/心律,心房失夺获,心室失夺获,心房感知不良,心室感知不良,心房起搏不良,心室起搏不良".split(",");


        LoginUser loginUser = tokenService.getLoginUser(request);
        DoctorTerm doctorTerm = new DoctorTerm();
        doctorTerm.setDoctorPhone(loginUser.getUser().getUserName());
        List<DoctorTerm> doctorTerms = doctorTermService.selectDoctorTermList(doctorTerm);
        if (doctorTerms != null && !doctorTerms.isEmpty()) {
            String text = doctorTerms.get(0).getTermText();
            if (text.length() > 2) {
                JSONArray objects = JSONArray.parseArray(text);
                String[] stringArray = new String[objects.size()];
                for (int i = 0; i < objects.size(); i++) {
                    stringArray[i] = objects.getString(i);
                }
                resMap.put("医生术语", stringArray);
            }
        }
        resMap.put("选择性建议", ad);
        resMap.put("总述", normal);
        resMap.put("技术条件", heart_rhythm);
        resMap.put("窦性心律及心律失常", arrhythmia);
        resMap.put("室上性心律失常", premature_escape);
        resMap.put("室上性快速性心律失常", flutter);
        resMap.put("室性心律失常", ST_T_change);
        resMap.put("室性快速性心律失常", atrioventricular_hypertrophy);
        resMap.put("房室传导", myocardial_infarction);
        resMap.put("心室内及心房内传导", conduction_block);
        resMap.put("电轴与电压", critical_value);
        resMap.put("心脏肥厚或增大", other);
        resMap.put("ST段、T波和U波", st);
        resMap.put("心肌梗死", xg);
        resMap.put("起搏器", qbq);

        return AjaxResult.success(resMap);
    }


    /**
     * 获取时间信息
     */
    @GetMapping("/get_week_info")
    public AjaxResult getWeekInfo(Report rep) throws Exception {

        if (rep.getPPhone() != null && !"".equals(rep.getPPhone())) {
            rep.setPPhone(aesUtils.encrypt(rep.getPPhone()));
        }
        if (rep.getdPhone() != null && !"".equals(rep.getdPhone())) {
            rep.setdPhone(aesUtils.encrypt(rep.getdPhone()));
        }
        PInfoVO pInfoVO = patientManagementService.updatePatient(rep);


        return AjaxResult.success(pInfoVO);
    }

    @GetMapping("/getDealWithInfo")
    public AjaxResult getDealWithInfo(Report rep) throws Exception {
        if (rep.getPPhone() != null && !"".equals(rep.getPPhone())) {
            rep.setPPhone(aesUtils.encrypt(rep.getPPhone()));
        }
        if (rep.getdPhone() != null && !"".equals(rep.getdPhone())) {
            rep.setdPhone(aesUtils.encrypt(rep.getdPhone()));
        }
        HashMap<String, Integer> result = new HashMap<>();

        //已处理
        rep.setDiagnosisStatus(1L);
        List<Report> reports1 = reportService.selectReportList(rep);
        int dealSize = reports1.size();
        //未处理
        rep.setDiagnosisStatus(2L);
        List<Report> reports2 = reportService.selectReportList(rep);
        int notDealSize = reports2.size();
        //已拒绝
        NotDealWith notDealWith = new NotDealWith();
        notDealWith.setDoctorPhone(rep.getdPhone());
        HashMap<String, Object> params = new HashMap<>();
        params.put("beginRefuseTime", rep.getStartReportTime());
        params.put("endRefuseTime", rep.getEndReportTime());
        notDealWith.setParams(params);
        List<NotDealWith> notDealWiths = notDealWithService.selectNotDealWithList(notDealWith);
        int refuse = notDealWiths.size();


        result.put("已处理数据", dealSize);
        result.put("未处理数据", notDealSize);
        result.put("已拒绝数据", refuse);

        return AjaxResult.success(result);
    }

    @GetMapping("/doctorFinishList")
    public TableDataInfo doctorFinishList(Report report) throws Exception {
        if (report.getPPhone() != null && !"".equals(report.getPPhone())) {
            report.setPPhone(aesUtils.encrypt(report.getPPhone()));
        }
        if (report.getdPhone() != null && !"".equals(report.getdPhone())) {
            report.setdPhone(aesUtils.encrypt(report.getdPhone()));
        }
        startPage();
        List<Report> patientPhone = reportService.groupByPatientPhone(report.getdPhone());

        List<WeekReport> wp = weekReportService.groupByPatientPhone(report.getdPhone());
        if (wp != null && wp.size() != 0) {
            Report rp;
            for (WeekReport weekReport : wp) {
                int dl = 0;
                for (Report rep : patientPhone) {
                    if (Objects.equals(weekReport.getPatientPhone(), rep.getPPhone())) {
                        dl = 1;
                        break;
                    }
                }
                if (dl == 0) {
                    rp = new Report();
                    rp.setPPhone(weekReport.getPatientPhone());
                    patientPhone.add(rp);

                }

            }
        }

        ArrayList<ReportM> resList = new ArrayList<>();
        ReportM reportM;
        PatientManagement patientManagement;
        Patient patient;
        Date birthDay;
        MedicalHistory medicalHistory;
        MedicalData medicalData = new MedicalData();
        //病种
        ArrayList<String> medical;
        //病种和id映射
        HashMap<String, String> medicalHashMap = new HashMap<>();
        List<MedicalData> medicalData1 = medicalDataService.selectMedicalDataList(medicalData);
        for (MedicalData data : medicalData1) {
            medicalHashMap.put(data.getMedicalCode().toString(), data.getMedicalName());
        }

        for (Report r : patientPhone) {
            medical = new ArrayList<>();
            reportM = new ReportM();
            patient = patientService.selectPatientByPatientPhone(r.getPPhone());
            medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(r.getPPhone());

            if (medicalHistory != null && medicalHistory.getPastMedicalHistory() != null && !Objects.equals(medicalHistory.getPastMedicalHistory(), "")) {
                String[] split = medicalHistory.getPastMedicalHistory().split(",");
                for (String s : split) {
                    medical.add(medicalHashMap.get(s));
                }
                reportM.setMedicalHistory(medical);
            } else {
                medical.add("无");
                reportM.setMedicalHistory(medical);
            }
            birthDay = patient.getBirthDay();
            if (birthDay != null)
                reportM.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
            else {
                reportM.setPatientAge(patient.getPatientAge());
            }
            reportM.setPatientName(aesUtils.decrypt(patient.getPatientName()));
            reportM.setPatientSex(patient.getPatientSex());
            reportM.setPatientPhone(aesUtils.decrypt(String.valueOf(r.getPPhone())));
            resList.add(reportM);
        }

//        return getTable(resList, new PageInfo(patientPhone).getTotal());
        return getTable(resList, new PageInfo(resList).getTotal());
    }

    @GetMapping("/wx")
    public AjaxResult Wx() throws ParseException {
        String msg = "17337345250";
        WxUtil.sendAdvice(msg);
        return AjaxResult.success();
    }


    /**
     * 查询抢单的用户信息
     *
     * @return
     */
    @GetMapping("/docOrPatientList")
    public AjaxResult docOrPatientList() throws Exception {
        List<PatientManagement> patientManagements = patientManagementService.selectPatientManagementList();
        for (PatientManagement management : patientManagements) {
            if (DateUtil.isValidDate(management.getBirthDay())) {
                try {
                    management.setPatientAge(String.valueOf(DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(management.getBirthDay()))));
                } catch (ParseException e) {
                    System.out.println(1);
                }
            }
            if (management.getPatientPhone() != null && !"".equals(management.getPatientPhone())) {
                management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));

            }
            if (management.getPatientName() != null && !"".equals(management.getPatientName())) {
                management.setPatientName(aesUtils.decrypt(management.getPatientName()));
            }
            if (management.getDiagnosisDoctor() != null && !"".equals(management.getDiagnosisDoctor())) {
                management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
            }
        }
        return AjaxResult.success(patientManagements);
    }

    /**
     * web选择医生诊断
     *
     * @param report
     * @return
     */
    @GetMapping("/docUpdate")
    public AjaxResult docUpdate(Report report) throws Exception {

        int i = updateReport(report);
        return AjaxResult.success(i > 0);
    }


    /**
     * app选择医师诊断
     *
     * @param report
     * @return
     */
    @PutMapping("/appRelationDocUpdate")
    public AjaxResult appRelationDocUpdate(@RequestBody Report report, HttpServletRequest request) throws Exception {

        LoginUser loginUser = tokenService.getLoginUser(request);
        Long userId = loginUser.getUser().getUserId();
        if (Boolean.TRUE.equals(redisTemplate.hasKey("reportPutAddApp" + userId))) {
            return AjaxResult.error("请勿重复提交");
        }
        redisTemplate.opsForValue().set("reportPutAddApp" + userId, String.valueOf(userId), 5, TimeUnit.SECONDS);
        VipPatient vipPhone = vipPatientService.findVipPhone(loginUser.getUser().getPhonenumber());
        if (vipPhone == null) {
            if (loginUser.getUser().getDetectionNum() <= 0) {
                return AjaxResult.error("用户服务次数不足");
            }
        }
        if (vipPhone != null && vipPhone.getVipNum() <= 0) {
            return AjaxResult.error("用户服务次数不足");
        }
        int i = updateReport(report);


        if (i > 0) {
            patientService.detectionNumSubtract(report.getLoginUserPhone());
        }
        return AjaxResult.success(i > 0);
    }

    /**
     * app选择医师绑定
     *
     * @param report
     * @return
     */
    @PutMapping("/appRelationDocUpdateByBinding")
    public AjaxResult appRelationDocUpdateByBinding(@RequestBody Report report, HttpServletRequest request) throws Exception {

        LoginUser loginUser = tokenService.getLoginUser(request);

        Long userId = loginUser.getUser().getUserId();

        SysUser sysUser = sysUserMapper.selectUserById(userId);
        if (Boolean.TRUE.equals(redisTemplate.hasKey("reportPutAddApp" + userId))) {
            return AjaxResult.error("请勿重复提交");
        }
        redisTemplate.opsForValue().set("reportPutAddApp" + userId, String.valueOf(userId), 5, TimeUnit.SECONDS);
        VipPatient vipPhone = vipPatientService.findVipPhone(loginUser.getUser().getPhonenumber());
        if (vipPhone == null) {
            if (sysUser.getDetectionNum() <= 0) {
                return AjaxResult.error("用户服务次数不足");
            }
        }
        if (vipPhone != null && vipPhone.getVipNum() <= 0) {
            return AjaxResult.error("用户服务次数不足");
        }
        int i = updateReport(report);
        getReportEncrypt(report);
        Report report1 = reportService.selectReportByPId(report.getpId());
        Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(report.getdPhone());
        if (doctor1 == null) {
            throw new ServiceException("医师不存在");
        }
        report.setDiagnosisDoctorAes(aesUtils.decrypt(doctor1.getDoctorName()));
        report.setDPhoneAes(aesUtils.decrypt(doctor1.getDoctorPhone()));
        report.setReportId(report1.getReportId());
        report.setDiagnosisStatus(2L);
        report.setDiagnosisDoctor(doctor1.getDoctorName());
        report.setReportTime(new Date());
        report.setStartTime(new Date());

//        Doctor doctor = new Doctor();
//        doctor.getHospitalNameList().add(doctor1.getHospital());
//        List<Doctor> doctors = doctorService.selectDoctorListNot(doctor);
//        //定时器, 30分钟无医生诊断, 换医生诊断.
//        wxMsgRunConfig.redisDTStart(report.getpId(),doctors);
        WxUtil.send(aesUtils.decrypt(report.getdPhone()));
        DiagnoseDoc diagnoseDoc = new DiagnoseDoc();
        diagnoseDoc.setReportId(report.getReportId());
        diagnoseDoc.setDoctorPhone(report.getdPhone());
        diagnoseDoc.setDiagnoseType("2");
        diagnoseDocService.insertDiagnose(diagnoseDoc);
        int i1 = reportService.updateReport(report);
        if (i1 > 0) {
            patientService.detectionNumSubtract(report.getLoginUserPhone());
        }
        return AjaxResult.success(i > 0);
    }

    private int updateReport(Report report) throws Exception {
        getReportEncrypt(report);
        Report report1 = reportService.selectReportByPId(report.getpId());
        Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(report.getdPhone());
        if (doctor1 == null) {
            throw new ServiceException("医师不存在");
        }
        report.setDiagnosisDoctorAes(aesUtils.decrypt(doctor1.getDoctorName()));
        report.setDPhoneAes(aesUtils.decrypt(doctor1.getDoctorPhone()));
        report.setReportId(report1.getReportId());
        report.setDiagnosisStatus(2L);
        report.setLoginUserPhone(report1.getPPhone());
        report.setDiagnosisDoctor(doctor1.getDoctorName());
        report.setReportTime(new Date());
        report.setStartTime(new Date());

        Doctor doctor = new Doctor();
        doctor.getHospitalNameList().add(doctor1.getHospital());
        List<Doctor> doctors = doctorService.selectDoctorListNot(doctor);
        //定时器, 30分钟无医生诊断, 换医生诊断.
        wxMsgRunConfig.redisDTStart(report.getpId(), doctors);
        WxUtil.send(aesUtils.decrypt(report.getdPhone()));
        DiagnoseDoc diagnoseDoc = new DiagnoseDoc();
        diagnoseDoc.setReportId(report.getReportId());
        diagnoseDoc.setDoctorPhone(report.getdPhone());
        diagnoseDoc.setDiagnoseType("2");
        diagnoseDocService.insertDiagnose(diagnoseDoc);
        return reportService.updateReport(report);
    }

    private void getReportEncrypt(Report report) throws Exception {
        if (report != null) {
            if (report.getPPhone() != null && !report.getPPhone().isEmpty()) {
                report.setPPhone(aesUtils.encrypt(report.getPPhone()));
            }
            if (report.getdPhone() != null && !"".equals(report.getdPhone())) {
                report.setdPhone(aesUtils.encrypt(report.getdPhone()));
            }
            if (StringUtils.isNotEmpty(report.getLoginUserPhone())) {
                report.setLoginUserPhone(aesUtils.encrypt(report.getLoginUserPhone()));
            }
        }

    }

    /**
     * 社区版提交报告，不需要减少次数
     */
    @Log(title = "报告", businessType = BusinessType.UPDATE)
    @PutMapping("/updateReport")
    public AjaxResult updateReport(@RequestBody Report report, HttpServletRequest request) throws Exception {


        LoginUser loginUser = SecurityUtils.getLoginUser();
        String s = report.getpId();

        getReportEncrypt(report);
        if (StringUtils.isNotEmpty(report.getLoginUserPhone())) {
            report.setLoginUserPhone(aesUtils.encrypt(report.getLoginUserPhone()));

            Doctor doctor = doctorService.selectDoctorByDoctorPhone(report.getLoginUserPhone());
            if (doctor == null) {
                return AjaxResult.error("非医生账号");
            }

        }
        if (StringUtils.isEmpty(report.getLoginUserPhone())) {
            return AjaxResult.error("上传异常，请联系管理员");
        }

        if (StringUtils.isNotEmpty(report.getDiagnosisDoctor())) {
            report.setDiagnosisDoctor(aesUtils.encrypt(report.getDiagnosisDoctor()));
            Doctor doctor = new Doctor();
            doctor.setDoctorName(report.getDiagnosisDoctor());
            List<Doctor> doctors = doctorService.selectDoctorListNot(doctor);
            if (doctors == null || doctors.size() == 0) {
                return AjaxResult.error("当前医生不存在");
            }
            report.setdPhone(doctors.get(0).getDoctorPhone());
        }
        List<Doctor> doctors = null;
        //当前报告信息
        Report report1 = reportService.selectReportByPId(s);
        if (!SysUser.isAdmin(loginUser.getUser().getUserId())) {
            if (report1.getDiagnosisStatus() == 1) {
                return AjaxResult.error("该数据已被诊断");
            }
        }
        report.setReportId(report1.getReportId());
        Date date = new Date();
        report.setReportTime(date);
        //患者请求医生
        Report report2 = reportService.selectReportByPId(report.getpId());
        StringBuilder stringBuilder = new StringBuilder();
        String phone = aesUtils.decrypt(report2.getPPhone());
        if (phone.length() > 11) {
            stringBuilder.append(aesUtils.encrypt(phone.substring(0, 11)));
        } else {
            stringBuilder.append(report2.getPPhone());
        }
        Patient patient = patientService.selectPatientByPatientPhone(report2.getPPhone());
        if (patient.getPatientName() != null && !"".equals(patient.getPatientName())) {
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }
        if (report.getDiagnosisStatus() == 2) {
            //选择医院加入公共抢单
            if (report.getHospital() != null) {
                Doctor doctor = new Doctor();
                doctor.getHospitalNameList().add(report.getHospital());
                doctors = doctorService.selectDoctorListNot(doctor);
                if (doctors != null && doctors.size() != 0) {

                    if (report.getpId() != null && !"".equals(report.getpId())) {
                        wxMsgRunConfig.redisAdd(report.getpId(), doctors);
                    }
                } else {
                    return AjaxResult.error("当前医院平台无医生");
                }
            } else {
                return AjaxResult.error("请先选择医院医院");
            }
            report.setStartTime(new Date());
            int i = reportService.updateReport(report);
            return toAjax(i);

        }
        return toAjax(1);
    }


    @GetMapping("/reportAesCopy")
    public AjaxResult reportAesCopy(Limit limit) throws Exception {
        reportService.reportAes(limit);
        return AjaxResult.success();
    }


    //记录报告
    private void reportPut(String phone, String msg, String status, HttpServletRequest request) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.runAsync(() -> {
            System.out.println("异步线程 =====> 开始记录服务使用日志 =====> " + new Date());
            try {
                FwLog fwLog = new FwLog();
                fwLog.setUserName(phone);
                fwLog.setMsg(msg);
                fwLog.setStatus("1");
                fwLog.setLogTime(new Date());
                fwLog.setFwStatus(status);
                fwLog.setFwNum(1);
                String ipAddr = IpUtils.getIpAddr(request);
                fwLog.setIpaddr(ipAddr);
                try {
                    String address = getAddress(ipAddr);
                    fwLog.setLoginLocation(address);
                } catch (Exception e) {
                    System.out.println(e);
                }
                fwLogMapper.insert(fwLog);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束记录服务使用日志 =====> " + new Date());
        }, executorService);
        executorService.shutdown(); // 回收线程池
    }


    private void WxMsgPut(String patientPhone, String hospitalName, String patientName,Report report1) {
        SysUser sysUser2 = sysUserMapper.selectUserByPhone(patientPhone);
        if (sysUser2 != null) {
            try {
                wxPublicRequest.sendMsgByReport(hospitalName, sysUser2, patientName, "心电图检测", "诊断完成",report1);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //预警消息
    private void WxYJMsgPut(String patientPhone, String hospitalName, String patientName, String yj) {
        SysUser sysUser2 = sysUserMapper.selectUserByPhone(patientPhone);
        if (sysUser2 != null) {
            try {
                wxPublicRequest.sendMsg(hospitalName, sysUser2.getOpenId(), patientName, "心电图检测", "您提交的心电图存在异常，预警程度: " + yj);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @GetMapping("/reportEarlyWarningMsg")
    public AjaxResult reportEarlyWarningMsg(Report report) throws Exception {

        Report report1 = reportService.selectReportByPId(report.getpId());
        if (report1 == null) {
            return AjaxResult.error("报告不存在");
        }
        PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(report.getpId());
        if (patientManagement == null) {
            return AjaxResult.error("报告不存在");
        }
        String phone = "";
        if (StringUtils.isNotEmpty(report1.getLoginUserPhone())) {
            phone = report1.getLoginUserPhone();
        } else if (report1.getPPhone() != null) {
            String decrypt = aesUtils.decrypt(report1.getPPhone());
            if (decrypt.length() >= 11) {
                phone = aesUtils.encrypt(decrypt.substring(0, 11));
            }
        }

        if (report.getWarningText() == null || report.getWarningText().length() > 20) {
            return AjaxResult.error("预警消息最多20个字");
        }

        SysUser sysUser = sysUserMapper.selectUserByPhone(phone);
        if (sysUser != null) {
            boolean isKeys = wxPublicRequest.reportEarlyWarning(sysUser, "心电图检测", aesUtils.decrypt(patientManagement.getPatientName()), report.getWarningText(), patientManagement.getConnectionTime(),report1);
            if (isKeys) {
                return AjaxResult.success();
            }
            return AjaxResult.error("用户未授权推送");
        }


        return AjaxResult.error("用户未授权推送");
    }


    @PutMapping("/putReport")
    public AjaxResult putReport(@RequestBody Report report) throws Exception {
        return AjaxResult.success(reportService.updateReportByPId(report));
    }
}
