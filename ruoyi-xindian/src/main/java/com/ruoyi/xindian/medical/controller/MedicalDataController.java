package com.ruoyi.xindian.medical.controller;

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
import com.ruoyi.xindian.medical.domain.MedicalData;
import com.ruoyi.xindian.medical.service.IMedicalDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 病数据Controller
 * 
 * @author hanhan
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/medicalData/medicalData")
public class MedicalDataController extends BaseController
{
    @Autowired
    private IMedicalDataService medicalDataService;

    /**
     * 查询病数据列表
     */
    @PreAuthorize("@ss.hasPermi('medicalData:medicalData:list')")
    @GetMapping("/list")
    public TableDataInfo list(MedicalData medicalData)
    {
        startPage();
        List<MedicalData> list = medicalDataService.selectMedicalDataList(medicalData);
        return getDataTable(list);
    }

    /**
     * 导出病数据列表
     */
    @PreAuthorize("@ss.hasPermi('medicalData:medicalData:export')")
    @Log(title = "病数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MedicalData medicalData)
    {
        List<MedicalData> list = medicalDataService.selectMedicalDataList(medicalData);
        ExcelUtil<MedicalData> util = new ExcelUtil<MedicalData>(MedicalData.class);
        util.exportExcel(response, list, "病数据数据");
    }

    /**
     * 获取病数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalData:medicalData:query')")
    @GetMapping(value = "/{medicalDataId}")
    public AjaxResult getInfo(@PathVariable("medicalDataId") Long medicalDataId)
    {
        return AjaxResult.success(medicalDataService.selectMedicalDataByMedicalDataId(medicalDataId));
    }

    /**
     * 新增病数据
     */
    @PreAuthorize("@ss.hasPermi('medicalData:medicalData:add')")
    @Log(title = "病数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MedicalData medicalData)
    {
        return toAjax(medicalDataService.insertMedicalData(medicalData));
    }

    /**
     * 修改病数据
     */
    @PreAuthorize("@ss.hasPermi('medicalData:medicalData:edit')")
    @Log(title = "病数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MedicalData medicalData)
    {
        return toAjax(medicalDataService.updateMedicalData(medicalData));
    }

    /**
     * 删除病数据
     */
    @PreAuthorize("@ss.hasPermi('medicalData:medicalData:remove')")
    @Log(title = "病数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{medicalDataIds}")
    public AjaxResult remove(@PathVariable Long[] medicalDataIds)
    {
        return toAjax(medicalDataService.deleteMedicalDataByMedicalDataIds(medicalDataIds));
    }
}
