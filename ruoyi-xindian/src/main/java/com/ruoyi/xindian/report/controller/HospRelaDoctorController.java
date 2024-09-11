package com.ruoyi.xindian.report.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.report.domain.HospRelaDoctor;
import com.ruoyi.xindian.report.service.HospRelaDoctorService;
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

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报告自动提交Controller
 * 
 * @author ruoyi
 * @date 2024-09-10
 */
@RestController
@RequestMapping("/rela_doctor/hosp_rela_doctor")
public class HospRelaDoctorController extends BaseController
{
    @Autowired
    private HospRelaDoctorService hospRelaDoctorService;

    /**
     * 查询报告自动提交列表
     */
    @PreAuthorize("@ss.hasPermi('rela_doctor:hosp_rela_doctor:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospRelaDoctor hospRelaDoctor)
    {
        startPage();
        List<HospRelaDoctor> list = hospRelaDoctorService.selectHospRelaDoctorList(hospRelaDoctor);
        return getDataTable(list);
    }

    /**
     * 导出报告自动提交列表
     */
    @PreAuthorize("@ss.hasPermi('rela_doctor:hosp_rela_doctor:export')")
    @Log(title = "报告自动提交", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospRelaDoctor hospRelaDoctor)
    {
        List<HospRelaDoctor> list = hospRelaDoctorService.selectHospRelaDoctorList(hospRelaDoctor);
        ExcelUtil<HospRelaDoctor> util = new ExcelUtil<HospRelaDoctor>(HospRelaDoctor.class);
        util.exportExcel(response, list, "报告自动提交数据");
    }

    /**
     * 获取报告自动提交详细信息
     */
    @PreAuthorize("@ss.hasPermi('rela_doctor:hosp_rela_doctor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospRelaDoctorService.selectHospRelaDoctorById(id));
    }

    /**
     * 新增报告自动提交
     */
    @PreAuthorize("@ss.hasPermi('rela_doctor:hosp_rela_doctor:add')")
    @Log(title = "报告自动提交", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospRelaDoctor hospRelaDoctor)
    {
        return toAjax(hospRelaDoctorService.insertHospRelaDoctor(hospRelaDoctor));
    }

    /**
     * 修改报告自动提交
     */
    @PreAuthorize("@ss.hasPermi('rela_doctor:hosp_rela_doctor:edit')")
    @Log(title = "报告自动提交", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospRelaDoctor hospRelaDoctor)
    {
        return toAjax(hospRelaDoctorService.updateHospRelaDoctor(hospRelaDoctor));
    }

    /**
     * 删除报告自动提交
     */
    @PreAuthorize("@ss.hasPermi('rela_doctor:hosp_rela_doctor:remove')")
    @Log(title = "报告自动提交", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hospRelaDoctorService.deleteHospRelaDoctorByIds(ids));
    }
}
