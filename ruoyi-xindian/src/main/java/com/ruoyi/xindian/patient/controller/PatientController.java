package com.ruoyi.xindian.patient.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
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
 * @date 2022-08-15
 */
@RestController
@RequestMapping("/patient/patient")
public class PatientController extends BaseController
{
    @Autowired
    private IPatientService patientService;

    @Autowired
    private IHospitalService hospitalService;

    /**
     * 查询患者列表
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:list')")
    @GetMapping("/list")
    public TableDataInfo list(Patient patient)
    {
        startPage();
        List<Patient> list = patientService.selectPatientList(patient);
        for (Patient listPatient : list) {
            Hospital hospital = hospitalService.selectHospitalByHospitalId(listPatient.getHospitalCode());
            listPatient.setHospitalName(hospital.getHospitalName());
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
        List<Patient> list = patientService.selectPatientList(patient);
        ExcelUtil<Patient> util = new ExcelUtil<Patient>(Patient.class);
        util.exportExcel(response, list, "患者数据");
    }

    /**
     * 获取患者详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:query')")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") String patientId)
    {
        return AjaxResult.success(patientService.selectPatientByPatientId(patientId));
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
    public AjaxResult remove(@PathVariable String[] patientIds)
    {
        return toAjax(patientService.deletePatientByPatientIds(patientIds));
    }
}
