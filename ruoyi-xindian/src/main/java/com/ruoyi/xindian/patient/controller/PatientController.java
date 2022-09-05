package com.ruoyi.xindian.patient.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

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

    /**
     * 查询患者列表
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:list')")
    @GetMapping("/list")
    public TableDataInfo list(Patient patient)
    {
        startPage();
        List<Patient> list = patientService.selectPatientList(patient);
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
        List<Patient> list = patientService.selectPatientList(patient);
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
     * 通过patientNumber获取患者详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:query')")
    @GetMapping(value = "/getInfoByPatientNumber/{patientNumber}")
    public AjaxResult getInfoByPatientNumber(@PathVariable("patientNumber") String patientNumber)
    {
        return AjaxResult.success(patientService.selectPatientByPatientCode(patientNumber));
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
}
