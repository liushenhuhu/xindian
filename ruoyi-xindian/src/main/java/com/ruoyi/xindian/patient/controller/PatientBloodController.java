package com.ruoyi.xindian.patient.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.patient.domain.PatientBlood;
import com.ruoyi.xindian.patient.service.PatientBloodService;
import org.apache.poi.util.StringUtil;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【患者血压】Controller
 * 
 * @author ruoyi
 * @date 2023-11-16
 */
@RestController
@RequestMapping("/blood/blood")
public class PatientBloodController extends BaseController
{
    @Autowired
    private PatientBloodService patientBloodService;

    /**
     * 查询【患者血压】列表
     */
    @PreAuthorize("@ss.hasPermi('blood:blood:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientBlood patientBlood) throws Exception {
        startPage();
        List<PatientBlood> list = patientBloodService.selectPatientBloodList(patientBlood);
        return getDataTable(list);
    }


    /**
     * 查询列表
     * @param patientBlood
     * @return
     * @throws Exception
     */
    @GetMapping("/getAppBloodList")
    public AjaxResult getAppBloodList(PatientBlood patientBlood) throws Exception {
        return AjaxResult.success(patientBloodService.selectPatientBloodList(patientBlood));
    }

    /**
     * 做统计列表
     * @param patientBlood
     * @return
     * @throws Exception
     */
    @GetMapping("/getAppBloodStatisticsList")
    public AjaxResult getAppBloodStatisticsList(PatientBlood patientBlood) throws Exception {
        return AjaxResult.success(patientBloodService.selectPatientBloodStatistics(patientBlood));
    }

    /**
     * 导出【患者血压】列表
     */
    @PreAuthorize("@ss.hasPermi('blood:blood:export')")
    @Log(title = "【患者血压】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PatientBlood patientBlood) throws Exception {
        List<PatientBlood> list = patientBloodService.selectPatientBloodList(patientBlood);
        ExcelUtil<PatientBlood> util = new ExcelUtil<PatientBlood>(PatientBlood.class);
        util.exportExcel(response, list, "【患者血压】数据");
    }

    /**
     * 获取【患者血压】详细信息
     */
    @PreAuthorize("@ss.hasPermi('blood:blood:query')")
    @GetMapping(value = "/{bloodId}")
    public AjaxResult getInfo(@PathVariable("bloodId") String bloodId) throws Exception {
        return AjaxResult.success(patientBloodService.selectPatientBloodByBloodId(bloodId));
    }

    /**
     * 新增【患者血压】
     */
    @Log(title = "【患者血压】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatientBlood patientBlood)
    {
        return toAjax(patientBloodService.insertPatientBlood(patientBlood));
    }

    /**
     * 修改【患者血压】
     */
    @PreAuthorize("@ss.hasPermi('blood:blood:edit')")
    @Log(title = "【患者血压】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PatientBlood patientBlood)
    {
        return toAjax(patientBloodService.updatePatientBlood(patientBlood));
    }

    /**
     * 删除【患者血压】
     */
    @PreAuthorize("@ss.hasPermi('blood:blood:remove')")
    @Log(title = "【患者血压】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bloodIds}")
    public AjaxResult remove(@PathVariable String[] bloodIds)
    {
        return toAjax(patientBloodService.deletePatientBloodByBloodIds(bloodIds));
    }
}
