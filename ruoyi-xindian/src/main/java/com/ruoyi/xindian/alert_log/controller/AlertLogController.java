package com.ruoyi.xindian.alert_log.controller;

import com.ruoyi.common.annotation.Aes;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.alert_log.domain.AssignedAno;
import com.ruoyi.xindian.alert_log.service.IAlertLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 预警日志Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/alert_log/alert_log")
public class AlertLogController extends BaseController {
    @Autowired
    private IAlertLogService alertLogService;

    @Autowired
    private ISysUserService userService;


    @Resource
    private AesUtils aesUtils;

    /**
     * 查询预警日志列表
     */
    @Aes
//    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlertLog alertLog) throws Exception {
        List<AlertLog> list = new ArrayList<>();
        SysUser sysUser = userService.selectUserById(getUserId());
        if (sysUser != null && sysUser.getRoleId()!=null && sysUser.getRoleId() == 101) {
            String hospitalCode = sysUser.getHospitalCode();
            alertLog.setHospitalCode(hospitalCode);
            startPage();
            if (null == alertLog.getEcgType()) {
                list = alertLogService.selectAlertLogList(alertLog);
            } else if (alertLog.getEcgType().equals("12")) {
                list = alertLogService.selectAlertLogList12(alertLog);
            } else if (alertLog.getEcgType().equals("single")) {
                list = alertLogService.selectAlertLogListSingle(alertLog);
            }
        } else if (getDeptId()!=null && getDeptId() == 106) {
            Long userId = getUserId();
            alertLog.setUserId(Math.toIntExact(userId));
            startPage();
            list = alertLogService.selectAnoListByUserId(alertLog);
        } else if (sysUser != null && sysUser.getRoleId()!=null && sysUser.getRoleId() == 104) {
            alertLog.setDoctorPhone(sysUser.getPhonenumber());
            startPage();
            if (null == alertLog.getEcgType()) {
                list = alertLogService.selectAlertLogList(alertLog);
            } else if (alertLog.getEcgType().equals("12")) {
                list = alertLogService.selectAlertLogList12(alertLog);
            } else if (alertLog.getEcgType().equals("single")) {
                list = alertLogService.selectAlertLogListSingle(alertLog);
            }
        } else {
            startPage();
            if (null == alertLog.getEcgType()) {
                list = alertLogService.selectAlertLogList(alertLog);
            } else if (alertLog.getEcgType().equals("12")) {
                list = alertLogService.selectAlertLogList12(alertLog);
            } else if (alertLog.getEcgType().equals("single")) {
                list = alertLogService.selectAlertLogListSingle(alertLog);
//                return getTable(list,)
            }
        }
        for (AlertLog c:list){
            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
                c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
            }
            if (c.getPatientName()!=null&&!"".equals(c.getPatientName())){
                c.setPatientName(aesUtils.decrypt(c.getPatientName()));
            }
            if(c.getFamilyPhone()!=null&&!"".equals(c.getFamilyPhone())){
                c.setFamilyPhone(aesUtils.decrypt(c.getFamilyPhone()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 查询预警日志列表
     */
//    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:list')")
    @GetMapping("/listAno")
    public TableDataInfo listAno(AlertLog alertLog) {
        List<AlertLog> list = new ArrayList<>();
        SysUser sysUser = userService.selectUserById(getUserId());
        if (sysUser != null && sysUser.getRoleId()!=null && sysUser.getRoleId() == 101) {
            String hospitalCode = sysUser.getHospitalCode();
            alertLog.setHospitalCode(hospitalCode);
            startPage();
            list = alertLogService.selectAlertLogList(alertLog);
        } else if (getDeptId() == 106) {
            Long userId = getUserId();
            alertLog.setUserId(Math.toIntExact(userId));
            startPage();
            list = alertLogService.selectAnoListByUserId(alertLog);
        } else if (sysUser != null && sysUser.getRoleId()!=null && sysUser.getRoleId() == 104) {
            alertLog.setDoctorPhone(sysUser.getPhonenumber());
            startPage();
            list = alertLogService.selectAlertLogList(alertLog);
        } else {
            startPage();
            list = alertLogService.selectAlertLogList(alertLog);
        }
        return getDataTable(list);
    }

    /**
     * 查询预警日志列表
     */
    @PostMapping("/list1")
    public TableDataInfo list1(@RequestBody AlertLog alertLog) {
        startPage();
        List<AlertLog> list = alertLogService.selectAlertLogList(alertLog);
        return getDataTable(list);
    }


    /**
     * 导出预警日志列表
     */
//    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:export')")
    @Log(title = "预警日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlertLog alertLog) {
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
//    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") String logId) {
        return AjaxResult.success(alertLogService.selectAlertLogByLogId(logId));
    }

    /**
     * 新增预警日志
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:add')")
    @Log(title = "预警日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlertLog alertLog) {
        return toAjax(alertLogService.insertAlertLog(alertLog));
    }

    /**
     * 修改预警日志
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:edit')")
    @Log(title = "预警日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlertLog alertLog) {
        return toAjax(alertLogService.updateAlertLog(alertLog));
    }

    /**
     * 删除预警日志
     */
    @PreAuthorize("@ss.hasPermi('alert_log:alert_log:remove')")
    @Log(title = "预警日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable String[] logIds) {
        return toAjax(alertLogService.deleteAlertLogByLogIds(logIds));
    }

    @PostMapping("/newEquipment")
    public AjaxResult newEquipment(String message) {
        return AjaxResult.success("success");
    }

    @PostMapping("/assignedAno")
    public AjaxResult assignedAno(@RequestBody AssignedAno assignedAno) {
        String pId = assignedAno.getpId();
        String userId = assignedAno.getUserId();
        alertLogService.insertAno(pId);
        return toAjax(alertLogService.updateAno(userId, pId));
    }


}
