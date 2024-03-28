package com.ruoyi.xindian.log_user.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.log_user.domain.AlertLogCount;
import com.ruoyi.xindian.log_user.service.AlertLogCountService;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
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
 * 预警类型统计Controller
 * 
 * @author ruoyi
 * @date 2024-03-28
 */
@RestController
@RequestMapping("/alert_log_count/count")
public class AlertLogCountController extends BaseController
{
    @Autowired
    private AlertLogCountService alertLogCountService;


    /**
     * 查询预警类型统计列表
     */
    @PreAuthorize("@ss.hasPermi('alert_log_count:count:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlertLogCount alertLogCount)
    {
        startPage();
        List<AlertLogCount> list = alertLogCountService.selectAlertLogCountList(alertLogCount);
        return getDataTable(list);
    }

    /**
     * 导出预警类型统计列表
     */
    @PreAuthorize("@ss.hasPermi('alert_log_count:count:export')")
    @Log(title = "预警类型统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlertLogCount alertLogCount)
    {
        List<AlertLogCount> list = alertLogCountService.selectAlertLogCountList(alertLogCount);
        ExcelUtil<AlertLogCount> util = new ExcelUtil<AlertLogCount>(AlertLogCount.class);
        util.exportExcel(response, list, "预警类型统计数据");
    }

    /**
     * 获取预警类型统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('alert_log_count:count:query')")
    @GetMapping(value = "/{countId}")
    public AjaxResult getInfo(@PathVariable("countId") Long countId)
    {
        return AjaxResult.success(alertLogCountService.selectAlertLogCountByCountId(countId));
    }

    /**
     * 新增预警类型统计
     */
    @PreAuthorize("@ss.hasPermi('alert_log_count:count:add')")
    @Log(title = "预警类型统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlertLogCount alertLogCount)
    {
        Long userId = getUserId();
        alertLogCount.setUserId(userId);
        return toAjax(alertLogCountService.insertAlertLogCount(alertLogCount));
    }

    /**
     * 修改预警类型统计
     */
    @PreAuthorize("@ss.hasPermi('alert_log_count:count:edit')")
    @Log(title = "预警类型统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlertLogCount alertLogCount)
    {
        return toAjax(alertLogCountService.updateAlertLogCount(alertLogCount));
    }

    /**
     * 删除预警类型统计
     */
    @PreAuthorize("@ss.hasPermi('alert_log_count:count:remove')")
    @Log(title = "预警类型统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{countIds}")
    public AjaxResult remove(@PathVariable Long[] countIds)
    {
        return toAjax(alertLogCountService.deleteAlertLogCountByCountIds(countIds));
    }

    /**
     * 查找数据库统计中的所有类型
     * @return
     */
    @GetMapping("/getLogType")
    public AjaxResult getLogType(){
        List<ListValueAndLabelVO> logType = alertLogCountService.getLogType();
        return AjaxResult.success(logType);
    }
}
