package com.ruoyi.xindian.dataLabby.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.xindian.dataLabby.domain.dataLabbyDto;
import com.ruoyi.xindian.dataLabby.service.IDataLabbyService;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.report.config.WxMsgRunConfig;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.xindian.statistics.domain.DiagnoseDoc;
import com.ruoyi.xindian.statistics.service.DiagnoseDocService;
import com.ruoyi.xindian.util.DateUtil;
import com.ruoyi.xindian.util.ThreadUtil;
import com.ruoyi.xindian.util.WxUtil;
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
import com.ruoyi.xindian.dataLabby.domain.dataLabby;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单Controller
 * 
 * @author chenpeng
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/order/order")
public class dataLabbyController extends BaseController
{
    @Autowired
    private IDataLabbyService orderService;
    @Autowired
    private IHospitalService hospitalService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private IPatientService patientService;


    @Resource
    private SysDictDataMapper dictDataMapper;

    @Resource
    private TokenService tokenService;
    @Resource
    private WxMsgRunConfig wxMsgRunConfig;

    @Resource
    private DiagnoseDocService diagnoseDocService;

    @Resource
    private AesUtils aesUtils;
    private final Lock lock = new ReentrantLock();
    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(dataLabby order)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String hospitalCode = loginUser.getUser().getHospitalCode();
        //管理员
        if("15888888888".equals(loginUser.getUser().getPhonenumber())){
            startPage();
            List<dataLabby> list = orderService.selectOrderList(order);
            List<dataLabbyDto> resList=new ArrayList<>();
            dataLabbyDto dataDto;
            for (dataLabby data : list) {
                dataDto = new dataLabbyDto();
                dataDto=dataToDto(data,dataDto);
                resList.add(dataDto);
            }
            return getTable(resList,new PageInfo(list).getTotal());
        }
        if(hospitalCode==null || hospitalCode.equals("")){
            return getDataTable(new ArrayList<>());
        }
        Hospital hospital = hospitalService.selectHospitalByHospitalCode(hospitalCode);
        if(hospital==null) return getDataTable(new ArrayList<>());
        order.setHospitalId(hospital.getHospitalId());
        startPage();
        List<dataLabby> list = orderService.selectOrderList(order);
        List<dataLabbyDto> resList=new ArrayList<>();
        dataLabbyDto dataDto;
        for (dataLabby data : list) {
            dataDto = new dataLabbyDto();
            dataDto=dataToDto(data,dataDto);
            resList.add(dataDto);
        }
        return getTable(resList,new PageInfo(list).getTotal());
    }
    public dataLabbyDto dataToDto(dataLabby d,dataLabbyDto t){
        t.setReportId(d.getReportId());
        t.setOrderId(d.getOrderId());
        t.setHospitalId(d.getHospitalId());
        Report report = reportService.selectReportByReportId(d.getReportId());
        Patient patient = patientService.selectPatientByPatientPhone(report.getPPhone());
        t.setPatientName(patient.getPatientName());
        t.setPId(report.getpId());
//        if (patient.getPatientPhone().length()==14||patient.getPatientPhone().length()==15){
//            patient.setPatientPhone(patient.getPatientPhone().substring(0,11));
//        }
        t.setPatientPhone(patient.getPatientPhone());
        if(patient.getBirthDay() == null){
            t.setPatientAge(patient.getPatientAge());
        } else {
            int age = DateUtil.getAge(patient.getBirthDay());
            t.setPatientAge(String.valueOf(age));
        }
        t.setPatientSex(patient.getPatientSex());
        return t;
    }
    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, dataLabby order)
    {
        List<dataLabby> list = orderService.selectOrderList(order);
        ExcelUtil<dataLabby> util = new ExcelUtil<dataLabby>(dataLabby.class);
        util.exportExcel(response, list, "订单数据");
    }
    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return AjaxResult.success(orderService.selectOrderByOrderId(orderId));
    }
    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('order:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody dataLabby order)
    {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('order:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody dataLabby order)
    {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('order:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderService.deleteOrderByOrderIds(orderIds));
    }
    /**
     * 抢单
     */
    @GetMapping(value = "/dataLabby/{reportId}")
    public AjaxResult getOrder(@PathVariable("reportId") String pId, HttpServletRequest request) throws Exception {

        lock.lock();
        try{

            LoginUser loginUser = tokenService.getLoginUser(request);
            String phonenumber = loginUser.getUser().getPhonenumber();
            Doctor doctor = doctorService.selectDoctorByDoctorPhone(phonenumber);

            if(doctor==null){
                return AjaxResult.error("没有抢单的权限");
            }

            SysDictData sysDictData1 = new SysDictData();
            sysDictData1.setDictType("docker_report_preempt");
            sysDictData1.setStatus("0");
            List<SysDictData> sysDictData = dictDataMapper.selectDictDataList(sysDictData1);
            Boolean isDoc = false;
            for (SysDictData s : sysDictData){
                if (s.getDictValue().equals(doctor.getIsDoc())){
                    isDoc=true;
                    break;
                }
            }
            if (!isDoc){
                return AjaxResult.error("没有抢单的权限");
            }
            Report report = reportService.selectReportByPId(pId);

            if (report==null){
                return AjaxResult.error("当前订单不存在");
            }
            if(report.getdPhone()!=null){
                return AjaxResult.error("当前订单已被抢走！");
            }


            report.setDiagnosisDoctorAes(aesUtils.decrypt(doctor.getDoctorName()));
            report.setDPhoneAes(aesUtils.decrypt(doctor.getDoctorPhone()));
            report.setdPhone(phonenumber);
            report.setDiagnosisDoctor(doctor.getDoctorName());
            report.setDiagnosisStatus(2L);
            report.setReportTime(new Date());
            report.setStartTime(new Date());

            DiagnoseDoc diagnoseDoc = new DiagnoseDoc();
            diagnoseDoc.setReportId(report.getReportId());
            diagnoseDoc.setDoctorPhone(phonenumber);
            diagnoseDoc.setDiagnoseType("1");
            diagnoseDocService.insertDiagnose(diagnoseDoc);
            reportService.updateReport(report);
            Doctor doctor1 = doctorService.selectDoctorByDoctorPhone(phonenumber);
            Doctor doctor2 = new Doctor();
            doctor2.getHospitalNameList().add(doctor1.getHospital());
            List<Doctor> doctors = doctorService.selectDoctorListNot(doctor2);
            wxMsgRunConfig.redisDTStart(pId,doctors);
        } catch (Exception e){
            return AjaxResult.error(String.valueOf(e));
        } finally {
            lock.unlock();
        }
        return AjaxResult.success();
    }
}
