package com.ruoyi.xindian.hospital.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.service.IDepartmentService;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 医生Controller
 * 
 * @author hanhan
 * @date 2022-12-03
 */
@RestController
@RequestMapping("/doctor/doctor")
public class DoctorController extends BaseController
{
    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private TokenService tokenService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private IHospitalService hospitalService;


    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;
    /**
    /**
     * 查询医生列表
     */
//    @PreAuthorize("@ss.hasPermi('doctor:doctor:list')")
    @GetMapping("/list")
    public TableDataInfo list(Doctor doctor, HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        Department department = new Department();
        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
        if (sysUser.getDeptId()!=null&&sysUser.getDeptId()==200){

            Hospital hospital = hospitalService.selectHospitalByHospitalCode(sysUser.getHospitalCode());

            doctor.getHospitalNameList().add(hospital.getHospitalName());
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    doctor.getHospitalNameList().add(hospital1.getHospitalName());
                }
            }
            List<Doctor> doctors = doctorService.selectUserDoc(doctor);
            for (Doctor value : doctors) {
                department.setDepartmentCode(value.getDepartmentCode());
                List<Department> departments = departmentService.selectDepartmentList(department);
                value.setDepartmentName(departments.get(0).getDepartmentName());
            }
            return getDataTable(doctors);
        }

            startPage();
            List<Doctor> list = doctorService.selectDoctorList(doctor);
            for (Doctor value : list) {
                department.setDepartmentCode(value.getDepartmentCode());
                List<Department> departments = departmentService.selectDepartmentList(department);
                value.setDepartmentName(departments.get(0).getDepartmentName());
            }
            return getDataTable(list);




    }

    @GetMapping("/nameList")
    public List<String> nameList()
    {
        Doctor doctor = new Doctor();
        return doctorService.selectDoctorNameList(doctor);
    }

    /**
     * 导出医生列表
     */
    @PreAuthorize("@ss.hasPermi('doctor:doctor:export')")
    @Log(title = "医生", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Doctor doctor)
    {
        List<Doctor> list = doctorService.selectDoctorList(doctor);
        ExcelUtil<Doctor> util = new ExcelUtil<Doctor>(Doctor.class);
        util.exportExcel(response, list, "医生数据");
    }

    /**
     * 获取医生详细信息
     */
//    @PreAuthorize("@ss.hasPermi('doctor:doctor:query')")
    @GetMapping(value = "/{doctorId}")
    public AjaxResult getInfo(@PathVariable("doctorId") Long doctorId)
    {
        Doctor doctor = doctorService.selectDoctorByDoctorId(doctorId);
        Department department = new Department();
        department.setDepartmentCode(doctor.getDepartmentCode());
        List<Department> departments = departmentService.selectDepartmentList(department);
        doctor.setDepartmentName(departments.get(0).getDepartmentName());
        return AjaxResult.success(doctor);
    }

    @GetMapping(value = "/getInfoByDoctorPhone/{doctorPhone}")
    public AjaxResult getInfoByDoctorPhone(@PathVariable("doctorPhone") String doctorPhone)
    {
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(doctorPhone);
        Department department = new Department();
        department.setDepartmentCode(doctor.getDepartmentCode());
        List<Department> departments = departmentService.selectDepartmentList(department);
        doctor.setDepartmentName(departments.get(0).getDepartmentName());
        return AjaxResult.success(doctor);
    }

    /**
     * 新增医生
     */
    @PreAuthorize("@ss.hasPermi('doctor:doctor:add')")
    @Log(title = "医生", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Doctor doctor)
    {
        return toAjax(doctorService.insertDoctor(doctor));
    }

    /**
     * 修改医生
     */
    @PreAuthorize("@ss.hasPermi('doctor:doctor:edit')")
    @Log(title = "医生", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Doctor doctor)
    {
//        Doctor doctor1 = new Doctor();
//        doctor1.setDoctorPhone(doctor.getDoctorPhone());
//        List<Doctor> doctors = doctorService.selectDoctorList(doctor1);
//        doctor.setDoctorId(doctors.get(0).getDoctorId());
        return toAjax(doctorService.updateDoctor(doctor));
    }

    /**
     * 删除医生
     */
    @PreAuthorize("@ss.hasPermi('doctor:doctor:remove')")
    @Log(title = "医生", businessType = BusinessType.DELETE)
	@DeleteMapping("/{doctorIds}")
    public AjaxResult remove(@PathVariable Long[] doctorIds)
    {
        return toAjax(doctorService.deleteDoctorByDoctorIds(doctorIds));
    }



    @GetMapping("/isDoc")
    public AjaxResult idDoc(){
        return AjaxResult.success(doctorService.selectIsDoc());
    }
}
