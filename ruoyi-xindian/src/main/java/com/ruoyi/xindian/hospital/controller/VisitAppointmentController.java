package com.ruoyi.xindian.hospital.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.hospital.domain.*;
import com.ruoyi.xindian.hospital.service.*;
import com.ruoyi.xindian.hospital.vo.PlanMsgAllVo;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.vo.DateListVO;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.util.StrUtil;
import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
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


    @Resource
    private IVisitPlanService visitPlanService;

    @Resource
    private IPatientService patientService;


    @Resource
    private VisitPlanController visitPlanController;


    @Resource
    private VisitAllocationService visitAllocationService;

    @Resource
    private IHospitalService hospitalService;

    @Resource
    private TokenService tokenService;

    @Resource
    private VisitWaitService visitWaitService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    /**
     * 查询出诊预约表列表
     */
    @GetMapping("/WebList")
    public TableDataInfo list(VisitAppointment visitAppointment,HttpServletRequest request) throws Exception {

        LoginUser loginUser = tokenService.getLoginUser(request);
        visitAppointmentAesEncrypt(visitAppointment);
        List<VisitAppointment> visitAppointments = new ArrayList<>();
        if (SysUser.isAdmin(loginUser.getUserId())){
            startPage();
            visitAppointments = visitAppointmentService.selectVisitAppointmentList(visitAppointment);
        }else {

            if (loginUser.getUser().getHospitalCode()!=null){

                if (loginUser.getUser().getDeptId()!=null&&loginUser.getUser().getDeptId()==200&&loginUser.getUser().getRoleId()==109){

                    Hospital hospital = hospitalService.selectHospitalByHospitalCode(loginUser.getUser().getHospitalCode());
                    visitAppointment.setHospitalId(hospital.getHospitalId());
                    startPage();
                    visitAppointments = visitAppointmentService.selectVisitAppointmentList(visitAppointment);
                }else {
                    visitAppointment.setDoctorPhone(loginUser.getUser().getUserName());
                    startPage();
                    visitAppointments = visitAppointmentService.selectVisitAppointmentList(visitAppointment);
                }
            }

        }

        for (VisitAppointment visitAppointment1 : visitAppointments){
            visitAppointmentAesDecrypt(visitAppointment1);
        }
        return getDataTable(visitAppointments);
    }



    /**
     * 查询出诊预约表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(VisitAppointment visitAppointment) throws Exception {

        visitAppointmentAesEncrypt(visitAppointment);


        findOvertimeVisit(visitAppointment.getPatientPhone());
        List<VisitAppointment> visitAppointments = new ArrayList<>();
        startPage();
        visitAppointments = visitAppointmentService.selectVisitAppointmentList(visitAppointment);

        for (VisitAppointment visitAppointment1 : visitAppointments){
            visitAppointmentAesDecrypt(visitAppointment1);
        }
        return getDataTable(visitAppointments);
    }



    /**
     * 查询出诊预约表列表
     */
    @GetMapping("/DocAppList")
    public TableDataInfo DocAppList(VisitAppointment visitAppointment) throws Exception {

        visitAppointmentAesEncrypt(visitAppointment);

        List<VisitAppointment> visitAppointments = new ArrayList<>();
        startPage();
        visitAppointments = visitAppointmentService.selectVisitAppointmentList(visitAppointment);

        for (VisitAppointment visitAppointment1 : visitAppointments){
            visitAppointmentAesDecrypt(visitAppointment1);
        }
        return getDataTable(visitAppointments);
    }

    /**
     * 查询出诊预约表列表条数
     */
    @GetMapping("/DocList")
    public AjaxResult DocList(VisitAppointment visitAppointment) throws Exception {
        visitAppointmentAesEncrypt(visitAppointment);
        List<DateListVO> dateList = visitAppointmentService.getDateList(visitAppointment);
        return AjaxResult.success(dateList);
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
        return AjaxResult.success(visitAppointment);
    }

    /**
     * 新增出诊预约表
     */
    @PreAuthorize("@ss.hasPermi('hospital:visitAppointment:add')")
    @Log(title = "出诊预约表", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody VisitAppointment visitAppointment) throws Exception {
        visitAppointmentAesEncrypt(visitAppointment);
        Patient patient = patientService.selectPatientByPatientPhone(visitAppointment.getPatientPhone());
        if (patient==null){
            Patient patient1 = new Patient();
            BeanUtils.copyProperties(visitAppointment,patient1);
            patientService.insertPatient(patient1);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse(simpleDateFormat.format(date));

        VisitPlan visitPlan = new VisitPlan();
        visitPlan.setDay(parse);
        visitPlan.setDoctorPhone(visitAppointment.getDoctorPhone());
        List<VisitPlan> visitPlans = visitPlanService.selectVisitPlanWebList(visitPlan);
        if (visitPlans==null|| visitPlans.isEmpty()){
            return AjaxResult.error("该医生今日未排班");
        }


        VisitPlan visitPlan1 = visitPlans.get(0);
        VisitAppointment visitAppointment2 = new VisitAppointment();
        visitAppointment2.setPatientPhone(visitAppointment.getPatientPhone());
        visitAppointment2.setPlanId(visitPlan1.getPlanId());

        List<VisitAppointment> visitAppointments1 = visitAppointmentService.selectVisitAppointmentList(visitAppointment2);

        for (VisitAppointment v : visitAppointments1){

            if (v.getStatus()!=2){
                throw new ServiceException("今日已在"+aesUtils.decrypt(v.getDoctorName())+"医生预约了"+v.getPeriodStart()+"-"+v.getPeriodEnd()+"时段，不能重复预约");
            }
        }
        visitPlanController.getDateClassify(visitPlan1,parse);
        Iterator<Map.Entry<Object, Object>> iterator = visitPlan1.getVisitTimeMap().entrySet().iterator();
        Map<Integer,Integer>  map = new TreeMap<>();
        while (iterator.hasNext()){

            Map.Entry<Object, Object> next = iterator.next();
            List<VisitAllocation> value1 =(List<VisitAllocation>) next.getValue();

            for (VisitAllocation visitAllocation:value1){
                if (visitAllocation.getStatus()!=1){
                    if (!map.containsKey(visitAllocation.getSlotId())){
                        map.put(visitAllocation.getSlotId(),visitAllocation.getSlotId());
                    }
                }
            }
        }
        if (map.isEmpty()){
            return AjaxResult.error("该医生今日排班已满");
        }

        List<Integer> list = new ArrayList<>(map.keySet());
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        VisitAllocation visitAllocation = visitAllocationService.selectById(list.get(0));
        if (visitAllocation==null){
            return AjaxResult.error("该医生今日排班已满");
        }
        visitAppointment.setTimePeriod(visitAllocation.getSlotId());
        visitAppointment.setPeriodStart(visitAllocation.getStartTime());
        visitAppointment.setPeriodEnd(visitAllocation.getEndTime());
        visitAppointment.setPlanId(visitPlan1.getPlanId());
        visitAppointment.setAppointmentId(OrderNoUtils.getNo());
        visitAppointment.setCreateTime(date);
        visitAppointment.setUpdateTime(date);
        visitAppointment.setPayStatus("1");
        visitAppointment.setOutpatientType("线下挂号");
        visitAppointment.setDoctorProfessional(visitPlan1.getDoctor().getProfessional());
        visitAppointment.setPeriodDay(simpleDateFormat.parse(simpleDateFormat.format(visitPlan1.getDay())));
        visitAppointment.setDoctorName(visitPlan1.getDoctor().getDoctorName());
        visitAppointment.setSpecialName(visitPlan1.getHospitalSpecial().getSpecialName());
        visitAppointment.setOutpatientName(visitPlan1.getHospitalOutpatient().getOutpatientName());
        visitAppointment.setPayPrice(visitPlan1.getDoctor().getChargePrice());
        visitAppointment.setClinicAddress(visitPlan1.getHospitalClinic().getAddress());
        int i = visitAppointmentService.insertVisitAppointment(visitAppointment);
        if (i>0){

            return toAjax(visitWaitService.insertVisit(visitAppointment.getAppointmentId())>0);
        }
        return toAjax(false);

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
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(visitAppointmentService.deleteVisitAppointmentByIds(ids));
    }


    @DeleteMapping("/delById")
    public AjaxResult delById(String appointmentId)
    {
        return toAjax(visitAppointmentService.deleteVisitAppointmentById(appointmentId));
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

            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getVisitOrderId"+userId))){
                return AjaxResult.error("请勿重复支付");
            }
            redisTemplate.opsForValue().set("getVisitOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);
            if (StringUtils.isEmpty(planMsgAllVo.getPatientPhone())){
                return AjaxResult.error("患者手机号错误");
            }
            if (planMsgAllVo.getPlanId()==null||planMsgAllVo.getPlanId().equals("")){
                return AjaxResult.error("排班信息错误");
            }
            VisitAppointment visitAppointment = visitAppointmentService.addVisitAppointment(planMsgAllVo, request);
            visitAppointmentAesDecrypt(visitAppointment);
            return AjaxResult.success(visitAppointment);
        }finally {
            lock.unlock();
        }

    }

    @PutMapping("/appUpdateTime")
    public AjaxResult appUpdateTime(@RequestBody PlanMsgAllVo planMsgAllVo, HttpServletRequest request) throws Exception {
        lock.lock();
        try {
            if (StringUtils.isEmpty(planMsgAllVo.getPatientPhone())){
                return AjaxResult.error("患者手机号错误");
            }
            if (planMsgAllVo.getPlanId()==null||planMsgAllVo.getPlanId().equals("")){
                return AjaxResult.error("排班信息错误");
            }
            VisitAppointment visitAppointment = visitAppointmentService.putVisitAppointment(planMsgAllVo, request);
            visitAppointmentAesDecrypt(visitAppointment);
            return AjaxResult.success(visitAppointment);
        }finally {
            lock.unlock();
        }

    }


    @GetMapping("/updateStatus")
    public AjaxResult updateStatus(String appointmentId){
        visitAppointmentService.updateVisitAppointmentStatus(appointmentId,"2");
        return AjaxResult.success();
    }




    @GetMapping("/visitSignIn")
    public AjaxResult visitSignIn(String appointmentId) throws ParseException {
        if (StringUtils.isEmpty(appointmentId)){
            return AjaxResult.error("参数不存在");
        }
       return AjaxResult.success( visitAppointmentService.visitSignIn(appointmentId)>0);
    }



    private void findOvertimeVisit(String patientPhone) throws ParseException {

        VisitAppointment visitAppointment = new VisitAppointment();
        visitAppointment.setPatientPhone(patientPhone);
        visitAppointment.setStatus(0L);
        List<VisitAppointment> visitAppointments = visitAppointmentService.selectVisitAppointmentList(visitAppointment);
        Date date = new Date();
        SimpleDateFormat sF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        for (VisitAppointment visitAppointmentAll:visitAppointments){
            Date parse3 = sF.parse(sF.format(date));
            Date parse4 = sF.parse(sF.format(visitAppointmentAll.getPeriodDay()));
            if (parse3.compareTo(parse4) > 0&&visitAppointmentAll.getStatus()==0){
                VisitAppointment visitAppointment1 = new VisitAppointment();
                visitAppointment1.setStatus(3L);
                visitAppointment1.setAppointmentId(visitAppointmentAll.getAppointmentId());
                visitAppointmentService.updateVisitAppointment(visitAppointment1);
            }else if (parse3.compareTo(parse4) == 0&&visitAppointmentAll.getStatus()==0){
                Date parse1 = simpleDateFormat.parse(simpleDateFormat.format(date));
                Date parse = simpleDateFormat.parse(visitAppointmentAll.getPeriodEnd());
                if (parse1.compareTo(parse) > 0) {
                    VisitWait visitWait = new VisitWait();
                    visitWait.setAppointmentId(visitAppointmentAll.getAppointmentId());
                    List<VisitWait> waitList = visitWaitService.selectVisitWaitList(visitWait);
                    if (waitList==null|| waitList.isEmpty()){
                        VisitAppointment visitAppointment1 = new VisitAppointment();
                        visitAppointment1.setStatus(3L);
                        visitAppointment1.setAppointmentId(visitAppointmentAll.getAppointmentId());
                        visitAppointmentService.updateVisitAppointment(visitAppointment1);
                    }
                }

            }



        }




    }

    private void visitAppointmentAesEncrypt(VisitAppointment visitAppointment) throws Exception {

        if (visitAppointment==null){
            return;
        }
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

        if (visitAppointment==null){
            return;
        }

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
