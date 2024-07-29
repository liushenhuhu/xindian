package com.ruoyi.xindian.lease.controller;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.lease.domain.Lease;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.service.LeaseService;
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
 * 租赁Controller
 * 
 * @author ruoyi
 * @date 2024-07-18
 */
@RestController
@RequestMapping("/lease/lease")
public class LeaseController extends BaseController
{
    @Autowired
    private LeaseService leaseService;

    /**
     * 查询租赁列表
     */
//    @PreAuthorize("@ss.hasPermi('lease:lease:list')")
    @GetMapping("/list")
    public TableDataInfo list(Lease lease)
    {
        startPage();
        List<Lease> list = leaseService.selectLeaseList(lease);
        return getDataTable(list);
    }

    /**
     * 导出租赁列表
     */
    @PreAuthorize("@ss.hasPermi('lease:lease:export')")
    @Log(title = "租赁", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Lease lease)
    {
        List<Lease> list = leaseService.selectLeaseList(lease);
        ExcelUtil<Lease> util = new ExcelUtil<Lease>(Lease.class);
        util.exportExcel(response, list, "租赁数据");
    }

    /**
     * 获取租赁详细信息
     */
    @PreAuthorize("@ss.hasPermi('lease:lease:query')")
    @GetMapping(value = "/{leaseId}")
    public AjaxResult getInfo(@PathVariable("leaseId") Long leaseId)
    {
        return AjaxResult.success(leaseService.selectLeaseByLeaseId(leaseId));
    }


    @GetMapping("/getUsableList")
    public AjaxResult getUsableList(LeaseDetails lease)
    {
        List<Lease> list = leaseService.selectLeaseListByUsable(lease);
        return AjaxResult.success(list);
    }

    /**
     * 新增租赁
     */
    @PreAuthorize("@ss.hasPermi('lease:lease:add')")
    @Log(title = "租赁", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Lease lease)
    {
        return toAjax(leaseService.insertLease(lease));
    }

    /**
     * 修改租赁
     */
    @PreAuthorize("@ss.hasPermi('lease:lease:edit')")
    @Log(title = "租赁", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Lease lease)
    {
        return toAjax(leaseService.updateLease(lease));
    }

    /**
     * 删除租赁
     */
    @PreAuthorize("@ss.hasPermi('lease:lease:remove')")
    @Log(title = "租赁", businessType = BusinessType.DELETE)
	@DeleteMapping("/{leaseIds}")
    public AjaxResult remove(@PathVariable Long[] leaseIds)
    {
        return toAjax(leaseService.deleteLeaseByLeaseIds(leaseIds));
    }

    @GetMapping("/getPhoneIs")
    public AjaxResult getPhoneIs(Lease lease)
    {
        List<Lease> list = leaseService.list(new LambdaQueryWrapper<Lease>().eq(Lease::getPhone, lease.getPhone()).eq(Lease::getGiveBack, 0).last("limit 1"));
        return AjaxResult.success(!list.isEmpty());
    }


    @GetMapping("/getUserEqList")
    public AjaxResult getUserEqList(LeaseDetails lease)
    {
        List<Lease> list = leaseService.selectLeaseListByUsed(lease);
        return AjaxResult.success(list);
    }
}
