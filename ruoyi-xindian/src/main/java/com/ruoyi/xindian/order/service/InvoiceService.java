package com.ruoyi.xindian.order.service;

import com.ruoyi.xindian.order.domain.Invoice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【invoice】的数据库操作Service
* @createDate 2023-07-24 17:05:08
*/
public interface InvoiceService extends IService<Invoice> {
    /**
     * 查询【请填写功能名称】列表
     *
     * @param invoice 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Invoice> selectInvoiceList(Invoice invoice);

    /**
     * 通过Id查看详情
     * @param orderId
     * @return
     */
    Invoice selectInvoiceId(String orderId);

    Boolean updateId(Invoice invoice);
}
