package com.ruoyi.xindian.relationship.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
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
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.service.IPatientRelationshipService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 患者关系表Controller
 *
 * @author chenpeng
 * @date 2023-06-30
 */
@RestController
@RequestMapping("/relationship/relationship")
public class PatientRelationshipController extends BaseController
{
    @Autowired
    private IPatientRelationshipService patientRelationshipService;

    @Autowired
    private IPatientService patientService;

    /**
     * 查询患者关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientRelationship patientRelationship)
    {
        startPage();
        List<PatientRelationship> list = patientRelationshipService.selectPatientRelationshipList(patientRelationship);
        for (PatientRelationship relationship : list) {
            Patient patient = patientService.selectPatientByPatientPhone(relationship.getSonPhone());
            if(patient != null)
                relationship.setRelationshipPatientName(patient.getPatientName());
        }
        return getDataTable(list);
    }

    /**
     * 导出患者关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:export')")
    @Log(title = "患者关系表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PatientRelationship patientRelationship)
    {
        List<PatientRelationship> list = patientRelationshipService.selectPatientRelationshipList(patientRelationship);
        ExcelUtil<PatientRelationship> util = new ExcelUtil<PatientRelationship>(PatientRelationship.class);
        util.exportExcel(response, list, "患者关系表数据");
    }

    /**
     * 获取患者关系表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(patientRelationshipService.selectPatientRelationshipById(id));
    }

    /**
     * 新增患者关系表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:add')")
    @Log(title = "患者关系表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatientRelationship patientRelationship)
    {
        return toAjax(patientRelationshipService.insertPatientRelationship(patientRelationship));
    }

    /**
     * 修改患者关系表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:edit')")
    @Log(title = "患者关系表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PatientRelationship patientRelationship)
    {
        return toAjax(patientRelationshipService.updatePatientRelationship(patientRelationship));
    }

    /**
     * 删除患者关系表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:remove')")
    @Log(title = "患者关系表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(patientRelationshipService.deletePatientRelationshipByIds(ids));
    }
}
