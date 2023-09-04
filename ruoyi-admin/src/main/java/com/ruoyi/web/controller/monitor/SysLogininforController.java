package com.ruoyi.web.controller.monitor;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.domain.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.service.ISysLogininforService;

/**
 * 系统访问记录
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController
{
    @Autowired
    private ISysLogininforService logininforService;
    @Autowired
    private AesUtils aesUtils;
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysLogininfor logininfor) throws Exception {
        startPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        for (SysLogininfor c :list){
            if (c.getUserName()!=null&&!"".equals(c.getUserName())){
                c.setUserName(aesUtils.decrypt(c.getUserName()));
            }
        }
        return getDataTable(list);
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLogininfor logininfor) throws Exception {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        for (SysLogininfor c :list){
            if (c.getUserName()!=null&&!"".equals(c.getUserName())){
                c.setUserName(aesUtils.decrypt(c.getUserName()));
            }
        }
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        logininforService.cleanLogininfor();
        return AjaxResult.success();
    }
}
