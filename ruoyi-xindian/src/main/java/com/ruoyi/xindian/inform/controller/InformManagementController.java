package com.ruoyi.xindian.inform.controller;

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
import com.ruoyi.xindian.inform.domain.InformManagement;
import com.ruoyi.xindian.inform.service.IInformManagementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报告管理Controller
 * 
 * @author hanhan
 * @date 2022-08-14
 */
@RestController
@RequestMapping("/inform/inform")
public class InformManagementController extends BaseController
{
    @Autowired
    private IInformManagementService informManagementService;

    /**
     * 查询报告管理列表
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:list')")
    @GetMapping("/list")
    public TableDataInfo list(InformManagement informManagement)
    {
        startPage();
        List<InformManagement> list = informManagementService.selectInformManagementList(informManagement);
        return getDataTable(list);
    }

    /**
     * 导出报告管理列表
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:export')")
    @Log(title = "报告管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InformManagement informManagement)
    {
        List<InformManagement> list = informManagementService.selectInformManagementList(informManagement);
        ExcelUtil<InformManagement> util = new ExcelUtil<InformManagement>(InformManagement.class);
        util.exportExcel(response, list, "报告管理数据");
    }

    /**
     * 获取报告管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:query')")
    @GetMapping(value = "/{informId}")
    public AjaxResult getInfo(@PathVariable("informId") String informId)
    {
        return AjaxResult.success(informManagementService.selectInformManagementByInformId(informId));
    }

    /**
     * 新增报告管理
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:add')")
    @Log(title = "报告管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InformManagement informManagement)
    {
        return toAjax(informManagementService.insertInformManagement(informManagement));
    }

    /**
     * 修改报告管理
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:edit')")
    @Log(title = "报告管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InformManagement informManagement)
    {
        return toAjax(informManagementService.updateInformManagement(informManagement));
    }

    /**
     * 删除报告管理
     */
    @PreAuthorize("@ss.hasPermi('inform:inform:remove')")
    @Log(title = "报告管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{informIds}")
    public AjaxResult remove(@PathVariable String[] informIds)
    {
        return toAjax(informManagementService.deleteInformManagementByInformIds(informIds));
    }
}
