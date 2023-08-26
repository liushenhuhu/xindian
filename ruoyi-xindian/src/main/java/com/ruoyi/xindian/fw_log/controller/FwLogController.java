package com.ruoyi.xindian.fw_log.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.fw_log.service.FwLogService;
import com.ruoyi.xindian.patientCount.domain.PatientCount;
import com.ruoyi.xindian.patientCount.service.PatientCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
*
* 日志管理
*
* **/
@RestController
@RequestMapping("/fwLog/fwLog")
public class FwLogController extends BaseController {
    @Autowired
    private FwLogService fwLogService;
    @Autowired
    private AesUtils aesUtils;
    /**
     * 查询日志列表
     */
    @PreAuthorize("@ss.hasPermi('fwLog:fwLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(FwLog fwLog) throws Exception {
        if(fwLog.getUserName()!=null&&!"".equals(fwLog.getUserName())){
            fwLog.setUserName(aesUtils.encrypt(fwLog.getUserName()));
        }
        startPage();
        LambdaQueryWrapper<FwLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(fwLog.getUserName())&&fwLog.getUserName()!=null,
                        FwLog::getUserName,fwLog.getUserName())
                .between(!"".equals(fwLog.getStartTime())&&fwLog.getStartTime()!=null
                                &&!"".equals(fwLog.getEndTime())&&fwLog.getEndTime()!=null,
                        FwLog::getLogTime,fwLog.getStartTime(),fwLog.getEndTime())
                .eq(!"".equals(fwLog.getIpaddr())&&fwLog.getIpaddr()!=null,
                        FwLog::getIpaddr,fwLog.getIpaddr())
                .eq(!"".equals(fwLog.getStatus())&&fwLog.getStatus()!=null,FwLog::getStatus,fwLog.getStatus())
                .orderByDesc(FwLog::getLogTime);

        List<FwLog> list = fwLogService.list(wrapper);
        for (FwLog c:list){
            c.setUserName(aesUtils.decrypt(c.getUserName()));
        }
        return getDataTable(list);
    }

    /**
     * 导出日志记录
     */
    @PreAuthorize("@ss.hasPermi('fwLog:fwLog:export')")
    @Log(title = "服务日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FwLog fwLog) throws Exception {
        if(fwLog.getUserName()!=null&&!"".equals(fwLog.getUserName())){
            fwLog.setUserName(aesUtils.encrypt(fwLog.getUserName()));
        }
        LambdaQueryWrapper<FwLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(fwLog.getUserName())&&fwLog.getUserName()!=null,
                        FwLog::getUserName,fwLog.getUserName())
                .between(!"".equals(fwLog.getStartTime())&&fwLog.getStartTime()!=null
                                &&!"".equals(fwLog.getEndTime())&&fwLog.getEndTime()!=null,
                        FwLog::getLogTime,fwLog.getStartTime(),fwLog.getEndTime())
                .eq(!"".equals(fwLog.getIpaddr())&&fwLog.getIpaddr()!=null,
                        FwLog::getIpaddr,fwLog.getIpaddr())
                .eq(!"".equals(fwLog.getStatus())&&fwLog.getStatus()!=null,FwLog::getStatus,fwLog.getStatus())
                .orderByDesc(FwLog::getLogTime);

        List<FwLog> list = fwLogService.list(wrapper);
        for (FwLog c:list){
            c.setUserName(aesUtils.decrypt(c.getUserName()));
        }
        ExcelUtil<FwLog> util = new ExcelUtil<FwLog>(FwLog.class);
        util.exportExcel(response, list, "服务日志数据");
    }
//    /**
//     * 获取日志记录详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('fwLog:fwLog:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {
//        FwLog fwLog = fwLogService.getById(id);
//        fwLog.setUserName(aesUtils.decrypt(fwLog.getUserName()));
//        return AjaxResult.success(fwLog);
//    }
//
//    /**
//     * 新增日志
//     */
//    @PreAuthorize("@ss.hasPermi('fwLog:fwLog:add')")
//    @Log(title = "服务日志", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody FwLog fwLog) throws Exception {
//        fwLog.setUserName(aesUtils.encrypt(fwLog.getUserName()));
//        fwLog.setLogTime(new Date());
//        return toAjax(fwLogService.save(fwLog));
//    }
//    /**
//     * 修改日志记录
//     */
//    @PreAuthorize("@ss.hasPermi('fwLog:fwLog:edit')")
//    @Log(title = "服务日志", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody FwLog fwLog) throws Exception {
//
//        return toAjax(fwLogService.updateById(fwLog));
//    }
    /**
     * 删除日志记录
     */
    @PreAuthorize("@ss.hasPermi('fwLog:fwLog:remove')")
    @Log(title = "服务日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fwLogService.removeByIds(Arrays.asList(ids)));
    }
}
