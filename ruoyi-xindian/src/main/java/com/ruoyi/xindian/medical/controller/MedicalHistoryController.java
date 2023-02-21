package com.ruoyi.xindian.medical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 病史Controller
 * 
 * @author hanhan
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/medicalHistory/medicalHistory")
public class MedicalHistoryController extends BaseController
{
    @Autowired
    private IMedicalHistoryService medicalHistoryService;

    /**
     * 查询病史列表
     */
    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(MedicalHistory medicalHistory)
    {
        startPage();
        List<MedicalHistory> list = medicalHistoryService.selectMedicalHistoryList(medicalHistory);
        return getDataTable(list);
    }

    /**
     * 导出病史列表
     */
    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:export')")
    @Log(title = "病史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MedicalHistory medicalHistory)
    {
        List<MedicalHistory> list = medicalHistoryService.selectMedicalHistoryList(medicalHistory);
        ExcelUtil<MedicalHistory> util = new ExcelUtil<MedicalHistory>(MedicalHistory.class);
        util.exportExcel(response, list, "病史数据");
    }

    /**
     * 获取病史详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:query')")
    @GetMapping(value = "/{medicalHistoryId}")
    public AjaxResult getInfo(@PathVariable("medicalHistoryId") Long medicalHistoryId)
    {
        return AjaxResult.success(medicalHistoryService.selectMedicalHistoryByMedicalHistoryId(medicalHistoryId));
    }

    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:query')")
    @GetMapping(value = "/getByPatientPhone/{medicalHistoryId}")
    public AjaxResult getInfoByPatientPhone(@PathVariable("patientPhone") String patientPhone)
    {
        return AjaxResult.success(medicalHistoryService.selectMedicalHistoryByPatientPhone(patientPhone));
    }

    /**
     * 新增病史
     */
    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:add')")
    @Log(title = "病史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MedicalHistory medicalHistory)
    {
        return toAjax(medicalHistoryService.insertMedicalHistory(medicalHistory));
    }

    /**
     * 修改病史
     */
    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:edit')")
    @Log(title = "病史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MedicalHistory medicalHistory)
    {
        return toAjax(medicalHistoryService.updateMedicalHistory(medicalHistory));
    }

    /**
     * 删除病史
     */
    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:remove')")
    @Log(title = "病史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{medicalHistoryIds}")
    public AjaxResult remove(@PathVariable Long[] medicalHistoryIds)
    {
        return toAjax(medicalHistoryService.deleteMedicalHistoryByMedicalHistoryIds(medicalHistoryIds));
    }
}
