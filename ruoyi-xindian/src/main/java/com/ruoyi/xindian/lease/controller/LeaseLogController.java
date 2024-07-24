package com.ruoyi.xindian.lease.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.lease.domain.LeaseLog;
import com.ruoyi.xindian.lease.service.LeaseLogService;
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
 * 租赁日志Controller
 * 
 * @author ruoyi
 * @date 2024-07-23
 */
@RestController
@RequestMapping("/lease/lease_log")
public class LeaseLogController extends BaseController
{
    @Autowired
    private LeaseLogService leaseLogService;

    /**
     * 查询租赁日志列表
     */
    @PreAuthorize("@ss.hasPermi('lease:lease_log:list')")
    @GetMapping("/list")
    public TableDataInfo list(LeaseLog leaseLog)
    {
        startPage();
        List<LeaseLog> list = leaseLogService.selectLeaseLogList(leaseLog);
        return getDataTable(list);
    }

    /**
     * 导出租赁日志列表
     */
    @PreAuthorize("@ss.hasPermi('lease:lease_log:export')")
    @Log(title = "租赁日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LeaseLog leaseLog)
    {
        List<LeaseLog> list = leaseLogService.selectLeaseLogList(leaseLog);
        ExcelUtil<LeaseLog> util = new ExcelUtil<LeaseLog>(LeaseLog.class);
        util.exportExcel(response, list, "租赁日志数据");
    }

    /**
     * 获取租赁日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('lease:lease_log:query')")
    @GetMapping(value = "/{leaseLogId}")
    public AjaxResult getInfo(@PathVariable("leaseLogId") Long leaseLogId)
    {
        return AjaxResult.success(leaseLogService.selectLeaseLogByLeaseLogId(leaseLogId));
    }

    /**
     * 新增租赁日志
     */
    @PreAuthorize("@ss.hasPermi('lease:lease_log:add')")
    @Log(title = "租赁日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LeaseLog leaseLog)
    {
        return toAjax(leaseLogService.insertLeaseLog(leaseLog));
    }

    /**
     * 修改租赁日志
     */
    @PreAuthorize("@ss.hasPermi('lease:lease_log:edit')")
    @Log(title = "租赁日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LeaseLog leaseLog)
    {
        return toAjax(leaseLogService.updateLeaseLog(leaseLog));
    }

    /**
     * 删除租赁日志
     */
    @PreAuthorize("@ss.hasPermi('lease:lease_log:remove')")
    @Log(title = "租赁日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{leaseLogIds}")
    public AjaxResult remove(@PathVariable Long[] leaseLogIds)
    {
        return toAjax(leaseLogService.deleteLeaseLogByLeaseLogIds(leaseLogIds));
    }
}
