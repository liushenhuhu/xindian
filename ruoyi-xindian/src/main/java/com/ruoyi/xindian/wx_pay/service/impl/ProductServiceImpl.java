package com.ruoyi.xindian.wx_pay.service.impl;




import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.wx_pay.domain.Product;
import com.ruoyi.xindian.wx_pay.mapper.ProductMapper;
import com.ruoyi.xindian.wx_pay.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Override
    public Product selectPId(Long productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public int updateId(Product product) {
        return productMapper.updateById(product);
    }
}
