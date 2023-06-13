package com.ruoyi.xindian.hospital.controller;

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
import com.ruoyi.xindian.hospital.domain.VisitBlacklist;
import com.ruoyi.xindian.hospital.service.IVisitBlacklistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出诊黑名单Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/visitBlacklist")
public class VisitBlacklistController extends BaseController
{
    @Autowired
    private IVisitBlacklistService visitBlacklistService;

    /**
     * 查询出诊黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitBlacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisitBlacklist visitBlacklist)
    {
        startPage();
        List<VisitBlacklist> list = visitBlacklistService.selectVisitBlacklistList(visitBlacklist);
        return getDataTable(list);
    }

    /**
     * 导出出诊黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitBlacklist:export')")
    @Log(title = "出诊黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitBlacklist visitBlacklist)
    {
        List<VisitBlacklist> list = visitBlacklistService.selectVisitBlacklistList(visitBlacklist);
        ExcelUtil<VisitBlacklist> util = new ExcelUtil<VisitBlacklist>(VisitBlacklist.class);
        util.exportExcel(response, list, "出诊黑名单数据");
    }

    /**
     * 获取出诊黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitBlacklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(visitBlacklistService.selectVisitBlacklistById(id));
    }

    /**
     * 新增出诊黑名单
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitBlacklist:add')")
    @Log(title = "出诊黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitBlacklist visitBlacklist)
    {
        return toAjax(visitBlacklistService.insertVisitBlacklist(visitBlacklist));
    }

    /**
     * 修改出诊黑名单
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitBlacklist:edit')")
    @Log(title = "出诊黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitBlacklist visitBlacklist)
    {
        return toAjax(visitBlacklistService.updateVisitBlacklist(visitBlacklist));
    }

    /**
     * 删除出诊黑名单
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitBlacklist:remove')")
    @Log(title = "出诊黑名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visitBlacklistService.deleteVisitBlacklistByIds(ids));
    }
}
