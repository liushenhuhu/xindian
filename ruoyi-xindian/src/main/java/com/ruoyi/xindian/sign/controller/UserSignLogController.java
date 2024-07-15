package com.ruoyi.xindian.sign.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.xindian.sign.domain.UserSignLog;
import com.ruoyi.xindian.sign.service.IUserSignLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * signController
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
@RestController
@RequestMapping("/system/log")
public class UserSignLogController extends BaseController
{
    @Autowired
    private IUserSignLogService userSignLogService;

    /**
     * 查询sign列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserSignLog userSignLog)
    {
        startPage();
        List<UserSignLog> list = userSignLogService.selectUserSignLogList(userSignLog);
        return getDataTable(list);
    }

    /**
     * 导出sign列表
     */
    @Log(title = "sign", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserSignLog userSignLog)
    {
        List<UserSignLog> list = userSignLogService.selectUserSignLogList(userSignLog);
        ExcelUtil<UserSignLog> util = new ExcelUtil<UserSignLog>(UserSignLog.class);
        util.exportExcel(response, list, "sign数据");
    }

    /**
     * 获取sign详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userSignLogService.selectUserSignLogById(id));
    }

    /**
     * 新增sign
     */
    @Log(title = "sign", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserSignLog userSignLog)
    {
        return toAjax(userSignLogService.insertUserSignLog(userSignLog));
    }

    /**
     * 修改sign
     */
    @Log(title = "sign", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserSignLog userSignLog)
    {
        return toAjax(userSignLogService.updateUserSignLog(userSignLog));
    }

    /**
     * 删除sign
     */
    @Log(title = "sign", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userSignLogService.deleteUserSignLogByIds(ids));
    }
}
