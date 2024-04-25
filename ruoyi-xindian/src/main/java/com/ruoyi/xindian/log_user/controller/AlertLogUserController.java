package com.ruoyi.xindian.log_user.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.log_user.domain.SelectVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xindian.log_user.domain.AlertLogUser;
import com.ruoyi.xindian.log_user.service.IAlertLogUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标注分配Controller
 * 
 * @author hanhan
 * @date 2022-12-30
 */
@RestController
@RequestMapping("/log_user/log_user")
public class AlertLogUserController extends BaseController
{
    @Autowired
    private IAlertLogUserService alertLogUserService;

    /**
     * 查询标注分配列表
     */
    @PreAuthorize("@ss.hasPermi('log_user:log_user:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlertLogUser alertLogUser)
    {
        List<AlertLogUser> list = new ArrayList<>();
        if (getDeptId() != null && getDeptId() == 106) {
            Long userId = getUserId();
            alertLogUser.setUserId((long) Math.toIntExact(userId));
            startPage();
            list = alertLogUserService.selectAlertLogUserList(alertLogUser);
        } else {
            startPage();
            list = alertLogUserService.selectAlertLogUserList(alertLogUser);
        }
        return getDataTable(list);
    }

    /**
     * 导出标注分配列表
     */
    @PreAuthorize("@ss.hasPermi('log_user:log_user:export')")
    @Log(title = "标注分配", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlertLogUser alertLogUser)
    {
        alertLogUser.setAnoStatus(1L);
        List<AlertLogUser> list = new ArrayList<>();
        if (getDeptId() != null && getDeptId() == 106) {
            Long userId = getUserId();
            alertLogUser.setUserId((long) Math.toIntExact(userId));
            list = alertLogUserService.selectAlertLogUserList(alertLogUser);
        } else {
            list = alertLogUserService.selectAlertLogUserList(alertLogUser);
        }

        ExcelUtil<AlertLogUser> util = new ExcelUtil<AlertLogUser>(AlertLogUser.class);
        util.exportExcel(response, list, "标注分配数据");
    }

    /**
     * 获取标注分配详细信息
     */
    @PreAuthorize("@ss.hasPermi('log_user:log_user:query')")
    @GetMapping(value = "/edit")
    public AjaxResult getInfo(@RequestParam("logId") String logId,@RequestParam("userId") String userId)
    {
        return AjaxResult.success(alertLogUserService.selectAlertLogUserByLogId(logId,userId));
    }

    /**
     * 新增标注分配
     */
    @PreAuthorize("@ss.hasPermi('log_user:log_user:add')")
    @Log(title = "标注分配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlertLogUser alertLogUser)
    {
        return toAjax(alertLogUserService.insertAlertLogUser(alertLogUser));
    }

    /**
     * 修改标注分配
     */
    @PreAuthorize("@ss.hasPermi('log_user:log_user:edit')")
    @Log(title = "标注分配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlertLogUser alertLogUser)
    {
        return toAjax(alertLogUserService.updateAlertLogUser(alertLogUser));
    }

    /**
     * 删除标注分配
     */
    @PreAuthorize("@ss.hasPermi('log_user:log_user:remove')")
    @Log(title = "标注分配", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable String[] logIds)
    {
        return toAjax(alertLogUserService.deleteAlertLogUserByLogIds(logIds));
    }
    /**
    *
     * select数据查询
    * */
    //@PreAuthorize("@ss.hasPermi('log_user:log_user:remove')")
    //@Log(title = "预警类型列表查询", businessType = BusinessType.DELETE)
    @GetMapping("/selectlist")
    public AjaxResult selectlist()
    {
        List<SelectVO> selectVOList=alertLogUserService.selectList();
        return AjaxResult.success(selectVOList);
    }
    //标注成功
    @GetMapping("/islabel")
    public AjaxResult islabel(AlertLogUser alertLogUser) {
        return toAjax(alertLogUserService.islabel(alertLogUser));
    }
}
