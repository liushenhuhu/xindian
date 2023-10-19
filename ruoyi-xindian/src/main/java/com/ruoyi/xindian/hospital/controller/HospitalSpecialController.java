package com.ruoyi.xindian.hospital.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 查询医院专科表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:list')")
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
    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:export')")
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
    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hospitalSpecialService.selectHospitalSpecialById(id));
    }

    /**
     * 新增医院专科表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:add')")
    @Log(title = "医院专科表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalSpecial hospitalSpecial)
    {
        return toAjax(hospitalSpecialService.insertHospitalSpecial(hospitalSpecial));
    }

    /**
     * 修改医院专科表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:edit')")
    @Log(title = "医院专科表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalSpecial hospitalSpecial)
    {
        return toAjax(hospitalSpecialService.updateHospitalSpecial(hospitalSpecial));
    }

    /**
     * 删除医院专科表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalSpecial:remove')")
    @Log(title = "医院专科表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hospitalSpecialService.deleteHospitalSpecialByIds(ids));
    }


    /**
     * 获取所有的专科列表
     * @return
     */
    @GetMapping("/getSpecialList")
    public AjaxResult getSpecialList(){
        return AjaxResult.success(hospitalSpecialService.selectHospitalSpecialList(new HospitalSpecial()));
    }
}
