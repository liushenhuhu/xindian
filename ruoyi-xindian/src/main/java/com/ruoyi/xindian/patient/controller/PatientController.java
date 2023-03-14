package com.ruoyi.xindian.patient.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 患者Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/patient/patient")
public class PatientController extends BaseController
{
    @Autowired
    private IPatientService patientService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IHospitalService hospitalService;

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 查询患者列表
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:list')")
    @GetMapping("/list")
    public TableDataInfo list(Patient patient)
    {
        List<Patient> list = new ArrayList<>();
        if (getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalName = sysUser.getHospitalName();
            patient.setPatientSource(hospitalName);
            startPage();
            list = patientService.selectPatientList(patient);
        } else {
            startPage();
            list = patientService.selectPatientList(patient);
        }
        return getDataTable(list);
    }

    /**
     * 导出患者列表
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:export')")
    @Log(title = "患者", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Patient patient)
    {
        List<Patient> list = new ArrayList<>();
        if (getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalName = sysUser.getHospitalName();
            patient.setPatientSource(hospitalName);
            list = patientService.selectPatientList(patient);
        } else {
            list = patientService.selectPatientList(patient);
        }
        ExcelUtil<Patient> util = new ExcelUtil<Patient>(Patient.class);
        util.exportExcel(response, list, "患者数据");
    }

    /**
     * 获取患者详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:query')")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId)
    {
        return AjaxResult.success(patientService.selectPatientByPatientId(patientId));
    }


    /**
     * 通过patientName获取患者详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:query')")
    @GetMapping(value = "/getInfoByPatientName/{patientName}")
    public AjaxResult getInfoByPatientName(@PathVariable("patientName") String patientName)
    {
        Patient patient = patientService.selectPatientByPatientName(patientName);
        System.out.println(patient);
        return AjaxResult.success(patient);
    }

    /**
     * 新增患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:add')")
    @Log(title = "患者", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Patient patient)
    {
        return toAjax(patientService.insertPatient(patient));
    }

    /**
     * 修改患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:edit')")
    @Log(title = "患者", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Patient patient)
    {
        return toAjax(patientService.updatePatient(patient));
    }

    /**
     * 删除患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:remove')")
    @Log(title = "患者", businessType = BusinessType.DELETE)
    @DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds)
    {
        return toAjax(patientService.deletePatientByPatientIds(patientIds));
    }

    /**
     * 修改患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:edit')")
    @Log(title = "患者", businessType = BusinessType.UPDATE)
    @PostMapping("/updateMonitoringStatus")
    public void updateMonitoringStatus(@RequestBody List<String> monitoringStatusList)
    {
        System.out.println("执行开始");
        patientService.updateMonitoringStatus();
        for (String equipmentCode : monitoringStatusList) {
            patientService.updateMonitoringStatus2(equipmentCode);
        }
        System.out.println("执行完毕");
    }

    @GetMapping("/addDict")
    public AjaxResult addDict() {
        SysDictData dict = new SysDictData();
        dict.setDictType("hospital_name_name_list");
        dict.setListClass("default");
        dict.setCreateBy("admin");
        Hospital hospital = new Hospital();
        List<Hospital> allHospital = hospitalService.selectHospitalList(hospital);
        int i = 0;
        for (Hospital hospital1 : allHospital) {
            dict.setDictSort((long) i);
            dict.setDictLabel(hospital1.getHospitalName());
            dict.setDictValue(hospital1.getHospitalName());
            List<SysDictData> sysDictData = dictDataService.selectDictDataList(dict);
            if (sysDictData.isEmpty()) {
                dictDataService.insertDictData(dict);
            }
            i = i +1;
        }
        return AjaxResult.success("down");
    }

    /**
     * 删除患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:remove')")
    @Log(title = "患者", businessType = BusinessType.DELETE)
    @DeleteMapping("/delByPatientPhone/{patientPhone}")
    public AjaxResult delPatient(@PathVariable String patientPhone)
    {
        return toAjax(patientService.deletePatientByPatientPhone(patientPhone));
    }
}
