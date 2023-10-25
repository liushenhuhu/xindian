package com.ruoyi.xindian.hospital.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
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
import com.ruoyi.xindian.hospital.domain.VisitAppointment;
import com.ruoyi.xindian.hospital.service.IVisitAppointmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出诊预约表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/visitAppointment")
public class VisitAppointmentController extends BaseController
{
    @Autowired
    private IVisitAppointmentService visitAppointmentService;


    @Resource
    private AesUtils aesUtils;

    /**
     * 查询出诊预约表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisitAppointment visitAppointment)
    {
        startPage();
        List<VisitAppointment> list = visitAppointmentService.selectVisitAppointmentList(visitAppointment);
        return getDataTable(list);
    }

    /**
     * 导出出诊预约表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:export')")
    @Log(title = "出诊预约表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitAppointment visitAppointment)
    {
        List<VisitAppointment> list = visitAppointmentService.selectVisitAppointmentList(visitAppointment);
        ExcelUtil<VisitAppointment> util = new ExcelUtil<VisitAppointment>(VisitAppointment.class);
        util.exportExcel(response, list, "出诊预约表数据");
    }

    /**
     * 获取出诊预约表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(visitAppointmentService.selectVisitAppointmentById(id));
    }

    /**
     * 新增出诊预约表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:add')")
    @Log(title = "出诊预约表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitAppointment visitAppointment)
    {
        return toAjax(visitAppointmentService.insertVisitAppointment(visitAppointment));
    }

    /**
     * 修改出诊预约表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:edit')")
    @Log(title = "出诊预约表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitAppointment visitAppointment)
    {
        return toAjax(visitAppointmentService.updateVisitAppointment(visitAppointment));
    }

    /**
     * 删除出诊预约表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:remove')")
    @Log(title = "出诊预约表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visitAppointmentService.deleteVisitAppointmentByIds(ids));
    }


    /**
     * app获取出诊预约人
     * @param planId
     * @return
     * @throws Exception
     */
    @GetMapping("/getAppointmentByPlanId")
    public AjaxResult getAppointmentByPlanId(Long planId) throws Exception {
        List<VisitAppointment> visitAppointments = visitAppointmentService.selectByPlanId(planId);
        for (VisitAppointment visitAppointment : visitAppointments){
            if (StringUtils.isNotEmpty(visitAppointment.getAccompanyPhone())){
                visitAppointment.setAccompanyPhone(aesUtils.decrypt(visitAppointment.getAccompanyPhone()));
            }
            if (StringUtils.isNotEmpty(visitAppointment.getPatientPhone())){
                visitAppointment.setPatientPhone(aesUtils.decrypt(visitAppointment.getPatientPhone()));
            }
        }
        return AjaxResult.success(visitAppointments);
    }
}
