package com.ruoyi.xindian.report.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;
import com.ruoyi.xindian.mark_info.domain.User;
import com.ruoyi.xindian.medical.domain.MedicalData;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalDataService;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.report.domain.NotDealWith;
import com.ruoyi.xindian.report.domain.ReportM;
import com.ruoyi.xindian.report.service.INotDealWithService;
import com.ruoyi.xindian.util.DateUtil;
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
    private IAppDataService appDataService;

    /**
     * 查询报告列表
     */
    @PreAuthorize("@ss.hasPermi('report:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(Report report)
    {
        List<Report> list;
        startPage();
        list = reportService.selectReportList(report);

        ArrayList<ReportM> resList = new ArrayList<>();
        ReportM reportM;
        PatientManagement patientManagement;
        Patient patient;
        Date birthDay;
        MedicalHistory medicalHistory;
        MedicalData medicalData=new MedicalData();
        //病种
        ArrayList<String> medical = new ArrayList<>();
        //病种和id映射
        HashMap<String, String> medicalHashMap = new HashMap<>();
        List<MedicalData> medicalData1 = medicalDataService.selectMedicalDataList(medicalData);
        for (MedicalData data : medicalData1) {
            medicalHashMap.put(data.getMedicalCode().toString(),data.getMedicalName());
        }

        for (Report r : list) {
            medical.clear();
            reportM=new ReportM();
            patientManagement = patientManagementService.selectPatientManagementByPId(r.getpId());

            BeanUtils.copyProperties(r,reportM);
            if(patientManagement != null){
                medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(patientManagement.getPatientPhone());
                if(medicalHistory!=null && medicalHistory.getPastMedicalHistory()!=null){
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

                reportM.setPatientPhone(patientManagement.getPatientPhone());
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
    @PreAuthorize("@ss.hasPermi('report:report:export')")
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
    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/{reportId}")
    public AjaxResult getInfo(@PathVariable("reportId") Long reportId)
    {
        return AjaxResult.success(reportService.selectReportByReportId(reportId));
    }

    /**
     * 获取报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/getInfoByPid/{pId}")
    public AjaxResult getInfoByPid(@PathVariable("pId") String pId)
    {
        return AjaxResult.success(reportService.selectReportByPId(pId));
    }

    /**
     * 新增报告
     */
    @PreAuthorize("@ss.hasPermi('report:report:add')")
    @Log(title = "报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Report report)
    {
        return toAjax(reportService.insertReport(report));
    }

    /**
     * 修改报告
     */
    @PreAuthorize("@ss.hasPermi('report:report:edit')")
    @Log(title = "报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Report report)
    {
//        User currentUser = ShiroUtils.getSysUser();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String phonenumber = loginUser.getUser().getPhonenumber();
        String s = report.getpId();
        Report report1 = reportService.selectReportByPId(s);
        report.setReportId(report1.getReportId());
        //咨询医生次数减一
        if(phonenumber.equals(report1.getPPhone())){
            AppData appData = appDataService.selectAppDataByPatientPhone(phonenumber);
            Long questionNum = appData.getQuestionNum();
            if(questionNum==0){
                return AjaxResult.error("咨询次数已用完");
            }
            appData.setQuestionNum(questionNum-1);
            appDataService.updateAppData(appData);
        }
        //拒绝逻辑
        if(report.getDiagnosisStatus()==3){
            //退回次数加一
            AppData appData = appDataService.selectAppDataByPatientPhone(report1.getPPhone());
            Long questionNum = appData.getQuestionNum();
            appData.setQuestionNum(questionNum + 1);
            appDataService.updateAppData(appData);

            NotDealWith notDealWith = new NotDealWith();
            notDealWith.setPid(s);
            notDealWith.setDoctorPhone(report.getdPhone());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            notDealWith.setRefuseTime(calendar.getTime());
            notDealWith.setRefuseReason(report.getDiagnosisConclusion());
            notDealWithService.insertNotDealWith(notDealWith);
//            report.setDiagnosisConclusion("");
            return toAjax(reportService.updateReport(report));
        }
        //医生结论
        return toAjax(reportService.updateReport(report));
    }

    /**
     * 删除报告
     */
    @PreAuthorize("@ss.hasPermi('report:report:remove')")
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

        return AjaxResult.success(resMap);
    }
    /**
     * 获取时间信息
     */
    @GetMapping("/get_week_info")
    public AjaxResult getWeekInfo(Report rep) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        int weekIdx = calendar.get(Calendar.DAY_OF_WEEK) - 1;//1 星期一
//        if(weekIdx<0) weekIdx=0;
//        int flag=1;
//        int index=0;
//        ArrayList<String> weekDay = new ArrayList<>();
        HashMap<String, Integer> normal = new HashMap<>();
        HashMap<String, Integer> abnormal = new HashMap<>();
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

        Report report = new Report();
//        params.put("beginReportTime",minTime);
//        params.put("endReportTime",maxTime);
//        report.setParams(params);
        report.setPPhone(rep.getPPhone());
//        report.set
        List<Report> reports = reportService.selectReportList(report);
        String key="";
        HashMap<String, Object> result = new HashMap<>();

        for (Report re : reports) {
            key= new SimpleDateFormat("yyyy-MM-dd").format(re.getReportTime());

            if(re.getIntelligentDiagnosis()!=null && re.getIntelligentDiagnosis().contains("正常")){
                if(abnormal.containsKey(key)){
                    normal.put(key,normal.get(key)+1);
                } else{
                    normal.put(key,1);
                }
            }
            else {
                if(abnormal.containsKey(key)){
                    abnormal.put(key,abnormal.get(key)+1);
                } else{
                    abnormal.put(key,1);
                }
            }
        }
        result.put("normal",normal);
        result.put("abnormal",abnormal);
        return AjaxResult.success(result);
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
        params.put("beginRefuseTime",rep.getParams().get("beginReportTime"));
        params.put("endRefuseTime",rep.getParams().get("endReportTime"));
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
            reportM.setPatientPhone(r.getPPhone());
            resList.add(reportM);
        }
        return getTable(resList,new PageInfo(patientPhone).getTotal());
    }

}
