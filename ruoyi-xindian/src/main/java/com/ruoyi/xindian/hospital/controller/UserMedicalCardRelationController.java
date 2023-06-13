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
import com.ruoyi.xindian.hospital.domain.UserMedicalCardRelation;
import com.ruoyi.xindian.hospital.service.IUserMedicalCardRelationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户就诊卡关系表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/userMedicalCardRelation")
public class UserMedicalCardRelationController extends BaseController
{
    @Autowired
    private IUserMedicalCardRelationService userMedicalCardRelationService;

    /**
     * 查询用户就诊卡关系表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:userMedicalCardRelation:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserMedicalCardRelation userMedicalCardRelation)
    {
        startPage();
        List<UserMedicalCardRelation> list = userMedicalCardRelationService.selectUserMedicalCardRelationList(userMedicalCardRelation);
        return getDataTable(list);
    }

    /**
     * 导出用户就诊卡关系表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:userMedicalCardRelation:export')")
    @Log(title = "用户就诊卡关系表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserMedicalCardRelation userMedicalCardRelation)
    {
        List<UserMedicalCardRelation> list = userMedicalCardRelationService.selectUserMedicalCardRelationList(userMedicalCardRelation);
        ExcelUtil<UserMedicalCardRelation> util = new ExcelUtil<UserMedicalCardRelation>(UserMedicalCardRelation.class);
        util.exportExcel(response, list, "用户就诊卡关系表数据");
    }

    /**
     * 获取用户就诊卡关系表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:userMedicalCardRelation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userMedicalCardRelationService.selectUserMedicalCardRelationById(id));
    }

    /**
     * 新增用户就诊卡关系表
     */
    @PreAuthorize("@ss.hasPermi('hospital:userMedicalCardRelation:add')")
    @Log(title = "用户就诊卡关系表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserMedicalCardRelation userMedicalCardRelation)
    {
        return toAjax(userMedicalCardRelationService.insertUserMedicalCardRelation(userMedicalCardRelation));
    }

    /**
     * 修改用户就诊卡关系表
     */
    @PreAuthorize("@ss.hasPermi('hospital:userMedicalCardRelation:edit')")
    @Log(title = "用户就诊卡关系表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserMedicalCardRelation userMedicalCardRelation)
    {
        return toAjax(userMedicalCardRelationService.updateUserMedicalCardRelation(userMedicalCardRelation));
    }

    /**
     * 删除用户就诊卡关系表
     */
    @PreAuthorize("@ss.hasPermi('hospital:userMedicalCardRelation:remove')")
    @Log(title = "用户就诊卡关系表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userMedicalCardRelationService.deleteUserMedicalCardRelationByIds(ids));
    }
}
