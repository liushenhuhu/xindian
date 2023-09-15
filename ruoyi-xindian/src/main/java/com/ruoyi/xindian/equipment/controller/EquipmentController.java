package com.ruoyi.xindian.equipment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.service.IDepartmentService;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import org.aspectj.weaver.loadtime.Aj;
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
 * 设备Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/equipment/equipment")
public class EquipmentController extends BaseController {
    @Autowired
    private IEquipmentService equipmentService;

    @Autowired
    private ISysUserService userService;
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IDoctorService doctorService;


    @Autowired
    private IHospitalService hospitalService;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;
    /**
     * 查询设备列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Equipment equipment) throws Exception {
        List<Equipment> list = new ArrayList<>();

        if (equipment.getDepartmentName()!=null&&!"".equals(equipment.getDepartmentName())){
            Department department1 = new Department();
            department1.setDepartmentName(equipment.getDepartmentName());

            List<Department> departments1 = departmentService.selectDepartmentList(department1);
            for (Department c:departments1){
                equipment.getDepartmentCodeList().add(c.getDepartmentCode());
            }
        }
        if (getDeptId()!=null && getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalCode = sysUser.getHospitalCode();
            equipment.getHospitalCodeList().add(hospitalCode);
            if (equipment.getHospitalCode()!=null){
                Hospital hospital = hospitalService.selectHospitalByHospitalCode(hospitalCode);
                AssociatedHospital associatedHospital = new AssociatedHospital();
                associatedHospital.setHospitalId(hospital.getHospitalId());
                List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
                if (associatedHospitals!=null&&associatedHospitals.size()>0){
                    for (AssociatedHospital c:associatedHospitals){
                        Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                        equipment.getHospitalCodeList().add(hospital1.getHospitalCode());
                    }
                }
            }
            if (equipment.getHospitalCode()!=null&&!"".equals(equipment.getHospitalCode())){
                List<String> equipmentList = equipment.getHospitalCodeList();
                if (equipmentList!=null&&equipmentList.size()>0){
                    for (String c : equipmentList){
                        if (c.equals(equipment.getHospitalCode())){

                            equipment.getHospitalCodeList().clear();
                            equipment.getHospitalCodeList().add(equipment.getHospitalCode());
                            break;
                        }
                    }
                }
            }
            startPage();
            list = equipmentService.selectEquipmentList(equipment);
        } else {
            if (equipment.getHospitalCode()!=null&&!"".equals(equipment.getHospitalCode())){
                Hospital hospital = hospitalService.selectCode(equipment.getHospitalCode());
                if (hospital==null){
                    return getDataTable(list);
                }
                equipment.getHospitalCodeList().add(hospital.getHospitalCode());
            }
            startPage();
            list = equipmentService.selectEquipmentList(equipment);
        }
        Department department = new Department();
        for (Equipment value : list) {
            if(value.getDepartmentCode()==null){
                continue;
            }
            if (value.getPatientPhone()!=null&&!"".equals(value.getPatientPhone())){
                value.setPatientPhone(aesUtils.decrypt(value.getPatientPhone()));
            }
            department.setDepartmentCode(value.getDepartmentCode());
            List<Department> departments = departmentService.selectDepartmentList(department);
            value.setEquipmentName(departments.get(0).getDepartmentName());
        }
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:export')")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Equipment equipment) throws Exception {
        List<Equipment> list = new ArrayList<>();
        if (getDeptId()!=null && getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalCode = sysUser.getHospitalCode();
            equipment.setHospitalCode(hospitalCode);
            list = equipmentService.selectEquipmentList(equipment);
        } else {
            list = equipmentService.selectEquipmentList(equipment);
        }
        for (Equipment c:list){
            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
                c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
            }
        }
        ExcelUtil<Equipment> util = new ExcelUtil<Equipment>(Equipment.class);
        util.exportExcel(response, list, "设备数据");
    }

    /**
     * 获取设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:query')")
    @GetMapping(value = "/{equipmentId}")
    public AjaxResult getInfo(@PathVariable("equipmentId") Long equipmentId) throws Exception {

        Equipment equipment = equipmentService.selectEquipmentByEquipmentId(equipmentId);
        if(equipment.getDepartmentCode()!=null){
            Department department = new Department();
            department.setDepartmentCode(equipment.getDepartmentCode());
            List<Department> departments = departmentService.selectDepartmentList(department);
            equipment.setEquipmentName(departments.get(0).getDepartmentName());

        }
        if (equipment.getPatientPhone()!=null&&!"".equals(equipment.getPatientPhone())){
            equipment.setPatientPhone(aesUtils.decrypt(equipment.getPatientPhone()));
        }
        return AjaxResult.success(equipment);
    }

    /**
     * 新增设备
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:add')")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Equipment equipment) throws Exception {

        if (equipment.getPatientPhone()!=null&&!"".equals(equipment.getPatientPhone())) {
            equipment.setPatientPhone(aesUtils.encrypt(equipment.getPatientPhone()));
        }
        Equipment equipment1 = equipmentService.selectEquipmentByEquipmentCode(equipment.getEquipmentCode());
        if (equipment1 != null) {
            return AjaxResult.error("设备号已存在");
        }
        return toAjax(equipmentService.insertEquipment(equipment));

    }

    /**
     * 修改设备
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:edit')")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Equipment equipment) throws Exception {
        if (equipment.getPatientPhone()!=null&&!"".equals(equipment.getPatientPhone())){
            equipment.setPatientPhone(aesUtils.encrypt(equipment.getPatientPhone()));
        }
        return toAjax(equipmentService.updateEquipment(equipment));
    }

    /**
     * 删除设备
     */
    @PreAuthorize("@ss.hasPermi('equipment:equipment:remove')")
    @Log(title = "设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{equipmentIds}")
    public AjaxResult remove(@PathVariable Long[] equipmentIds) {
        return toAjax(equipmentService.deleteEquipmentByEquipmentIds(equipmentIds));
    }

    /**
     * 修改设备
     */
//    @PreAuthorize("@ss.hasPermi('equipment:equipment:edit')")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PostMapping("/updateEquipmentStatus")
    public String updateEquipmentStatus(@RequestBody String[] equipmentList) {
        equipmentService.updateEquipmentStatusAll();
        if (equipmentList.length != 0) {
            equipmentService.updateEquipmentStatus(equipmentList);
        }
        return "down";
    }
    /**
     * 获取开关机状态
     */
    @GetMapping("/getOnOffStatus")
    public AjaxResult getOnOffStatus(HttpServletRequest request) throws Exception {
        String equipmentCode = request.getParameter("equipmentCode");
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentCode);
        if (equipment.getPatientPhone()!=null&&!"".equals(equipment.getPatientPhone())){
            equipment.setPatientPhone(aesUtils.decrypt(equipment.getPatientPhone()));
        }
        return AjaxResult.success(equipment);

    }
    @GetMapping("/getEquipmentCodeListByPhone/{phone}")
    public AjaxResult getEquipmentCodeListByPhone(@PathVariable String phone) throws Exception {
        String encrypt = aesUtils.encrypt(phone);
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(encrypt);
        if(doctor == null){
            return AjaxResult.error("非医生无权限");
        }
        Equipment equipment = new Equipment();
        equipment.setPatientPhone(encrypt);
        List<Equipment> equipmentList = equipmentService.selectEquipmentList(equipment);
        StringBuilder EquipmentCodeList= new StringBuilder();
        for (Equipment eq : equipmentList) {
            EquipmentCodeList.append(eq.getEquipmentCode());
            EquipmentCodeList.append(",");
        }
        if(EquipmentCodeList.length()!=0){
            EquipmentCodeList.deleteCharAt(EquipmentCodeList.length()-1);
        }
        return AjaxResult.success(EquipmentCodeList);
    }

//    @PreAuthorize("@ss.hasPermi('equipment:equipment:code')")
    @GetMapping("/code")
    public AjaxResult code(String equimentCode){
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equimentCode);
        if (equipment!=null){
            return AjaxResult.success(true);
        }
        return AjaxResult.error();
    }
    //查询在线设备数量
    @GetMapping("/onlineNum")
    public AjaxResult onlineNum(){
        int count = equipmentService.selectEquipmentOnlineNum();

        return AjaxResult.success(count);
    }

}
