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
import com.ruoyi.xindian.hospital.domain.HospitalClinic;
import com.ruoyi.xindian.hospital.service.IHospitalClinicService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 诊室信息表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalClinic")
public class HospitalClinicController extends BaseController
{
    @Autowired
    private IHospitalClinicService hospitalClinicService;

    /**
     * 查询诊室信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalClinic:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalClinic hospitalClinic)
    {
        startPage();
        List<HospitalClinic> list = hospitalClinicService.selectHospitalClinicList(hospitalClinic);
        return getDataTable(list);
    }

    /**
     * 导出诊室信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalClinic:export')")
    @Log(title = "诊室信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalClinic hospitalClinic)
    {
        List<HospitalClinic> list = hospitalClinicService.selectHospitalClinicList(hospitalClinic);
        ExcelUtil<HospitalClinic> util = new ExcelUtil<HospitalClinic>(HospitalClinic.class);
        util.exportExcel(response, list, "诊室信息表数据");
    }

    /**
     * 获取诊室信息表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalClinic:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalClinicService.selectHospitalClinicById(id));
    }

    /**
     * 新增诊室信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalClinic:add')")
    @Log(title = "诊室信息表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalClinic hospitalClinic)
    {
        return toAjax(hospitalClinicService.insertHospitalClinic(hospitalClinic));
    }

    /**
     * 修改诊室信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalClinic:edit')")
    @Log(title = "诊室信息表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalClinic hospitalClinic)
    {
        return toAjax(hospitalClinicService.updateHospitalClinic(hospitalClinic));
    }

    /**
     * 删除诊室信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalClinic:remove')")
    @Log(title = "诊室信息表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hospitalClinicService.deleteHospitalClinicByIds(ids));
    }
}
