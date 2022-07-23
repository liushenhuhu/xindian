package com.ruoyi.xindian.equipment.controller;

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
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备管理Controller
 * 
 * @author Han
 * @date 2022-07-22
 */
@RestController
@RequestMapping("/equipment/equipment")
public class EquipmentController extends BaseController
{
    @Autowired
    private IEquipmentService equipmentService;

    /**
     * 查询设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Equipment equipment)
    {
        startPage();
        List<Equipment> list = equipmentService.selectEquipmentList(equipment);
        return getDataTable(list);
    }

    /**
     * 导出设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:export')")
    @Log(title = "设备管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Equipment equipment)
    {
        List<Equipment> list = equipmentService.selectEquipmentList(equipment);
        ExcelUtil<Equipment> util = new ExcelUtil<Equipment>(Equipment.class);
        util.exportExcel(response, list, "设备管理数据");
    }

    /**
     * 获取设备管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(equipmentService.selectEquipmentById(id));
    }

    /**
     * 新增设备管理
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:add')")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Equipment equipment)
    {
        return toAjax(equipmentService.insertEquipment(equipment));
    }

    /**
     * 修改设备管理
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:edit')")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Equipment equipment)
    {
        return toAjax(equipmentService.updateEquipment(equipment));
    }

    /**
     * 删除设备管理
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:remove')")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipmentService.deleteEquipmentByIds(ids));
    }
}
