package com.ruoyi.xindian.hospital.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/hospital/hospital")
public class HospitalController extends BaseController
{
    @Autowired
    private IHospitalService hospitalService;

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 查询医院列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospital:list')")
    @GetMapping("/list")
    public TableDataInfo list(Hospital hospital)
    {
        startPage();
        List<Hospital> list = hospitalService.selectHospitalList(hospital);
        for (Hospital hospital1 : list) {
            hospital1.setPrice("0");
        }
        return getDataTable(list);
    }

    /**
     * 导出医院列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:export')")
    @Log(title = "医院", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Hospital hospital)
    {
        List<Hospital> list = hospitalService.selectHospitalList(hospital);
        ExcelUtil<Hospital> util = new ExcelUtil<Hospital>(Hospital.class);
        util.exportExcel(response, list, "医院数据");
    }

    /**
     * 获取医院详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:query')")
    @GetMapping(value = "/{hospitalId}")
    public AjaxResult getInfo(@PathVariable("hospitalId") Long hospitalId)
    {
        return AjaxResult.success(hospitalService.selectHospitalByHospitalId(hospitalId));
    }

    /**
     * 通过hospitalCode获取医院详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:query')")
    @GetMapping(value = "/getInfoByHospitalCode/{hospitalCode}")
    public AjaxResult getInfoByHospitalCode(@PathVariable("hospitalCode") String hospitalCode)
    {
        return AjaxResult.success(hospitalService.selectHospitalByHospitalCode(hospitalCode));
    }

    /**
     * 新增医院
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:add')")
    @Log(title = "医院", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Hospital hospital)
    {
        Hospital hospital1 = hospitalService.selectCode(hospital.getHospitalName());
        if (hospital1!=null){
            return AjaxResult.error("医院名称已经存在");
        }
        Hospital hospital2 = hospitalService.selectHospitalByHospitalCode(hospital.getHospitalCode());
        if (hospital2!=null){
            return AjaxResult.error("医院代号已经存在");
        }
        return toAjax(hospitalService.insertHospital(hospital));
    }

    /**
     * 修改医院
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:edit')")
    @Log(title = "医院", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Hospital hospital)
    {
        return toAjax(hospitalService.updateHospital(hospital));
    }

    /**
     * 删除医院
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospital:remove')")
    @Log(title = "医院", businessType = BusinessType.DELETE)
    @DeleteMapping("/{hospitalIds}")
    public AjaxResult remove(@PathVariable Long[] hospitalIds)
    {
        return toAjax(hospitalService.deleteHospitalByHospitalIds(hospitalIds));
    }


    public List<Hospital> getAllHospital(Hospital hospital)
    {
        return hospitalService.selectHospitalList(hospital);
    }

    @GetMapping("/addDict")
    public AjaxResult addDict() {
        SysDictData dict = new SysDictData();
        dict.setDictType("hospital_name_list");
        dict.setListClass("default");
        dict.setCreateBy("admin");
        Hospital hospital = new Hospital();
        List<Hospital> allHospital = getAllHospital(hospital);
        int i = 0;
        for (Hospital hospital1 : allHospital) {
            dict.setDictSort((long) i);
            dict.setDictLabel(hospital1.getHospitalName());
            dict.setDictValue(hospital1.getHospitalCode());
            List<SysDictData> sysDictData = dictDataService.selectDictDataList(dict);
            if (sysDictData.isEmpty()) {
                dictDataService.insertDictData(dict);
            }
            i = i +1;
        }
        return AjaxResult.success("down");
    }

}
