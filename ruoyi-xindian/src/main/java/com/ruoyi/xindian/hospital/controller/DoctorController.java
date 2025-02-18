package com.ruoyi.xindian.hospital.controller;

import com.alibaba.fastjson2.JSONObject;
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
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.service.IDepartmentService;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.util.FileUploadUtils;
import com.ruoyi.xindian.util.RoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 医生Controller
 *
 * @author hanhan
 * @date 2022-12-03
 */
@RestController
@RequestMapping("/doctor/doctor")
public class DoctorController extends BaseController {
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

    @Resource
    private ISysDictDataService dictDataService;
    /**
     * /**
     * 查询医生列表
     */
//    @PreAuthorize("@ss.hasPermi('doctor:doctor:list')")
    @Aes
    @GetMapping("/list")
    public TableDataInfo list(Doctor doctor, HttpServletRequest request) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(request);
        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
        if (!SysUser.isAdmin(loginUser.getUserId())&& !RoleUtils.isRoleListOne(loginUser,sysDictData)){
            Hospital hospital = hospitalService.selectHospitalByHospitalCode(sysUser.getHospitalCode());
            if (hospital != null) {
                doctor.getHospitalNameList().add(hospital.getHospitalName());
                AssociatedHospital associatedHospital = new AssociatedHospital();
                associatedHospital.setHospitalId(hospital.getHospitalId());
                List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
                if (associatedHospitals != null && !associatedHospitals.isEmpty()) {
                    for (AssociatedHospital c : associatedHospitals) {
                        Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                        doctor.getHospitalNameList().add(hospital1.getHospitalName());
                    }
                }
                startPage();
                List<Doctor> doctors = doctorService.selectUserDoc(doctor);
                return getTableDataInfo(doctors);
            }
            return getDataTable(new ArrayList<>());

        }
        if (StringUtils.isNotEmpty(doctor.getHospital())) {
            doctor.getHospitalNameList().add(doctor.getHospital());
        }
        startPage();
        List<Doctor> list = doctorService.selectDoctorList(doctor);
        return getTableDataInfo(list);


    }

    private TableDataInfo getTableDataInfo(List<Doctor> doctors) throws Exception {
        for (Doctor value : doctors) {
            //解密
            if (!StringUtils.isEmpty(value.getDoctorName())) {
                value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
            }
            if (!StringUtils.isEmpty(value.getDoctorPhone())) {
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
        }
        return getDataTable(doctors);
    }


    @Aes
    @GetMapping("/getDocList")
    public TableDataInfo getDocList(Doctor doctor) throws Exception {
        List<Doctor> list = doctorService.selectDoctorList(doctor);
        return getTableDataInfo(list);

    }


    @GetMapping("/nameList")
    public List<String> nameList() {
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
            if (!StringUtils.isEmpty(value.getDoctorName())) {
                value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
            }
            if (!StringUtils.isEmpty(value.getDoctorPhone())) {
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
        if (doctor != null) {
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

        return AjaxResult.success(new Doctor());
    }

    @GetMapping(value = "/getInfoByDoctorPhone/{doctorPhone}")
    public AjaxResult getInfoByDoctorPhone(@PathVariable("doctorPhone") String doctorPhone) throws Exception {
        String encrypt = aesUtils.encrypt(doctorPhone);
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(encrypt);
        if (doctor == null) {
            return AjaxResult.error("医生不存在");
        }
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
    public AjaxResult add(@RequestParam("doctor") String doctor2, @RequestParam(value = "imgFile", required = false) MultipartFile imgFile, @RequestParam(value = "zdVisa", required = false) MultipartFile zdVisa) throws Exception {
        Doctor doctor = JSONObject.parseObject(doctor2, Doctor.class);

        String encrypt = aesUtils.encrypt(doctor.getDoctorPhone());
        Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(encrypt);
        if (doctor1 != null) {
            return AjaxResult.error("手机号已存在");
        }
        if (imgFile != null) {
            String fileUploadUrl = fileUploadUtils.uploadImgUrl(imgFile, "doctor", doctor.getDoctorPhone());
            doctor.setImg(fileUploadUrl);
        }
        //电子签
        if (zdVisa != null) {
            String fileUploadUrl = fileUploadUtils.uploadDzVisa(zdVisa, "doctor", "doctorDzVisa");
            doctor.setDzVisa(fileUploadUrl);
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
    public AjaxResult edit(@RequestParam("doctor") String doctor3, @RequestParam(value = "imgFile", required = false) MultipartFile imgFile, @RequestParam(value = "zdVisa", required = false) MultipartFile zdVisa) throws Exception {
        Doctor doctor = JSONObject.parseObject(doctor3, Doctor.class);
        doctor.setDoctorPhone(aesUtils.encrypt(doctor.getDoctorPhone()));
        doctor.setDoctorName(aesUtils.encrypt(doctor.getDoctorName()));
        Doctor doctor1 = doctorService.selectDoctorByDoctorId(doctor.getDoctorId());
        if (!doctor1.getDoctorPhone().equals(doctor.getDoctorPhone())) {
            Doctor doctor2 = doctorService.selectDoctorByDoctorPhone(doctor.getDoctorPhone());
            if (doctor2 != null) {
                return AjaxResult.error("手机号已存在");
            }
        }
        if (imgFile != null) {
            String fileUploadUrl = fileUploadUtils.uploadImgUrl(imgFile, "doctor", doctor.getDoctorPhone());
            doctor.setImg(fileUploadUrl);
        }
        //电子签
        if (zdVisa != null) {
            String fileUploadUrl = fileUploadUtils.uploadDzVisa(zdVisa, "doctor", "doctorDzVisa");
            doctor.setDzVisa(fileUploadUrl);
        }

        Hospital hospital = hospitalService.selectHospitalByHospitalCode(doctor.getHospital());
        doctor.setHospital(hospital.getHospitalName());

        return toAjax(doctorService.updateDoctor(doctor));
    }

    /**
     * 上传医生电子签
     * @param doctorId
     * @param zdVisa
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadDzVisa")
    public AjaxResult edit(@RequestParam("doctorId") Long doctorId, @RequestParam(value = "zdVisa", required = false) MultipartFile zdVisa) throws Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);
        //电子签
        if (zdVisa != null) {
            String fileUploadUrl = fileUploadUtils.uploadDzVisa(zdVisa, "doctor", "doctorDzVisa");
            doctor.setDzVisa(fileUploadUrl);
        }
        return toAjax(doctorService.updateDoctor(doctor));
    }

    /**
     * 删除医生
     */
    @PreAuthorize("@ss.hasPermi('doctor:doctor:remove')")
    @Log(title = "医生", businessType = BusinessType.DELETE)
    @DeleteMapping("/{doctorIds}")
    public AjaxResult remove(@PathVariable Long[] doctorIds) {
        return toAjax(doctorService.deleteDoctorByDoctorIds(doctorIds));
    }


    @GetMapping("/docList")
    public AjaxResult docList(Doctor doctor) throws Exception {
        List<Doctor> doctors = doctorService.selectDoctorListNot(doctor);
        for (Doctor value : doctors) {
            if (!StringUtils.isEmpty(value.getDoctorName())) {
                value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
            }
            if (!StringUtils.isEmpty(value.getDoctorPhone())) {
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
        }
        return AjaxResult.success(doctors);
    }


    @GetMapping("/isDoc")
    public AjaxResult idDoc() {
        return AjaxResult.success(doctorService.selectIsDoc());
    }


    /**
     * 门诊预约通过医院code查询当前医院的医生
     */
    @GetMapping("/getCodeVisitDoc")
    public AjaxResult getCodeVisitDoc(String code) throws Exception {

        Hospital hospital = hospitalService.selectHospitalByHospitalCode(code);

        if (hospital == null) {
            return AjaxResult.error("请选择医院");
        }
        List<Doctor> doctors = doctorService.selectVisitDoc(hospital.getHospitalName());
        for (Doctor value : doctors) {
            if (!StringUtils.isEmpty(value.getDoctorName())) {
                value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
            }
            if (!StringUtils.isEmpty(value.getDoctorPhone())) {
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
        }
        return AjaxResult.success(doctors);
    }

    /**
     * 门诊预约通过医院code查询当前医院的医生
     */
    @GetMapping("/getHospitalVisitDoc")
    public AjaxResult getHospitalVisitDoc(Doctor doctor, HttpServletRequest request) throws Exception {
        if (!StringUtils.isEmpty(doctor.getDoctorName())) {
            doctor.setDoctorName(aesUtils.encrypt(doctor.getDoctorName()));
        }
        if (!StringUtils.isEmpty(doctor.getDoctorPhone())) {
            doctor.setDoctorPhone(aesUtils.encrypt(doctor.getDoctorPhone()));
        }
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<Doctor> doctors = new ArrayList<>();
        if (doctor.getHospital() == null) {

            if (SysUser.isAdmin(loginUser.getUserId())) {
                doctors = doctorService.selectDoctorListNot(doctor);
            } else {
                if (loginUser.getUser().getHospitalCode() != null) {
                    Hospital hospital = hospitalService.selectHospitalByHospitalCode(loginUser.getUser().getHospitalCode());
                    if (loginUser.getUser().getDeptId() != null && loginUser.getUser().getDeptId() == 200) {
                        doctors = doctorService.selectVisitDoc(hospital.getHospitalName());
                    } else {
                        doctor.setDoctorPhone(loginUser.getUser().getUserName());
                        doctor.getHospitalNameList().add(hospital.getHospitalName());
                        doctors = doctorService.selectDoctorListNot(doctor);

                    }
                }
            }
        } else {
            doctors = doctorService.selectDoctorListNot(doctor);
        }

        for (Doctor value : doctors) {
            if (!StringUtils.isEmpty(value.getDoctorName())) {
                value.setDoctorName(aesUtils.decrypt(value.getDoctorName()));
            }
            if (!StringUtils.isEmpty(value.getDoctorPhone())) {
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
        }
        return AjaxResult.success(doctors);
    }

    @GetMapping("/getXDDoc")
    public AjaxResult getXDDoc(String hospitalName) {
        List<Doctor> doctors = doctorService.selectXDDoc(hospitalName);
        return AjaxResult.success(doctors);
    }
}
