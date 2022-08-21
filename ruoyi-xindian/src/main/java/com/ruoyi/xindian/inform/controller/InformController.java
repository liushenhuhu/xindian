package com.ruoyi.xindian.inform.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
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
import com.ruoyi.xindian.inform.domain.Inform;
import com.ruoyi.xindian.inform.service.IInformService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报告Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/inform/inform")
public class InformController extends BaseController
{
    @Autowired
    private IInformService informService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IHospitalService hospitalService;

    @Autowired
    private IPatientManagementService patientManagementService;

    /**
     * 查询报告列表
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:list')")
    @GetMapping("/list")
    public TableDataInfo list(Inform inform)
    {
        startPage();
        List<Inform> list = informService.selectInformList(inform);
        for (Inform listInform : list) {
            PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(listInform.getpId());
            Patient patient = patientService.selectPatientByPatientNumber(patientManagement.getPatientNumber());
            Hospital hospital = hospitalService.selectHospitalByHospitalCode(patientManagement.getHospitalCode());
            listInform.setPatientName(patient.getPatientName());
            listInform.setPatientNumber(patient.getPatientNumber());
            listInform.setPatientAge(patient.getPatientAge());
            listInform.setPatientSex(patient.getPatientSex());
            listInform.setPatientPhone(patient.getPatientPhone());
            listInform.setBedNumber(patient.getBedNumber());
            listInform.setCaseHistoryNumber(patient.getCaseHistoryNumber());
            listInform.setPatientSource(patient.getPatientSource());
            listInform.setHospitalCode(hospital.getHospitalCode());
            listInform.setHospitalName(hospital.getHospitalName());
        }
        return getDataTable(list);
    }

    /**
     * 导出报告列表
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:export')")
    @Log(title = "报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Inform inform)
    {
        List<Inform> list = informService.selectInformList(inform);
        ExcelUtil<Inform> util = new ExcelUtil<Inform>(Inform.class);
        util.exportExcel(response, list, "报告数据");
    }

    /**
     * 获取报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:query')")
    @GetMapping(value = "/{informId}")
    public AjaxResult getInfo(@PathVariable("informId") Long informId)
    {
        return AjaxResult.success(informService.selectInformByInformId(informId));
    }

    /**
     * 新增报告
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:add')")
    @Log(title = "报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Inform inform)
    {
        return toAjax(informService.insertInform(inform));
    }

    /**
     * 修改报告
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:edit')")
    @Log(title = "报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Inform inform)
    {
        return toAjax(informService.updateInform(inform));
    }

    /**
     * 删除报告
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:remove')")
    @Log(title = "报告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{informIds}")
    public AjaxResult remove(@PathVariable Long[] informIds)
    {
        return toAjax(informService.deleteInformByInformIds(informIds));
    }
}
