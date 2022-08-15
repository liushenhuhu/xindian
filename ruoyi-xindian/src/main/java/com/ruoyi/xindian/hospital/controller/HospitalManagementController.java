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
import com.ruoyi.xindian.hospital.domain.HospitalManagement;
import com.ruoyi.xindian.hospital.service.IHospitalManagementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院管理Controller
 *
 * @author hanhan
 * @date 2022-08-15
 */
@RestController
@RequestMapping("/hospital/hospital")
public class HospitalManagementController extends BaseController
{
    @Autowired
    private IHospitalManagementService hospitalManagementService;

    /**
     * 查询医院管理列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalManagement hospitalManagement)
    {
        startPage();
        List<HospitalManagement> list = hospitalManagementService.selectHospitalManagementList(hospitalManagement);
        return getDataTable(list);
    }

    /**
     * 导出医院管理列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:export')")
    @Log(title = "医院管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalManagement hospitalManagement)
    {
        List<HospitalManagement> list = hospitalManagementService.selectHospitalManagementList(hospitalManagement);
        ExcelUtil<HospitalManagement> util = new ExcelUtil<HospitalManagement>(HospitalManagement.class);
        util.exportExcel(response, list, "医院管理数据");
    }

    /**
     * 获取医院管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:query')")
    @GetMapping(value = "/{hospitalId}")
    public AjaxResult getInfo(@PathVariable("hospitalId") String hospitalId)
    {
        return AjaxResult.success(hospitalManagementService.selectHospitalManagementByHospitalId(hospitalId));
    }

    /**
     * 新增医院管理
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:add')")
    @Log(title = "医院管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalManagement hospitalManagement)
    {
        return toAjax(hospitalManagementService.insertHospitalManagement(hospitalManagement));
    }

    /**
     * 修改医院管理
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:edit')")
    @Log(title = "医院管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalManagement hospitalManagement)
    {
        return toAjax(hospitalManagementService.updateHospitalManagement(hospitalManagement));
    }

    /**
     * 删除医院管理
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:remove')")
    @Log(title = "医院管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{hospitalIds}")
    public AjaxResult remove(@PathVariable String[] hospitalIds)
    {
        return toAjax(hospitalManagementService.deleteHospitalManagementByHospitalIds(hospitalIds));
    }
}
