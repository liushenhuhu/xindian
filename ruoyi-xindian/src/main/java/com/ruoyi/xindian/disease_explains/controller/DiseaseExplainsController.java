package com.ruoyi.xindian.disease_explains.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.disease_explains.domain.DiseaseExplains;
import com.ruoyi.xindian.disease_explains.service.DiseaseExplainsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 心电图报告解析说明Controller
 * 
 * @author ruoyi
 * @date 2024-08-16
 */
@RestController
@RequestMapping("/disease_explains/explains")
public class DiseaseExplainsController extends BaseController
{
    @Autowired
    private DiseaseExplainsService diseaseExplainsService;

    /**
     * 查询心电图报告解析说明列表
     */
    @PreAuthorize("@ss.hasPermi('disease_explains:explains:list')")
    @GetMapping("/list")
    public TableDataInfo list(DiseaseExplains diseaseExplains)
    {
        startPage();
        List<DiseaseExplains> list = diseaseExplainsService.selectDiseaseExplainsList(diseaseExplains);
        return getDataTable(list);
    }

    /**
     * 导出心电图报告解析说明列表
     */
    @PreAuthorize("@ss.hasPermi('disease_explains:explains:export')")
    @Log(title = "心电图报告解析说明", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DiseaseExplains diseaseExplains)
    {
        List<DiseaseExplains> list = diseaseExplainsService.selectDiseaseExplainsList(diseaseExplains);
        ExcelUtil<DiseaseExplains> util = new ExcelUtil<DiseaseExplains>(DiseaseExplains.class);
        util.exportExcel(response, list, "心电图报告解析说明数据");
    }

    /**
     * 获取心电图报告解析说明详细信息
     */
    @PreAuthorize("@ss.hasPermi('disease_explains:explains:query')")
    @GetMapping(value = "/{diseaseId}")
    public AjaxResult getInfo(@PathVariable("diseaseId") Long diseaseId)
    {
        return AjaxResult.success(diseaseExplainsService.selectDiseaseExplainsByDiseaseId(diseaseId));
    }

    /**
     * 新增心电图报告解析说明
     */
    @PreAuthorize("@ss.hasPermi('disease_explains:explains:add')")
    @Log(title = "心电图报告解析说明", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DiseaseExplains diseaseExplains)
    {
        return toAjax(diseaseExplainsService.insertDiseaseExplains(diseaseExplains));
    }

    /**
     * 修改心电图报告解析说明
     */
    @PreAuthorize("@ss.hasPermi('disease_explains:explains:edit')")
    @Log(title = "心电图报告解析说明", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DiseaseExplains diseaseExplains)
    {
        return toAjax(diseaseExplainsService.updateDiseaseExplains(diseaseExplains));
    }

    /**
     * 删除心电图报告解析说明
     */
    @PreAuthorize("@ss.hasPermi('disease_explains:explains:remove')")
    @Log(title = "心电图报告解析说明", businessType = BusinessType.DELETE)
	@DeleteMapping("/{diseaseIds}")
    public AjaxResult remove(@PathVariable Long[] diseaseIds)
    {
        return toAjax(diseaseExplainsService.deleteDiseaseExplainsByDiseaseIds(diseaseIds));
    }

    @GetMapping("/getExplain")
    public AjaxResult getExplain(DiseaseExplains diseaseExplains){
        return AjaxResult.success(diseaseExplainsService.getOne((new LambdaQueryWrapper<DiseaseExplains>().eq(DiseaseExplains::getDiseaseName,diseaseExplains.getDiseaseName()).last("limit 1"))));
    }
}
