package com.ruoyi.xindian.hospital.controller;

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
import com.ruoyi.xindian.hospital.domain.UserCase;
import com.ruoyi.xindian.hospital.service.IUserCaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户病历Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/userCase")
public class UserCaseController extends BaseController
{
    @Autowired
    private IUserCaseService userCaseService;

    /**
     * 查询用户病历列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:userCase:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserCase userCase)
    {
        startPage();
        List<UserCase> list = userCaseService.selectUserCaseList(userCase);
        return getDataTable(list);
    }

    /**
     * 导出用户病历列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:userCase:export')")
    @Log(title = "用户病历", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserCase userCase)
    {
        List<UserCase> list = userCaseService.selectUserCaseList(userCase);
        ExcelUtil<UserCase> util = new ExcelUtil<UserCase>(UserCase.class);
        util.exportExcel(response, list, "用户病历数据");
    }

    /**
     * 获取用户病历详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:userCase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userCaseService.selectUserCaseById(id));
    }

    /**
     * 新增用户病历
     */
    @PreAuthorize("@ss.hasPermi('hospital:userCase:add')")
    @Log(title = "用户病历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserCase userCase)
    {
        return toAjax(userCaseService.insertUserCase(userCase));
    }

    /**
     * 修改用户病历
     */
    @PreAuthorize("@ss.hasPermi('hospital:userCase:edit')")
    @Log(title = "用户病历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserCase userCase)
    {
        return toAjax(userCaseService.updateUserCase(userCase));
    }

    /**
     * 删除用户病历
     */
    @PreAuthorize("@ss.hasPermi('hospital:userCase:remove')")
    @Log(title = "用户病历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userCaseService.deleteUserCaseByIds(ids));
    }
}
