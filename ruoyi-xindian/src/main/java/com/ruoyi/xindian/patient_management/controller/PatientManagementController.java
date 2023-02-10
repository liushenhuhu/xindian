package com.ruoyi.xindian.patient_management.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    private IHospitalService hospitalService;

    @Autowired
    private IEquipmentService iEquipmentService;

    @Autowired
    private ISysUserService userService;


    /**
     * 查询患者管理列表
     */
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientManagement patientManagement) {
//        startPage();
//        List<PatientManagement> list_add = new ArrayList<>();
        List<PatientManagement> list = new ArrayList<>();
        if (getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalName = sysUser.getHospitalName();
            patientManagement.setHospitalName(hospitalName);
            startPage();
            if (null == patientManagement.getEcgType()) {
                list = patientManagementService.selectPatientManagementList(patientManagement);
            } else if (patientManagement.getEcgType().equals("DECGsingle")) {
                list = patientManagementService.selectPatientManagementListDECGsingle(patientManagement);
            } else if (patientManagement.getEcgType().equals("JECG12")) {
                list = patientManagementService.selectPatientManagementListJECG12(patientManagement);
            } else{
                list = patientManagementService.selectPatientManagementList(patientManagement);
            }

        } else {
            startPage();
            if (null == patientManagement.getEcgType()) {
                list = patientManagementService.selectPatientManagementList(patientManagement);
            } else if (patientManagement.getEcgType().equals("DECGsingle")) {
                list = patientManagementService.selectPatientManagementListDECGsingle(patientManagement);
            } else if (patientManagement.getEcgType().equals("JECG12")) {
                list = patientManagementService.selectPatientManagementListJECG12(patientManagement);
            } else {
                list = patientManagementService.selectPatientManagementList(patientManagement);
            }
        }
        return getDataTable(list);
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
            String hospitalName = sysUser.getHospitalName();
            patientManagement.setHospitalName(hospitalName);
            list = patientManagementService.selectPatientManagementList(patientManagement);
        } else {
            list = patientManagementService.selectPatientManagementList(patientManagement);
        }
//        List<PatientManagement> list = patientManagementService.selectPatientManagementList(patientManagement);
        ExcelUtil<PatientManagement> util = new ExcelUtil<PatientManagement>(PatientManagement.class);
        util.exportExcel(response, list, "患者管理数据");
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
    @PreAuthorize("@ss.hasPermi('patient_management:patient_management:edit')")
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


    @GetMapping("getPatientManagementByPhone/{patientPhone}")
    public AjaxResult getPatientManagementByPhone(@PathVariable String patientPhone) {
        PatientManagement patientManagement = new PatientManagement();
        patientManagement.setPatientPhone(patientPhone);
        List<PatientManagement> patientManagements = patientManagementService.selectListOrderByTime(patientManagement);
        return AjaxResult.success(patientManagements);
    }


}
