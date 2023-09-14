package com.ruoyi.xindian.hospital.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Aes;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
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
import com.ruoyi.xindian.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private FileUploadUtils fileUploadUtils;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;
    /**
    /**
     * 查询医生列表
     */
//    @PreAuthorize("@ss.hasPermi('doctor:doctor:list')")
    @Aes
    @GetMapping("/list")
    public TableDataInfo list(Doctor doctor, HttpServletRequest request) throws Exception {
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
            startPage();
            List<Doctor> doctors = doctorService.selectUserDoc(doctor);
            for (Doctor value : doctors) {
                department.setDepartmentCode(value.getDepartmentCode());
                List<Department> departments = departmentService.selectDepartmentList(department);
                value.setDepartmentName(departments.get(0).getDepartmentName());
                //解密
                if(!StringUtils.isEmpty(value.getDoctorName())){
                    value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
                }
                if(!StringUtils.isEmpty(value.getDoctorPhone())){
                    value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
                }
            }
            return getDataTable(doctors);
        }

            startPage();
            List<Doctor> list = doctorService.selectDoctorList(doctor);
            for (Doctor value : list) {
                department.setDepartmentCode(value.getDepartmentCode());
                List<Department> departments = departmentService.selectDepartmentList(department);
                value.setDepartmentName(departments.get(0).getDepartmentName());
                //解密
                if(!StringUtils.isEmpty(value.getDoctorName())){
                    value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
                }
                if(!StringUtils.isEmpty(value.getDoctorPhone())){
                    value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
                }
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
    public void export(HttpServletResponse response, Doctor doctor) throws Exception {
        List<Doctor> list = doctorService.selectDoctorList(doctor);
        for (Doctor value : list) {
            //解密
            if(!StringUtils.isEmpty(value.getDoctorName())){
                value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
            }
            if(!StringUtils.isEmpty(value.getDoctorPhone())){
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
        }
        ExcelUtil<Doctor> util = new ExcelUtil<Doctor>(Doctor.class);
        util.exportExcel(response, list, "医生数据");
    }

    /**
     * 获取医生详细信息
     */
//    @PreAuthorize("@ss.hasPermi('doctor:doctor:query')")
    @GetMapping(value = "/{doctorId}")
    public AjaxResult getInfo(@PathVariable("doctorId") Long doctorId) throws Exception {
        Doctor doctor = doctorService.selectDoctorByDoctorId(doctorId);
        doctor.setDoctorPhone(aesUtils.decrypt(doctor.getDoctorPhone()));
        doctor.setDoctorName(aesUtils.decrypt(doctor.getDoctorName()));
        Department department = new Department();
        department.setDepartmentCode(doctor.getDepartmentCode());
        List<Department> departments = departmentService.selectDepartmentList(department);
        doctor.setDepartmentName(departments.get(0).getDepartmentName());
        Hospital hospital = hospitalService.selectCode(doctor.getHospital());
        doctor.setHospitalCode(hospital.getHospitalCode());
        return AjaxResult.success(doctor);
    }

    @GetMapping(value = "/getInfoByDoctorPhone/{doctorPhone}")
    public AjaxResult getInfoByDoctorPhone(@PathVariable("doctorPhone") String doctorPhone) throws Exception {
        String encrypt = aesUtils.encrypt(doctorPhone);
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(encrypt);
        doctor.setDoctorPhone(aesUtils.decrypt(doctor.getDoctorPhone()));
        doctor.setDoctorName(aesUtils.decrypt(doctor.getDoctorName()));
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
    public AjaxResult add( @RequestParam("doctor")String doctor2,@RequestParam(value = "imgFile",required = false) MultipartFile imgFile) throws Exception {
        Doctor doctor = JSONObject.parseObject(doctor2, Doctor.class);

        String encrypt = aesUtils.encrypt(doctor.getDoctorPhone());
        Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(encrypt);
        if (doctor1!=null){
            return AjaxResult.error("手机号已存在");
        }
        if (imgFile!=null){
            String fileUploadUrl = fileUploadUtils.uploadImgUrl(imgFile, "doctor", doctor.getDoctorPhone());
            doctor.setImg(fileUploadUrl);
        }
        doctor.setDoctorPhone(aesUtils.encrypt(doctor.getDoctorPhone()));
        doctor.setDoctorName(aesUtils.encrypt(doctor.getDoctorName()));
        Hospital hospital = hospitalService.selectHospitalByHospitalCode(doctor.getHospital());
        doctor.setHospital(hospital.getHospitalName());
        return toAjax(doctorService.insertDoctor(doctor));
    }

    /**
     * 修改医生
     */
    @PreAuthorize("@ss.hasPermi('doctor:doctor:edit')")
    @Log(title = "医生", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestParam("doctor")String doctor3,@RequestParam(value = "imgFile",required = false) MultipartFile imgFile) throws Exception {
        Doctor doctor = JSONObject.parseObject(doctor3, Doctor.class);
        Doctor doctor1 = doctorService.selectDoctorByDoctorId(doctor.getDoctorId());
        if (!aesUtils.decrypt(doctor1.getDoctorPhone()).equals(doctor.getDoctorPhone())){
            Doctor doctor2 = doctorService.selectDoctorByDoctorPhone(doctor.getDoctorPhone());
            if (doctor2!=null){
                return AjaxResult.error("手机号已存在");
            }
        }
        if (imgFile!=null){
            String fileUploadUrl = fileUploadUtils.uploadImgUrl(imgFile, "doctor", doctor.getDoctorPhone());
            doctor.setImg(fileUploadUrl);
        }
        Hospital hospital = hospitalService.selectHospitalByHospitalCode(doctor.getHospital());
        doctor.setHospital(hospital.getHospitalName());
        doctor.setDoctorPhone(aesUtils.encrypt(doctor.getDoctorPhone()));
        doctor.setDoctorName(aesUtils.encrypt(doctor.getDoctorName()));
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



    @GetMapping("/docList")
    public AjaxResult docList(Doctor doctor) throws Exception {
        List<Doctor> doctors = doctorService.selectDoctorList(doctor);
        for (Doctor value:doctors){
            if(!StringUtils.isEmpty(value.getDoctorName())){
                value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
            }
            if(!StringUtils.isEmpty(value.getDoctorPhone())){
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
        }
        return AjaxResult.success(doctors);
    }


    @GetMapping("/isDoc")
    public AjaxResult idDoc(){
        return AjaxResult.success(doctorService.selectIsDoc());
    }
}
