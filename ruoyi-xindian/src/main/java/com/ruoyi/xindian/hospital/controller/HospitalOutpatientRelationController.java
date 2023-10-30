package com.ruoyi.xindian.hospital.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.domain.HospitalSpecialRelation;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
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
import com.ruoyi.xindian.hospital.domain.HospitalOutpatientRelation;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientRelationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院门诊关系表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalOutpatientRelation")
public class HospitalOutpatientRelationController extends BaseController
{
    @Autowired
    private IHospitalOutpatientRelationService hospitalOutpatientRelationService;


    @Resource
    private IDoctorService doctorService;

    @Resource
    private IHospitalService hospitalService;
    /**
     * 查询医院门诊关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        startPage();
        List<HospitalOutpatientRelation> list = hospitalOutpatientRelationService.selectHospitalOutpatientRelationList(hospitalOutpatientRelation);
        return getDataTable(list);
    }

    /**
     * 导出医院门诊关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:export')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        List<HospitalOutpatientRelation> list = hospitalOutpatientRelationService.selectHospitalOutpatientRelationList(hospitalOutpatientRelation);
        ExcelUtil<HospitalOutpatientRelation> util = new ExcelUtil<HospitalOutpatientRelation>(HospitalOutpatientRelation.class);
        util.exportExcel(response, list, "医院门诊关系表数据");
    }

    /**
     * 获取医院门诊关系表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalOutpatientRelationService.selectHospitalOutpatientRelationById(id));
    }

    /**
     * 新增医院门诊关系表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:add')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        return toAjax(hospitalOutpatientRelationService.insertHospitalOutpatientRelation(hospitalOutpatientRelation));
    }

    /**
     * 修改医院门诊关系表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:edit')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalOutpatientRelation hospitalOutpatientRelation)
    {
        return toAjax(hospitalOutpatientRelationService.updateHospitalOutpatientRelation(hospitalOutpatientRelation));
    }

    /**
     * 删除医院门诊关系表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalOutpatientRelation:remove')")
    @Log(title = "医院门诊关系表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {

        for (Long id : ids){
            HospitalOutpatientRelation hospitalOutpatientRelation = hospitalOutpatientRelationService.selectHospitalOutpatientRelationById(id);
            Doctor doctor = new Doctor();
            Hospital hospital = hospitalService.selectHospitalByHospitalId(hospitalOutpatientRelation.getHospitalId());
            doctor.getHospitalNameList().add(hospital.getHospitalName());
            doctor.setOutpatientId(hospitalOutpatientRelation.getOutpatientId());
            List<Doctor> doctors = doctorService.selectDoctorList(doctor);
            if (doctors!=null&&doctors.size()>0){
                return AjaxResult.error("该门诊下绑定有医生，无法删除");
            }

        }
        return toAjax(hospitalOutpatientRelationService.deleteHospitalOutpatientRelationByIds(ids));
    }

    @GetMapping("/getHospitalOutpatientList")
    public AjaxResult getHospitalOutpatientList(HospitalOutpatientRelation hospitalOutpatientRelation){
        List<HospitalOutpatientRelation> list = hospitalOutpatientRelationService.selectHospitalOutpatientRelationList(hospitalOutpatientRelation);
        Map<String,String> map = new HashMap<>();
        Iterator<HospitalOutpatientRelation> iterator = list.iterator();
        while (iterator.hasNext()){
            HospitalOutpatientRelation next = iterator.next();
            if (map.containsKey(next.getHospitalOutpatient().getOutpatientName())){
                iterator.remove();
            }else {
                map.put(next.getHospitalOutpatient().getOutpatientName(),next.getHospitalOutpatient().getOutpatientName());
            }
        }
        return AjaxResult.success(list);
    }
}
