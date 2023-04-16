package com.ruoyi.xindian.hospital.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.service.IDepartmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科室Controller
 * 
 * @author hanhan
 * @date 2022-12-03
 */
@RestController
@RequestMapping("/department/department")
public class DepartmentController extends BaseController
{
    @Autowired
    private IDepartmentService departmentService;

    /**
     * 查询科室列表
     */
    @PreAuthorize("@ss.hasPermi('department:department:list')")
    @GetMapping("/list")
    public TableDataInfo list(Department department)
    {
        startPage();
        List<Department> list = departmentService.selectDepartmentList(department);
        return getDataTable(list);
    }
    @GetMapping("/getAllDepartment")
    public TableDataInfo getAllDepartment()
    {
        Department department = new Department();
        HashMap<String, String> Map ;
        List<Department> list = departmentService.selectDepartmentList(department);
        ArrayList<Object> res = new ArrayList<>();
        for (Department dep : list) {
            Map = new HashMap<>();
            Map.put(dep.getDepartmentCode(),dep.getDepartmentName());
            res.add(Map);
        }
        return getDataTable(res);
    }

    /**
     * 导出科室列表
     */
    @PreAuthorize("@ss.hasPermi('department:department:export')")
    @Log(title = "科室", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Department department)
    {
        List<Department> list = departmentService.selectDepartmentList(department);
        ExcelUtil<Department> util = new ExcelUtil<Department>(Department.class);
        util.exportExcel(response, list, "科室数据");
    }

    /**
     * 获取科室详细信息
     */
    @PreAuthorize("@ss.hasPermi('department:department:query')")
    @GetMapping(value = "/{departmentId}")
    public AjaxResult getInfo(@PathVariable("departmentId") Long departmentId)
    {
        return AjaxResult.success(departmentService.selectDepartmentByDepartmentId(departmentId));
    }

    /**
     * 新增科室
     */
    @PreAuthorize("@ss.hasPermi('department:department:add')")
    @Log(title = "科室", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Department department)
    {
        return toAjax(departmentService.insertDepartment(department));
    }

    /**
     * 修改科室
     */
    @PreAuthorize("@ss.hasPermi('department:department:edit')")
    @Log(title = "科室", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Department department)
    {
        return toAjax(departmentService.updateDepartment(department));
    }

    /**
     * 删除科室
     */
    @PreAuthorize("@ss.hasPermi('department:department:remove')")
    @Log(title = "科室", businessType = BusinessType.DELETE)
	@DeleteMapping("/{departmentIds}")
    public AjaxResult remove(@PathVariable Long[] departmentIds)
    {
        return toAjax(departmentService.deleteDepartmentByDepartmentIds(departmentIds));
    }

}
