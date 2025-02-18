package com.ruoyi.xindian.label.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.label.VO.AuditVo;
import com.ruoyi.xindian.label.VO.LogUserVO;
import com.ruoyi.xindian.label.domain.AlertLogAudit;
import com.ruoyi.xindian.label.service.AlertLogAuditService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/label/audit")
public class AlertLogAuditController extends BaseController {




    @Resource
    private AlertLogAuditService alertLogAuditService;


    @Resource
    private TokenService tokenService;

    /**
     * 查询标注数据审核列表
     */
    @PreAuthorize("@ss.hasPermi('label:audit:list')")
    @GetMapping("/list")
    public TableDataInfo list(AuditVo auditVo, HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser.getUser().getDeptId()==202){
            auditVo.setUserId(loginUser.getUserId());
        }
        startPage();
        List<AuditVo> list = alertLogAuditService.selectAlertLogAuditList(auditVo);
        return getDataTable(list);
    }

    /**
     * 导出标注数据审核列表
     */
    @PreAuthorize("@ss.hasPermi('label:audit:export')")
    @Log(title = "标注数据审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AuditVo auditVo)
    {
        List<AlertLogAudit> list = alertLogAuditService.selectAuditList(auditVo);
        ExcelUtil<AlertLogAudit> util = new ExcelUtil<AlertLogAudit>(AlertLogAudit.class);
        util.exportExcel(response, list, "标注数据审核数据");
    }

    /**
     * 获取标注数据审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('label:audit:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") String logId)
    {
        return AjaxResult.success(alertLogAuditService.selectAlertLogAuditByLogId(logId));
    }

    /**
     * 新增标注数据审核
     */
    @PreAuthorize("@ss.hasPermi('label:audit:add')")
    @Log(title = "标注数据审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlertLogAudit alertLogAudit)
    {
        alertLogAudit.setUserId(getUserId());
        return toAjax(alertLogAuditService.insertAlertLogAudit(alertLogAudit));
    }

    /**
     * 修改标注数据审核
     */
    @PreAuthorize("@ss.hasPermi('label:audit:edit')")
    @Log(title = "标注数据审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlertLogAudit alertLogAudit)
    {
        alertLogAudit.setUserId(getUserId());
        return toAjax(alertLogAuditService.updateAlertLogAudit(alertLogAudit));
    }

    /**
     * 删除标注数据审核
     */
    @PreAuthorize("@ss.hasPermi('label:audit:remove')")
    @Log(title = "标注数据审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable String[] logIds)
    {
        return toAjax(alertLogAuditService.deleteAlertLogAuditByLogIds(logIds));
    }




    @GetMapping("/getLogId")
    public AjaxResult getLogId(String logId)
    {

        Map<String,Object> map = new HashMap<>();

        //审核后的结果
        AlertLogAudit alertLogAudit = alertLogAuditService.selectAlertLogAuditByLogId(logId);

        map.put("Audit",alertLogAudit);

        //标注人员的结果
        List<LogUserVO> logUserVOS = alertLogAuditService.selectLogUser(logId);
        for (int i = 0; i < logUserVOS.size(); i++) {
            map.put("user"+i,logUserVOS.get(i));
        }


        return AjaxResult.success(map);
    }



}
