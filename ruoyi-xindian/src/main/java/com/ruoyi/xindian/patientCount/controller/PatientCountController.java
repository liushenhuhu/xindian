package com.ruoyi.xindian.patientCount.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.fw_log.service.FwLogService;
import com.ruoyi.xindian.patientCount.domain.PatientCount;

import com.ruoyi.xindian.patientCount.service.PatientCountService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 *
 * 用户服务次数管理
 *
 * **/
@RestController
@RequestMapping("/patientCount/patientCount")
public class PatientCountController extends BaseController {
    @Autowired
    private PatientCountService patientCountService;
    @Autowired
    private FwLogService fwLogService;
    @Autowired
    private IVipPatientService vipPatientService;
    @Autowired
    private AesUtils aesUtils;

    @Autowired
    private ISysUserService sysUserService;
    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('patientCount:patientCount:list')")
    @GetMapping("/list")
    public TableDataInfo list(PatientCount patientCount) throws Exception {
        if(patientCount.getPhonenumberAes()!=null&&!"".equals(patientCount.getPhonenumberAes())){
            patientCount.setPhonenumberAes(aesUtils.encrypt(patientCount.getPhonenumberAes()));
        }
        startPage();
        List<PatientCount> list = patientCountService.selectWFLogAndVip(patientCount);
        for (PatientCount c:list){
            //使用次数
            long usesNum = 0;
            //购买次数
            long  payNum = 0;
            if (c.getFwLogs()!=null&&c.getFwLogs().size()>0){
                for (FwLog b : c.getFwLogs()){
                    if (b.getFwStatus()!=null&&"2".equals(b.getFwStatus())){
                        usesNum +=b.getFwNum();
                    }
                    if (b.getFwStatus()!=null&&"1".equals(b.getFwStatus())){
                        payNum +=b.getFwNum();
                    }
                }
            }

            //剩余次数
            if (c.getVipPatient()!=null && c.getVipPatient().getVipNum()!=null){
                c.setDetectionNum(c.getVipPatient().getVipNum());
            }
            //总次数
            c.setTotalNum(usesNum+c.getDetectionNum());
            c.setPayNum(payNum);
            c.setUsesNum(usesNum);
            if (StringUtils.isNotEmpty(c.getPhonenumberAes())){
                c.setPhonenumberAes(aesUtils.decrypt(c.getPhonenumberAes()));
            }

        }
        return getDataTable(list);
    }

    /**
     * 导出vip用户列表
     */
    @PreAuthorize("@ss.hasPermi('patientCount:patientCount:export')")
    @Log(title = "vip用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PatientCount patientCount) throws Exception {
        if(patientCount.getPhonenumberAes()!=null&&!"".equals(patientCount.getPhonenumberAes())){
            patientCount.setPhonenumberAes(aesUtils.encrypt(patientCount.getPhonenumberAes()));
        }
        List<PatientCount> list = patientCountService.selectWFLogAndVip(patientCount);
        for (PatientCount c:list){
            //使用次数
            long usesNum = 0;
            //购买次数
            long  payNum = 0;
            if (c.getFwLogs()!=null&&c.getFwLogs().size()>0){
                for (FwLog b : c.getFwLogs()){
                    if (b.getFwStatus()!=null&&"2".equals(b.getFwStatus())){
                        usesNum +=b.getFwNum();
                    }
                    if (b.getFwStatus()!=null&&"1".equals(b.getFwStatus())){
                        payNum +=b.getFwNum();
                    }
                }
            }

            //剩余次数
            if (c.getVipPatient()!=null && c.getVipPatient().getVipNum()!=null){
                c.setDetectionNum(c.getVipPatient().getVipNum());
            }
            //总次数
            c.setTotalNum(usesNum+c.getDetectionNum());
            c.setPayNum(payNum);
            c.setUsesNum(usesNum);
            if (StringUtils.isNotEmpty(c.getPhonenumberAes())){
                c.setPhonenumberAes(aesUtils.decrypt(c.getPhonenumberAes()));
            }

        }
        ExcelUtil<PatientCount> util = new ExcelUtil<PatientCount>(PatientCount.class);
        util.exportExcel(response, list, "vip用户数据");
    }
    /**
     * 获取用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('patientCount:patientCount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {
        PatientCount patientCount = patientCountService.getById(id);
        patientCount.setPhonenumberAes(aesUtils.decrypt(patientCount.getPhonenumberAes()));
        return AjaxResult.success(patientCount);
    }

//    /**
//     * 新增vip用户
//     */
//    @PreAuthorize("@ss.hasPermi('patientCount:patientCount:add')")
//    @Log(title = "用户", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody PatientCount patientCount) throws Exception {
//        patientCount.setPatientPhone(aesUtils.encrypt(patientCount.getPatientPhone()));
//        return toAjax(patientCountService.insertpatientCount(patientCount));
//    }
    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('patientCount:patientCount:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PatientCount patientCount) throws Exception {
        patientCount.setPhonenumberAes(aesUtils.encrypt(patientCount.getPhonenumberAes()));
        return toAjax(patientCountService.updateById(patientCount));
    }
    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('patientCount:patientCount:remove')")
    @Log(title = "用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(patientCountService.removeById(ids));
    }
}
