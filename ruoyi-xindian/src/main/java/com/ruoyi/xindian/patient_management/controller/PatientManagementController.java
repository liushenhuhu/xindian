package com.ruoyi.xindian.patient_management.controller;

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
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IDepartmentService;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.Details;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.domain.PatientManagmentDept;
import com.ruoyi.xindian.patient_management.domain.SingleHistoryInfo;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.util.WxUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 查询患者管理列表
     */
//    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientManagement patientManagement) {
//        startPage();
//        List<PatientManagement> list_add = new ArrayList<>();
        List<PatientManagement> list = new ArrayList<>();
        ArrayList<PatientManagmentDept> resList = new ArrayList<>();
//        Long userId = getUserId();
        SysUser sysUser = userService.selectUserById(getUserId());
        if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId() == 101) {
            //101---医院
            String hospitalCode = sysUser.getHospitalCode();
            patientManagement.setHospitalCode(hospitalCode);
//            String phonenumber = sysUser.getPhonenumber();
//            if(!"15888888888".equals(phonenumber)){
//                patientManagement.setDoctorPhone(phonenumber);
//                if(patientManagement.getDiagnosisStatus() != null && patientManagement.getDiagnosisStatus()==0)
//                    patientManagement.setDiagnosisStatus(2L);
//            }
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
        return getTable(resList, new PageInfo(list).getTotal());
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
    public AjaxResult sendMsg(@PathVariable("phone") String phone)
    {
        WxUtil.sendAdvice(phone);
        return AjaxResult.success();
    }
    /**
     * 获取患者管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:query')")
    @GetMapping(value = "/{pId}")
    public AjaxResult getInfo(@PathVariable("pId") String pId) {
        return AjaxResult.success(patientManagementService.selectPatientManagementByPId(pId));
    }

    /**
     * 新增患者管理
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:add')")
    @Log(title = "患者管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatientManagement patientManagement) {
        return toAjax(patientManagementService.insertPatientManagement(patientManagement));
    }

    /**
     * 修改患者管理
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:edit')")
    @Log(title = "患者管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PatientManagement patientManagement) {
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
    public SysUser getUserInfo() {
        SysUser sysUser = userService.selectUserById(getUserId());
//        String hospitalName = sysUser.getHospitalName();
        return sysUser;
    }


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
    public AjaxResult getPatientManagementByPhone(@PathVariable String patientPhone) {
        PatientManagement patientManagement = new PatientManagement();
        patientManagement.setPatientPhone(patientPhone);
        List<PatientManagement> patientManagements = patientManagementService.selectListOrderByTime(patientManagement);
        return AjaxResult.success(patientManagements);
    }

    @GetMapping("history_info/{patientPhone}")
    public AjaxResult getHistoryInfoBypatientPhone(@PathVariable String patientPhone) {

        SingleHistoryInfo Info = new SingleHistoryInfo();
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

        Info.setPatientPhone(patientPhone);
        List<SingleHistoryInfo> infos = patientManagementService.selectSingleHistoryInfoList(Info);
        for (SingleHistoryInfo info : infos) {
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
        res.put("description", description);
        return AjaxResult.success(res);
    }

}
