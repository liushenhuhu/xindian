package com.ruoyi.xindian.medical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;
import com.ruoyi.xindian.medical.domain.MedicalData;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.domain.MedicalHistoryDto;
import com.ruoyi.xindian.medical.service.IMedicalDataService;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 病史Controller
 * 
 * @author hanhan
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/medicalHistory/medicalHistory")
public class MedicalHistoryController extends BaseController
{
    @Autowired
    private IMedicalHistoryService medicalHistoryService;
    @Autowired
    private IMedicalDataService medicalDataService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IAppDataService appDataService;


    @Autowired
    private AesUtils aesUtils;

    /**
     * 查询病史列表
     */
//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(MedicalHistory medicalHistory)
    {
        startPage();
        List<MedicalHistory> list = medicalHistoryService.selectMedicalHistoryList(medicalHistory);
        return getDataTable(list);
    }

    /**
     * 导出病史列表
     */
//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:export')")
    @Log(title = "病史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MedicalHistory medicalHistory)
    {
        List<MedicalHistory> list = medicalHistoryService.selectMedicalHistoryList(medicalHistory);
        ExcelUtil<MedicalHistory> util = new ExcelUtil<MedicalHistory>(MedicalHistory.class);
        util.exportExcel(response, list, "病史数据");
    }

    /**
     * 获取病史详细信息
     */
//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:query')")
    @GetMapping(value = "/{medicalHistoryId}")
    public AjaxResult getInfo(@PathVariable("medicalHistoryId") Long medicalHistoryId)
    {
        return AjaxResult.success(medicalHistoryService.selectMedicalHistoryByMedicalHistoryId(medicalHistoryId));
    }

    /**
     * 获取病史详情
     * @param patientPhone
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:query')")
    @GetMapping(value = "/getByPatientPhone/{patientPhone}")
    public AjaxResult getInfoByPatientPhone(@PathVariable("patientPhone") String patientPhone) throws Exception {
        String encrypt = aesUtils.encrypt(patientPhone);
        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(encrypt);
        MedicalHistoryDto medicalHistoryDto = new MedicalHistoryDto();
        if(medicalHistory!=null){
            medicalHistoryDto.setMedicalHistoryId(medicalHistory.getMedicalHistoryId());
            medicalHistoryDto.setHeight(medicalHistory.getHeight());
            medicalHistoryDto.setWeight(medicalHistory.getWeight());
            medicalHistoryDto.setPastMedicalHistory(medicalHistory.getPastMedicalHistory());
            medicalHistoryDto.setLivingHabit(medicalHistory.getLivingHabit());
            medicalHistoryDto.setPatientPhone(aesUtils.decrypt(medicalHistory.getPatientPhone()));
            Patient patient = patientService.selectPatientByPatientPhone(encrypt);
            if(patient!=null){
                medicalHistoryDto.setGender(patient.getPatientSex());
                medicalHistoryDto.setUserName(aesUtils.decrypt(patient.getPatientName()));
                medicalHistoryDto.setHospital(patient.getPatientSource());
            }
        }

        //病种和id映射
        HashMap<String, String> medicalHashMap = new HashMap<>();
        MedicalData medicalData=new MedicalData();
        List<MedicalData> medicalData1 = medicalDataService.selectMedicalDataList(medicalData);
        StringBuilder med= new StringBuilder();
        for (MedicalData data : medicalData1) {
            medicalHashMap.put(data.getMedicalCode().toString(),data.getMedicalName());
        }

        if(medicalHistory!=null && medicalHistory.getPastMedicalHistory()!=null){
            String[] split = medicalHistory.getPastMedicalHistory().split(",");
            for (String s : split) {
                if(med.toString().equals("")){
                    med.append(medicalHashMap.get(s));
                }
                else{
                    med.append(", ").append(medicalHashMap.get(s));
                }

            }
            medicalHistoryDto.setM_data(String.valueOf(med));
        }
        else{
            medicalHistoryDto.setM_data(String.valueOf(med));
        }
        //获取出生日期
        AppData appData = appDataService.selectAppDataByPatientPhone(encrypt);
        if(appData!=null){
            medicalHistoryDto.setBirthDay(appData.getBirthDay());
        }

        return AjaxResult.success(medicalHistoryDto);
    }

    /**
     * 新增病史
     */
//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:add')")
    @Log(title = "病史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory.getPatientPhone()!=null&&!"".equals(medicalHistory.getPatientPhone())){
            medicalHistory.setPatientPhone(aesUtils.encrypt(medicalHistory.getPatientPhone()));
        }

        return toAjax(medicalHistoryService.insertMedicalHistory(medicalHistory));
    }


//    @Log(title = "病史", businessType = BusinessType.INSERT)
    @PostMapping("addQuestionnaireRegister")
    public AjaxResult addQuestionnaireRegister(@RequestBody MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory.getPatientPhone()!=null&&!"".equals(medicalHistory.getPatientPhone())){
            medicalHistory.setPatientPhone(aesUtils.encrypt(medicalHistory.getPatientPhone()));
        }

        return toAjax(medicalHistoryService.insertMedicalHistory(medicalHistory));
    }

    /**
     * 修改病史
     */
//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:edit')")
    @Log(title = "病史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MedicalHistoryDto medicalHistory) throws Exception {
        if (medicalHistory.getPatientPhone()!=null&&!"".equals(medicalHistory.getPatientPhone())){
            medicalHistory.setPatientPhone(aesUtils.encrypt(medicalHistory.getPatientPhone()));
        }
        if (medicalHistory.getUserName()!=null&&!"".equals(medicalHistory.getUserName())){
            medicalHistory.setUserName(aesUtils.encrypt(medicalHistory.getUserName()));
        }
        System.out.println(medicalHistory);
        Patient patient = patientService.selectPatientByPatientPhone(medicalHistory.getPatientPhone());
        AppData appData = appDataService.selectAppDataByPatientPhone(medicalHistory.getPatientPhone());
        patient.setPatientSex(medicalHistory.getGender());
        patient.setPatientName(medicalHistory.getUserName());
        patient.setBirthDay(medicalHistory.getBirthDay());
        if (medicalHistory.getHospital()!=null){
            patient.setPatientSource(medicalHistory.getHospital());
        }
        if (medicalHistory.getFamilyPhone()!=null&&!"".equals(medicalHistory.getFamilyPhone())){
            medicalHistory.setFamilyPhone(aesUtils.encrypt(medicalHistory.getFamilyPhone()));
        }
        patient.setFamilyName(appData.getFamilyName());
        patient.setFamilyPhone(appData.getFamilyPhone());
        patientService.updatePatient(patient);
        if(appData!=null)
        {
            appData.setBirthDay(medicalHistory.getBirthDay());
            appData.setPatientName(medicalHistory.getUserName());
            appDataService.updateAppData(appData);
        }
        return toAjax(medicalHistoryService.updateMedicalHistory(medicalHistory));
    }

    /**
     * 删除病史
     */
//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:remove')")
    @Log(title = "病史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{medicalHistoryIds}")
    public AjaxResult remove(@PathVariable Long[] medicalHistoryIds)
    {
        return toAjax(medicalHistoryService.deleteMedicalHistoryByMedicalHistoryIds(medicalHistoryIds));
    }

//    @PreAuthorize("@ss.hasPermi('medicalHistory:medicalHistory:remove')")
    @Log(title = "病史", businessType = BusinessType.DELETE)
    @GetMapping("delByPatientPhone/{patientPhone}")
    public AjaxResult remove(@PathVariable("patientPhone") String patientPhone)
    {
        return toAjax(medicalHistoryService.deleteMedicalHistoryByPatientPhone(patientPhone));
    }
}
