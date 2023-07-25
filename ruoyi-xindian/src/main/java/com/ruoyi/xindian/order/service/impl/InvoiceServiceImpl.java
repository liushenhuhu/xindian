package com.ruoyi.xindian.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.order.domain.Invoice;
import com.ruoyi.xindian.order.service.InvoiceService;
import com.ruoyi.xindian.order.mapper.InvoiceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【invoice】的数据库操作Service实现
* @createDate 2023-07-24 17:05:08
*/
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice>
    implements InvoiceService{

    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    public List<Invoice> selectInvoiceList(Invoice invoice) {
        return invoiceMapper.selectInvoiceList(invoice);
    }

    @Override
    public Invoice selectInvoiceId(String orderId) {
        return invoiceMapper.selectById(orderId);
    }

    @Transactional
    @Override
    public Boolean updateId(Invoice invoice) {
        int i = invoiceMapper.updateById(invoice);
        return true;
    }
}




