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
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.service.IAssociatedHospitalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院关联Controller
 * 
 * @author chenpeng
 * @date 2023-03-10
 */
@RestController
@RequestMapping("/associatedHospital/associatedHospital")
public class AssociatedHospitalController extends BaseController
{
    @Autowired
    private IAssociatedHospitalService associatedHospitalService;

    /**
     * 查询医院关联列表
     */
//    @PreAuthorize("@ss.hasPermi('associatedHospital:associatedHospital:list')")
    @GetMapping("/list")
    public TableDataInfo list(AssociatedHospital associatedHospital)
    {
        startPage();
        List<AssociatedHospital> list = associatedHospitalService.selectAssociatedHospitalList(associatedHospital);
        return getDataTable(list);
    }

    /**
     * 导出医院关联列表
     */
//    @PreAuthorize("@ss.hasPermi('associatedHospital:associatedHospital:export')")
    @Log(title = "医院关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AssociatedHospital associatedHospital)
    {
        List<AssociatedHospital> list = associatedHospitalService.selectAssociatedHospitalList(associatedHospital);
        ExcelUtil<AssociatedHospital> util = new ExcelUtil<AssociatedHospital>(AssociatedHospital.class);
        util.exportExcel(response, list, "医院关联数据");
    }

    /**
     * 获取医院关联详细信息
     */
//    @PreAuthorize("@ss.hasPermi('associatedHospital:associatedHospital:query')")
    @GetMapping(value = "/{hId}")
    public AjaxResult getInfo(@PathVariable("hId") Long hId)
    {
        return AjaxResult.success(associatedHospitalService.selectAssociatedHospitalByHId(hId));
    }

    /**
     * 新增医院关联
     */
//    @PreAuthorize("@ss.hasPermi('associatedHospital:associatedHospital:add')")
    @Log(title = "医院关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssociatedHospital associatedHospital)
    {
        return toAjax(associatedHospitalService.insertAssociatedHospital(associatedHospital));
    }

    /**
     * 修改医院关联
     */
//    @PreAuthorize("@ss.hasPermi('associatedHospital:associatedHospital:edit')")
    @Log(title = "医院关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssociatedHospital associatedHospital)
    {
        return toAjax(associatedHospitalService.updateAssociatedHospital(associatedHospital));
    }

    /**
     * 删除医院关联
     */
//    @PreAuthorize("@ss.hasPermi('associatedHospital:associatedHospital:remove')")
    @Log(title = "医院关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hIds}")
    public AjaxResult remove(@PathVariable Long[] hIds)
    {
        return toAjax(associatedHospitalService.deleteAssociatedHospitalByHIds(hIds));
    }
}
