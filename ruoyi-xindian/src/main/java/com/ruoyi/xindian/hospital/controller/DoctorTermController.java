package com.ruoyi.xindian.hospital.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.hospital.domain.DoctorTerm;
import com.ruoyi.xindian.hospital.service.DoctorTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 医生术语
 */
@RestController
@RequestMapping("/term/term")
public class DoctorTermController extends BaseController {


    @Resource
    private TokenService tokenService;

    @Autowired
    private DoctorTermService doctorTermService;



    /**
     * 查询医生术语列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DoctorTerm doctorTerm, HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        doctorTerm.setDoctorPhone(loginUser.getUser().getUserName());
        startPage();
        List<DoctorTerm> list = doctorTermService.selectDoctorTermList(doctorTerm);
        return getDataTable(list);
    }

    /**
     * 导出医生术语列表
     */
    @PreAuthorize("@ss.hasPermi('system:term:export')")
    @Log(title = "医生术语", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DoctorTerm doctorTerm)
    {
        List<DoctorTerm> list = doctorTermService.selectDoctorTermList(doctorTerm);
        ExcelUtil<DoctorTerm> util = new ExcelUtil<DoctorTerm>(DoctorTerm.class);
        util.exportExcel(response, list, "医生术语数据");
    }

    /**
     * 获取医生术语详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:term:query')")
    @GetMapping(value = "/{ termId}")
    public AjaxResult getInfo(@PathVariable(" termId") Long  termId)
    {
        return AjaxResult.success(doctorTermService.selectDoctorTermByTermId( termId));
    }

    /**
     * 新增医生术语
     */
    @PreAuthorize("@ss.hasPermi('system:term:add')")
    @Log(title = "医生术语", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DoctorTerm doctorTerm)
    {
        return toAjax(doctorTermService.insertDoctorTerm(doctorTerm));
    }

    /**
     * 修改医生术语
     */
    @PreAuthorize("@ss.hasPermi('system:term:edit')")
    @Log(title = "医生术语", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DoctorTerm doctorTerm,HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        doctorTerm.setDoctorPhone(loginUser.getUser().getUserName());
        return toAjax(doctorTermService.updateDoctorTerm(doctorTerm));
    }

    /**
     * 删除医生术语
     */
    @PreAuthorize("@ss.hasPermi('system:term:remove')")
    @Log(title = "医生术语", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ termIds}")
    public AjaxResult remove(@PathVariable Long[]  termIds)
    {
        return toAjax(doctorTermService.deleteDoctorTermByTermIds( termIds));
    }



}
