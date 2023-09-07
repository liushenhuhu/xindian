package com.ruoyi.xindian.callLog.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xindian.callLog.domain.CallLog;
import com.ruoyi.xindian.callLog.service.ICallLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【通话记录】Controller
 * 
 * @author ruoyi
 * @date 2023-09-05
 */
@RestController
@RequestMapping("/callLog/callLog")
public class CallLogController extends BaseController
{
    @Autowired
    private ICallLogService callLogService;

    /**
     * 查询【通话记录】列表
     */
    @PreAuthorize("@ss.hasPermi('callLog:callLog:list')")
    @GetMapping("/list")
    public TableDataInfo pagelist(CallLog callLog)
    {
        startPage();
        List<CallLog> list = callLogService.selectCallLogList(callLog);
        return getDataTable(list);
    }


    /**
     * 导出【通话记录】列表
     */
    @PreAuthorize("@ss.hasPermi('callLog:callLog:export')")
    @Log(title = "【通话记录】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CallLog callLog)
    {
        List<CallLog> list = callLogService.selectCallLogList(callLog);
        ExcelUtil<CallLog> util = new ExcelUtil<CallLog>(CallLog.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【通话记录】详细信息
     */
    @PreAuthorize("@ss.hasPermi('callLog:callLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(callLogService.selectCallLogById(id));
    }
    //根据pid查3个
    @PreAuthorize("@ss.hasPermi('callLog:callLog:list')")
    @GetMapping("/web/list")
    public AjaxResult weblist(@RequestParam("deviceSn") String deviceSn)
    {
        Long pId = callLogService.selectPidByDeviceSn(deviceSn);

        List<CallLog> list = callLogService.selectCallLogWebList(pId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid",pId);
        map.put("list",list);
        return AjaxResult.success(map);
    }

    /**
     * 新增【通话记录】
     */
    @PreAuthorize("@ss.hasPermi('callLog:callLog:add')")
    @Log(title = "【通话记录】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CallLog callLog)
    {
        return toAjax(callLogService.insertCallLog(callLog));
    }

    /**
     * 修改【通话记录】
     */
    @PreAuthorize("@ss.hasPermi('callLog:callLog:edit')")
    @Log(title = "【通话记录】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CallLog callLog)
    {
        return toAjax(callLogService.updateCallLog(callLog));
    }

    /**
     * 删除【通话记录】
     */
    @PreAuthorize("@ss.hasPermi('callLog:callLog:remove')")
    @Log(title = "【通话记录】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(callLogService.deleteCallLogByIds(ids));
    }
}
