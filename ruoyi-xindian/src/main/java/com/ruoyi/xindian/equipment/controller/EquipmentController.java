package com.ruoyi.xindian.equipment.controller;

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
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Department;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.service.IAssociatedHospitalService;
import com.ruoyi.xindian.hospital.service.IDepartmentService;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.service.LeaseDetailsService;
import com.ruoyi.xindian.util.RoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    @Autowired
    private IAssociatedHospitalService associatedHospitalService;

    @Resource
    private LeaseDetailsService leaseDetailsService;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private ISysDictDataService dictDataService;


    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;


    @Resource
    private RedisTemplate<String, String> redisTemplate;
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
        Long userId = getUserId();
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
        if (!SysUser.isAdmin(userId)&& !RoleUtils.isRoleListOne(getLoginUser(),sysDictData)){
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalCode = sysUser.getHospitalCode();
            equipment.getHospitalCodeList().add(hospitalCode);
            if (equipment.getHospitalCode()!=null){
                Hospital hospital = hospitalService.selectHospitalByHospitalCode(hospitalCode);
                if (hospital!=null){
                    AssociatedHospital associatedHospital = new AssociatedHospital();
                    associatedHospital.setHospitalId(hospital.getHospitalId());
                    List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
                    if (associatedHospitals!=null&& !associatedHospitals.isEmpty()){
                        for (AssociatedHospital c:associatedHospitals){
                            Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                            equipment.getHospitalCodeList().add(hospital1.getHospitalCode());
                        }
                    }
                }

            }
            if (equipment.getHospitalCode()!=null&&!"".equals(equipment.getHospitalCode())){
                List<String> equipmentList = equipment.getHospitalCodeList();
                if (equipmentList!=null&& !equipmentList.isEmpty()){
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
        Equipment equipment1 = equipmentService.selectEquipmentByEquipmentId(equipment.getEquipmentId());
        if (!equipment1.getEquipmentCode().equals(equipment.getEquipmentCode())){
            Equipment equipment3 = equipmentService.selectEquipmentByEquipmentCode(equipment.getEquipmentCode());
            if (equipment3 != null) {
                return AjaxResult.error("设备号已存在");
            }
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

        if (equipmentList.length != 0&& !equipmentList[0].isEmpty()) {
            System.out.println(System.currentTimeMillis());
            equipmentService.updateEquipmentStatus(equipmentList);
            System.out.println(System.currentTimeMillis());
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


    /**
     * 查询设备号是否存在
     * @param equimentCode
     * @return
     */
    @GetMapping("/code")
    public AjaxResult code(String equimentCode){
        try {
            Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equimentCode);
            if (equipment!=null){
                return AjaxResult.success(true);
            }
            return AjaxResult.success(true);
        }catch (Exception e){
            return AjaxResult.success(true);
        }
    }
    //查询在线设备数量
    @GetMapping("/onlineNum")
    public AjaxResult onlineNum(){
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        List<String> hospitalIds = new ArrayList<>();
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
        if (SysUser.isAdmin(loginUser.getUserId())|| RoleUtils.isRoleListOne(getLoginUser(),sysDictData)){
            int count = equipmentService.selectEquipmentOnlineNum(hospitalIds);
            return AjaxResult.success(count);
        }

        Hospital hospital = hospitalService.selectHospitalByHospitalCode(user.getHospitalCode());
        if(hospital!=null){
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());

            List<AssociatedHospital> associatedHospitals = associatedHospitalService.selectAssociatedHospitalList(associatedHospital);
            hospitalIds.add(hospital.getHospitalCode());
            for (AssociatedHospital associatedHospital1 : associatedHospitals) {
                Hospital hospital1 = hospitalService.selectHospitalByHospitalId(associatedHospital1.getLowerLevelHospitalId());
                hospitalIds.add(hospital1.getHospitalCode());
            }

            int count = equipmentService.selectEquipmentOnlineNum(hospitalIds);
            return AjaxResult.success(count);
        }


        return AjaxResult.success(null);
    }




    @GetMapping("/ecg_use")
    public TableDataInfo ecg_use(Equipment equipment) throws Exception {
        if (equipment.getPatientPhone()!=null&&!"".equals(equipment.getPatientPhone())){
            equipment.setPatientPhone(aesUtils.encrypt(equipment.getPatientPhone()));
        }
        startPage();
        List<Equipment> equipment1 = equipmentService.selectEquipmentListAll(equipment);
        return getDataTable(equipment1);
    }


    @GetMapping("/getEquipmentList")
    public AjaxResult getEquipmentList(Equipment equipment) throws Exception {

        List<Equipment> list = new ArrayList<>();

        if (equipment.getDepartmentName()!=null&&!"".equals(equipment.getDepartmentName())){
            Department department1 = new Department();
            department1.setDepartmentName(equipment.getDepartmentName());

            List<Department> departments1 = departmentService.selectDepartmentList(department1);
            for (Department c:departments1){
                equipment.getDepartmentCodeList().add(c.getDepartmentCode());
            }
        }
        Long userId = getUserId();
        List<String> sysDictData = dictDataService.selectDictDataByType("admin_select");
        if (!SysUser.isAdmin(userId)&& !RoleUtils.isRoleListOne(getLoginUser(),sysDictData)){
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalCode = sysUser.getHospitalCode();
            equipment.getHospitalCodeList().add(hospitalCode);
            if (equipment.getHospitalCode()!=null){
                Hospital hospital = hospitalService.selectHospitalByHospitalCode(hospitalCode);
                if (hospital!=null){
                    AssociatedHospital associatedHospital = new AssociatedHospital();
                    associatedHospital.setHospitalId(hospital.getHospitalId());
                    List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
                    if (associatedHospitals!=null&& !associatedHospitals.isEmpty()){
                        for (AssociatedHospital c:associatedHospitals){
                            Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                            equipment.getHospitalCodeList().add(hospital1.getHospitalCode());
                        }
                    }
                }
            }
            if (equipment.getHospitalCode()!=null&&!"".equals(equipment.getHospitalCode())){
                List<String> equipmentList = equipment.getHospitalCodeList();
                if (equipmentList!=null&& !equipmentList.isEmpty()){
                    for (String c : equipmentList){
                        if (c.equals(equipment.getHospitalCode())){

                            equipment.getHospitalCodeList().clear();
                            equipment.getHospitalCodeList().add(equipment.getHospitalCode());
                            break;
                        }
                    }
                }
            }
            list = equipmentService.selectEquipmentList(equipment);
        } else {
            if (equipment.getHospitalCode()!=null&&!"".equals(equipment.getHospitalCode())){
                Hospital hospital = hospitalService.selectCode(equipment.getHospitalCode());
                if (hospital==null){
                    return AjaxResult.success(list);
                }
                equipment.getHospitalCodeList().add(hospital.getHospitalCode());
            }
            list = equipmentService.selectEquipmentList(equipment);
        }
        List<String> leaseDetails = leaseDetailsService.selectLeaseDetailsList(new LeaseDetails()).stream().map(LeaseDetails::getEquipmentCode).collect(Collectors.toList());
        Collection<Equipment> collect = list.stream().filter(c -> StringUtils.isNotEmpty(c.getEquipmentCode()))
                .filter(c -> !leaseDetails.contains(c.getEquipmentCode())).collect(Collectors.toList());
        return AjaxResult.success(collect);
    }




    @GetMapping("/getCodeByHospital")
    public AjaxResult getCodeByHospital(String equipmentCode) throws Exception {

        return AjaxResult.success(equipmentService.getCodeByHospital(equipmentCode));
    }

}
