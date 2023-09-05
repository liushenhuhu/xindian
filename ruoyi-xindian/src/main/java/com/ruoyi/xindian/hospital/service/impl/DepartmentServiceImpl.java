package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.DepartmentMapper;
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.service.IDepartmentService;

import javax.annotation.Resource;

/**
 * 科室Service业务层处理
 * 
 * @author hanhan
 * @date 2022-12-03
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService 
{
    @Autowired
    private DepartmentMapper departmentMapper;
    @Resource
    private HospitalMapper hospitalMapper;


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    /**
     * 查询科室
     * 
     * @param departmentId 科室主键
     * @return 科室
     */
    @Override
    public Department selectDepartmentByDepartmentId(Long departmentId)
    {
        return departmentMapper.selectDepartmentByDepartmentId(departmentId);
    }

    /**
     * 查询科室列表
     * 
     * @param department 科室
     * @return 科室
     */
    @Override
    public List<Department> selectDepartmentList(Department department)
    {
        return departmentMapper.selectDepartmentList(department);
    }


    @Override
    public List<Department> selectAllDepartment()
    {
        return departmentMapper.selectAllDepartment();
    }


    /**
     * 新增科室
     * 
     * @param department 科室
     * @return 结果
     */
    @Override
    public int insertDepartment(Department department)
    {
        return departmentMapper.insertDepartment(department);
    }

    /**
     * 修改科室
     * 
     * @param department 科室
     * @return 结果
     */
    @Override
    public int updateDepartment(Department department)
    {
        return departmentMapper.updateDepartment(department);
    }

    /**
     * 批量删除科室
     * 
     * @param departmentIds 需要删除的科室主键
     * @return 结果
     */
    @Override
    public int deleteDepartmentByDepartmentIds(Long[] departmentIds)
    {
        return departmentMapper.deleteDepartmentByDepartmentIds(departmentIds);
    }

    /**
     * 删除科室信息
     * 
     * @param departmentId 科室主键
     * @return 结果
     */
    @Override
    public int deleteDepartmentByDepartmentId(Long departmentId)
    {
        return departmentMapper.deleteDepartmentByDepartmentId(departmentId);
    }

    @Override
    public Department selectCode(Department department) {
        return departmentMapper.selectHospitalCodeDepartment(department);
    }

    @Override
    public List<ListValueAndLabelVO> depList() {
        List<ListValueAndLabelVO> listValueAndLabelVOS = departmentMapper.depList();

        for (ListValueAndLabelVO c :listValueAndLabelVOS){
            Department department = new Department();
            department.getHospitalCodeList().add(c.getValue());
            List<Department> departments = departmentMapper.selectDepartmentList(department);
            for (Department a :departments){
                DocVO listValueAndLabelVO = new DocVO();
                listValueAndLabelVO.setLabel(a.getDepartmentName());
                listValueAndLabelVO.setValue(a.getDepartmentCode());
                c.getChildren().add(listValueAndLabelVO);
            }
        }
        return listValueAndLabelVOS;
    }

    @Override
    public List<DocVO> depGroupList() {
        return departmentMapper.depGroupList();
    }
}
