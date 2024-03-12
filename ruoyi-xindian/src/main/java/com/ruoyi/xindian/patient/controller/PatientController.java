package com.ruoyi.xindian.patient.controller;
import java.util.Date;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Aes;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.DoctorRelationPatient;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.domain.PatientMedicalHistoryDTO;
import com.ruoyi.xindian.patient.service.DoctorRelationPatientService;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.domain.PatientRelationshipDto;
import com.ruoyi.xindian.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 患者Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/patient/patient")
public class PatientController extends BaseController
{

    @Autowired
    private IMedicalHistoryService medicalHistoryService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IHospitalService hospitalService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IAppDataService appDataService;
    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    @Resource
    private AesUtils aesUtils;



    @Resource
    private DoctorRelationPatientService doctorRelationPatientService;

    @Resource
    private RedisTemplate<String,Patient> redisTemplate;

    /**
     * 查询患者列表
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:list')")
    @GetMapping("/list")
    @Aes
    public TableDataInfo list(Patient patient,Integer pageSize,Integer pageNum) throws Exception {
        List<Patient> list = new ArrayList<>();
        Long userId = getUserId();

        if (!SysUser.isAdmin(userId)) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String userHospitalCode= sysUser.getHospitalCode();
            if(userHospitalCode!=null){
                Hospital hospital = hospitalService.selectHospitalByHospitalCode(userHospitalCode);
                if (hospital!=null){
                    patient.getHospitalNameList().add(hospital.getHospitalName());
                    patient.setHospitalId(hospital.getHospitalId());
                }

            }else {
                return getDataTable(list);
            }

            if (patient.getHospitalId()!=null){
                AssociatedHospital associatedHospital = new AssociatedHospital();
                associatedHospital.setHospitalId(patient.getHospitalId());
                List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
                if (associatedHospitals!=null&&associatedHospitals.size()>0){
                    for (AssociatedHospital c:associatedHospitals){
                        Hospital hospital1 = hospitalService.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                        patient.getHospitalNameList().add(hospital1.getHospitalName());
                    }
                }
            }
            if (patient.getPatientSource()!=null&&!"".equals(patient.getPatientSource())){
                List<String> patientList = patient.getHospitalNameList();
                if (patientList!=null&&patientList.size()>0){
                    for (String c : patientList){
                        if (c.equals(patient.getPatientSource())){
                            patient.getHospitalNameList().clear();
                            patient.getHospitalNameList().add(patient.getPatientSource());
                            break;
                        }
                    }
                }
            }
            startPage();
            list = patientService.selectPatientList(patient);
        } else {
            if(patient.getPatientSource()!=null&&!"".equals(patient.getPatientSource())){
                patient.getHospitalNameList().add(patient.getPatientSource());
            }
            if (StringUtils.isNotEmpty(patient.getIsSelect())&& patient.getIsSelect().equals("1")){
                if (StringUtils.isNotEmpty(patient.getPatientName())){

                    String patientName = aesUtils.decrypt(patient.getPatientName());

                    if (Boolean.TRUE.equals(redisTemplate.hasKey("patientListByTest:" + patientName))){
                        List<Patient> listKeys = redisTemplate.opsForList().range("patientListByTest:"+ patientName, (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);

                        return getTable(listKeys, redisTemplate.opsForList().size("patientListByTest:"+patientName));
                    }
                    List<Patient> list1 = new ArrayList<>();
                    List<Patient> patientList = redisTemplate.opsForList().range("patientList", 0, -1);
                    if (patientList != null) {
                        for (Patient pat : patientList) {
                            if(pat.getPatientName().contains(patientName)){
                                list1.add(pat);
                            }
                        }
                    }

                    if (patientList != null && list1.size() == patientList.size()) {
                        List<Patient> listKeys = redisTemplate.opsForList().range("patientList", (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);
                        long total = 0;
                        if (listKeys != null) {
                            total = new PageInfo(listKeys).getTotal();
                        }
                        return getTable(listKeys, total);
                    }else {
                        redisTemplate.delete("patientListByTest:*");
                        for (Patient s : list1){
                            redisTemplate.opsForList().rightPush("patientListByTest:"+patientName,s);
                        }
                        List<Patient> listKeys = redisTemplate.opsForList().range("patientListByTest:"+patientName, (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);
                        return getTable(listKeys, redisTemplate.opsForList().size("patientListByTest:"+ patientName));
                    }


                }
            }







            startPage();
            list   = patientService.selectPatientList(patient);

        }

        for (Patient pat : list) {
            if(pat.getBirthDay()!=null)
                pat.setPatientAge(String.valueOf(DateUtil.getAge(pat.getBirthDay())));
            if(pat.getPatientSex().length()>1){
                    pat.setPatientSex(pat.getPatientSex().substring(0,1));
                }
            if(pat.getPatientPhone() != null){
                pat.setPatientPhone(aesUtils.decrypt(pat.getPatientPhone()));
            }
            if(pat.getPatientName() != null){
                pat.setPatientName(aesUtils.decrypt(pat.getPatientName()));
            }
            if (pat.getFamilyPhone()!=null&&!"".equals(pat.getFamilyPhone())){
                pat.setFamilyPhone(aesUtils.decrypt(pat.getFamilyPhone()));
            }
//            if(pat.getPatientAge()==null || Objects.equals(pat.getPatientAge(), "")){
//                pat.setPatientAge(appDataService.selectAppDataByPatientPhone(patient.getPatientPhone()).getPatientAge());
//            }
        }
        return getDataTable(list);
    }

    /**
     * 导出患者列表
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:export')")
    @Log(title = "患者", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Patient patient)
    {
        List<Patient> list = new ArrayList<>();
        if (getDeptId() == 200) {
            SysUser sysUser = userService.selectUserById(getUserId());
            String hospitalName = sysUser.getHospitalName();
            patient.setPatientSource(hospitalName);
            list = patientService.selectPatientList(patient);
        } else {
            list = patientService.selectPatientList(patient);
        }
        ExcelUtil<Patient> util = new ExcelUtil<Patient>(Patient.class);
        util.exportExcel(response, list, "患者数据");
    }

    /**
     * 获取患者详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:query')")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId) throws Exception {
        Patient patient = patientService.selectPatientByPatientId(patientId);
        if(patient.getPatientPhone() != null){
            patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
        }
        if(patient.getPatientName() != null){
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }
        if (patient.getFamilyPhone()!=null&&!"".equals(patient.getFamilyPhone())){
            patient.setFamilyPhone(aesUtils.decrypt(patient.getFamilyPhone()));
        }
        if(StringUtils.isNotEmpty(patient.getFamilyName())){
            patient.setFamilyName(aesUtils.decrypt(patient.getFamilyName()));
        }
        return AjaxResult.success(patient);
    }

    /**
     * 获取患者详细信息
     */
    @GetMapping(value = "/getInfoByPhone/{patientPhone}")
    public AjaxResult getInfoByPhone(@PathVariable("patientPhone") String patientPhone) throws Exception {

        Patient patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(patientPhone));
        Date birthDay = patient.getBirthDay();
        if(birthDay != null){
            patient.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
        }
        if(patient.getPatientPhone() != null){
            patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
        }
        if(patient.getPatientName() != null){
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }
        if (patient.getFamilyPhone()!=null&&!"".equals(patient.getFamilyPhone())){
            patient.setFamilyPhone(aesUtils.decrypt(patient.getFamilyPhone()));
        }
        if(StringUtils.isNotEmpty(patient.getFamilyName())){
            patient.setFamilyName(aesUtils.decrypt(patient.getFamilyName()));
        }
        return AjaxResult.success(patient);
    }


    /**
     * 通过patientName获取患者详细信息
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:query')")
    @GetMapping(value = "/getInfoByPatientName/{patientName}")
    public AjaxResult getInfoByPatientName(@PathVariable("patientName") String patientName) throws Exception {
        Patient patient = patientService.selectPatientByPatientName(patientName);
        System.out.println(patient);
        Date birthDay = patient.getBirthDay();
        if(birthDay != null){
            patient.setPatientAge(Integer.toString(DateUtil.getAge(birthDay)));
        }
        if(patient.getPatientPhone() != null){
            patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
        }
        if(patient.getPatientName() != null){
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }
        if (patient.getFamilyPhone()!=null&&!"".equals(patient.getFamilyPhone())){
            patient.setFamilyPhone(aesUtils.decrypt(patient.getFamilyPhone()));
        }
        if(StringUtils.isNotEmpty(patient.getFamilyName())){
            patient.setFamilyName(aesUtils.decrypt(patient.getFamilyName()));
        }
        return AjaxResult.success(patient);
    }

    /**
     * 新增患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:add')")
    @Log(title = "患者", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Patient patient) throws Exception {
        //加密
        patient.setPatientNameAes(patient.getPatientName());
        patient.setPatientPhoneAes(patient.getPatientPhone());
        patient.setPatientName(aesUtils.encrypt(patient.getPatientName()));
        patient.setPatientPhone(aesUtils.encrypt(patient.getPatientPhone()));
        Patient patient1 = patientService.selectPatientByPatientPhone(patient.getPatientPhone());
        if (patient1!=null){
            return AjaxResult.error("手机号已重复");
        }
        if (StringUtils.isNotEmpty(patient.getFamilyPhone())){
            patient.setFamilyPhone(aesUtils.encrypt(patient.getFamilyPhone()));
        }

        patient.setPatientAge(String.valueOf(DateUtil.getAge(patient.getBirthDay())));


        return toAjax(patientService.insertPatient(patient));
    }

    /**
     * 修改患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:edit')")
    @Log(title = "患者", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Patient patient) throws Exception {
        //加密
        patient.setPatientName(aesUtils.encrypt(patient.getPatientName()));
        patient.setPatientPhone(aesUtils.encrypt(patient.getPatientPhone()));
        if (StringUtils.isNotEmpty(patient.getFamilyPhone())){
            patient.setFamilyPhone(aesUtils.encrypt(patient.getFamilyPhone()));
        }
        Patient patient2 = patientService.selectPatientByPatientId(patient.getPatientId());
        if (!patient2.getPatientPhone().equals(patient.getPatientPhone())){
            Patient patient1 = patientService.selectPatientByPatientPhone(patient.getPatientPhone());
            if (patient1!=null){
                return AjaxResult.error("手机号已重复");
            }
        }

        if (patient.getBirthDay()!=null){
            patient.setPatientAge(String.valueOf(DateUtil.getAge(patient.getBirthDay())));
        }
        return toAjax(patientService.updatePatient(patient));
    }

    /**
     * 删除患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:remove')")
    @Log(title = "患者", businessType = BusinessType.DELETE)
    @DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds)
    {
        return toAjax(patientService.deletePatientByPatientIds(patientIds));
    }

    /**
     * 修改患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:edit')")
    @Log(title = "患者", businessType = BusinessType.UPDATE)
    @PostMapping("/updateMonitoringStatus")
    public void updateMonitoringStatus(@RequestBody List<String> monitoringStatusList)
    {
        System.out.println("执行开始");
        patientService.updateMonitoringStatus();
        for (String equipmentCode : monitoringStatusList) {
            patientService.updateMonitoringStatus2(equipmentCode);
        }
        System.out.println("执行完毕");
    }

    @GetMapping("/addDict")
    public AjaxResult addDict() {
        SysDictData dict = new SysDictData();
        dict.setDictType("hospital_name_name_list");
        dict.setListClass("default");
        dict.setCreateBy("admin");
        Hospital hospital = new Hospital();
        List<Hospital> allHospital = hospitalService.selectHospitalList(hospital);
        int i = 0;
        for (Hospital hospital1 : allHospital) {
            dict.setDictSort((long) i);
            dict.setDictLabel(hospital1.getHospitalName());
            dict.setDictValue(hospital1.getHospitalName());
            List<SysDictData> sysDictData = dictDataService.selectDictDataList(dict);
            if (sysDictData.isEmpty()) {
                dictDataService.insertDictData(dict);
            }
            i = i +1;
        }
        return AjaxResult.success("down");
    }

    /**
     * 删除患者
     */
    @PreAuthorize("@ss.hasPermi('patient:patient:remove')")
    @Log(title = "患者", businessType = BusinessType.DELETE)
    @DeleteMapping("/delByPatientPhone/{patientPhone}")
    public AjaxResult delPatient(@PathVariable String patientPhone)
    {
        return toAjax(patientService.deletePatientByPatientPhone(patientPhone));
    }


    /**
     * 患者使用医生的手机号进行注册
     * 患者新手机号为   医生手机号-患者姓名-随机三位字符串
     * @param patientMedicalHistoryDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/addPatientAndDocPhoneOrMed")
    public AjaxResult addPatientAndDocPhoneOrMed(@RequestBody PatientMedicalHistoryDTO patientMedicalHistoryDTO) throws Exception {


        if (StringUtils.isEmpty(patientMedicalHistoryDTO.getDoctorPhone())){
            return AjaxResult.error("医生手机号不能为空");
        }
        if (StringUtils.isEmpty(patientMedicalHistoryDTO.getPatientName())){
            return AjaxResult.error("患者姓名不能为空");
        }

        PatientMedicalHistoryDTO patientMedicalHistoryDTO1 = getRelationship(patientMedicalHistoryDTO);

        if (StringUtils.isNotEmpty(patientMedicalHistoryDTO1.getPatientName())){
            patientMedicalHistoryDTO1.setPatientName(aesUtils.encrypt(patientMedicalHistoryDTO1.getPatientName()));
        }

        if (StringUtils.isNotEmpty(patientMedicalHistoryDTO1.getPatientPhone())){
            patientMedicalHistoryDTO1.setPatientPhone(aesUtils.encrypt(patientMedicalHistoryDTO1.getPatientPhone()));
        }

        MedicalHistory medicalHistory = getMedicalHistory(patientMedicalHistoryDTO1);

        AppData appData = getAppData(patientMedicalHistoryDTO1);

        Patient patient = getPatient(patientMedicalHistoryDTO1);

        Patient patient1 = patientService.selectPatientByPatientPhone(patient.getPatientPhone());
        if(patient1!=null){
            patient.setPatientId(patient1.getPatientId());
            patientService.updatePatient(patient);
        }else{
            patientService.insertPatient(patient);
        }
        appDataService.insertAppData(appData);
        MedicalHistory medicalHistory1 = medicalHistoryService.selectMedicalHistoryByPatientPhone(medicalHistory.getPatientPhone());
        if(medicalHistory1!=null){
            medicalHistory.setMedicalHistoryId(medicalHistory1.getMedicalHistoryId());
            medicalHistoryService.updateMedicalHistory(medicalHistory);
        }
        else{
            medicalHistoryService.insertMedicalHistory(medicalHistory);
        }
        return AjaxResult.success(aesUtils.decrypt(patient.getPatientPhone()));

    }


    /**
     * 解析出患者信息
     * @param patientRelationship
     * @return
     * @throws Exception
     */
    private Patient getPatient(PatientMedicalHistoryDTO patientRelationship) throws Exception {
        Patient patient = new Patient();
        patient.setPatientName(patientRelationship.getPatientName());
        patient.setPatientPhone(patientRelationship.getPatientPhone());
        patient.setPatientSex(patientRelationship.getPatientSex());
        patient.setBirthDay(patientRelationship.getBirthDay());
        if (patientRelationship.getBirthDay()!=null){
            patient.setPatientAge(String.valueOf(DateUtil.getAge(patientRelationship.getBirthDay())));
        }
        return patient;
    }

    /**
     * 通过医生的手机号生成患者对应的手机号
     * @param patientRelationship
     * @return
     */
    private PatientMedicalHistoryDTO getRelationship(PatientMedicalHistoryDTO patientRelationship){
        StringBuilder patientPhone = new StringBuilder(patientRelationship.getDoctorPhone());
        patientPhone.append("-").append(randomGen(5)).append("-");
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            patientPhone.append(random.nextInt(10));
        }
        patientRelationship.setPatientPhone(String.valueOf(patientPhone));
        return patientRelationship;
    }

    public String randomGen(int place) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }

    /**
     * 解析出患者相关信息
     * @param patientRelationship
     * @return
     */
    private MedicalHistory getMedicalHistory(PatientMedicalHistoryDTO patientRelationship){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setPastMedicalHistory(patientRelationship.getPastMedicalHistory());
        medicalHistory.setLivingHabit(patientRelationship.getLivingHabit());
        medicalHistory.setHeight(patientRelationship.getHeight());
        medicalHistory.setWeight(patientRelationship.getWeight());
        medicalHistory.setPatientPhone(patientRelationship.getPatientPhone());
        return medicalHistory;
    }


    /**
     * 解析除appData的所需数据
     * @param patientRelationship
     * @return
     * @throws Exception
     */
    private AppData getAppData(PatientMedicalHistoryDTO patientRelationship) throws Exception {
        AppData appData = new AppData();
        appData.setBirthDay(patientRelationship.getBirthDay());
        appData.setPatientPhone(patientRelationship.getPatientPhone());
        appData.setUserName(patientRelationship.getPatientPhone());
        appData.setPatientSex(patientRelationship.getPatientSex());
        appData.setPatientName(patientRelationship.getPatientName());
        return appData;
    }


    @GetMapping("/getPatientByCode")
    public TableDataInfo getPatientByCode(String code) throws Exception{
        startPage();
        List<Patient> patients = patientService.selectPatientByCode(code);
        for (Patient patient:patients){
            if(patient.getBirthDay()!=null){
                patient.setPatientAge(String.valueOf(DateUtil.getAge(patient.getBirthDay())));
            }
            if(patient.getPatientPhone()!=null){
                patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
            }
            if(patient.getPatientName()!=null){
                patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
            }
        }
        return getDataTable(patients);
    }
    @PostMapping("/addPatientByJZ")
    public AjaxResult getPatientByCode(@RequestBody Patient patient) throws Exception {
        if (patient.getDoctorPhone()==null){
            return AjaxResult.success(patient);
        }
        if(StringUtils.isEmpty(patient.getPatientPhone())){
            patient.setPatientPhone(patient.getDoctorPhone()+"-"+randomGenNum(6));
        }
        patient.setBindingDoctor(aesUtils.encrypt(patient.getDoctorPhone()));
        encryptPatient(patient);

        Patient patient1 = patientService.selectPatientByPatientPhone(patient.getPatientPhone());
        if (patient1!= null) {

            DoctorRelationPatient doctorRelationPatient = new DoctorRelationPatient();
            doctorRelationPatient.setDoctorPhone(patient.getDoctorPhone());
            doctorRelationPatient.setPatientPhone(patient.getPatientPhone());
            List<DoctorRelationPatient> doctorRelationPatients = doctorRelationPatientService.selectDoctorRelationPatientList(doctorRelationPatient);
            if (doctorRelationPatients!=null&& doctorRelationPatients.isEmpty()) {
                doctorRelationPatientService.insertDoctorRelationPatient(doctorRelationPatient);
            }
            decryptPatient(patient1);
            return AjaxResult.success(patient1);
        }else {
            int i = patientService.insertPatient(patient);
            MedicalHistory medicalHistory = new MedicalHistory();
            BeanUtils.copyProperties(patient,medicalHistory);
            int i1 = medicalHistoryService.insertMedicalHistory(medicalHistory);
            DoctorRelationPatient doctorRelationPatient = new DoctorRelationPatient();
            doctorRelationPatient.setDoctorPhone(patient.getDoctorPhone());
            doctorRelationPatient.setPatientPhone(patient.getPatientPhone());
            doctorRelationPatientService.insertDoctorRelationPatient(doctorRelationPatient);
            decryptPatient(patient);
            return AjaxResult.success(patient);
        }

    }


    @GetMapping("/getDoctorRelationPatient")
    public AjaxResult getDoctorRelationPatient(DoctorRelationPatient doctorRelationPatient) throws Exception {
        List<Patient> patients = patientService.selectByDoc(doctorRelationPatient);
        for (Patient patient:patients){
            decryptPatient(patient);
        }
        return AjaxResult.success(patients);
    }

    /**
     * 随机数
     * @param place 定义随机数的位数
     */
    public String randomGenNum(int place) {
        String base = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }


    private void  encryptPatient(Patient patient) throws Exception {
        if(patient.getBirthDay() != null){
            patient.setPatientAge(Integer.toString(DateUtil.getAge(patient.getBirthDay())));
        }
        if(patient.getPatientPhone() != null){
            patient.setPatientPhone(aesUtils.encrypt(patient.getPatientPhone()));
        }
        if(patient.getPatientName() != null){
            patient.setPatientName(aesUtils.encrypt(patient.getPatientName()));
        }
        if (patient.getFamilyPhone()!=null&&!"".equals(patient.getFamilyPhone())){
            patient.setFamilyPhone(aesUtils.encrypt(patient.getFamilyPhone()));
        }
        if (patient.getDoctorPhone()!=null&&!"".equals(patient.getDoctorPhone())){
            patient.setDoctorPhone(aesUtils.encrypt(patient.getDoctorPhone()));
        }
    }

    private void decryptPatient(Patient patient) throws Exception {

        if(patient.getBirthDay()!=null){
            patient.setPatientAge(String.valueOf(DateUtil.getAge(patient.getBirthDay())));
        }
        if(patient.getPatientPhone()!=null){
            patient.setPatientPhone(aesUtils.decrypt(patient.getPatientPhone()));
        }
        if(patient.getPatientName()!=null){
            patient.setPatientName(aesUtils.decrypt(patient.getPatientName()));
        }
        if(patient.getDoctorPhone()!=null){
            patient.setDoctorPhone(aesUtils.decrypt(patient.getDoctorPhone()));
        }
    }
}
