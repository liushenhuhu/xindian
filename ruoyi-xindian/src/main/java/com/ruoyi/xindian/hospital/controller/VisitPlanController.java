package com.ruoyi.xindian.hospital.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.hospital.domain.*;
import com.ruoyi.xindian.hospital.service.*;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出诊信息表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/visitPlan")
public class VisitPlanController extends BaseController
{
    @Autowired
    private IVisitPlanService visitPlanService;


    @Autowired
    private IHospitalService hospitalService;

    @Resource
    private RedisTemplate<String ,Object> redisTemplate;


    @Autowired
    private AesUtils aesUtils;

    @Resource
    private VisitAllocationService visitAllocationService;


    @Resource
    private IVisitAppointmentService visitAppointmentService;


    @Resource
    private IDoctorService doctorService;

    /**
     * 查询出诊信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:list')")
    @GetMapping("/WebList")
    public TableDataInfo list(VisitPlan visitPlan) throws Exception {
        if (StringUtils.isNotEmpty(visitPlan.getDoctorPhone())){
            visitPlan.setDoctorPhone(aesUtils.encrypt(visitPlan.getDoctorPhone()));
        }
        startPage();
        List<VisitPlan> list = visitPlanService.selectVisitPlanWebList(visitPlan);
        for (VisitPlan value : list){
            if(!StringUtils.isEmpty(value.getDoctor().getDoctorName())){
                value.getDoctor().setDoctorName(aesUtils.decrypt(value.getDoctor().getDoctorName()));
            }
            if(!StringUtils.isEmpty(value.getDoctor().getDoctorPhone())){
                value.getDoctor().setDoctorPhone(aesUtils.decrypt(value.getDoctor().getDoctorPhone()));
            }
            if(!StringUtils.isEmpty(value.getDoctorPhone())){
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
        }
        return getDataTable(list);
    }


    @GetMapping("/getDateDocPlanList")
    public AjaxResult appList(VisitPlan visitPlan) throws Exception {
        if (StringUtils.isNotEmpty(visitPlan.getDoctorPhone())){
            visitPlan.setDoctorPhone(aesUtils.encrypt(visitPlan.getDoctorPhone()));
        }
        Hospital hospital = hospitalService.selectHospitalByHospitalCode(visitPlan.getHospitalCode());

        if (hospital==null){
            return AjaxResult.error("请先选择医院");
        }
        visitPlan.setHospitalId(hospital.getHospitalId());
        List<VisitPlan> list = visitPlanService.selectVisitPlanList(visitPlan);
        for (VisitPlan value:list){

            if(!StringUtils.isEmpty(value.getDoctor().getDoctorName())){
                value.getDoctor().setDoctorName(aesUtils.decrypt(value.getDoctor().getDoctorName()));
            }
            if(!StringUtils.isEmpty(value.getDoctor().getDoctorPhone())){
                value.getDoctor().setDoctorPhone(aesUtils.decrypt(value.getDoctor().getDoctorPhone()));
            }
            if(!StringUtils.isEmpty(value.getDoctorPhone())){
                value.setDoctorPhone(aesUtils.decrypt(value.getDoctorPhone()));
            }
            Map<Object, Object> visitTime;
            if (Boolean.TRUE.equals(redisTemplate.hasKey("visitTime"))){
              visitTime = redisTemplate.opsForHash().entries("visitTime");
            }else {
                visitTime = visitAllocationService.addRedis();
                redisTemplate.opsForHash().putAll("visitTime",visitTime);
            }
            if (value.getTime()==1){
                value.getVisitTimeMap().put("am",visitTime.get("am"));
            }else if (value.getTime()==2){
                value.getVisitTimeMap().put("pm",visitTime.get("pm"));
            }else if (value.getTime()==3){
                value.getVisitTimeMap().putAll(visitTime);
            }
            List<VisitAppointment> visitAppointments = visitAppointmentService.selectByPlanId(value.getPlanId());

            for (VisitAppointment visitAppointment : visitAppointments){
                Iterator<Map.Entry<Object, Object>> iterator = value.getVisitTimeMap().entrySet().iterator();
                while (iterator.hasNext()){

                    Map.Entry<Object, Object> next = iterator.next();
                    List<VisitAllocation> value1 =(List<VisitAllocation>) next.getValue();

                    for (VisitAllocation visitAllocation:value1){

                        if (Objects.equals(visitAppointment.getTimePeriod(), visitAllocation.getSlotId())){
                            visitAllocation.setStatus(1);
                            break;
                        }
                    }

                }

            }


        }
        return AjaxResult.success(list);
    }





    /**
     * 导出出诊信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:export')")
    @Log(title = "出诊信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitPlan visitPlan)
    {
        List<VisitPlan> list = visitPlanService.selectVisitPlanList(visitPlan);
        ExcelUtil<VisitPlan> util = new ExcelUtil<VisitPlan>(VisitPlan.class);
        util.exportExcel(response, list, "出诊信息表数据");
    }

    /**
     * 获取出诊信息表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {
        VisitPlan visitPlan = visitPlanService.selectVisitPlanById(id);
        if (StringUtils.isNotEmpty(visitPlan.getDoctorPhone())){
            visitPlan.setDoctorPhone(aesUtils.decrypt(visitPlan.getDoctorPhone()));
        }
        return AjaxResult.success(visitPlan);
    }

    /**
     * 新增出诊信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:add')")
    @Log(title = "出诊信息表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitPlan visitPlan) throws Exception {
        if (StringUtils.isNotEmpty(visitPlan.getDoctorPhone())){
            visitPlan.setDoctorPhone(aesUtils.encrypt(visitPlan.getDoctorPhone()));
        }
        if (visitPlan.getDoctorPhone()==null){
            return AjaxResult.error("医生数据有误，请稍后再试");
        }

        Doctor doctor = doctorService.selectDoctorByDoctorPhone(visitPlan.getDoctorPhone());
        if (doctor==null){
            return AjaxResult.error("医生数据有误，请稍后再试");
        }
        if (visitPlan.getDay()==null){
            return AjaxResult.error("出诊时间有误，请稍后再试");
        }
        VisitPlan visitPlan1 = new VisitPlan();
        visitPlan1.setDay(visitPlan.getDay());
        visitPlan1.setDoctorPhone(visitPlan.getDoctorPhone());
        List<VisitPlan> visitPlans = visitPlanService.selectVisitPlanWebList(visitPlan1);
        if (visitPlans!=null&&visitPlans.size()>0){
            // 创建一个SimpleDateFormat对象，指定日期时间格式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 使用format()方法将Date对象转换为指定格式的字符串
            String dateString = dateFormat.format(visitPlan.getDay());
            return AjaxResult.error("当前医生已在"+dateString+"安排了出诊");
        }
        visitPlan.setSpecialId(doctor.getSpecialId());
        visitPlan.setOutpatientId(doctor.getOutpatientId());
        return toAjax(visitPlanService.insertVisitPlan(visitPlan));
    }

    /**
     * 修改出诊信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:edit')")
    @Log(title = "出诊信息表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitPlan visitPlan) throws Exception {

        List<VisitAppointment> visitAppointments = visitAppointmentService.selectByPlanId(visitPlan.getPlanId());
        if (visitAppointments!=null&&visitAppointments.size()>0){
            return AjaxResult.error("当前排班已有患者挂号，不能修改");
        }
        if (StringUtils.isNotEmpty(visitPlan.getDoctorPhone())){
            visitPlan.setDoctorPhone(aesUtils.encrypt(visitPlan.getDoctorPhone()));
        }
        if (visitPlan.getDoctorPhone()==null){
            return AjaxResult.error("医生数据有误，请稍后再试");
        }

        Doctor doctor = doctorService.selectDoctorByDoctorPhone(visitPlan.getDoctorPhone());
        if (doctor==null){
            return AjaxResult.error("医生数据有误，请稍后再试");
        }
        if (visitPlan.getDay()==null){
            return AjaxResult.error("出诊时间有误，请稍后再试");
        }

        VisitPlan visitPlan1 = visitPlanService.selectVisitPlanById(visitPlan.getPlanId());
        if (visitPlan1!=null&&visitPlan1.getDay().compareTo(visitPlan.getDay())!=0){
            VisitPlan visitPlan2 = new VisitPlan();
            visitPlan2.setDay(visitPlan.getDay());
            visitPlan2.setDoctorPhone(visitPlan.getDoctorPhone());
            List<VisitPlan> visitPlans = visitPlanService.selectVisitPlanWebList(visitPlan2);
            if (visitPlans!=null&&visitPlans.size()>0){
                // 创建一个SimpleDateFormat对象，指定日期时间格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                // 使用format()方法将Date对象转换为指定格式的字符串
                String dateString = dateFormat.format(visitPlan.getDay());
                return AjaxResult.error("当前医生已在"+dateString+"安排了出诊");
            }

        }
        return toAjax(visitPlanService.updateVisitPlan(visitPlan));
    }

    /**
     * 删除出诊信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitPlan:remove')")
    @Log(title = "出诊信息表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids){
            List<VisitAppointment> visitAppointments = visitAppointmentService.selectByPlanId(id);
            if (visitAppointments!=null&&visitAppointments.size()>0){
                return AjaxResult.error("当前排班已有患者挂号，不能删除");
            }
        }
        return toAjax(visitPlanService.deleteVisitPlanByIds(ids));
    }
}
