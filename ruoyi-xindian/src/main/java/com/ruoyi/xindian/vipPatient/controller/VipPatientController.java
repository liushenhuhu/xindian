package com.ruoyi.xindian.vipPatient.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.product.domain.TProduct;
import com.ruoyi.xindian.product.service.ITProductService;
import com.ruoyi.xindian.vipPatient.domain.VipWayPrice;
import com.ruoyi.xindian.vipPatient.service.IVipWayPriceService;
import org.aspectj.weaver.loadtime.Aj;
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
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * vip用户Controller
 *
 * @author ruoyi
 * @date 2023-07-06
 */
@RestController
@RequestMapping("/vipPatient/patient")
public class VipPatientController extends BaseController
{
    @Autowired
    private IVipPatientService vipPatientService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IVipWayPriceService vipWayPriceService;

    @Autowired
    private ITProductService itProductService;


    @Resource
    private ISysUserService sysUserService;
    @Resource
    private TokenService tokenService;


    @Autowired
    private AesUtils aesUtils;
    /**
     * 查询vip用户列表
     */
    @PreAuthorize("@ss.hasPermi('vipPatient:patient:list')")
    @GetMapping("/list")
    public TableDataInfo list(VipPatient vipPatient) throws Exception {
        startPage();
        List<VipPatient> list = vipPatientService.selectVipPatientList(vipPatient);
        for (VipPatient c:list){
           c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
        }
        return getDataTable(list);
    }

    /**
     * 导出vip用户列表
     */
    @PreAuthorize("@ss.hasPermi('vipPatient:patient:export')")
    @Log(title = "vip用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VipPatient vipPatient) throws Exception {
        List<VipPatient> list = vipPatientService.selectVipPatientList(vipPatient);
        for (VipPatient c:list){
            c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
        }
        ExcelUtil<VipPatient> util = new ExcelUtil<VipPatient>(VipPatient.class);
        util.exportExcel(response, list, "vip用户数据");
    }

    /**
     * 获取vip用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('vipPatient:patient:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {
        VipPatient vipPatient = vipPatientService.selectVipPatientById(id);
        vipPatient.setPatientPhone(aesUtils.decrypt(vipPatient.getPatientPhone()));
        return AjaxResult.success();
    }

    /**
     * 新增vip用户
     */
    @PreAuthorize("@ss.hasPermi('vipPatient:patient:add')")
    @Log(title = "vip用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VipPatient vipPatient) throws Exception {
        vipPatient.setPatientPhone(aesUtils.encrypt(vipPatient.getPatientPhone()));
        return toAjax(vipPatientService.insertVipPatient(vipPatient));
    }

    /**
     * 修改vip用户
     */
    @PreAuthorize("@ss.hasPermi('vipPatient:patient:edit')")
    @Log(title = "vip用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VipPatient vipPatient) throws Exception {
        vipPatient.setPatientPhone(aesUtils.encrypt(vipPatient.getPatientPhone()));
        //获取当前时间
        VipPatient vip = vipPatientService.selectVipPatientById(vipPatient.getId());
        vipPatient.setVipNum(vip.getVipNum() + vipPatient.getVipNum());
        Date endDate = vipPatient.getEndDate();
        // 创建 Calendar 实例并设置原始日期
        Calendar calendar = Calendar.getInstance();
        // 设置日期
        calendar.setTime(endDate);
        // 加一年
        calendar.add(Calendar.YEAR, 1);
        // 获取加一年后的日期
        Date newDate = calendar.getTime();
        vip.setEndDate(newDate);

        return toAjax(vipPatientService.updateVipPatient(vipPatient));
    }

    /**
     * 删除vip用户
     */
    @PreAuthorize("@ss.hasPermi('vipPatient:patient:remove')")
    @Log(title = "vip用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vipPatientService.deleteVipPatientByIds(ids));
    }

    /**
     * 检测是否为vip
     */
    @GetMapping("/isVip")
    public AjaxResult isVip(HttpServletRequest request) throws ParseException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        String phonenumber = loginUser.getUser().getPhonenumber();
        VipPatient vipPatients = vipPatientService.findVipPhone(phonenumber);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = format.parse(format.format(new Date()));
        if(vipPatients!=null){
            Date parse1 = format.parse(format.format(vipPatients.getEndDate()));
            //判断日期是否过期，如果过期则直接删除
            if (parse.getTime()>parse1.getTime()){
                vipPatientService.deleteVipPatientById(vipPatients.getId());
                return AjaxResult.success("false");
            }
            return AjaxResult.success("true",vipPatients);
        }
        else{
            SysUser sysUser = sysUserService.selectUserById(loginUser.getUser().getUserId());
            Date parse1 = format.parse(format.format(sysUser.getDetectionTime()));
            if (parse.getTime()>parse1.getTime()){
                sysUser.setDetectionNum(0L);
                sysUserService.updateUser(sysUser);
            }
            return AjaxResult.success("false");
        }
    }
    @PostMapping("/vip")
    public AjaxResult addVip(@RequestBody VipPatient vipPatient)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String phonenumber = loginUser.getUser().getPhonenumber();
        vipPatient.setPatientPhone(phonenumber);
        if(vipPatient.getVipType()==null){
            return AjaxResult.error("vipType is null");
        } else if(vipPatient.getVipType()==1){
            //获取当前时间
            LocalDate now = LocalDate.now();
            LocalDate nextYear = now.plusYears(1);
            Date date = Date.from(nextYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
            vipPatient.setEndDate(date);
            vipPatient.setVipNum(100L);
        } else{
            return AjaxResult.error("vipType is unsafe");
        }
        return toAjax(vipPatientService.insertVipPatient(vipPatient));
    }
    @PutMapping("vip")
    public AjaxResult editVip(@RequestBody VipPatient vipPatient)
    {
        if(vipPatient.getVipType()==1){
            //获取当前时间
            VipPatient vip = vipPatientService.selectVipPatientById(vipPatient.getId());
            vipPatient.setVipNum(vip.getVipNum() + 100);
            Date endDate = vipPatient.getEndDate();
            // 创建 Calendar 实例并设置原始日期
            Calendar calendar = Calendar.getInstance();
            // 设置日期
            calendar.setTime(endDate);
            // 加一年
            calendar.add(Calendar.YEAR, 1);
            // 获取加一年后的日期
            Date newDate = calendar.getTime();
            vip.setEndDate(newDate);
        } else {
            return AjaxResult.error("vipType is unsafe");
        }
        return toAjax(vipPatientService.updateVipPatient(vipPatient));
    }
    @GetMapping("/once")
    public AjaxResult once(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String phonenumber = loginUser.getUser().getPhonenumber();
        Patient p = patientService.selectPatientByPatientPhone(phonenumber);
        p.setDetectionNum(p.getDetectionNum() + 1);
        return toAjax(patientService.updatePatient(p));
    }
    @GetMapping("/vipWay")
    public TableDataInfo vipWay(){
        TProduct tProduct = new TProduct();
        tProduct.setType("服务");
        List<TProduct> tProducts = itProductService.selectTProductList(tProduct);

        return getDataTable(tProducts);
    }


    /**
     * 服务次数减一
     * @param patientPhone
     * @return
     */
    @GetMapping("/detectionNumSubtract")
    public AjaxResult detectionNumSubtract(String patientPhone) throws Exception {
        String encrypt = aesUtils.encrypt(patientPhone);
        Boolean aBoolean = patientService.detectionNumSubtract(patientPhone);

        return AjaxResult.success(aBoolean);
    }

    @GetMapping("/detectionTime/{phone}")
    public AjaxResult getDateTime(@PathVariable("phone")String phone) throws Exception {
        String encrypt = aesUtils.encrypt(phone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        VipPatient vipPhone = vipPatientService.findVipPhone(encrypt);
        if (vipPhone!=null){
            return AjaxResult.success(simpleDateFormat.format(vipPhone.getEndDate()));
        }
        SysUser sysUser = sysUserService.selectUserByPhone(encrypt);
        return AjaxResult.success(simpleDateFormat.format(sysUser.getDetectionTime()));
    }

}
