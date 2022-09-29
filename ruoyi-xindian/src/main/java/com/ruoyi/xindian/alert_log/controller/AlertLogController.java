package com.ruoyi.xindian.alert_log.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
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
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.alert_log.service.IAlertLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 预警日志Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/alert_log/alert_log")
public class AlertLogController extends BaseController
{
    @Autowired
    private IAlertLogService alertLogService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询预警日志列表
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlertLog alertLog)
    {
        List<AlertLog> list = new ArrayList<>();
        if (getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalName = sysUser.getHospitalName();
            alertLog.setHospitalName(hospitalName);
            startPage();
            list = alertLogService.selectAlertLogList(alertLog);
        } else {
            startPage();
            list = alertLogService.selectAlertLogList(alertLog);
        }
        return getDataTable(list);
    }

    /**
     * 导出预警日志列表
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:export')")
    @Log(title = "预警日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlertLog alertLog)
    {

        List<AlertLog> list = new ArrayList<>();
        if (getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalName = sysUser.getHospitalName();
            alertLog.setHospitalName(hospitalName);
            list = alertLogService.selectAlertLogList(alertLog);
        } else {
            list = alertLogService.selectAlertLogList(alertLog);
        }

        ExcelUtil<AlertLog> util = new ExcelUtil<AlertLog>(AlertLog.class);
        util.exportExcel(response, list, "预警日志数据");
    }

    /**
     * 获取预警日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId)
    {
        return AjaxResult.success(alertLogService.selectAlertLogByLogId(logId));
    }

    /**
     * 新增预警日志
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:add')")
    @Log(title = "预警日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlertLog alertLog)
    {
        return toAjax(alertLogService.insertAlertLog(alertLog));
    }

    /**
     * 修改预警日志
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:edit')")
    @Log(title = "预警日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlertLog alertLog)
    {
        return toAjax(alertLogService.updateAlertLog(alertLog));
    }

    /**
     * 删除预警日志
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:remove')")
    @Log(title = "预警日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable Long[] logIds)
    {
        return toAjax(alertLogService.deleteAlertLogByLogIds(logIds));
    }
}
