package com.ruoyi.xindian.order.mapper;

import com.ruoyi.xindian.order.domain.Invoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 13401
* @description 针对表【invoice】的数据库操作Mapper
* @createDate 2023-07-24 17:05:08
* @Entity com.ruoyi.xindian.order.domain.Invoice
*/
public interface InvoiceMapper extends BaseMapper<Invoice> {

    /**
     * 查询【请填写功能名称】列表
     *
     * @param invoice 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Invoice> selectInvoiceList(Invoice invoice);
}




