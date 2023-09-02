package com.ruoyi.xindian.patient_management.controller;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
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
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.*;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.util.PhoneCheckUtils;
import com.ruoyi.xindian.util.WxUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private AesUtils aesUtils;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    /**
     * 查询患者管理列表
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientManagement patientManagement,HttpServletRequest request) throws Exception {
//        startPage();
//        List<PatientManagement> list_add = new ArrayList<>();
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<PatientManagement> list = new ArrayList<>();
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();

        if(patientManagement.getHospitalCode()!=null&&!"".equals(patientManagement.getHospitalCode())){
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
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.getHospitalCodeList().add(hospital1.getHospitalCode());
                }
            }
            String code = patientManagement.getHospitalCode();
            if (code!=null&&!"".equals(code)){
                List<String> patientList = patientManagement.getHospitalCodeList();
                if (patientList!=null&&patientList.size()>0){
                    for (String c : patientList){
                        if (c.equals(patientManagement.getHospitalCode())){
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
        } else if(sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 104) {
            //医生
            String phonenumber = sysUser.getPhonenumber();
            patientManagement.setDoctorPhone(phonenumber);
            if(patientManagement.getDiagnosisStatus() != null && patientManagement.getDiagnosisStatus()==0)
                patientManagement.setDiagnosisStatus(2L);
        }


        if (patientManagement.getPatientPhone()!=null){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName()!=null){
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getDiagnosisDoctor()!=null&&!"".equals(patientManagement.getDiagnosisDoctor())){
            patientManagement.setDiagnosisDoctor(aesUtils.encrypt(patientManagement.getDiagnosisDoctor()));
        }
        if (patientManagement.getDoctorPhone()!=null&&!"".equals(patientManagement.getDoctorPhone())){
            patientManagement.setDoctorPhone(aesUtils.encrypt(patientManagement.getDoctorPhone()));
        }
        if (patientManagement.getPatPhone()!=null&&!"".equals(patientManagement.getPatPhone())){
            patientManagement.setPatPhone(aesUtils.encrypt(patientManagement.getPatPhone()));
        }

        startPage();
        if (null == patientManagement.getEcgType()) {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        } else if (patientManagement.getEcgType().equals("DECGsingle")) {
            list = patientManagementService.selectPatientManagementListDECGsingle(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECG12")) {
            list = patientManagementService.selectPatientManagementListJECG12(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECGsingleGZ")) {
            list = patientManagementService.selectPatientManagementJECGList(patientManagement);
        }else if (patientManagement.getEcgType().equals("JECGsingle")) {
            list = patientManagementService.selectPatientManagementJECGsingle(patientManagement);
        } else if (patientManagement.getEcgType().equals("DECG12")) {
            list = patientManagementService.selectPatientManagementListDECG12(patientManagement);
        } else {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        }
        PatientManagmentDept patientManagmentDept;
        Doctor doctor = new Doctor();
        Department department = new Department();
        for (PatientManagement management : list) {
//            patientManagmentDept= (PatientManagmentDept) management;
            if(DateUtil.isValidDate(management.getBirthDay())){
                try {
                    management.setPatientAge(String.valueOf(DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(management.getBirthDay()))));
                } catch (ParseException e) {
                    System.out.println(1);
                }
            }

            if (management.getPatientPhone()!=null&&!"".equals(management.getPatientPhone())){
                management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));

            }
            if (management.getPatientName()!=null&&!"".equals(management.getPatientName())){
                management.setPatientName(aesUtils.decrypt(management.getPatientName()));
            }
            if (management.getDiagnosisDoctor()!=null&&!"".equals(management.getDiagnosisDoctor())){
                management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
            }
            if (management.getFamilyPhone()!=null&&!"".equals(management.getFamilyPhone())){
                management.setFamilyPhone(aesUtils.decrypt(management.getFamilyPhone()));
            }
            patientManagmentDept = new PatientManagmentDept();
            BeanUtils.copyProperties(management, patientManagmentDept);

            if (patientManagmentDept.getDoctorPhone() != null) {
                doctor.setDoctorPhone(patientManagmentDept.getDoctorPhone());
                List<Doctor> doctors = doctorService.selectDoctorList(doctor);
                if (doctors.get(0).getDepartmentCode() != null) {
                    department.setDepartmentCode(doctors.get(0).getDepartmentCode());
                    List<Department> departments = departmentService.selectDepartmentList(department);
                    patientManagmentDept.setDept(departments.get(0).getDepartmentName());
                }
            }
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


    /**
     * 查询患者管理列表
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:list')")
    @GetMapping("/listP")
    public TableDataInfo listP(PatientManagement patientManagement,HttpServletRequest request) throws Exception {

        List<PatientManagement> list = new ArrayList<>();
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();
        if (patientManagement.getPatientPhone()!=null){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName()!=null){
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getPatPhone()!=null&&!"".equals(patientManagement.getPatPhone())){
            patientManagement.setPatPhone(aesUtils.encrypt(patientManagement.getPatPhone()));
        }
        startPage();
        if (null == patientManagement.getEcgType()) {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        } else if (patientManagement.getEcgType().equals("DECGsingle")) {
            list = patientManagementService.selectPatientManagementListDECGsingle(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECG12")) {
            list = patientManagementService.selectPatientManagementListJECG12(patientManagement);
        } else if (patientManagement.getEcgType().equals("JECGsingleGZ")) {
            list = patientManagementService.selectPatientManagementJECGList(patientManagement);
        }else if (patientManagement.getEcgType().equals("JECGsingle")) {
            list = patientManagementService.selectPatientManagementJECGsingle(patientManagement);
        } else if (patientManagement.getEcgType().equals("DECG12")) {
            list = patientManagementService.selectPatientManagementListDECG12(patientManagement);
        } else {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        }
        PatientManagmentDept patientManagmentDept;
        Doctor doctor = new Doctor();
        Department department = new Department();
        for (PatientManagement management : list) {
//            patientManagmentDept= (PatientManagmentDept) management;
            if(DateUtil.isValidDate(management.getBirthDay())){
                try {
                    management.setPatientAge(String.valueOf(DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(management.getBirthDay()))));
                } catch (ParseException e) {
                    System.out.println(1);
                }
            }
            if (management.getPatientPhone()!=null&&!"".equals(management.getPatientPhone())){
                management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));

            }
            if (management.getPatientName()!=null&&!"".equals(management.getPatientName())){
                management.setPatientName(aesUtils.decrypt(management.getPatientName()));
            }
            if (management.getDiagnosisDoctor()!=null&&!"".equals(management.getDiagnosisDoctor())){
                management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
            }
            if (management.getFamilyPhone()!=null&&!"".equals(management.getFamilyPhone())){
                management.setFamilyPhone(aesUtils.decrypt(management.getFamilyPhone()));
            }
            patientManagmentDept = new PatientManagmentDept();
            BeanUtils.copyProperties(management, patientManagmentDept);

            if (patientManagmentDept.getDoctorPhone() != null) {
                doctor.setDoctorPhone(patientManagmentDept.getDoctorPhone());
                List<Doctor> doctors = doctorService.selectDoctorList(doctor);
                if (doctors.get(0).getDepartmentCode() != null) {
                    department.setDepartmentCode(doctors.get(0).getDepartmentCode());
                    List<Department> departments = departmentService.selectDepartmentList(department);
                    patientManagmentDept.setDept(departments.get(0).getDepartmentName());
                }
            }

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

    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:list')")
    @GetMapping("/listPatientTimeList")
    public TableDataInfo listPatientTimeList(PatientManagement patientManagement,HttpServletRequest request) throws Exception {

        if (patientManagement.getPatientPhone()!=null){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName()!=null){
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getPatPhone()!=null&&!"".equals(patientManagement.getPatPhone())){
            patientManagement.setPatPhone(aesUtils.encrypt(patientManagement.getPatPhone()));
        }
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();
        startPage();
        List<PatientManagement> list  = patientManagementService.selectPatientManagementList12(patientManagement);

        PatientManagmentDept patientManagmentDept;
        Doctor doctor = new Doctor();
        Department department = new Department();
        for (PatientManagement management : list) {
            if(DateUtil.isValidDate(management.getBirthDay())){
                try {
                    management.setPatientAge(String.valueOf(DateUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(management.getBirthDay()))));
                } catch (ParseException e) {
                    System.out.println(1);
                }
            }

            if (management.getPatientPhone()!=null&&!"".equals(management.getPatientPhone())){
                management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));

            }
            if (management.getPatientName()!=null&&!"".equals(management.getPatientName())){
                management.setPatientName(aesUtils.decrypt(management.getPatientName()));
            }
            if (management.getDiagnosisDoctor()!=null&&!"".equals(management.getDiagnosisDoctor())){
                management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
            }

            patientManagmentDept = new PatientManagmentDept();
            BeanUtils.copyProperties(management, patientManagmentDept);

            if (patientManagmentDept.getDoctorPhone() != null) {
                doctor.setDoctorPhone(patientManagmentDept.getDoctorPhone());
                List<Doctor> doctors = doctorService.selectDoctorList(doctor);
                if (doctors.get(0).getDepartmentCode() != null) {
                    department.setDepartmentCode(doctors.get(0).getDepartmentCode());
                    List<Department> departments = departmentService.selectDepartmentList(department);
                    patientManagmentDept.setDept(departments.get(0).getDepartmentName());
                }
            }


            if (management.getTimeDuration() == null) {
                patientManagmentDept.setAcquisitionDuration("报告未生成");
            } else {
                patientManagmentDept.setAcquisitionDuration("记录时长: " + DateUtil.timeToString(management.getTimeDuration()));
            }


            BigDecimal bigDecimal = new BigDecimal(String.valueOf(Double.parseDouble(management.getAvgTime()) / 60));
            BigDecimal bigDecimal1 = bigDecimal.setScale(1, RoundingMode.UP);
            patientManagmentDept.setAvgTime(bigDecimal1 +"分钟");
            resList.add(patientManagmentDept);
        }
        long total = new PageInfo(list).getTotal();
        return getTable(resList, total);
    }


    /**
     * 导出患者管理列表
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:export')")
    @Log(title = "患者管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PatientManagement patientManagement) {

        List<PatientManagement> list = new ArrayList<>();
        if (getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalCode = sysUser.getHospitalCode();
            patientManagement.setHospitalCode(hospitalCode);
            list = patientManagementService.selectPatientManagementList(patientManagement);
        } else {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        }
//        List<PatientManagement> list = patientManagementService.selectPatientManagementList(patientManagement);
        ExcelUtil<PatientManagement> util = new ExcelUtil<PatientManagement>(PatientManagement.class);
        util.exportExcel(response, list, "患者管理数据");
    }

    @GetMapping(value = "/sendMsg/{phone}")
    public AjaxResult sendMsg(@PathVariable("phone") String phone,HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (Boolean.TRUE.equals(redisTemplate.hasKey("userPhoneTimeOne:" + loginUser.getUser().getUserId()))){
            return AjaxResult.error("请勿重复请求验证码");
        }
        if (!PhoneCheckUtils.isPhoneLegal(phone)) {
            return AjaxResult.error("手机号格式不对，请重新输入");
        }
        redisTemplate.opsForValue().set("userPhoneTimeOne:" + loginUser.getUser().getUserId(),phone,50, TimeUnit.SECONDS);
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
        if (management.getPatientPhone()!=null&&!"".equals(management.getPatientPhone())){
            management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));
        }
        if (management.getPatientName()!=null&&!"".equals(management.getPatientName())){
            management.setPatientName(aesUtils.decrypt(management.getPatientName()));
        }
        if (management.getDiagnosisDoctor()!=null&&!"".equals(management.getDiagnosisDoctor())){
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
        if (management.getPatientPhone()!=null&&!"".equals(management.getPatientPhone())){
            management.setPatientPhone(aesUtils.encrypt(management.getPatientPhone()));
        }
        if (management.getPatientName()!=null&&!"".equals(management.getPatientName())){
            management.setPatientName(aesUtils.encrypt(management.getPatientName()));
        }
        if (management.getDiagnosisDoctor()!=null&&!"".equals(management.getDiagnosisDoctor())){
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
        if (patientManagement.getPatientPhone()!=null&&!"".equals(patientManagement.getPatientPhone())){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName()!=null&&!"".equals(patientManagement.getPatientName())){
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getDiagnosisDoctor()!=null&&!"".equals(patientManagement.getDiagnosisDoctor())){
            patientManagement.setDiagnosisDoctor(aesUtils.encrypt(patientManagement.getDiagnosisDoctor()));
        }
        if (patientManagement.getDoctorPhone()!=null&&!"".equals(patientManagement.getDoctorPhone())){
            patientManagement.setDoctorPhone(aesUtils.encrypt(patientManagement.getDoctorPhone()));
        }

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
    public String updateStatus(@RequestBody String[] pIds) {

        patientManagementService.updateStatusAll();
        patientService.updateMonitoringStatus();
        if (pIds.length != 0) {
            patientManagementService.updateStatus(pIds);
//            System.out.println(Arrays.toString(pIds));
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
        if (sysUser.getUserName()!=null){
            sysUser.setUserName(aesUtils.encrypt(sysUser.getUserName()));
        }
        if (sysUser.getPhonenumber()!=null){
            sysUser.setPhonenumber(aesUtils.encrypt(sysUser.getPhonenumber()));
        }
//        String hospitalName = sysUser.getHospitalName();
        return sysUser;
    }

    /**
     * 心电大屏数据医院查找
     * @param hospitalId
     * @param request
     * @return
     */
    @GetMapping(value = "/getInfoId")
    public AjaxResult getInfo(Long hospitalId, HttpServletRequest request) {

        LoginUser loginUser = tokenService.getLoginUser(request);

            if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())) {
                if (hospitalId.equals(1L)){
                    Hospital hospital = new Hospital();
                    hospital.setHospitalName("所有");
                    return AjaxResult.success(hospital);
                }
                Hospital hospital1 = hospitalService.selectHospitalByHospitalId(hospitalId);
                return AjaxResult.success(hospital1);
            }else {
                Hospital hospital1 = hospitalService.selectId(loginUser.getUser().getUserId());
                return AjaxResult.success(hospital1);
            }

    }

    @GetMapping("getPatientManagementByPhone/{patientPhone}")
    public AjaxResult getPatientManagementByPhone(@PathVariable String patientPhone) throws Exception {
        PatientManagement patientManagement = new PatientManagement();
        patientManagement.setPatientPhone(aesUtils.encrypt(patientPhone));
        List<PatientManagement> patientManagements = patientManagementService.selectListOrderByTime(patientManagement);
        for (PatientManagement management :patientManagements){
            if (management.getPatientPhone()!=null&&!"".equals(management.getPatientPhone())){
                management.setPatientPhone(aesUtils.decrypt(management.getPatientPhone()));
            }
            if (management.getPatientName()!=null&&!"".equals(management.getPatientName())){
                management.setPatientName(aesUtils.decrypt(management.getPatientName()));
            }
            if (management.getDiagnosisDoctor()!=null&&!"".equals(management.getDiagnosisDoctor())){
                management.setDiagnosisDoctor(aesUtils.decrypt(management.getDiagnosisDoctor()));
            }
        }
        return AjaxResult.success(patientManagements);
    }

    @GetMapping("history_info")
    public AjaxResult getHistoryInfoBypatientPhone(SingleHistoryInfo Info) throws Exception {

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

        if (Info!=null&&Info.getPatientPhone()!=null&&!Info.getPatientPhone().equals("")){
            Info.setPatientPhone(aesUtils.encrypt(Info.getPatientPhone()));
        }
        if (Info != null && (Info.getPatientPhone() == null || Info.getPatientPhone().equals(""))) {
            return AjaxResult.error("手机号不完整，请稍后在试");
        }

        List<SingleHistoryInfo> infos = patientManagementService.selectSingleHistoryInfoList(Info);
        if (infos!=null&&infos.size()>0){
            for (SingleHistoryInfo info : infos) {
                if (info.getPatientPhone()!=null){
                    info.setPatientPhone(aesUtils.decrypt(info.getPatientPhone()));
                }
                if (info.getApBeat() == null) info.setApBeat((long) 0);
                if (info.getPvBeat() == null) info.setPvBeat((long) 0);
                if (info.getAtrialFibrillation() == null) info.setAtrialFibrillation((long) 0);
                if (info.getAtrialFlutter() == null) info.setAtrialFlutter((long) 0);
                if (info.getLeftBlock() == null) info.setLeftBlock((long) 0);
                if (info.getRightBlock() == null) info.setRightBlock((long) 0);
                if (info.getBradycardia() == null) info.setBradycardia((long) 0);
                if (info.getTachycardia() == null) info.setTachycardia((long) 0);
            }
            res.put("infoNumber", infos);
        }else {
            List<SingleHistoryInfo> list = new ArrayList<>();
            SingleHistoryInfo singleHistoryInfo = new SingleHistoryInfo();
            singleHistoryInfo.setApBeat(0L);
            singleHistoryInfo.setPvBeat(0L);
            singleHistoryInfo.setAtrialFibrillation(0L);
            singleHistoryInfo.setAtrialFlutter(0L);
            singleHistoryInfo.setLeftBlock(0L);
            singleHistoryInfo.setRightBlock(0L);
            singleHistoryInfo.setBradycardia(0L);
            singleHistoryInfo.setTachycardia(0L);
            list.add(singleHistoryInfo);
            res.put("infoNumber", list);
        }

        Patient patient = null;
        if (Info != null) {
            patient = patientService.selectPatientByPatientPhone(Info.getPatientPhone());
        }
        if (patient.getPatientPhone()!=null&&!"".equals(patient.getPatientPhone())){
            patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
        }
        if (patient.getPatientName()!=null&&!"".equals(patient.getPatientName())){
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }
        res.put("description", description);
        res.put("patient",patient);
        return AjaxResult.success(res);
    }


    /**
     * 患者统计对应日期的诊断次数
     * @param patientManagement
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:dateList')")
    @GetMapping("/dateList")
    public AjaxResult dateList(PatientManagement patientManagement) throws Exception {
        if (patientManagement.getPatientPhone()!=null&&!"".equals(patientManagement.getPatientPhone())){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        if (patientManagement.getPatientName()!=null&&!"".equals(patientManagement.getPatientName())){
            patientManagement.setPatientName(aesUtils.encrypt(patientManagement.getPatientName()));
        }
        if (patientManagement.getDiagnosisDoctor()!=null&&!"".equals(patientManagement.getDiagnosisDoctor())){
            patientManagement.setDiagnosisDoctor(aesUtils.encrypt(patientManagement.getDiagnosisDoctor()));
        }
        if (patientManagement.getDoctorPhone()!=null&&!"".equals(patientManagement.getDoctorPhone())){
            patientManagement.setDoctorPhone(aesUtils.encrypt(patientManagement.getDoctorPhone()));
        }
        return AjaxResult.success(patientManagementService.getDateList(patientManagement));
    }


    /**
     * 通过医院给医生分组
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
     *
     * 根据在线的设备号查询患者手机号、家人电话
     *
     **/
    @GetMapping("/getPhone")
    public AjaxResult getPhone(@RequestParam("deviceSn")String deviceSn) throws Exception {
        PhoneList one=patientManagementService.selectpatientByEquipmentCode(deviceSn);
        if(one==null){
            return AjaxResult.success();
        }
        List<RolePhone> list=new ArrayList<>();
        if(one.getPatientPhone()!=null&&!"".equals(one.getPatientPhone())){
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
        return AjaxResult.success(list);
    }

}
