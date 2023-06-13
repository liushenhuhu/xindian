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
import com.ruoyi.xindian.hospital.domain.HospitalOutpatientRelation;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientRelationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院门诊关系表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalOutpatientRelation")
public class HospitalOutpatientRelationController extends BaseController
{
    @Autowired
    private IHospitalOutpatientRelationService hospitalOutpatientRelationService;

    /**
     * 查询医院门诊关系表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        startPage();
        List<HospitalOutpatientRelation> list = hospitalOutpatientRelationService.selectHospitalOutpatientRelationList(hospitalOutpatientRelation);
        return getDataTable(list);
    }

    /**
     * 导出医院门诊关系表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:export')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        List<HospitalOutpatientRelation> list = hospitalOutpatientRelationService.selectHospitalOutpatientRelationList(hospitalOutpatientRelation);
        ExcelUtil<HospitalOutpatientRelation> util = new ExcelUtil<HospitalOutpatientRelation>(HospitalOutpatientRelation.class);
        util.exportExcel(response, list, "医院门诊关系表数据");
    }

    /**
     * 获取医院门诊关系表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalOutpatientRelationService.selectHospitalOutpatientRelationById(id));
    }

    /**
     * 新增医院门诊关系表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:add')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        return toAjax(hospitalOutpatientRelationService.insertHospitalOutpatientRelation(hospitalOutpatientRelation));
    }

    /**
     * 修改医院门诊关系表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:edit')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        return toAjax(hospitalOutpatientRelationService.updateHospitalOutpatientRelation(hospitalOutpatientRelation));
    }

    /**
     * 删除医院门诊关系表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:remove')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hospitalOutpatientRelationService.deleteHospitalOutpatientRelationByIds(ids));
    }
}
