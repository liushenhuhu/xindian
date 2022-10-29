package com.ruoyi.xindian.pdfPath.controller;

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
import com.ruoyi.xindian.pdfPath.domain.PdfPath;
import com.ruoyi.xindian.pdfPath.service.IPdfPathService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * pdf保存路径Controller
 * 
 * @author hanhan
 * @date 2022-10-29
 */
@RestController
@RequestMapping("/pdfPath/pdfPath")
public class PdfPathController extends BaseController
{
    @Autowired
    private IPdfPathService pdfPathService;

    /**
     * 查询pdf保存路径列表
     */
    @PreAuthorize("@ss.hasPermi('pdfPath:pdfPath:list')")
    @GetMapping("/list")
    public TableDataInfo list(PdfPath pdfPath)
    {
        startPage();
        List<PdfPath> list = pdfPathService.selectPdfPathList(pdfPath);
        return getDataTable(list);
    }

    /**
     * 查询pdf保存路径列表
     */
    @PostMapping("/list1")
    public AjaxResult list1(@RequestBody PdfPath pdfPath)
    {
        List<PdfPath> list = pdfPathService.selectPdfPathList(pdfPath);
        return AjaxResult.success(list);
    }

    /**
     * 导出pdf保存路径列表
     */
    @PreAuthorize("@ss.hasPermi('pdfPath:pdfPath:export')")
    @Log(title = "pdf保存路径", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PdfPath pdfPath)
    {
        List<PdfPath> list = pdfPathService.selectPdfPathList(pdfPath);
        ExcelUtil<PdfPath> util = new ExcelUtil<PdfPath>(PdfPath.class);
        util.exportExcel(response, list, "pdf保存路径数据");
    }

    /**
     * 获取pdf保存路径详细信息
     */
    @PreAuthorize("@ss.hasPermi('pdfPath:pdfPath:query')")
    @GetMapping(value = "/{pathId}")
    public AjaxResult getInfo(@PathVariable("pathId") Long pathId)
    {
        return AjaxResult.success(pdfPathService.selectPdfPathByPathId(pathId));
    }

    /**
     * 新增pdf保存路径
     */
    @PreAuthorize("@ss.hasPermi('pdfPath:pdfPath:add')")
    @Log(title = "pdf保存路径", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PdfPath pdfPath)
    {
        return toAjax(pdfPathService.insertPdfPath(pdfPath));
    }

    /**
     * 修改pdf保存路径
     */
    @PreAuthorize("@ss.hasPermi('pdfPath:pdfPath:edit')")
    @Log(title = "pdf保存路径", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PdfPath pdfPath)
    {
        return toAjax(pdfPathService.updatePdfPath(pdfPath));
    }

    /**
     * 删除pdf保存路径
     */
    @PreAuthorize("@ss.hasPermi('pdfPath:pdfPath:remove')")
    @Log(title = "pdf保存路径", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pathIds}")
    public AjaxResult remove(@PathVariable Long[] pathIds)
    {
        return toAjax(pdfPathService.deletePdfPathByPathIds(pathIds));
    }
}
