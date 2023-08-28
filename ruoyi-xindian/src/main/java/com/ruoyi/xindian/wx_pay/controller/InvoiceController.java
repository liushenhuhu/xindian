package com.ruoyi.xindian.wx_pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.order.domain.Invoice;
import com.ruoyi.xindian.order.service.InvoiceService;
import com.ruoyi.xindian.product.domain.TProduct;
import com.ruoyi.xindian.wx_pay.util.AjaxResult;
import com.ruoyi.xindian.wx_pay.util.WXPayUtil;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.ruoyi.xindian.wx_pay.enums.OrderStatus.SUCCESS;

@RestController
@RequestMapping("/invoice/invoice")
public class InvoiceController extends BaseController {


    @Resource
    private InvoiceService invoiceService;

    @Resource
    private WXPublicRequest wxPublicRequest;


    @Resource
    private AesUtils aesUtils;

    @Value("${ruoyi.url}")
    private String url;
    /**
     * 查询发票列表
     */
    @PreAuthorize("@ss.hasPermi('invoice:invoice:list')")
    @GetMapping("/list")
    public TableDataInfo list(Invoice invoice) throws Exception {
        startPage();
        List<Invoice> list = invoiceService.selectInvoiceList(invoice);
        for (Invoice c :list){
            if (c.getTaxNo()!=null&&!"".equals(c.getTaxNo())){
                c.setTaxNo(aesUtils.decrypt(c.getTaxNo()));
            }
            if (c.getAddr()!=null&&!"".equals(c.getAddr())){
                c.setAddr(aesUtils.decrypt(c.getAddr()));
            }
            if (c.getPhone()!=null&&!"".equals(c.getPhone())){
                c.setPhone(aesUtils.decrypt(c.getPhone()));
            }
            if (c.getBankNo()!=null&&!"".equals(c.getBankNo())){
                c.setBankNo(aesUtils.decrypt(c.getBankNo()));
            }
        }
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('invoice:invoice:edit')")
    @GetMapping("/{id}")
    public AjaxResult getID(@PathVariable("id") String orderId) throws Exception {
        Invoice c = invoiceService.selectInvoiceId(orderId);
        if (c.getTaxNo()!=null&&!"".equals(c.getTaxNo())){
            c.setTaxNo(aesUtils.decrypt(c.getTaxNo()));
        }
        if (c.getAddr()!=null&&!"".equals(c.getAddr())){
            c.setAddr(aesUtils.decrypt(c.getAddr()));
        }
        if (c.getPhone()!=null&&!"".equals(c.getPhone())){
            c.setPhone(aesUtils.decrypt(c.getPhone()));
        }
        if (c.getBankNo()!=null&&!"".equals(c.getBankNo())){
            c.setBankNo(aesUtils.decrypt(c.getBankNo()));
        }
        return AjaxResult.success(c);
    }

    @PutMapping
    public AjaxResult put(Invoice c) throws Exception {
        if (c.getTaxNo()!=null&&!"".equals(c.getTaxNo())){
            c.setTaxNo(aesUtils.encrypt(c.getTaxNo()));
        }
        if (c.getAddr()!=null&&!"".equals(c.getAddr())){
            c.setAddr(aesUtils.encrypt(c.getAddr()));
        }
        if (c.getPhone()!=null&&!"".equals(c.getPhone())){
            c.setPhone(aesUtils.encrypt(c.getPhone()));
        }
        if (c.getBankNo()!=null&&!"".equals(c.getBankNo())){
            c.setBankNo(aesUtils.encrypt(c.getBankNo()));
        }
        return AjaxResult.success(invoiceService.updateId(c));
    }
    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:invoice:export')")
    @Log(title = "导出发票", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Invoice invoice)
    {
        List<Invoice> list = invoiceService.selectInvoiceList(invoice);
        ExcelUtil<Invoice> util = new ExcelUtil<Invoice>(Invoice.class);
        util.exportExcel(response, list, "发票数据");
    }


    @PostMapping("/upload/{id}")
    public AjaxResult upload(@RequestParam(value = "file",required = false) MultipartFile file ,@PathVariable("id")String orderId,@RequestParam("invoice")String invoice) throws Exception {

        Invoice invoice1 = JSONObject.parseObject(invoice, Invoice.class);
        if (!file.isEmpty()) {
            invoice1.setState("已处理");
            invoice1.setOpenId(orderId);
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
            invoice1.setInvoiceUrl(url+avatar);
            invoiceService.updateId(invoice1);

            String sMediaId = wxPublicRequest.setCardUpload(file);
            if (sMediaId!=null){
                wxPublicRequest.setWXCardInvoice(orderId,sMediaId);
            }
        }
        return AjaxResult.error("文件不存在");
    }
    /**
     * 支付成功回调
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("callBack")
    public void callBack(HttpServletRequest request, HttpServletResponse response){
        System.err.println("微信发票已报销,微信发送的callback信息,请注意修改订单信息");
        InputStream is = null;
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WXPayUtil.inputStream2String(is);
            Map<String, Object> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map

           String o = (String) notifyMap.get("Status");

           if (o!=null&&o.equals("INVOICE_REIMBURSE_INIT")){

               String CardId = (String) notifyMap.get("CardId");
               String code = (String) notifyMap.get("Code");
               wxPublicRequest.setCardState(CardId,code);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
