package com.ruoyi.xindian.hospital.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xindian.hospital.domain.*;
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
import com.ruoyi.xindian.hospital.service.IHospitalSpecialRelationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关系表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalSpecialRelation")
public class HospitalSpecialRelationController extends BaseController
{
    @Autowired
    private IHospitalSpecialRelationService hospitalSpecialRelationService;


    @Resource
    private IDoctorService doctorService;

    @Resource
    private IHospitalService hospitalService;
    /**
     * 查询关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecialRelation:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalSpecialRelation hospitalSpecialRelation)
    {
        startPage();
        List<HospitalSpecialRelation> list = hospitalSpecialRelationService.selectHospitalSpecialRelationList(hospitalSpecialRelation);
        return getDataTable(list);
    }

    /**
     * 导出关系表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecialRelation:export')")
    @Log(title = "关系表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalSpecialRelation hospitalSpecialRelation)
    {
        List<HospitalSpecialRelation> list = hospitalSpecialRelationService.selectHospitalSpecialRelationList(hospitalSpecialRelation);
        ExcelUtil<HospitalSpecialRelation> util = new ExcelUtil<HospitalSpecialRelation>(HospitalSpecialRelation.class);
        util.exportExcel(response, list, "关系表数据");
    }

    /**
     * 获取关系表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecialRelation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalSpecialRelationService.selectHospitalSpecialRelationById(id));
    }

    /**
     * 新增关系表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecialRelation:add')")
    @Log(title = "关系表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalSpecialRelation hospitalSpecialRelation)
    {
        return toAjax(hospitalSpecialRelationService.insertHospitalSpecialRelation(hospitalSpecialRelation));
    }

    /**
     * 修改关系表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecialRelation:edit')")
    @Log(title = "关系表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalSpecialRelation hospitalSpecialRelation)
    {
        return toAjax(hospitalSpecialRelationService.updateHospitalSpecialRelation(hospitalSpecialRelation));
    }

    /**
     * 删除关系表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecialRelation:remove')")
    @Log(title = "关系表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids){
            HospitalSpecialRelation hospitalSpecialRelation = hospitalSpecialRelationService.selectHospitalSpecialRelationById(id);

            Doctor doctor = new Doctor();
            Hospital hospital = hospitalService.selectHospitalByHospitalId(hospitalSpecialRelation.getHospitalId());
            doctor.getHospitalNameList().add(hospital.getHospitalName());
            doctor.setSpecialId(hospitalSpecialRelation.getSpecialId());
            List<Doctor> doctors = doctorService.selectDoctorListNot(doctor);
            if (doctors!=null&&doctors.size()>0){
                return AjaxResult.error("该门诊下绑定有医生，无法删除");
            }

        }

        return toAjax(hospitalSpecialRelationService.deleteHospitalSpecialRelationByIds(ids));
    }

    @GetMapping("/getHospitalSpecialList")
    public AjaxResult getHospitalOutpatientList(HospitalSpecialRelation hospitalSpecialRelation){
        List<HospitalSpecialRelation> list = hospitalSpecialRelationService.selectHospitalSpecialRelationList(hospitalSpecialRelation);
        Map<String,String> map = new HashMap<>();
        Iterator<HospitalSpecialRelation> iterator = list.iterator();
        while (iterator.hasNext()){
            HospitalSpecialRelation next = iterator.next();
            if (map.containsKey(next.getHospitalSpecial().getSpecialName())){
                iterator.remove();
            }else {
                map.put(next.getHospitalSpecial().getSpecialName(),next.getHospitalSpecial().getSpecialName());
            }
        }
        return AjaxResult.success(list);
    }
}
