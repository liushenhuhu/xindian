package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.verify.domain.SxDdReport;
import com.ruoyi.xindian.verify.service.SxDdReportService;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 善行单导心电贴Controller
 *
 * @author ruoyi
 * @date 2024-08-06
 */
@RestController
@RequestMapping("/sx_single/sx_single")
public class SxDdReportWebController extends BaseController
{
    @Autowired
    private SxDdReportService sxDdReportService;

    /**
     * 查询善行单导心电贴列表
     */
    @PreAuthorize("@ss.hasPermi('sx_single:sx_single:list')")
    @GetMapping("/list")
    public TableDataInfo list(SxDdReport sxDdReport)
    {
        startPage();
        List<SxDdReport> list = sxDdReportService.selectSxDdReportList(sxDdReport);
        return getDataTable(list);
    }

    /**
     * 导出善行单导心电贴列表
     */
    @PreAuthorize("@ss.hasPermi('sx_single:sx_single:export')")
    @Log(title = "善行单导心电贴", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SxDdReport sxDdReport)
    {
        List<SxDdReport> list = sxDdReportService.selectSxDdReportList(sxDdReport);
        ExcelUtil<SxDdReport> util = new ExcelUtil<SxDdReport>(SxDdReport.class);
        util.exportExcel(response, list, "善行单导心电贴数据");
    }

    /**
     * 获取善行单导心电贴详细信息
     */
    @PreAuthorize("@ss.hasPermi('sx_single:sx_single:query')")
    @GetMapping(value = "/{sxReportId}")
    public AjaxResult getInfo(@PathVariable("sxReportId") Long sxReportId)
    {
        return AjaxResult.success(sxDdReportService.selectSxDdReportBySxReportId(sxReportId));
    }

    /**
     * 新增善行单导心电贴
     */
    @PreAuthorize("@ss.hasPermi('sx_single:sx_single:add')")
    @Log(title = "善行单导心电贴", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SxDdReport sxDdReport)
    {
        return toAjax(sxDdReportService.insertSxDdReport(sxDdReport));
    }

    /**
     * 修改善行单导心电贴
     */
    @PreAuthorize("@ss.hasPermi('sx_single:sx_single:edit')")
    @Log(title = "善行单导心电贴", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SxDdReport sxDdReport)
    {
        return toAjax(sxDdReportService.updateSxDdReport(sxDdReport));
    }

    /**
     * 删除善行单导心电贴
     */
    @PreAuthorize("@ss.hasPermi('sx_single:sx_single:remove')")
    @Log(title = "善行单导心电贴", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sxReportIds}")
    public AjaxResult remove(@PathVariable Long[] sxReportIds)
    {
        return toAjax(sxDdReportService.deleteSxDdReportBySxReportIds(sxReportIds));
    }

//    @GetMapping("/getFilePath")
//    public AjaxResult getFilePath(SxDdReport sxDdReport)
//    {
//        return AjaxResult.success(sxDdReportService.getFilePath(sxDdReport));
//    }
}
