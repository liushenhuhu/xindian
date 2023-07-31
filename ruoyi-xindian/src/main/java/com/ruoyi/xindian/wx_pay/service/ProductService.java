package com.ruoyi.xindian.wx_pay.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.wx_pay.domain.Product;

public interface ProductService extends IService<Product> {

    /**
     * 查询商品数量
     */
    Product selectPId(Long productId);

    int updateId(Product product);
}
