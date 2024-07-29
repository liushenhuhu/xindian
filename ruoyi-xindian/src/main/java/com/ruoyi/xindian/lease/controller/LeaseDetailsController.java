package com.ruoyi.xindian.lease.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.lease.domain.Lease;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.service.LeaseDetailsService;
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
 * 租赁详情Controller
 * 
 * @author ruoyi
 * @date 2024-07-18
 */
@RestController
@RequestMapping("/lease_details/details")
public class LeaseDetailsController extends BaseController
{
    @Autowired
    private LeaseDetailsService leaseDetailsService;

    /**
     * 查询租赁详情列表
     */
//    @PreAuthorize("@ss.hasPermi('lease_details:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(LeaseDetails leaseDetails)
    {
        startPage();
        List<LeaseDetails> list = leaseDetailsService.selectLeaseDetailsList(leaseDetails);
        return getDataTable(list);
    }

    /**
     * 导出租赁详情列表
     */
//    @PreAuthorize("@ss.hasPermi('lease_details:details:export')")
    @Log(title = "租赁详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LeaseDetails leaseDetails)
    {
        List<LeaseDetails> list = leaseDetailsService.selectLeaseDetailsList(leaseDetails);
        ExcelUtil<LeaseDetails> util = new ExcelUtil<LeaseDetails>(LeaseDetails.class);
        util.exportExcel(response, list, "租赁详情数据");
    }

    /**
     * 获取租赁详情详细信息
     */
//    @PreAuthorize("@ss.hasPermi('lease_details:details:query')")
    @GetMapping(value = "/{leaseDetailsId}")
    public AjaxResult getInfo(@PathVariable("leaseDetailsId") Long leaseDetailsId)
    {
        return AjaxResult.success(leaseDetailsService.selectLeaseDetailsByLeaseDetailsId(leaseDetailsId));
    }

    /**
     * 新增租赁详情
     */
//    @PreAuthorize("@ss.hasPermi('lease_details:details:add')")
    @Log(title = "租赁详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LeaseDetails leaseDetails)
    {
        return toAjax(leaseDetailsService.insertLeaseDetails(leaseDetails));
    }

    /**
     * 修改租赁详情
     */
//    @PreAuthorize("@ss.hasPermi('lease_details:details:edit')")
    @Log(title = "租赁详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LeaseDetails leaseDetails)
    {
        return toAjax(leaseDetailsService.updateLeaseDetails(leaseDetails));
    }

    /**
     * 删除租赁详情
     */
//    @PreAuthorize("@ss.hasPermi('lease_details:details:remove')")
    @Log(title = "租赁详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{leaseDetailsIds}")
    public AjaxResult remove(@PathVariable Long[] leaseDetailsIds)
    {
        return toAjax(leaseDetailsService.deleteLeaseDetailsByLeaseDetailsIds(leaseDetailsIds));
    }

    @PutMapping("/putStatus")
    public AjaxResult putStatus(@RequestBody LeaseDetails leaseDetails) throws Exception {
        return toAjax(leaseDetailsService.updateLeaseDetailsByEquipmentCode(leaseDetails));
    }

    @GetMapping("/getLeaseByPhone")
    public AjaxResult getLeaseByPhone(LeaseDetails lease)
    {
        return AjaxResult.success(leaseDetailsService.selectLeaseByPhone(lease));
    }

    /**
     * 归还设备
     * @param lease
     * @return
     */
    @PostMapping("/giveBack")
    public AjaxResult giveBack(@RequestBody LeaseDetails lease,HttpServletResponse response) throws Exception {
        return AjaxResult.success(leaseDetailsService.giveBack(lease,response));
    }
}
