package com.ruoyi.xindian.report.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.dataLabby.domain.dataLabby;
import com.ruoyi.xindian.dataLabby.service.IDataLabbyService;
import com.ruoyi.xindian.detection.domain.Detection;
import com.ruoyi.xindian.detection.service.IDetectionService;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.medical.domain.MedicalData;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalDataService;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.patient_management.vo.PInfoVO;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.mapper.PatientRelationshipMapper;
import com.ruoyi.xindian.report.config.WxMsgRunConfig;
import com.ruoyi.xindian.report.domain.NotDealWith;
import com.ruoyi.xindian.report.domain.ReportM;
import com.ruoyi.xindian.report.service.INotDealWithService;
import com.ruoyi.xindian.util.*;
import com.ruoyi.xindian.vipPatient.controller.VipPatientController;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报告Controller
 * 
 * @author hanhan
 * @date 2022-10-05
 */
@RestController
@RequestMapping("/report/report")
public class ReportController extends BaseController
{


    @Resource
    private VipPatientController vipPatientController;


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

    @Autowired
    private IHospitalService hospitalService;


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private IVipPatientService vipPatientService;

    @Resource
    private WxMsgRunConfig wxMsgRunConfig;

    /**
     * 查询报告列表
     */
//    @PreAuthorize("@ss.hasPermi('report:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(Report report)
    {
        List<Report> list;
        startPage();
        list = reportService.selectReportList(report);

        ArrayList<ReportM> resList = new ArrayList<>();

        PatientManagement patientManagement;
        Patient patient;
        Date birthDay;
        MedicalHistory medicalHistory;
        MedicalData medicalData=new MedicalData();
        //病种
        ArrayList<String> medical;
        //病种和id映射
        HashMap<String, String> medicalHashMap = new HashMap<>();
        List<MedicalData> medicalData1 = medicalDataService.selectMedicalDataList(medicalData);
        for (MedicalData data : medicalData1) {
            medicalHashMap.put(data.getMedicalCode().toString(),data.getMedicalName());
        }
        ReportM reportM;
        for (Report r : list) {
            medical=new ArrayList<>();
            reportM=new ReportM();
            patientManagement = patientManagementService.selectPatientManagementByPId(r.getpId());

            BeanUtils.copyProperties(r,reportM);
            if(patientManagement != null){
                medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(patientManagement.getPatientPhone());
                if(medicalHistory!=null && medicalHistory.getPastMedicalHistory()!=null){
                    String[] split = medicalHistory.getPastMedicalHistory().split(",");
                    for (String s : split) {
                        if(medicalHashMap.get(s)==null){
                            medical.add("无");
                            break;
                        }
                        medical.add(medicalHashMap.get(s));
                    }
                    reportM.setMedicalHistory(medical);
                }
                else{
                    medical.add("无");
                    reportM.setMedicalHistory(medical);
                }
//                if (patientManagement.getPatientPhone()!=null&&!"".equals(patientManagement.getPatientPhone())){
//                    if (patientManagement.getPatientPhone().length()==14||patientManagement.getPatientPhone().length()==15){
//                        reportM.setPatientPhone(patientManagement.getPatientPhone().substring(0,11));
//                    }else {
//                        reportM.setPatientPhone(patientManagement.getPatientPhone());
//                    }
//                }

                patient = patientService.selectPatientByPatientPhone(patientManagement.getPatientPhone());
                birthDay = patient.getBirthDay();
                if(birthDay != null)
                    reportM.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
                else {
                    reportM.setPatientAge(patient.getPatientAge());
                }
                reportM.setPatientName(patient.getPatientName());
                reportM.setPatientSex(patient.getPatientSex());
            }
            resList.add(reportM);
        }
        return getTable(resList,new PageInfo(list).getTotal());
    }

    /**
     * 导出报告列表
     */
//    @PreAuthorize("@ss.hasPermi('report:report:export')")
    @Log(title = "报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Report report)
    {
        List<Report> list = reportService.selectReportList(report);
        ExcelUtil<Report> util = new ExcelUtil<Report>(Report.class);
        util.exportExcel(response, list, "报告数据");
    }

    /**
     * 获取报告详细信息
     */
//    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/{reportId}")
    public AjaxResult getInfo(@PathVariable("reportId") Long reportId)
    {
        Report report = reportService.selectReportByReportId(reportId);
//        if (report.getPPhone()!=null&&!"".equals(report.getPPhone())&&(report.getPPhone().length()==14||report.getPPhone().length()==15)){
//            report.setPPhone(report.getPPhone().substring(0,11));
//        }
        return AjaxResult.success(report);
    }

    /**
     * 获取报告详细信息
     */
//    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/getInfoByPid/{pId}")
    public AjaxResult getInfoByPid(@PathVariable("pId") String pId)
    {
        Report report = reportService.selectReportByPId(pId);
//        if (report.getPPhone()!=null&&!"".equals(report.getPPhone())&&(report.getPPhone().length()==14||report.getPPhone().length()==15)){
//            report.setPPhone(report.getPPhone().substring(0,11));
//        }
        return AjaxResult.success(report);
    }

    /**
     * 新增报告
     */
//    @PreAuthorize("@ss.hasPermi('report:report:add')")
    @Log(title = "报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Report report)
    {
        return toAjax(reportService.insertReport(report));
    }



    /**
     * 修改报告
     */
//    @PreAuthorize("@ss.hasPermi('report:report:edit')")
    @Log(title = "报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Report report) throws Exception {
//        User currentUser = ShiroUtils.getSysUser();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String s = report.getpId();
        List<Doctor> doctors = null;
        //当前报告信息
        Report report1 = reportService.selectReportByPId(s);
        report.setReportId(report1.getReportId());
        Date date = new Date();
        report.setReportTime(date);
        //患者请求医生
        Report report2 = reportService.selectReportByPId(report.getpId());
        StringBuilder stringBuilder = new StringBuilder();
        if (report2.getPPhone().length()==14||report2.getPPhone().length()==15){
            stringBuilder.append(report2.getPPhone(), 0, 11);
        }else {
            stringBuilder.append(report2.getPPhone());
        }
        SysUser sysUser = sysUserMapper.selectUserByPhone(String.valueOf(stringBuilder));
        Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(report2.getdPhone());
        Patient patient = patientService.selectPatientByPatientPhone(report2.getPPhone());

//        if (sysUser==null){
//            PatientRelationship patientRelationship = patientRelationshipMapper.selectFatherPhonePatientRelationship(report2.getPPhone());
//            sysUser = sysUserMapper.selectUserByPhone(patientRelationship.getFatherPhone());
//        }
        SysUser sysUser1 = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
        if(report.getDiagnosisStatus()==2){

            //判断用户是否存在服务次数
            VipPatient vipPhone = vipPatientService.findVipPhone(sysUser1.getPhonenumber());
            if (vipPhone==null){
                if (sysUser1.getDetectionNum()==0){
                    return AjaxResult.error("用户服务次数不足");
                }
            }
            if (vipPhone!=null){
                if (vipPhone.getVipNum()==0){
                    return AjaxResult.error("用户服务次数不足");
                }
            }

            //选择医院加入公共抢单
            if(report.getHospital()!=null){
                Doctor doctor = new Doctor();
                doctor.getHospitalNameList().add(report.getHospital());
                doctors = doctorService.selectDoctorList(doctor);
                if(doctors!=null && doctors.size()!=0){

                    if (report.getpId()!=null&&!"".equals(report.getpId())){
                        wxMsgRunConfig.redisAdd(report.getpId(),doctors);
                    }

                } else{
                    return AjaxResult.error("当前医院平台无医生");
                }
            }else {
                return AjaxResult.error("请先选择医院医院");
            }
            //咨询医生次数减一
                vipPatientController.detectionNumSubtract(sysUser1.getPhonenumber());

                int i = reportService.updateReport(report);
                return toAjax(i);


        } else if(report.getDiagnosisStatus()==3){ //拒绝逻辑
            Detection detection = new Detection();
            detection.setDetectionPid(report1.getpId());
            List<Detection> detections = detectionService.selectDetectionList(detection);
            if(detections!=null && detections.size()!=0){
                detectionService.deleteDetectionByDetectionId(detections.get(detections.size()-1).getDetectionId());
            }
            NotDealWith notDealWith = new NotDealWith();
            notDealWith.setPid(s);
            notDealWith.setDoctorPhone(report.getdPhone());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            notDealWith.setRefuseTime(calendar.getTime());
            notDealWith.setRefuseReason(report.getDiagnosisConclusion());
            notDealWithService.insertNotDealWith(notDealWith);


//            sysUserMapper.updateDetectionNumAdd(sysUser.getPhonenumber));
            if (sysUser==null){
                WxUtil.send(String.valueOf(stringBuilder));
            }else {
                try {
                    wxPublicRequest.sendMsg(doctor1.getHospital(),sysUser.getOpenId(),patient.getPatientName(),"心电图检测","诊断被拒");
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            return toAjax(reportService.updateReportNull(report));
        }else if(report.getDiagnosisStatus()==1){//医生诊断
            reportService.updateReport(report);
            if (sysUser==null){
                WxUtil.send(String.valueOf(stringBuilder));
            }else {
                try {
                    wxPublicRequest.sendMsg(doctor1.getHospital(),sysUser.getOpenId(),patient.getPatientName(),"心电图检测","诊断完成");
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
        return toAjax(1);
    }

    /**
     * 患者提交报告到大厅
     *
     */
    @PutMapping("/dataLabby")
    public AjaxResult dataLabby(@RequestBody Report report)
    {
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
    public AjaxResult remove(@PathVariable Long[] reportIds)
    {
        return toAjax(reportService.deleteReportByReportIds(reportIds));
    }

    /**
     * 常用术语
     */
    @GetMapping("/getCommonTerms")
    public AjaxResult getCommonTerms()
    {
        //正常
        String[] normal;
        //心律
        String[] heart_rhythm;
        //心律不齐
        String[] arrhythmia;
        //早搏逸搏
        String[] premature_escape;
        //颤动扑动
        String[] flutter;
        //ST、T改变
        String[] ST_T_change;
        //房室肥大
        String[] atrioventricular_hypertrophy;
        //心肌梗死
        String[] myocardial_infarction;
        //传导阻滞
        String[] conduction_block;
        //危急值
        String[] critical_value;
        //其它
        String[] other;
        //选择性建议
        String[] ad;
        //返回map
        HashMap<String, String[]> resMap = new HashMap<>();
        normal="正常心电图,大致正常心电图".split(",");
        heart_rhythm="窦性心律,异位心律,房性心律,室性心律,交界性心律,起搏心律".split(",");
        arrhythmia="窦性心律不齐, 心动过速, 心动过缓, 室性心动过速, 室上性心动过速, 交界性心动过速, 房性心动过速".split(", ");
        premature_escape="房性逸搏, 房性早搏, 房性停搏, 室性逸搏, 室性早搏, 交界性逸搏, 交界性早搏, 交界性停搏, 窦性停搏, 心室停搏, 全心停搏".split(", ");
        flutter="心房颤动, 心房扑动, 心室颤动, 心室扑动".split(", ");
        ST_T_change="ST改变, ST-T改变, T波异常, QT间期延长, QTc间期延长, 可见U波".split(", ");
        atrioventricular_hypertrophy="左心室肥大,左心房肥大,右心室肥大,右心房肥大,双心室肥大,双心房肥大".split(",");
        myocardial_infarction="亚急性,急性,陈旧性,前间壁心肌梗死,间壁心肌梗死,前侧壁心肌梗死,广泛前壁心肌梗死,前壁心肌梗死,下壁心肌梗死,下后壁心肌梗死,后壁心肌梗死,下侧壁心肌梗死,高侧壁心肌梗死,侧壁心肌梗死,心内膜下心肌梗死,右室心肌梗死,非ST抬高型心肌梗死,心尖部心肌梗死".split(",");
        conduction_block="I度房室传导阻滞，II度房室传导阻滞，II度I型房室传导阻滞，II度II型房室传导阻滞，III度房室传导阻滞，窦房传导阻滞，高度房室传导阻滞，左前分支传导阻滞，左后分支传导阻滞，完全性左束支传导阻滞，不完全性左束支传导阻滞，完全性右束支传导阻滞，不完全性右束支传导阻滞，非特异性室内传导阻滞，室内差异性传导".split("，");
        critical_value="心电图危急值".split(",");
        other="预激综合症, 心室预激波, 早期复极".split(", ");
        ad="建议到上级医院复查, 建议做动态心电图, 建议做12导联常规心电图, 信号存在干扰，请重新采集".split(", ");

        resMap.put("正常",normal);
        resMap.put("心律",heart_rhythm);
        resMap.put("心律不齐",arrhythmia);
        resMap.put("早搏逸搏",premature_escape);
        resMap.put("颤动扑动",flutter);
        resMap.put("ST、T改变",ST_T_change);
        resMap.put("房室肥大",atrioventricular_hypertrophy);
        resMap.put("心肌梗死",myocardial_infarction);
        resMap.put("传导阻滞",conduction_block);
        resMap.put("危急值",critical_value);
        resMap.put("其它",other);
        resMap.put("选择性建议",ad);

        return AjaxResult.success(resMap);
    }



    /**
     * 获取时间信息
     */
    @GetMapping("/get_week_info")
    public AjaxResult getWeekInfo(Report rep) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        int weekIdx = calendar.get(Calendar.DAY_OF_WEEK) - 1;//1 星期一
//        if(weekIdx<0) weekIdx=0;
//        int flag=1;
//        int index=0;
//        ArrayList<String> weekDay = new ArrayList<>();
//        HashMap<String, Integer> normal = new HashMap<>();
//        HashMap<String, Integer> abnormal = new HashMap<>();
//        HashMap<String, Object> params = new HashMap<>();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String maxTime="0000";
//        String minTime="9999";
//        for (int i = 0; i < 7; i++) {
//            Date date = DateUtils.addDays(new Date(), index);
//            String formatDate = format.format(date);
//            if(maxTime.compareTo(formatDate)<0) maxTime=formatDate;
//            if(minTime.compareTo(formatDate)>0) minTime=formatDate;
//            normal.put(formatDate,0);
//            abnormal.put(formatDate,0);
//            weekIdx+=flag;
//            index+=flag;
//            if(weekIdx==8){
//                weekIdx=0;
//                flag=-1;
//                index=-1;
//            }
//        }

//        Report report = new Report();
////        params.put("beginReportTime",minTime);
////        params.put("endReportTime",maxTime);
////        report.setParams(params);
//        report.setPPhone(rep.getPPhone());
//        List<Report> reports = reportService.selectReportList(rep);
//        String key="";
//        HashMap<String, Object> result = new HashMap<>();
//
//        for (Report re : reports) {
//            key= new SimpleDateFormat("yyyy-MM-dd").format(re.getReportTime());
//
//            if(re.getIntelligentDiagnosis()!=null && re.getIntelligentDiagnosis().contains("正常")){
//                if(normal.containsKey(key)){
//                    normal.put(key,normal.get(key)+1);
//                } else{
//                    normal.put(key,1);
//                }
//            }
//            else {
//                if(abnormal.containsKey(key)){
//                    abnormal.put(key,abnormal.get(key)+1);
//                } else{
//                    abnormal.put(key,1);
//                }
//            }
//        }
//        result.put("normal",normal);
//        result.put("abnormal",abnormal);
        PInfoVO pInfoVO = patientManagementService.updatePatient(rep);


        return AjaxResult.success(pInfoVO);
    }
    @GetMapping("/getDealWithInfo")
    public AjaxResult getDealWithInfo(Report rep) {

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
        params.put("beginRefuseTime",rep.getStartReportTime());
        params.put("endRefuseTime",rep.getEndReportTime());
        notDealWith.setParams(params);
        List<NotDealWith> notDealWiths = notDealWithService.selectNotDealWithList(notDealWith);
        int refuse = notDealWiths.size();


        result.put("已处理数据",dealSize);
        result.put("未处理数据",notDealSize);
        result.put("已拒绝数据",refuse);

        return AjaxResult.success(result);
    }

    @GetMapping("/doctorFinishList")
    public TableDataInfo doctorFinishList(Report report)
    {
        startPage();
        List<Report> patientPhone = reportService.groupByPatientPhone(report.getdPhone());

        ArrayList<ReportM> resList = new ArrayList<>();
        ReportM reportM;
        PatientManagement patientManagement;
        Patient patient;
        Date birthDay;
        MedicalHistory medicalHistory;
        MedicalData medicalData=new MedicalData();
        //病种
        ArrayList<String> medical;
        //病种和id映射
        HashMap<String, String> medicalHashMap = new HashMap<>();
        List<MedicalData> medicalData1 = medicalDataService.selectMedicalDataList(medicalData);
        for (MedicalData data : medicalData1) {
            medicalHashMap.put(data.getMedicalCode().toString(),data.getMedicalName());
        }

        for (Report r : patientPhone) {
            medical = new ArrayList<>();
            reportM=new ReportM();
            patient = patientService.selectPatientByPatientPhone(r.getPPhone());
            medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(r.getPPhone());

            if(medicalHistory!=null && medicalHistory.getPastMedicalHistory()!=null && !Objects.equals(medicalHistory.getPastMedicalHistory(), "")){
                String[] split = medicalHistory.getPastMedicalHistory().split(",");
                for (String s : split) {
                    medical.add(medicalHashMap.get(s));
                }
                reportM.setMedicalHistory(medical);
            }
            else{
                medical.add("无");
                reportM.setMedicalHistory(medical);
            }
            birthDay = patient.getBirthDay();
            if(birthDay != null)
                reportM.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
            else {
                reportM.setPatientAge(patient.getPatientAge());
            }
            reportM.setPatientName(patient.getPatientName());
            reportM.setPatientSex(patient.getPatientSex());
            StringBuilder stringBuilder = new StringBuilder();
//            if (r.getPPhone().length()==14||r.getPPhone().length()==15){
//                stringBuilder.append(r.getPPhone() ,0,11);
//            }
//            else {
                stringBuilder.append(r.getPPhone());
//            }
            reportM.setPatientPhone(String.valueOf(stringBuilder));
            resList.add(reportM);
        }
        return getTable(resList,new PageInfo(patientPhone).getTotal());
    }

    @GetMapping("/wx")
    public AjaxResult Wx() throws ParseException {
        String msg="17337345250";
        WxUtil.sendAdvice(msg);
        return AjaxResult.success();
    }


    @GetMapping("/docOrPatientList")
    public AjaxResult docOrPatientList(){
        List<PatientManagement> patientManagements = patientManagementService.selectPatientManagementList();
        for (PatientManagement management : patientManagements) {
            if(DateUtil.isValidDate(management.getBirthDay())){
                try {
                    management.setPatientAge(String.valueOf(DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(management.getBirthDay()))));
                } catch (ParseException e) {
                    System.out.println(1);
                }
            }
//            if (management.getPatientPhone().length()==14||management.getPatientPhone().length()==15){
//                management.setPatientPhone(management.getPatientPhone().substring(0,11));
//            }
        }
        return AjaxResult.success(patientManagements);
    }

    @GetMapping("/docUpdate")
    public AjaxResult docUpdate( Report report){
        Report report1 = reportService.selectReportByPId(report.getpId());
        Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(report.getdPhone());
        report.setReportId(report1.getReportId());
        report.setDiagnosisStatus(2L);
        report.setDiagnosisDoctor(doctor1.getDoctorName());
        report.setReportTime(new Date());
        report.setStartTime(new Date());
        Doctor doctor = new Doctor();
        doctor.getHospitalNameList().add(report.getHospital());
        List<Doctor> doctors = doctorService.selectDoctorList(doctor);
        //定时器, 30分钟无医生诊断, 换医生诊断.
        wxMsgRunConfig.redisDTStart(report.getpId(),doctors);
        WxUtil.send(report.getdPhone());
        int i = reportService.updateReport(report);
        return AjaxResult.success();
    }
}
