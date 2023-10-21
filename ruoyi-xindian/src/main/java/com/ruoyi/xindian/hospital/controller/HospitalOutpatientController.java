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
import com.ruoyi.xindian.hospital.domain.HospitalOutpatient;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院门诊表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalOutpatient")
public class HospitalOutpatientController extends BaseController
{
    @Autowired
    private IHospitalOutpatientService hospitalOutpatientService;

    /**
     * 查询医院门诊表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalOutpatient hospitalOutpatient)
    {
        startPage();
        List<HospitalOutpatient> list = hospitalOutpatientService.selectHospitalOutpatientList(hospitalOutpatient);
        return getDataTable(list);
    }

    /**
     * 导出医院门诊表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:export')")
    @Log(title = "医院门诊表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalOutpatient hospitalOutpatient)
    {
        List<HospitalOutpatient> list = hospitalOutpatientService.selectHospitalOutpatientList(hospitalOutpatient);
        ExcelUtil<HospitalOutpatient> util = new ExcelUtil<HospitalOutpatient>(HospitalOutpatient.class);
        util.exportExcel(response, list, "医院门诊表数据");
    }

    /**
     * 获取医院门诊表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalOutpatientService.selectHospitalOutpatientById(id));
    }

    /**
     * 新增医院门诊表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:add')")
    @Log(title = "医院门诊表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalOutpatient hospitalOutpatient)
    {
        return toAjax(hospitalOutpatientService.insertHospitalOutpatient(hospitalOutpatient));
    }

    /**
     * 修改医院门诊表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:edit')")
    @Log(title = "医院门诊表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalOutpatient hospitalOutpatient)
    {
        return toAjax(hospitalOutpatientService.updateHospitalOutpatient(hospitalOutpatient));
    }

    /**
     * 删除医院门诊表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:remove')")
    @Log(title = "医院门诊表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hospitalOutpatientService.deleteHospitalOutpatientByIds(ids));
    }


    @GetMapping("/getOutpatientList")
    public AjaxResult getOutpatientList(HospitalOutpatient hospitalOutpatient){
        return AjaxResult.success(hospitalOutpatientService.selectHospitalOutpatientList(hospitalOutpatient));
    }
}
