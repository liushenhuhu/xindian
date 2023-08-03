package com.ruoyi.xindian.hospital.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.xindian.hospital.domain.Department;

/**
 * 科室Service接口
 * 
 * @author hanhan
 * @date 2022-12-03
 */
public interface IDepartmentService 
{
    /**
     * 查询科室
     * 
     * @param departmentId 科室主键
     * @return 科室
     */
    public Department selectDepartmentByDepartmentId(Long departmentId);

    /**
     * 查询科室列表
     * 
     * @param department 科室
     * @return 科室集合
     */
    public List<Department> selectDepartmentList(Department department);

    public List<Department> selectDepartmentList(SysUser sysUser,Department department);
    public List<Department> selectAllDepartment();

    /**
     * 新增科室
     * 
     * @param department 科室
     * @return 结果
     */
    public int insertDepartment(Department department);

    /**
     * 修改科室
     * 
     * @param department 科室
     * @return 结果
     */
    public int updateDepartment(Department department);

    /**
     * 批量删除科室
     * 
     * @param departmentIds 需要删除的科室主键集合
     * @return 结果
     */
    public int deleteDepartmentByDepartmentIds(Long[] departmentIds);

    /**
     * 删除科室信息
     * 
     * @param departmentId 科室主键
     * @return 结果
     */
    public int deleteDepartmentByDepartmentId(Long departmentId);
}
