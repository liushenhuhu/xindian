package com.ruoyi.xindian.weekDetectionTime.controller;

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
import com.ruoyi.xindian.weekDetectionTime.domain.WeekDetectionTime;
import com.ruoyi.xindian.weekDetectionTime.service.IWeekDetectionTimeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * weekDetectionTimeController
 * 
 * @author chenpeng
 * @date 2024-07-22
 */
@RestController
@RequestMapping("/weekDetectionTime/weekDetectionTime")
public class WeekDetectionTimeController extends BaseController
{
    @Autowired
    private IWeekDetectionTimeService weekDetectionTimeService;

    /**
     * 查询weekDetectionTime列表
     */
    @PreAuthorize("@ss.hasPermi('weekDetectionTime:weekDetectionTime:list')")
    @GetMapping("/list")
    public TableDataInfo list(WeekDetectionTime weekDetectionTime)
    {
        startPage();
        List<WeekDetectionTime> list = weekDetectionTimeService.selectWeekDetectionTimeList(weekDetectionTime);
        return getDataTable(list);
    }

    /**
     * 导出weekDetectionTime列表
     */
    @PreAuthorize("@ss.hasPermi('weekDetectionTime:weekDetectionTime:export')")
    @Log(title = "weekDetectionTime", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WeekDetectionTime weekDetectionTime)
    {
        List<WeekDetectionTime> list = weekDetectionTimeService.selectWeekDetectionTimeList(weekDetectionTime);
        ExcelUtil<WeekDetectionTime> util = new ExcelUtil<WeekDetectionTime>(WeekDetectionTime.class);
        util.exportExcel(response, list, "weekDetectionTime数据");
    }

    /**
     * 获取weekDetectionTime详细信息
     */
    @PreAuthorize("@ss.hasPermi('weekDetectionTime:weekDetectionTime:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(weekDetectionTimeService.selectWeekDetectionTimeById(id));
    }

    /**
     * 新增weekDetectionTime
     */
    @PreAuthorize("@ss.hasPermi('weekDetectionTime:weekDetectionTime:add')")
    @Log(title = "weekDetectionTime", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WeekDetectionTime weekDetectionTime)
    {
        return toAjax(weekDetectionTimeService.insertWeekDetectionTime(weekDetectionTime));
    }

    /**
     * 修改weekDetectionTime
     */
    @PreAuthorize("@ss.hasPermi('weekDetectionTime:weekDetectionTime:edit')")
    @Log(title = "weekDetectionTime", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WeekDetectionTime weekDetectionTime)
    {
        return toAjax(weekDetectionTimeService.updateWeekDetectionTime(weekDetectionTime));
    }

    /**
     * 删除weekDetectionTime
     */
    @PreAuthorize("@ss.hasPermi('weekDetectionTime:weekDetectionTime:remove')")
    @Log(title = "weekDetectionTime", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(weekDetectionTimeService.deleteWeekDetectionTimeByIds(ids));
    }
}
