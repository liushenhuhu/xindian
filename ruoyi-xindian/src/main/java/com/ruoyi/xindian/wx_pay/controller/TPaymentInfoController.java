package com.ruoyi.xindian.wx_pay.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.wx_pay.domain.PaymentInfo;
import com.ruoyi.xindian.wx_pay.service.PaymentInfoService;
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
 * 【支付日志】Controller
 * 
 * @author ruoyi
 * @date 2023-08-25
 */
@RestController
@RequestMapping("/payLog/payLog")
public class TPaymentInfoController extends BaseController
{
    @Autowired
    private PaymentInfoService tPaymentInfoService;

    @Resource
    private AesUtils aesUtils;

    /**
     * 查询【支付日志】列表
     */
    @PreAuthorize("@ss.hasPermi('payLog:payLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(PaymentInfo tPaymentInfo) throws Exception {
        if (tPaymentInfo.getUserName()!=null&&!"".equals(tPaymentInfo.getUserName())){
            tPaymentInfo.setUserName(aesUtils.encrypt(tPaymentInfo.getUserName()));
        }
        startPage();
        List<PaymentInfo> list = tPaymentInfoService.selectTPaymentInfoList(tPaymentInfo);
        for (PaymentInfo c :list){
            if (c.getUserName()!=null&&!"".equals(c.getUserName())){
                c.setUserName(aesUtils.decrypt(c.getUserName()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出【支付日志】列表
     */
    @PreAuthorize("@ss.hasPermi('payLog:payLog:export')")
    @Log(title = "【支付日志】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PaymentInfo tPaymentInfo) throws Exception {
        List<PaymentInfo> list = tPaymentInfoService.selectTPaymentInfoList(tPaymentInfo);
        for (PaymentInfo c :list){
            if (c.getUserName()!=null&&!"".equals(c.getUserName())){
                c.setUserName(aesUtils.decrypt(c.getUserName()));
            }
        }
        ExcelUtil<PaymentInfo> util = new ExcelUtil<PaymentInfo>(PaymentInfo.class);
        util.exportExcel(response, list, "支付日志数据");
    }

    /**
     * 获取【支付日志】详细信息
     */
    @PreAuthorize("@ss.hasPermi('payLog:payLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) throws Exception {
        PaymentInfo paymentInfo = tPaymentInfoService.selectTPaymentInfoById(id);
        if (paymentInfo.getUserName()!=null&&!"".equals(paymentInfo.getUserName())){
            paymentInfo.setUserName(aesUtils.decrypt(paymentInfo.getUserName()));
        }
        return AjaxResult.success(paymentInfo);
    }

    /**
     * 新增【支付日志】
     */
    @PreAuthorize("@ss.hasPermi('payLog:payLog:add')")
    @Log(title = "【支付日志】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PaymentInfo tPaymentInfo) throws Exception {
        if (tPaymentInfo.getUserName()!=null&&!"".equals(tPaymentInfo.getUserName())){
            tPaymentInfo.setUserName(aesUtils.encrypt(tPaymentInfo.getUserName()));
        }
        return toAjax(tPaymentInfoService.insertTPaymentInfo(tPaymentInfo));
    }

    /**
     * 修改【支付日志】
     */
    @PreAuthorize("@ss.hasPermi('payLog:payLog:edit')")
    @Log(title = "【支付日志】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PaymentInfo tPaymentInfo) throws Exception {
        if (tPaymentInfo.getUserName()!=null&&!"".equals(tPaymentInfo.getUserName())){
            tPaymentInfo.setUserName(aesUtils.encrypt(tPaymentInfo.getUserName()));
        }
        return toAjax(tPaymentInfoService.updateTPaymentInfo(tPaymentInfo));
    }

    /**
     * 删除【支付日志】
     */
    @PreAuthorize("@ss.hasPermi('payLog:payLog:remove')")
    @Log(title = "【支付日志】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tPaymentInfoService.deleteTPaymentInfoByIds(ids));
    }
}
