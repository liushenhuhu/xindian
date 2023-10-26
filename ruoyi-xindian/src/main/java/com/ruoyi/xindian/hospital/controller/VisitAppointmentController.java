package com.ruoyi.xindian.hospital.controller;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.hospital.vo.PlanMsgAllVo;
import com.ruoyi.xindian.util.DateUtil;
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


    private final Lock lock = new ReentrantLock();
    @Resource
    private AesUtils aesUtils;

    /**
     * 查询出诊预约表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(VisitAppointment visitAppointment) throws Exception {

        visitAppointmentAesEncrypt(visitAppointment);
        startPage();
        List<VisitAppointment> list = visitAppointmentService.selectVisitAppointmentList(visitAppointment);
        for (VisitAppointment visitAppointment1 : list){
            visitAppointmentAesDecrypt(visitAppointment1);
        }
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
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) throws Exception {
        VisitAppointment visitAppointment = visitAppointmentService.selectVisitAppointmentById(id);
        visitAppointmentAesDecrypt(visitAppointment);
        return AjaxResult.success();
    }

    /**
     * 新增出诊预约表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:add')")
    @Log(title = "出诊预约表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitAppointment visitAppointment) throws Exception {
        visitAppointmentAesEncrypt(visitAppointment);
        return toAjax(visitAppointmentService.insertVisitAppointment(visitAppointment));
    }

    /**
     * 修改出诊预约表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:edit')")
    @Log(title = "出诊预约表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitAppointment visitAppointment) throws Exception {visitAppointmentAesEncrypt(visitAppointment);
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
            visitAppointmentAesDecrypt(visitAppointment);
        }
        return AjaxResult.success(visitAppointments);
    }


    @PostMapping("/addAppointment")
    public AjaxResult addAppointment(PlanMsgAllVo planMsgAllVo, HttpServletRequest request) throws Exception {


        lock.lock();
        try {
            if (StringUtils.isEmpty(planMsgAllVo.getPatientPhone())){
                return AjaxResult.error("患者手机号错误");
            }
            if (planMsgAllVo.getPlanId()==null||planMsgAllVo.getPlanId().equals("")){
                return AjaxResult.error("排班信息错误");
            }
            VisitAppointment visitAppointment = visitAppointmentService.addVisitAppointment(planMsgAllVo, request);
            visitAppointmentAesDecrypt(visitAppointment);
            return AjaxResult.success(visitAppointment);
        }catch (Exception e){
            return AjaxResult.error("创建订单失败");
        }finally {
            lock.unlock();
        }

    }


    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(String appointmentId){
        visitAppointmentService.updateVisitAppointmentStatus(appointmentId,"2");
        return AjaxResult.success();
    }



    private void visitAppointmentAesEncrypt(VisitAppointment visitAppointment) throws Exception {

        if (StringUtils.isNotEmpty(visitAppointment.getPatientPhone())){
            visitAppointment.setPatientPhone(aesUtils.encrypt(visitAppointment.getPatientPhone()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getAccompanyPhone())){
            visitAppointment.setAccompanyPhone(aesUtils.encrypt(visitAppointment.getAccompanyPhone()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getDoctorName())){
            visitAppointment.setDoctorName(aesUtils.encrypt(visitAppointment.getDoctorName()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getDoctorPhone())){
            visitAppointment.setDoctorPhone(aesUtils.encrypt(visitAppointment.getDoctorPhone()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getPatientName())){
            visitAppointment.setPatientName(aesUtils.encrypt(visitAppointment.getPatientName()));
        }
    }


    private void visitAppointmentAesDecrypt(VisitAppointment visitAppointment) throws Exception {

        if (StringUtils.isNotEmpty(visitAppointment.getPatientPhone())){
            visitAppointment.setPatientPhone(aesUtils.decrypt(visitAppointment.getPatientPhone()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getPatientName())){
            visitAppointment.setPatientName(aesUtils.decrypt(visitAppointment.getPatientName()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getAccompanyPhone())){
            visitAppointment.setAccompanyPhone(aesUtils.decrypt(visitAppointment.getAccompanyPhone()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getDoctorName())){
            visitAppointment.setDoctorName(aesUtils.decrypt(visitAppointment.getDoctorName()));
        }
        if (StringUtils.isNotEmpty(visitAppointment.getDoctorPhone())){
            visitAppointment.setDoctorPhone(aesUtils.decrypt(visitAppointment.getDoctorPhone()));
        }
        if (visitAppointment.getBirthBay()!=null){
            visitAppointment.setPatientAge(DateUtil.getAge(visitAppointment.getBirthBay()));
        }
    }
}
