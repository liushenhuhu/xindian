package com.ruoyi.xindian.relationship.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.domain.PatientRelationshipDto;
import com.ruoyi.xindian.relationship.service.IPatientRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

/**
 * 患者关系表Controller
 *
 * @author chenpeng
 * @date 2023-06-30
 */
@RestController
@RequestMapping("/relationship/relationship")
public class PatientRelationshipController extends BaseController
{
    @Autowired
    private IPatientRelationshipService patientRelationshipService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IAppDataService appDataService;

    @Autowired
    private IMedicalHistoryService medicalHistoryService;


    @Autowired
    private AesUtils aesUtils;

    /**
     * 查询患者关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientRelationship patientRelationship) throws Exception {
        if (patientRelationship.getFatherPhone()!=null&&!"".equals(patientRelationship.getFatherPhone())){
            patientRelationship.setFatherPhone(aesUtils.encrypt(patientRelationship.getFatherPhone()));
        }
        if (patientRelationship.getSonPhone()!=null&&!"".equals(patientRelationship.getSonPhone())){
            patientRelationship.setSonPhone(aesUtils.encrypt(patientRelationship.getSonPhone()));
        }
        List<PatientRelationship> list = patientRelationshipService.selectPatientRelationshipList(patientRelationship);
        for (PatientRelationship relationship : list) {

            Patient patient = patientService.selectPatientByPatientPhone(relationship.getSonPhone());
            if (relationship.getFatherPhone()!=null&&!"".equals(relationship.getFatherPhone())){
                relationship.setFatherPhone(aesUtils.decrypt(relationship.getFatherPhone()));
            }
            if (relationship.getSonPhone()!=null&&!"".equals(relationship.getSonPhone())){
                relationship.setSonPhone(aesUtils.decrypt(relationship.getSonPhone()));
            }
            if(patient != null){
                relationship.setRelationshipPatientName(aesUtils.decrypt(patient.getPatientName()));
                relationship.setHospital(patient.getPatientSource());
            }

        }
        if(patientRelationship.getFatherPhone() != null){
            PatientRelationship relationship = new PatientRelationship();
            Patient patient = patientService.selectPatientByPatientPhone(patientRelationship.getFatherPhone());
            relationship.setHospital(patient.getPatientSource());
            relationship.setRelationshipPatientName(aesUtils.decrypt(patient.getPatientName()));
            relationship.setFatherPhone(aesUtils.decrypt(patientRelationship.getFatherPhone()));
            relationship.setSonPhone(aesUtils.decrypt(patientRelationship.getFatherPhone()));
            relationship.setRelationship("自己");
            list.add(0,relationship);

        }
        return getDataTable(list);
    }

    /**
     * 导出患者关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:export')")
    @Log(title = "患者关系表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PatientRelationship patientRelationship)
    {
        List<PatientRelationship> list = patientRelationshipService.selectPatientRelationshipList(patientRelationship);
        ExcelUtil<PatientRelationship> util = new ExcelUtil<PatientRelationship>(PatientRelationship.class);
        util.exportExcel(response, list, "患者关系表数据");
    }

    /**
     * 获取患者关系表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {

        PatientRelationship patientRelationship = patientRelationshipService.selectPatientRelationshipById(id);
        if (patientRelationship.getFatherPhone()!=null&&!"".equals(patientRelationship.getFatherPhone())){
            patientRelationship.setFatherPhone(aesUtils.decrypt(patientRelationship.getFatherPhone()));
        }
        if (patientRelationship.getSonPhone()!=null&&!"".equals(patientRelationship.getSonPhone())){
            patientRelationship.setSonPhone(aesUtils.decrypt(patientRelationship.getSonPhone()));
        }
        return AjaxResult.success(patientRelationship);
    }
    private PatientRelationship getRelationship(PatientRelationshipDto patientRelationship){
        PatientRelationship relationship = new PatientRelationship();
        if (patientRelationship.getSonPhone()==null||patientRelationship.getSonPhone().equals(patientRelationship.getFatherPhone())){
            StringBuilder fatherPhone = new StringBuilder(patientRelationship.getFatherPhone());
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                fatherPhone.append(random.nextInt(10));
            }
            relationship.setSonPhone(fatherPhone.toString());
        }else {
            relationship.setSonPhone(patientRelationship.getSonPhone());
        }

        relationship.setFatherPhone(patientRelationship.getFatherPhone());

        relationship.setRelationship(patientRelationship.getRelationship());
        return relationship;
    }



    private PatientRelationship getRelationship1(PatientRelationshipDto patientRelationship){
        PatientRelationship relationship = new PatientRelationship();
        if (patientRelationship.getSonPhone()==null||"".equals(patientRelationship.getSonPhone())||patientRelationship.getSonPhone().equals(patientRelationship.getFatherPhone())){
            StringBuilder fatherPhone = new StringBuilder(patientRelationship.getFatherPhone()+"-");
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                fatherPhone.append(random.nextInt(10));
            }
            relationship.setSonPhone(fatherPhone.toString());
        }else {
            relationship.setSonPhone(patientRelationship.getSonPhone());
        }

        relationship.setFatherPhone(patientRelationship.getFatherPhone());

        relationship.setRelationship(patientRelationship.getRelationship());
        return relationship;
    }


    private AppData getAppData(PatientRelationshipDto patientRelationship) throws Exception {
        AppData appData = new AppData();
        appData.setBirthDay(patientRelationship.getBirthDay());
        appData.setPatientPhone(patientRelationship.getSonPhone());
        appData.setUserName(patientRelationship.getSonPhone());
        appData.setPatientSex(patientRelationship.getPatientSex());
        appData.setPatientName(aesUtils.encrypt(patientRelationship.getRelationshipPatientName()));
        return appData;
    }
    private MedicalHistory getMedicalHistory(PatientRelationshipDto patientRelationship){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setPastMedicalHistory(patientRelationship.getPastMedicalHistory());
        medicalHistory.setLivingHabit(patientRelationship.getLivingHabit());
        medicalHistory.setHeight(patientRelationship.getHeight());
        medicalHistory.setWeight(patientRelationship.getWeight());
        medicalHistory.setPatientPhone(patientRelationship.getSonPhone());
        return medicalHistory;
    }



    private Patient getPatient(PatientRelationshipDto patientRelationship) throws Exception {
        Patient patient = new Patient();
        patient.setPatientName(aesUtils.encrypt(patientRelationship.getRelationshipPatientName()));
        patient.setPatientPhone(patientRelationship.getSonPhone());
        patient.setPatientNameAes(patientRelationship.getRelationshipPatientName());
        patient.setPatientPhoneAes(aesUtils.decrypt(patientRelationship.getSonPhone()));
        patient.setPatientSex(patientRelationship.getPatientSex());
        patient.setBirthDay(patientRelationship.getBirthDay());
        return patient;
    }

    /**
     * 新增患者关系表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:add')")
    @Log(title = "患者关系表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatientRelationshipDto patientRelationship) throws Exception {
        PatientRelationship relationship = getRelationship(patientRelationship);
        if (relationship.getFatherPhone()!=null&&!"".equals(relationship.getFatherPhone())){
            relationship.setFatherPhone(aesUtils.encrypt(relationship.getFatherPhone()));
        }
        if (relationship.getSonPhone()!=null&&!"".equals(relationship.getSonPhone())){
            relationship.setSonPhone(aesUtils.encrypt(relationship.getSonPhone()));
        }
        if (patientRelationship.getFatherPhone()!=null&&!"".equals(patientRelationship.getFatherPhone())){
            patientRelationship.setFatherPhone(aesUtils.encrypt(patientRelationship.getFatherPhone()));
        }
        if (patientRelationship.getSonPhone()!=null&&!"".equals(patientRelationship.getSonPhone())){
            patientRelationship.setSonPhone(aesUtils.encrypt(patientRelationship.getSonPhone()));
        }
        PatientRelationship patientRelationship1 = new PatientRelationship();
        patientRelationship1.setSonPhone(relationship.getSonPhone());
        patientRelationship1.setFatherPhone(relationship.getFatherPhone());
        List<PatientRelationship> patientRelationships = patientRelationshipService.selectPatientRelationshipList(patientRelationship1);
        if (patientRelationships!=null&&patientRelationships.size()>0){
            return AjaxResult.error("该患者已在用户家人中");
        }
        if(patientRelationship.getRelationshipPatientName() != null){
            AppData appData = getAppData(patientRelationship);
            MedicalHistory medicalHistory = getMedicalHistory(patientRelationship);
            Patient patient = getPatient(patientRelationship);
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
        }


        return toAjax(patientRelationshipService.insertPatientRelationship(relationship));
    }




    @PostMapping("/addPatient")
    public AjaxResult addPatient(@RequestBody PatientRelationshipDto patientRelationship) throws Exception {

        PatientRelationship relationship = getRelationship1(patientRelationship);
        patientRelationship.setSonPhone(relationship.getSonPhone());
        if (relationship.getFatherPhone()!=null&&!"".equals(relationship.getFatherPhone())){
            relationship.setFatherPhone(aesUtils.encrypt(relationship.getFatherPhone()));
        }
        if (relationship.getSonPhone()!=null&&!"".equals(relationship.getSonPhone())){
            relationship.setSonPhone(aesUtils.encrypt(relationship.getSonPhone()));
        }
        if (patientRelationship.getFatherPhone()!=null&&!"".equals(patientRelationship.getFatherPhone())){
            patientRelationship.setFatherPhone(aesUtils.encrypt(patientRelationship.getFatherPhone()));
        }
        if (patientRelationship.getSonPhone()!=null&&!"".equals(patientRelationship.getSonPhone())){
            patientRelationship.setSonPhone(aesUtils.encrypt(patientRelationship.getSonPhone()));
        }
        PatientRelationship patientRelationship1 = new PatientRelationship();
        patientRelationship1.setSonPhone(relationship.getSonPhone());
        patientRelationship1.setFatherPhone(relationship.getFatherPhone());
        List<PatientRelationship> patientRelationships = patientRelationshipService.selectPatientRelationshipList(patientRelationship1);
        if (patientRelationships!=null&&patientRelationships.size()>0){
            return AjaxResult.error("该患者已在用户家人中");
        }
        if(patientRelationship.getRelationshipPatientName() != null){
            AppData appData = getAppData(patientRelationship);

            MedicalHistory medicalHistory = getMedicalHistory(patientRelationship);
            Patient patient = getPatient(patientRelationship);
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
        }
        return toAjax(patientRelationshipService.insertPatientRelationship(relationship));
    }


    /**
     * 修改患者关系表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:edit')")
    @Log(title = "患者关系表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PatientRelationship patientRelationship) throws Exception {
        if (patientRelationship.getFatherPhone()!=null&&!"".equals(patientRelationship.getFatherPhone())){
            patientRelationship.setFatherPhone(aesUtils.encrypt(patientRelationship.getFatherPhone()));
        }
        if (patientRelationship.getSonPhone()!=null&&!"".equals(patientRelationship.getSonPhone())){
            patientRelationship.setSonPhone(aesUtils.encrypt(patientRelationship.getSonPhone()));
        }
        if (patientRelationship.getRelationshipPatientName()!=null&&!"".equals(patientRelationship.getRelationshipPatientName())){
            patientRelationship.setRelationshipPatientName(patientRelationship.getRelationshipPatientName());
        }
        return toAjax(patientRelationshipService.updatePatientRelationship(patientRelationship));
    }

    /**
     * 删除患者关系表
     */
//    @PreAuthorize("@ss.hasPermi('relationship:relationship:remove')")
    @Log(title = "患者关系表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(patientRelationshipService.deletePatientRelationshipByIds(ids));
    }
}
