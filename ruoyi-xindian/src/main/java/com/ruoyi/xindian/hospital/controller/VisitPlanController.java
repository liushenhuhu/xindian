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
import com.ruoyi.xindian.hospital.domain.VisitPlan;
import com.ruoyi.xindian.hospital.service.IVisitPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出诊信息表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/visitPlan")
public class VisitPlanController extends BaseController
{
    @Autowired
    private IVisitPlanService visitPlanService;

    /**
     * 查询出诊信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisitPlan visitPlan)
    {
        startPage();
        List<VisitPlan> list = visitPlanService.selectVisitPlanList(visitPlan);
        return getDataTable(list);
    }

    /**
     * 导出出诊信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:export')")
    @Log(title = "出诊信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitPlan visitPlan)
    {
        List<VisitPlan> list = visitPlanService.selectVisitPlanList(visitPlan);
        ExcelUtil<VisitPlan> util = new ExcelUtil<VisitPlan>(VisitPlan.class);
        util.exportExcel(response, list, "出诊信息表数据");
    }

    /**
     * 获取出诊信息表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(visitPlanService.selectVisitPlanById(id));
    }

    /**
     * 新增出诊信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:add')")
    @Log(title = "出诊信息表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitPlan visitPlan)
    {
        return toAjax(visitPlanService.insertVisitPlan(visitPlan));
    }

    /**
     * 修改出诊信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:edit')")
    @Log(title = "出诊信息表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitPlan visitPlan)
    {
        return toAjax(visitPlanService.updateVisitPlan(visitPlan));
    }

    /**
     * 删除出诊信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:remove')")
    @Log(title = "出诊信息表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visitPlanService.deleteVisitPlanByIds(ids));
    }
}
