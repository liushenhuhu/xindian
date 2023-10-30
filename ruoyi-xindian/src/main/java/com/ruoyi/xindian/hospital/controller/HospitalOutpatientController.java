package com.ruoyi.xindian.hospital.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.service.IDoctorService;
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
import com.ruoyi.xindian.hospital.domain.HospitalOutpatient;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院门诊表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalOutpatient")
public class HospitalOutpatientController extends BaseController
{
    @Autowired
    private IHospitalOutpatientService hospitalOutpatientService;


    @Resource
    private IDoctorService doctorService;

    /**
     * 查询医院门诊表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalOutpatient hospitalOutpatient)
    {
        startPage();
        List<HospitalOutpatient> list = hospitalOutpatientService.selectHospitalOutpatientList(hospitalOutpatient);
        return getDataTable(list);
    }

    /**
     * 导出医院门诊表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:export')")
    @Log(title = "医院门诊表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalOutpatient hospitalOutpatient)
    {
        List<HospitalOutpatient> list = hospitalOutpatientService.selectHospitalOutpatientList(hospitalOutpatient);
        ExcelUtil<HospitalOutpatient> util = new ExcelUtil<HospitalOutpatient>(HospitalOutpatient.class);
        util.exportExcel(response, list, "医院门诊表数据");
    }

    /**
     * 获取医院门诊表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalOutpatientService.selectHospitalOutpatientById(id));
    }

    /**
     * 新增医院门诊表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:add')")
    @Log(title = "医院门诊表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalOutpatient hospitalOutpatient)
    {

        if (StringUtils.isEmpty(hospitalOutpatient.getOutpatientName())||hospitalOutpatient.getSpecialId()==null){
            return AjaxResult.error("参数错误，请重新提交");
        }

        HospitalOutpatient outNameAndSpec = hospitalOutpatientService.getOutNameAndSpec(hospitalOutpatient);
        if (outNameAndSpec!=null){
            return AjaxResult.error("门诊已存在");
        }

        return toAjax(hospitalOutpatientService.insertHospitalOutpatient(hospitalOutpatient));
    }

    /**
     * 修改医院门诊表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:edit')")
    @Log(title = "医院门诊表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalOutpatient hospitalOutpatient)
    {
        if (StringUtils.isEmpty(hospitalOutpatient.getOutpatientName())||hospitalOutpatient.getSpecialId()==null){
            return AjaxResult.error("参数错误，请稍后提交");
        }

        HospitalOutpatient hospitalOutpatient1 = hospitalOutpatientService.selectHospitalOutpatientById(hospitalOutpatient.getOutpatientId());
        if (hospitalOutpatient1==null){
            return AjaxResult.error("参数错误，请稍后提交");
        }
        if (!hospitalOutpatient1.getOutpatientName().equals(hospitalOutpatient.getOutpatientName())&&!hospitalOutpatient1.getSpecialId().equals(hospitalOutpatient.getSpecialId())){
            HospitalOutpatient outNameAndSpec = hospitalOutpatientService.getOutNameAndSpec(hospitalOutpatient);
            if (outNameAndSpec!=null){
                return AjaxResult.error("门诊已存在");
            }
        }

        return toAjax(hospitalOutpatientService.updateHospitalOutpatient(hospitalOutpatient));
    }

    /**
     * 删除医院门诊表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatient:remove')")
    @Log(title = "医院门诊表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids){
            Doctor doctor = new Doctor();
            doctor.setOutpatientId(id);
            List<Doctor> doctors = doctorService.selectDoctorList(doctor);
            if (doctors!=null&&doctors.size()>0){
                return AjaxResult.error("该门诊下绑定有医生，无法删除");
            }

        }


        return toAjax(hospitalOutpatientService.deleteHospitalOutpatientByIds(ids));
    }


    @GetMapping("/getOutpatientList")
    public AjaxResult getOutpatientList(HospitalOutpatient hospitalOutpatient){
        return AjaxResult.success(hospitalOutpatientService.selectHospitalOutpatientList(hospitalOutpatient));
    }
}
