package com.ruoyi.xindian.patient_management.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.hospital.service.IDepartmentService;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient.service.SingleHistoryDataService;
import com.ruoyi.xindian.patient_management.domain.*;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.patient_management.service.IWeeklyService;
import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.patient_management.vo.PatientManagementVO;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.pmEcgData.service.IPmEcgDataService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.util.PhoneCheckUtils;
import com.ruoyi.xindian.util.RoleUtils;
import com.ruoyi.xindian.util.WxUtil;
import com.ruoyi.xindian.verify.domain.SxReport;
import com.ruoyi.xindian.verify.service.SxReportService;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 患者管理Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/patient_management/patient_management")
public class PatientManagementController extends BaseController {
    @Autowired
    private IPatientManagementService patientManagementService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IWeeklyService weeklyService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IEquipmentService equipmentService;

    @Autowired
    private IHospitalService hospitalService;

    @Autowired
    private TokenService tokenService;

    @Resource
    private HospitalMapper hospitalMapper;

    @Autowired
    private AesUtils aesUtils;


    @Resource
    private IMedicalHistoryService medicalHistoryService;

    @Autowired
    private IPmEcgDataService pmEcgDataService;


    @Resource
    private WXPublicRequest wxPublicRequest;
    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private SxReportService sxReportService;
    @Resource
    private SingleHistoryDataService singleHistoryDataService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    /**
     * 查询患者管理列表
     */
//    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientManagement patientManagement, HttpServletRequest request, Integer pageNum, Integer pageSize) throws Exception {
//        startPage();
//        List<PatientManagement> list_add = new ArrayList<>();
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<PatientManagement> list = new ArrayList<>();
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();

        if (patientManagement.getHospitalCode() != null && !"".equals(patientManagement.getHospitalCode())) {
            patientManagement.getHospitalCodeList().add(patientManagement.getHospitalCode());
        }

//        Long userId = getUserId();
        SysUser sysUser = userService.selectUserById(loginUser.getUser().getUserId());
        if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 101) {
            //101---医院
            String hospitalCode = sysUser.getHospitalCode();
            patientManagement.getHospitalCodeList().clear();
            patientManagement.getHospitalCodeList().add(hospitalCode);
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(hospitalCode);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals != null && !associatedHospitals.isEmpty()) {
                for (AssociatedHospital c : associatedHospitals) {
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.getHospitalCodeList().add(hospital1.getHospitalCode());
                }
            }
            String code = patientManagement.getHospitalCode();
            if (code != null && !"".equals(code)) {
                List<String> patientList = patientManagement.getHospitalCodeList();
                if (patientList != null && !patientList.isEmpty()) {
                    for (String c : patientList) {
                        if (c.equals(patientManagement.getHospitalCode())) {
                            patientManagement.getHospitalCodeList().clear();
                            patientManagement.getHospitalCodeList().add(patientManagement.getHospitalCode());
                            break;
                        }
                    }
                }
            }
        } else if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 106) {
            // 106---科室账号
            Equipment equipment = new Equipment();
            equipment.setHospitalCode(sysUser.getHospitalCode());
            equipment.setDepartmentCode(sysUser.getDepartmentCode());
            List<Equipment> equipmentList = equipmentService.selectEquipmentList(equipment);
            List<String> equipmentCodeList = new ArrayList<>();
            for (Equipment equipment1 : equipmentList) {
                equipmentCodeList.add(equipment1.getEquipmentCode());
            }
            patientManagement.setEquipmentCodeList(equipmentCodeList);
        } else if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 104) {
            //医生
            String phonenumber = sysUser.getPhonenumber();
            patientManagement.setDPhone(aesUtils.decrypt(phonenumber));
            if (patientManagement.getDiagnosisStatus() != null && patientManagement.getDiagnosisStatus() == 0)
                patientManagement.setDiagnosisStatus(2L);
        } else if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 1104) {
            patientManagement.getHospitalCodeList().add(sysUser.getHospitalCode());
        } else if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 1105) {
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(sysUser.getHospitalCode());
            if (hospital == null) {
                return getTable(resList, 0);
            }
            Doctor doctor = new Doctor();
            doctor.getHospitalNameList().add(hospital.getHospitalName());
            List<Doctor> doctors = doctorService.selectDoctorListNot(doctor);
            if (doctors != null && !doctors.isEmpty()) {
                for (Doctor doctor1 : doctors) {
                    patientManagement.getBindingDoctors().add(doctor1.getDoctorPhone());
                }
            }
        }
//        else if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 1106) {
//            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(sysUser.getHospitalCode());
//            if (hospital == null) {
//                return getTable(resList, 0);
//            }
//            patientManagement.getHospitalCodeList().add(hospital.getHospitalCode());
//        }
        getEncryptManagement(patientManagement);

        startPage();
        if (null == patientManagement.getEcgType()) {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        } else if (patientManagement.getEcgType().equals("DECGsingle")) {
            list = patientManagementService.selectPatientManagementListDECGsingle(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECG12")) {
            if (StringUtils.isNotEmpty(patientManagement.getIsSelect()) && patientManagement.getIsSelect().equals("1") && SysUser.isAdmin(loginUser.getUserId()) && StringUtils.isNotEmpty(patientManagement.getPatientName())) {
                return getRedisTable(patientManagement, resList, pageNum, pageSize);
            } else {
                list = patientManagementService.selectPatientManagementListJECG12(patientManagement);
            }

        } else if (patientManagement.getEcgType().equals("JECGsingle")) {

            if (StringUtils.isNotEmpty(patientManagement.getIsSelect()) && patientManagement.getIsSelect().equals("1") && SysUser.isAdmin(loginUser.getUserId()) && StringUtils.isNotEmpty(patientManagement.getPatientName())) {
                return getRedisTable(patientManagement, resList, pageNum, pageSize);

            } else {
                list = patientManagementService.selectPatientManagementJECGsingle(patientManagement);
            }

        } else if (patientManagement.getEcgType().equals("JECG4")) {

            if (StringUtils.isNotEmpty(patientManagement.getIsSelect()) && patientManagement.getIsSelect().equals("1") && SysUser.isAdmin(loginUser.getUserId()) && StringUtils.isNotEmpty(patientManagement.getPatientName())) {
                return getRedisTable(patientManagement, resList, pageNum, pageSize);
            } else {
                list = patientManagementService.selectPatientManagementJECG4(patientManagement);
            }

        } else if (patientManagement.getEcgType().equals("DECG12")) {
            list = patientManagementService.selectPatientManagementListDECG12(patientManagement);
        } else {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        }
        return getTableDataInfo(list, resList, 2);
    }


    private TableDataInfo getRedisTable(PatientManagement patientManagement, ArrayList<PatientManagmentDept> resList, int pageNum, int pageSize) throws Exception {
        if (StringUtils.isNotEmpty(patientManagement.getPatientName())) {
            String patientName = aesUtils.decrypt(patientManagement.getPatientName());
            List<PatientManagement> list1 = new ArrayList<>();
            String ecgType = patientManagement.getEcgType();
            String diagnosisStatus = String.valueOf(patientManagement.getDiagnosisStatus());
            if (Boolean.TRUE.equals(redisTemplate.hasKey("patientManagementByName" + ecgType + ":" + patientName + "=" + diagnosisStatus))) {
                List<Object> listKeys = redisTemplate.opsForList().range("patientManagementByName" + ecgType + ":" + patientName + "=" + diagnosisStatus, (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);
                resList = getEncryptManagement(listKeys);
                return getTable(resList, redisTemplate.opsForList().size("patientManagementByName" + ecgType + ":" + patientName + "=" + diagnosisStatus));
            }
            List<Object> patientList = redisTemplate.opsForList().range("patientManagement" + ecgType, 0, -1);
            if (patientList != null) {
                for (Object pat : patientList) {
                    PatientManagement patientManagement1 = (PatientManagement) pat;
                    if (patientManagement1 == null || patientManagement1.getPatientName() == null || patientManagement1.getDiagnosisStatus() == null) {
                        continue;
                    }
                    if (patientManagement1.getPatientName().contains(patientName) && patientManagement1.getDiagnosisStatus().equals(patientManagement.getDiagnosisStatus())) {
                        list1.add(patientManagement1);
                    }
                }
            }
            if (patientList != null && list1.size() == patientList.size()) {
                List<Object> listKeys = redisTemplate.opsForList().range("patientManagement" + ecgType, (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);
                resList = getEncryptManagement(listKeys);
                return getTable(resList, patientList.size());
            } else {
                for (PatientManagement s : list1) {
                    redisTemplate.opsForList().rightPush("patientManagementByName" + ecgType + ":" + patientName + "=" + diagnosisStatus, s);
                }
                List<Object> listKeys = redisTemplate.opsForList().range("patientManagementByName" + ecgType + ":" + patientName + "=" + diagnosisStatus, (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);
                resList = getEncryptManagement(listKeys);
                return getTable(resList, redisTemplate.opsForList().size("patientManagementByName" + ecgType + ":" + patientName + "=" + diagnosisStatus));
            }
        }
        return getTable(resList, 0);
    }


    private ArrayList<PatientManagmentDept> getEncryptManagement(List<Object> list) throws Exception {
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return resList;
        }
        for (Object pat : list) {

            if (pat instanceof PatientManagement) {

                PatientManagement patientManagement = (PatientManagement) pat;
                PatientManagmentDept patientManagmentDept;
                patientManagmentDept = new PatientManagmentDept();
                BeanUtils.copyProperties(patientManagement, patientManagmentDept);

                if (patientManagement.getTimeDuration() == null) {
                    patientManagmentDept.setAcquisitionDuration("报告未生成");
                } else {
                    patientManagmentDept.setAcquisitionDuration("记录时长: " + DateUtil.timeToString(patientManagement.getTimeDuration()));
                }
                resList.add(patientManagmentDept);
            }
        }
        return resList;
    }

    private TableDataInfo getTableDataInfo(List<PatientManagement> list, ArrayList<PatientManagmentDept> resList, Integer type) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        PatientManagmentDept patientManagmentDept;
        for (PatientManagement management : list) {
            if (DateUtil.isValidDate(management.getBirthDay())) {
                try {
                    management.setPatientAge(String.valueOf(DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(management.getBirthDay()))));
                } catch (ParseException e) {
                    System.out.println(1);
                }
            }

//            try {
//                String ecgAnalysisData = management.getEcgAnalysisData();
//                if (StringUtils.isNotEmpty(ecgAnalysisData)){
//                    JSONObject jsonObject = JSONObject.parseObject(ecgAnalysisData);
//
//                    Object o = jsonObject.get("平均心率");
//                    if (o!=null){
//                        management.setXl(o.toString());
//                    }else {
//                        management.setXl("--");
//                    }
//                    Object o1 = jsonObject.get("p_xingeng");
//                    if (o1!=null){
//                        management.setXg(o1.toString());
//                    }else {
//                        management.setXg("--");
//                    }
//
//                }
//            }catch (Exception e){
//
//            }


            if (management.getPatientPhone() != null && !management.getPatientPhone().isEmpty()) {
                management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));
            }
            if (management.getPatientName() != null && !management.getPatientName().isEmpty()) {
                management.setPatientName(aesUtils.decrypt(management.getPatientName()));
            }
            if (management.getDiagnosisDoctor() != null && !management.getDiagnosisDoctor().isEmpty()) {
                management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
            }
            if (management.getFamilyPhone() != null && !management.getFamilyPhone().isEmpty()) {
                management.setFamilyPhone(aesUtils.decrypt(management.getFamilyPhone()));
            }
            if (StringUtils.isNotEmpty(management.getDoctorPhone())) {
                management.setDoctorPhone(aesUtils.decrypt(management.getDoctorPhone()));
            }
            if (StringUtils.isNotEmpty(management.getDPhone())) {
                management.setDPhone(aesUtils.decrypt(management.getDPhone()));
            }
            if (type != null && type == 1) {
                MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(aesUtils.encrypt(management.getPatientPhone()));
                if (medicalHistory != null) {
                    management.setWeight(medicalHistory.getWeight());
                    management.setHeight(medicalHistory.getHeight());
                }
            }

            try {
                management.setSxStatus(0);
                management.setSxPayStatus(0);
                if (StringUtils.isNotEmpty(management.getEcgType()) && management.getEcgType().contains("DECG")) {
                    SxReport sxReport = new SxReport();
                    sxReport.setPatientPhone(aesUtils.encrypt(management.getPatientPhone()));
                    String format = simpleDateFormat.format(management.getConnectionTime());
                    sxReport.setUploadStart(format);
                    List<SxReport> reportList = sxReportService.getReportList(sxReport);
                    if (reportList != null && !reportList.isEmpty()) {
                        management.setSxStatus(1);
                    } else {
                        management.setSxStatus(0);
                    }
                    if (management.getSxReportStatus() != null && management.getSxReportStatus() == 1) {
                        management.setSxPayStatus(1);
                    } else {
                        management.setSxPayStatus(0);
                    }
                }
            } catch (Exception e) {
                management.setSxPayStatus(0);
                management.setSxPayStatus(0);
            }
            patientManagmentDept = new PatientManagmentDept();
            BeanUtils.copyProperties(management, patientManagmentDept);

            if (management.getTimeDuration() == null) {
                patientManagmentDept.setAcquisitionDuration("报告未生成");
            } else {
                patientManagmentDept.setAcquisitionDuration("记录时长: " + DateUtil.timeToString(management.getTimeDuration()));
            }

            resList.add(patientManagmentDept);
        }
        long total = new PageInfo(list).getTotal();
        return getTable(resList, total);
    }

    private void getEncryptManagement(PatientManagement patientManagement) throws Exception {
        if (patientManagement.getDiagnosisDoctor() != null && !patientManagement.getDiagnosisDoctor().isEmpty()) {
            patientManagement.setDiagnosisDoctor(aesUtils.encrypt(patientManagement.getDiagnosisDoctor()));
        }
        if (patientManagement.getDoctorPhone() != null && !patientManagement.getDoctorPhone().isEmpty()) {
            patientManagement.setDoctorPhone(aesUtils.encrypt(patientManagement.getDoctorPhone()));
        }
        if (patientManagement.getPatientPhone() != null) {
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName() != null) {
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getPatPhone() != null && !patientManagement.getPatPhone().isEmpty()) {
            patientManagement.setPatPhone(aesUtils.encrypt(patientManagement.getPatPhone()));
        }
        if (patientManagement.getDPhone() != null && !patientManagement.getDPhone().isEmpty()) {
            patientManagement.setDPhone(aesUtils.encrypt(patientManagement.getDPhone()));
        }
    }


    /**
     * 查询患者管理列表
     */
//    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:list')")
    @GetMapping("/listP")
    public TableDataInfo listP(PatientManagement patientManagement, HttpServletRequest request) throws Exception {

        List<PatientManagement> list = new ArrayList<>();
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();
        if (patientManagement.getPatientPhone() != null) {
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName() != null) {
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getPatPhone() != null && !"".equals(patientManagement.getPatPhone())) {
            patientManagement.setPatPhone(aesUtils.encrypt(patientManagement.getPatPhone()));
        }
        startPage();
        if (null == patientManagement.getEcgType()) {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        } else if (patientManagement.getEcgType().equals("DECGsingle")) {
            list = patientManagementService.selectPatientManagementListDECGsingle(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECG12")) {
            list = patientManagementService.selectPatientManagementListJECG12(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECGsingle")) {
            list = patientManagementService.selectPatientManagementJECGsingle(patientManagement);
        } else if (patientManagement.getEcgType().equals("DECG12")) {
            list = patientManagementService.selectPatientManagementListDECG12(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECGDUO")) {
            list = patientManagementService.selectPatientManagementListJECGDUO(patientManagement);
        } else {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        }
        return getTableDataInfo(list, resList, 1);
    }


    /**
     * 社区版获取患者信息
     *
     * @param patientManagement
     * @return
     * @throws Exception
     */
    @GetMapping("/getEquipmentCodeList")
    public TableDataInfo getEquipmentCodeList(PatientManagement patientManagement, Integer pageSize, Integer pageNum) throws Exception {

        List<PatientManagement> list = new ArrayList<>();
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();
        if (patientManagement.getPatientPhone() != null) {
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (StringUtils.isNotEmpty(patientManagement.getDoctorPhone())) {
            patientManagement.setDoctorPhone(aesUtils.encrypt(patientManagement.getDoctorPhone()));
        }
//        startPage();
        list = patientManagementService.selectPatientManagementSPList(patientManagement, pageSize, pageNum);

        PatientManagmentDept patientManagmentDept;

        if (StringUtils.isNotEmpty(patientManagement.getPatientPhone())) {
            patientManagement.setPatientName(patientManagement.getPatientPhone());
            patientManagement.setPatientPhone(null);
            if (list == null || list.size() == 0) {
                startPage();
                list = patientManagementService.selectPatientManagementSPList(patientManagement, pageSize, pageNum);
            }
        }
        return getTableDataInfo(list, resList, 1);
    }

    /**
     * web端查询指定医生诊断的相关报告
     *
     * @param patientManagement
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/listPatientTimeList")
    public TableDataInfo listPatientTimeList(PatientManagement patientManagement, HttpServletRequest request) throws Exception {

        if (patientManagement.getPatientPhone() != null) {
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName() != null) {
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getPatPhone() != null && !"".equals(patientManagement.getPatPhone())) {
            patientManagement.setPatPhone(aesUtils.encrypt(patientManagement.getPatPhone()));
        }
        if (StringUtils.isNotEmpty(patientManagement.getDoctorPhone())) {
            patientManagement.setDoctorPhone(aesUtils.encrypt(patientManagement.getDoctorPhone()));
        }
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();
        startPage();
        List<PatientManagement> list = patientManagementService.selectPatientManagementList12(patientManagement);

        PatientManagmentDept patientManagmentDept;
        Doctor doctor = new Doctor();
        Department department = new Department();
        for (PatientManagement management : list) {
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

            patientManagmentDept = new PatientManagmentDept();
            BeanUtils.copyProperties(management, patientManagmentDept);

            if (patientManagmentDept.getDoctorPhone() != null) {
                doctor.setDoctorPhone(patientManagmentDept.getDoctorPhone());
                List<Doctor> doctors = doctorService.selectDoctorList(doctor);
                if (!doctors.isEmpty()&&doctors.get(0).getDepartmentCode() != null) {
                    department.setDepartmentCode(doctors.get(0).getDepartmentCode());
                    List<Department> departments = departmentService.selectDepartmentList(department);
                    if (!departments.isEmpty()){
                        patientManagmentDept.setDept(departments.get(0).getDepartmentName());
                    }
                }
            }


            if (management.getTimeDuration() == null) {
                patientManagmentDept.setAcquisitionDuration("报告未生成");
            } else {
                patientManagmentDept.setAcquisitionDuration("记录时长: " + DateUtil.timeToString(management.getTimeDuration()));
            }


            BigDecimal bigDecimal = new BigDecimal(String.valueOf(Double.parseDouble(management.getAvgTime()) / 60));
            BigDecimal bigDecimal1 = bigDecimal.setScale(1, RoundingMode.UP);
            patientManagmentDept.setAvgTime(bigDecimal1 + "分钟");
            resList.add(patientManagmentDept);
        }
        long total = new PageInfo(list).getTotal();
        return getTable(resList, total);
    }


//    /**
//     * 导出患者管理列表
//     */
//    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:export')")
//    @Log(title = "患者管理", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, PatientManagement patientManagement) {
//
//        List<PatientManagement> list = new ArrayList<>();
//        if (getDeptId() == 200) {
//            SysUser sysUser = userService.selectUserById(getUserId());
//            String hospitalCode = sysUser.getHospitalCode();
//            patientManagement.setHospitalCode(hospitalCode);
//            list = patientManagementService.selectPatientManagementList(patientManagement);
//        } else {
//            list = patientManagementService.selectPatientManagementList(patientManagement);
//        }
////        List<PatientManagement> list = patientManagementService.selectPatientManagementList(patientManagement);
//        ExcelUtil<PatientManagement> util = new ExcelUtil<PatientManagement>(PatientManagement.class);
//        util.exportExcel(response, list, "患者管理数据");
//    }

    @GetMapping(value = "/sendMsg/{phone}")
    public AjaxResult sendMsg(@PathVariable("phone") String phone, HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (Boolean.TRUE.equals(redisTemplate.hasKey("userPhoneTimeOne:" + loginUser.getUser().getUserId()))) {
            return AjaxResult.error("请勿重复请求验证码");
        }
        if (!PhoneCheckUtils.isPhoneLegal(phone)) {
            return AjaxResult.error("手机号格式不对，请重新输入");
        }
        redisTemplate.opsForValue().set("userPhoneTimeOne:" + loginUser.getUser().getUserId(), phone, 50, TimeUnit.SECONDS);
        WxUtil.sendAdvice(phone);
//        System.out.println(phone);
        return AjaxResult.success();
    }

    /**
     * 获取患者管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:query')")
    @GetMapping(value = "/{pId}")
    public AjaxResult getInfo(@PathVariable("pId") String pId) throws Exception {
        PatientManagement management = patientManagementService.selectPatientManagementByPId(pId);
        if (management.getPatientPhone() != null && !"".equals(management.getPatientPhone())) {
            management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));
        }
        if (management.getPatientName() != null && !"".equals(management.getPatientName())) {
            management.setPatientName(aesUtils.decrypt(management.getPatientName()));
        }
        if (management.getDiagnosisDoctor() != null && !"".equals(management.getDiagnosisDoctor())) {
            management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
        }
        return AjaxResult.success(management);
    }

    /**
     * 新增患者管理
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:add')")
    @Log(title = "患者管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatientManagement management) throws Exception {
        if (management.getPatientPhone() != null && !"".equals(management.getPatientPhone())) {
            management.setPatientPhone(aesUtils.encrypt(management.getPatientPhone()));
        }
        if (management.getPatientName() != null && !"".equals(management.getPatientName())) {
            management.setPatientName(aesUtils.encrypt(management.getPatientName()));
        }
        if (management.getDiagnosisDoctor() != null && !"".equals(management.getDiagnosisDoctor())) {
            management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
        }
        return toAjax(patientManagementService.insertPatientManagement(management));
    }

    /**
     * 修改患者管理
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:edit')")
    @Log(title = "患者管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PatientManagement patientManagement) throws Exception {
        getEncryptManagement(patientManagement);

        return toAjax(patientManagementService.updatePatientManagement(patientManagement));
    }

    /**
     * 删除患者管理
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:remove')")
    @Log(title = "患者管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pIds}")
    public AjaxResult remove(@PathVariable String[] pIds) {
        return toAjax(patientManagementService.deletePatientManagementByPIds(pIds));
    }

    /**
     * 更新患者状态
     */
//    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:edit')")
    @Log(title = "患者管理", businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestBody String[] pIds) throws Exception {

        patientManagementService.updateStatusAll();
        patientService.updateMonitoringStatus();
        if (pIds.length != 0 && !pIds[0].isEmpty()) {
            patientManagementService.updateStatus(pIds);

            for (String pId : pIds) {
                PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);
                if (patientManagement != null) {
                    Patient patient = new Patient();
                    patient.setPatientName(patientManagement.getPatientName());
                    patient.setPatientPhone(patientManagement.getPatientPhone());
                    Patient patient1 = patientService.selectPatientByNameAndPhone(patient);
                    patient1.setMonitoringStatus("1");
                    patientService.updatePatient(patient1);
                }
            }
        }
        return "down";
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    public SysUser getUserInfo() throws Exception {
        SysUser sysUser = userService.selectUserById(getUserId());
        if (sysUser.getUserName() != null) {
            sysUser.setUserName(aesUtils.encrypt(sysUser.getUserName()));
        }
        if (sysUser.getPhonenumber() != null) {
            sysUser.setPhonenumber(aesUtils.encrypt(sysUser.getPhonenumber()));
        }
//        String hospitalName = sysUser.getHospitalName();
        return sysUser;
    }

    @Resource
    private ISysDictDataService dictDataService;
    /**
     * 心电大屏数据查找医院
     *
     * @param hospitalId
     * @param request
     * @return
     */
    @GetMapping(value = "/getInfoId")
    public AjaxResult getInfo(Long hospitalId, HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
       if (SysUser.isAdmin(loginUser.getUserId()) || RoleUtils.isRoleListOne(loginUser,sysDictData)){
            if (hospitalId.equals(1L)) {
                Hospital hospital = new Hospital();
                hospital.setHospitalName("所有");
                return AjaxResult.success(hospital);
            }
            Hospital hospital1 = hospitalService.selectHospitalByHospitalId(hospitalId);
            return AjaxResult.success(hospital1);
        } else {
            Hospital hospital1 = hospitalService.selectId(loginUser.getUser().getUserId());
            return AjaxResult.success(hospital1);
        }
    }

    @GetMapping("getPatientManagementByPhone/{patientPhone}")
    public AjaxResult getPatientManagementByPhone(@PathVariable String patientPhone) throws Exception {
        PatientManagement patientManagement = new PatientManagement();
        patientManagement.setPatientPhone(aesUtils.encrypt(patientPhone));
        List<PatientManagement> patientManagements = patientManagementService.selectListOrderByTime(patientManagement);
        for (PatientManagement management : patientManagements) {
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

    @GetMapping("history_info")
    public AjaxResult getHistoryInfoBypatientPhone(SingleHistoryData Info) throws Exception {

        HashMap<String, Object> res = new HashMap<>();
        // 详情
        HashMap<String, String> description = new HashMap<>();
        description.put("normalEcg", Details.normal);
        //房性早搏
        description.put("apBeat", Details.AP);
        //室性早搏
        description.put("pvBeat", Details.PV);
        //心房颤动
        description.put("atrialFibrillation", Details.Atrial_fibrillation);
        //心房扑动
        description.put("atrialFlutter", Details.Atrial_flutter);
        //左束支传导阻滞
        description.put("leftBlock", Details.Left_bundle_branch_block);
        //右束支传导阻滞
        description.put("rightBlock", Details.Right_bundle_branch_block);
        //心动过缓
        description.put("bradycardia", Details.bradycardia);
        //心动过速
        description.put("tachycardia", Details.tachycardia);

        if (Info != null && Info.getPatientPhone() != null && !Info.getPatientPhone().isEmpty()) {
            Info.setPatientPhone(aesUtils.encrypt(Info.getPatientPhone()));
        }
        if (Info != null && (Info.getPatientPhone() == null || Info.getPatientPhone().isEmpty())) {
            return AjaxResult.error("手机号不完整，请稍后在试");
        }

        SingleHistoryData singleHistoryData = singleHistoryDataService.selectSingleHistoryDataByHisId(Info);
        Patient patient = null;
        if (Info != null) {
            patient = patientService.selectPatientByPatientPhone(Info.getPatientPhone());
        }
        if (patient != null && patient.getPatientPhone() != null && !"".equals(patient.getPatientPhone())) {
            patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
        }
        if (patient != null && patient.getPatientName() != null && !"".equals(patient.getPatientName())) {
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }
        res.put("infoNumber", singleHistoryData);
        res.put("description", description);
        res.put("patient", patient);
        return AjaxResult.success(res);
    }


    /**
     * 患者统计对应日期的诊断次数
     *
     * @param patientManagement
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:dateList')")
    @GetMapping("/dateList")
    public AjaxResult dateList(PatientManagement patientManagement) throws Exception {
        getEncryptManagement(patientManagement);
        return AjaxResult.success(patientManagementService.getDateList(patientManagement));
    }


    /**
     * 通过医院给医生分组
     *
     * @return
     */
    @GetMapping("/listDoc")
    public AjaxResult listDoc() throws Exception {
        return AjaxResult.success(doctorService.listDoc());
    }


    @GetMapping("/aesCopy")
    public AjaxResult aesCopy(Limit limit) throws Exception {
        patientManagementService.aesCopy(limit);
        return AjaxResult.success();
    }

    /**
     * 根据在线的设备号查询患者手机号、家人电话
     **/
    @GetMapping("/getPhone")
    public AjaxResult getPhone(@RequestParam("deviceSn") String deviceSn) throws Exception {
        PhoneList one = patientManagementService.selectpatientByEquipmentCode(deviceSn);
        if (one == null) {
            return AjaxResult.success();
        }
        List<RolePhone> list = new ArrayList<>();
        if (one.getPatientPhone() != null && !"".equals(one.getPatientPhone())) {
            RolePhone a1 = new RolePhone("患者", aesUtils.decrypt(one.getPatientPhone()));
            list.add(a1);
        }
//        if(one.getFamilyPhone()!=null&&!"".equals(one.getFamilyPhone())) {
//            RolePhone a2 = new RolePhone("家人", aesUtils.decrypt(one.getFamilyPhone()));
//            list.add(a2);
//        }
//        if(one.getDoctorPhone()!=null&&!"".equals(one.getDoctorPhone())) {
//            RolePhone a3 = new RolePhone("医生", aesUtils.decrypt(one.getDoctorPhone()));
//            list.add(a3);
//        }
        RolePhone a3 = new RolePhone("医生", "");
        list.add(a3);
        RolePhone a2 = new RolePhone("家人", "");
        list.add(a2);
        return AjaxResult.success(list);
    }


    @GetMapping("/getEcgType")
    public AjaxResult getEcgType(String ecgType) {
        return AjaxResult.success(patientManagementService.selectEcgType(ecgType));
    }

    @GetMapping("/getHrCount")
    public AjaxResult getHrCount(PatientManagement patientManagement) throws Exception {
        return AjaxResult.success(patientManagementService.selectPatientManagementCount(patientManagement));
    }

    @GetMapping("/getHrCountApp")
    public AjaxResult getHrCountApp(PatientManagement patientManagement) throws Exception {
        return AjaxResult.success(patientManagementService.selectPatientManagementCountApp(patientManagement));
    }


    /**
     * 获取用户的监测总数和今日监测数量
     */
    @GetMapping("/getTodayCount")
    public AjaxResult getTodayCount(String patientPhone) throws Exception {
        return AjaxResult.success(patientManagementService.selectTodayCount(patientPhone));
    }


    @PostMapping("/export")
    public void export(HttpServletResponse response, PatientManagement patientManagement) throws Exception {
        List<PatientManagementVO> list = patientManagementService.getPatientManagementByCode(null);
        ExcelUtil<PatientManagementVO> util = new ExcelUtil<PatientManagementVO>(PatientManagementVO.class);
        util.exportExcel(response, list, "患者管理数据");
    }

    @GetMapping("/getWeekly")
    public AjaxResult getWeekly(SingleHistoryData Info) throws Exception {
        if (Info == null) {
            return AjaxResult.error("参数不为能空！");
        }
        if (Info.getPatientPhone() != null && !Info.getPatientPhone().isEmpty()) {
            Info.setPatientPhone(aesUtils.encrypt(Info.getPatientPhone()));
        }
        if (Info.getPatientPhone() == null || Info.getPatientPhone().isEmpty()) {
            return AjaxResult.error("手机号不完整，请稍后在试");
        }
//        String patientPhone = Info.getPatientPhone();
        HashMap<String, Object> res = weeklyService.getWeeklyByPhone(Info);

        return AjaxResult.success(res);
    }

    @GetMapping("/refreshWeekly")
    public AjaxResult getDTest(SingleHistoryData Info) throws Exception {
        weeklyService.getWeekly(Info);
        return AjaxResult.success();
    }
}
