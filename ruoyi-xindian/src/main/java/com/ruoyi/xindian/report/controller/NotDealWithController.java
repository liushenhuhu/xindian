package com.ruoyi.xindian.report.controller;

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
import com.ruoyi.xindian.report.domain.NotDealWith;
import com.ruoyi.xindian.report.service.INotDealWithService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医生处理表Controller
 *
 * @author chenpeng
 * @date 2023-03-22
 */
@RestController
@RequestMapping("/system/with")
public class NotDealWithController extends BaseController
{
    @Autowired
    private INotDealWithService notDealWithService;

    /**
     * 查询医生处理表列表
     */
    @PreAuthorize("@ss.hasPermi('system:with:list')")
    @GetMapping("/list")
    public TableDataInfo list(NotDealWith notDealWith)
    {
        startPage();
        List<NotDealWith> list = notDealWithService.selectNotDealWithList(notDealWith);
        return getDataTable(list);
    }

    /**
     * 导出医生处理表列表
     */
    @PreAuthorize("@ss.hasPermi('system:with:export')")
    @Log(title = "医生处理表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NotDealWith notDealWith)
    {
        List<NotDealWith> list = notDealWithService.selectNotDealWithList(notDealWith);
        ExcelUtil<NotDealWith> util = new ExcelUtil<NotDealWith>(NotDealWith.class);
        util.exportExcel(response, list, "医生处理表数据");
    }

    /**
     * 获取医生处理表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:with:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(notDealWithService.selectNotDealWithById(id));
    }

    /**
     * 新增医生处理表
     */
    @PreAuthorize("@ss.hasPermi('system:with:add')")
    @Log(title = "医生处理表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NotDealWith notDealWith)
    {
        return toAjax(notDealWithService.insertNotDealWith(notDealWith));
    }

    /**
     * 修改医生处理表
     */
    @PreAuthorize("@ss.hasPermi('system:with:edit')")
    @Log(title = "医生处理表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NotDealWith notDealWith)
    {
        return toAjax(notDealWithService.updateNotDealWith(notDealWith));
    }

    /**
     * 删除医生处理表
     */
    @PreAuthorize("@ss.hasPermi('system:with:remove')")
    @Log(title = "医生处理表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(notDealWithService.deleteNotDealWithByIds(ids));
    }
}

