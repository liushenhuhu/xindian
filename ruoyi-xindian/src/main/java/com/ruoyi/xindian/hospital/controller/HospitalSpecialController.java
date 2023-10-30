package com.ruoyi.xindian.hospital.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xindian.hospital.domain.HospitalOutpatient;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientService;
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
import com.ruoyi.xindian.hospital.domain.HospitalSpecial;
import com.ruoyi.xindian.hospital.service.IHospitalSpecialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院专科表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalSpecial")
public class HospitalSpecialController extends BaseController
{
    @Autowired
    private IHospitalSpecialService hospitalSpecialService;


    @Resource
    private IHospitalOutpatientService hospitalOutpatientService;

    /**
     * 查询医院专科表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalSpecial hospitalSpecial)
    {
        startPage();
        List<HospitalSpecial> list = hospitalSpecialService.selectHospitalSpecialList(hospitalSpecial);
        return getDataTable(list);
    }

    /**
     * 导出医院专科表列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:export')")
    @Log(title = "医院专科表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalSpecial hospitalSpecial)
    {
        List<HospitalSpecial> list = hospitalSpecialService.selectHospitalSpecialList(hospitalSpecial);
        ExcelUtil<HospitalSpecial> util = new ExcelUtil<HospitalSpecial>(HospitalSpecial.class);
        util.exportExcel(response, list, "医院专科表数据");
    }

    /**
     * 获取医院专科表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalSpecialService.selectHospitalSpecialById(id));
    }

    /**
     * 新增医院专科表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:add')")
    @Log(title = "医院专科表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalSpecial hospitalSpecial)
    {
        if (StringUtils.isEmpty(hospitalSpecial.getSpecialName())){
            return AjaxResult.error("请填写专科名称");
        }
        HospitalSpecial hospitalSpecialName = hospitalSpecialService.getHospitalSpecialName(hospitalSpecial.getSpecialName());
        if (hospitalSpecialName!=null){
            return AjaxResult.error("专科名称已存在");
        }
        return toAjax(hospitalSpecialService.insertHospitalSpecial(hospitalSpecial));
    }

    /**
     * 修改医院专科表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:edit')")
    @Log(title = "医院专科表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalSpecial hospitalSpecial)
    {  if (StringUtils.isEmpty(hospitalSpecial.getSpecialName())){
            return AjaxResult.error("请填写专科名称");
        }
        HospitalSpecial hospitalSpecial1 = hospitalSpecialService.selectHospitalSpecialById(hospitalSpecial.getSpecialId());
        if (hospitalSpecial1==null){
            return AjaxResult.error("专科不存在");
        }
        if (!hospitalSpecial1.getSpecialName().equals(hospitalSpecial.getSpecialName())){
            HospitalSpecial hospitalSpecialName = hospitalSpecialService.getHospitalSpecialName(hospitalSpecial.getSpecialName());
            if (hospitalSpecialName!=null){
                return AjaxResult.error("专科名称已存在");
            }
        }
        return toAjax(hospitalSpecialService.updateHospitalSpecial(hospitalSpecial));
    }

    /**
     * 删除医院专科表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:remove')")
    @Log(title = "医院专科表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids){
            HospitalOutpatient hospitalOutpatient = new HospitalOutpatient();
            hospitalOutpatient.setSpecialId(id);
            List<HospitalOutpatient> hospitalOutpatients = hospitalOutpatientService.selectHospitalOutpatientList(hospitalOutpatient);
            if (hospitalOutpatients!=null&&hospitalOutpatients.size()>0){
                return AjaxResult.error("当前专科已有门诊，不能删除");
            }
        }
        return toAjax(hospitalSpecialService.deleteHospitalSpecialByIds(ids));
    }


    /**
     * 获取所有的专科列表
     * @return
     */
    @GetMapping("/getSpecialList")
    public AjaxResult getSpecialList(HospitalSpecial hospitalSpecial){
        return AjaxResult.success(hospitalSpecialService.selectHospitalSpecialList(hospitalSpecial));
    }
}
