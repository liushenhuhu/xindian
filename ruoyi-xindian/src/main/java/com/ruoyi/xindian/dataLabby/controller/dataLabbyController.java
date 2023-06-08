package com.ruoyi.xindian.dataLabby.controller;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.xindian.dataLabby.service.IDataLabbyService;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
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
        if(hospitalCode==null || hospitalCode.equals("")){
            return getDataTable(null);
        }
        Hospital hospital = hospitalService.selectHospitalByHospitalCode(hospitalCode);
        if(hospital==null) return getDataTable(null);
        order.setHospitalId(hospital.getHospitalId());
        startPage();
        List<dataLabby> list = orderService.selectOrderList(order);
        return getDataTable(list);
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
    @GetMapping(value = "/dataLabby/{orderId}")
    public AjaxResult getOrder(@PathVariable("orderId") Long orderId)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String phonenumber = loginUser.getUser().getPhonenumber();
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(phonenumber);
        lock.lock();
        try{
            dataLabby order = orderService.selectOrderByOrderId(orderId);
            if(order==null)
                return AjaxResult.error("当前订单已被抢走！");

            Long reportId = order.getReportId();
            Report report = reportService.selectReportByReportId(reportId);
            report.setdPhone(phonenumber);
            report.setDiagnosisDoctor(doctor.getDoctorName());
            orderService.deleteOrderByOrderId(orderId);
            reportService.updateReport(report);
        } catch (Exception e){
            return AjaxResult.error(String.valueOf(e));
        } finally {
            lock.unlock();
        }
        return AjaxResult.success();
    }
}
