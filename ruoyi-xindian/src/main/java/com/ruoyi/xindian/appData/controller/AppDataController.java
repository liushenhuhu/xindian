package com.ruoyi.xindian.appData.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.vo.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * app相关数据Controller
 *
 * @author hanhan
 * @date 2022-10-06
 */
@RestController
@RequestMapping("/appData/appData")
public class AppDataController extends BaseController {
    @Autowired
    private IAppDataService appDataService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;


    @Resource
    private AesUtils aesUtils;
    /**
     * 查询app相关数据列表
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppData appData) throws Exception {
        if (appData.getUserName()!=null&&!"".equals(appData.getUserName())){
            appData.setUserName(aesUtils.encrypt(appData.getUserName()));
        }
        if (appData.getPatientPhone()!=null&&!"".equals(appData.getPatientPhone())){
            appData.setPatientPhone(aesUtils.encrypt(appData.getPatientPhone()));
        }
        if (appData.getPatientName()!=null&&!"".equals(appData.getPatientName())){
            appData.setPatientName(aesUtils.encrypt(appData.getPatientName()));
        }
        startPage();
        List<AppData> list = appDataService.selectAppDataList(appData);
        for (AppData c :list){
            if (c.getUserName()!=null&&!"".equals(c.getUserName())){
                c.setUserName(aesUtils.decrypt(c.getUserName()));
            }
            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
                c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
            }
            if (c.getPatientName()!=null&&!"".equals(c.getPatientName())){
                c.setPatientName(aesUtils.decrypt(c.getPatientName()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 查询app相关数据列表
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:list')")
    @PostMapping("/list1")
    public TableDataInfo list1(@RequestBody AppData appData) throws Exception {
        if (appData.getUserName()!=null&&!"".equals(appData.getUserName())){
            appData.setUserName(aesUtils.encrypt(appData.getUserName()));
        }
        if (appData.getPatientPhone()!=null&&!"".equals(appData.getPatientPhone())){
            appData.setPatientPhone(aesUtils.encrypt(appData.getPatientPhone()));
        }
        if (appData.getPatientName()!=null&&!"".equals(appData.getPatientName())){
            appData.setPatientName(aesUtils.encrypt(appData.getPatientName()));
        }
        startPage();
        List<AppData> list = appDataService.selectAppDataList(appData);
        for (AppData c :list){
            if (c.getUserName()!=null&&!"".equals(c.getUserName())){
                c.setUserName(aesUtils.decrypt(c.getUserName()));
            }
            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
                c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
            }
            if (c.getPatientName()!=null&&!"".equals(c.getPatientName())){
                c.setPatientName(aesUtils.decrypt(c.getPatientName()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出app相关数据列表
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:export')")
    @Log(title = "app相关数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppData appData) {
        List<AppData> list = appDataService.selectAppDataList(appData);
        ExcelUtil<AppData> util = new ExcelUtil<AppData>(AppData.class);
        util.exportExcel(response, list, "app相关数据数据");
    }

    /**
     * 获取app相关数据详细信息
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:query')")
    @GetMapping(value = "/{appDataId}")
    public AjaxResult getInfo(@PathVariable("appDataId") Long appDataId) throws Exception {
        AppData appData = appDataService.selectAppDataByAppDataId(appDataId);
        if (appData.getUserName()!=null&&!"".equals(appData.getUserName())){
            appData.setUserName(aesUtils.decrypt(appData.getUserName()));
        }
        if (appData.getPatientPhone()!=null&&!"".equals(appData.getPatientPhone())){
            appData.setPatientPhone(aesUtils.decrypt(appData.getPatientPhone()));
        }
        if (appData.getPatientName()!=null&&!"".equals(appData.getPatientName())){
            appData.setPatientName(aesUtils.decrypt(appData.getPatientName()));
        }
        return AjaxResult.success(appData);
    }

    /**
     * 新增app相关数据
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:add')")
    @Log(title = "app相关数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppData appData) throws Exception {

        int res = 0;
        Patient patient = new Patient();
        Patient patientSel = new Patient();
        patient.setPatientNameAes(appData.getPatientName());
        patient.setPatientPhoneAes(appData.getPatientPhone());
        if (appData.getUserName()!=null&&!"".equals(appData.getUserName())){
            appData.setUserName(aesUtils.encrypt(appData.getUserName()));
        }
        if (appData.getPatientPhone()!=null&&!"".equals(appData.getPatientPhone())){
            appData.setPatientPhone(aesUtils.encrypt(appData.getPatientPhone()));
        }
        if (appData.getPatientName()!=null&&!"".equals(appData.getPatientName())){
            appData.setPatientName(aesUtils.encrypt(appData.getPatientName()));
        }
        patientSel.setPatientPhone(appData.getPatientPhone());
        patient.setPatientName(appData.getPatientName());
        patient.setPatientPhone(appData.getPatientPhone());
        patient.setPatientSex(appData.getPatientSex());
        patient.setBirthDay(appData.getBirthDay());
        if(appData.getPatientAge() != null){
            patient.setPatientAge(appData.getPatientAge());
        }

        List<Patient> patientList = patientService.selectPatientList(patientSel);
        if (null == patientList || patientList.size() == 0) {
            Date endDate =new Date();
            // 创建 Calendar 实例并设置原始日期
            Calendar calendar = Calendar.getInstance();
            // 设置日期
            calendar.setTime(endDate);
            // 加一年
            calendar.add(Calendar.YEAR, 1);
            // 获取加一年后的日期
            Date newDate = calendar.getTime();
//            patient.setDetectionTime(newDate);
            patientService.insertPatient(patient);
        } else {
            Patient patient1 = patientService.selectPatientByPatientPhone(patient.getPatientPhone());
            patient.setPatientId(patient1.getPatientId());
            patientService.updatePatient(patient);
        }
        AppData appDataSel = new AppData();
        appDataSel.setPatientPhone(appData.getPatientPhone());
        List<AppData> appDataList = appDataService.selectAppDataList(appDataSel);
        if (null == appDataList || appDataList.size() == 0) {
            res = appDataService.insertAppData(appData);
        } else {
            AppData appData1 = appDataService.selectAppDataByPatientPhone(appData.getPatientPhone());
            appData.setAppDataId(appData1.getAppDataId());
            res = appDataService.updateAppData(appData);
        }
        return toAjax(res);
    }


    @GetMapping("/getPatientByCode")
    public AjaxResult getPatientByCode(Patient patient){
        if(patient.getPatientPhone()==null){
            patient.setPatientPhone(randomGenNum(11));
        }
        return AjaxResult.success(patient);
    }

    /**
     * 随机数
     * @param place 定义随机数的位数
     */
    public String randomGenNum(int place) {
        String base = "123456789";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }

    /**
     * 新增app相关数据
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:add')")
    @Log(title = "app相关数据", businessType = BusinessType.INSERT)
    @PostMapping("/doctor")
    public AjaxResult add_doctor(@RequestBody AppData appData) throws Exception {
        if (appData.getUserName()!=null&&!"".equals(appData.getUserName())){
            appData.setUserName(aesUtils.encrypt(appData.getUserName()));
        }
        if (appData.getPatientPhone()!=null&&!"".equals(appData.getPatientPhone())){
            appData.setPatientPhone(aesUtils.encrypt(appData.getPatientPhone()));
        }
        if (appData.getPatientName()!=null&&!"".equals(appData.getPatientName())){
            appData.setPatientName(aesUtils.encrypt(appData.getPatientName()));
        }

        return toAjax(appDataService.insertAppData(appData));
    }

    /**
     * 修改app相关数据
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:edit')")
    @Log(title = "app相关数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppData appData) throws Exception {
        if (appData.getUserName()!=null&&!"".equals(appData.getUserName())){
            appData.setUserName(aesUtils.encrypt(appData.getUserName()));
        }
        if (appData.getPatientPhone()!=null&&!"".equals(appData.getPatientPhone())){
            appData.setPatientPhone(aesUtils.encrypt(appData.getPatientPhone()));
        }
        if (appData.getPatientName()!=null&&!"".equals(appData.getPatientName())){
            appData.setPatientName(aesUtils.encrypt(appData.getPatientName()));
        }
        return toAjax(appDataService.updateAppData(appData));
    }

    /**
     * 删除app相关数据
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:remove')")
    @Log(title = "app相关数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/doctor/{appDataIds}")
    public AjaxResult remove_doctor(@PathVariable Long[] appDataIds) {
        return toAjax(appDataService.deleteAppDataByAppDataIds(appDataIds));
    }

    /**
     * 删除app相关数据
     */
    @Log(title = "app相关数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{appDataIds}")
    public AjaxResult remove(@PathVariable Long[] appDataIds) {
        int res = 0;
        for (Long appDataId : appDataIds) {
            AppData appData = appDataService.selectAppDataByAppDataId(appDataId);
            res = delByData(appData);
        }
        return toAjax(res);
    }

    /**
     * 删除app相关数据
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:remove')")
    @Log(title = "app相关数据", businessType = BusinessType.DELETE)
    @GetMapping("/del/{appDataIds}")
    public AjaxResult del(@PathVariable String appDataIds) {
        String[] split = appDataIds.split(",");
        Long[] ids = new Long[split.length];
        for (int i = 0; i < split.length; i++) {
            ids[i] = Long.parseLong(split[i]);
        }
        int ans = appDataService.deleteAppDataByAppDataIds(ids);
        return toAjax(ans);
    }

    /**
     * 删除app相关数据（手机号）
     */
//    @PreAuthorize("@ss.hasPermi('appData:appData:remove')")
    @Log(title = "app相关数据", businessType = BusinessType.DELETE)
    @GetMapping("/delByPhone/{patientPhone}")
    public AjaxResult delByPhone(@PathVariable String patientPhone) {
        AppData appData = appDataService.selectAppDataByPatientPhone(patientPhone);
        return toAjax(delByData(appData));
    }

    private int delByData(AppData appData) {
        int res1 = appDataService.deleteAppDataByAppDataId(appData.getAppDataId());
        int res2 = patientService.deletePatientByPatientPhone(appData.getPatientPhone());
        return res1 + res2;
    }


    @GetMapping("/aesCopy")
    public AjaxResult appAesCopy(Limit limit) throws Exception {
        appDataService.appAesCopy(limit);
        return AjaxResult.success();
    }

}
