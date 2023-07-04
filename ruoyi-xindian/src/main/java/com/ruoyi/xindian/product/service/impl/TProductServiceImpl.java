package com.ruoyi.xindian.product.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.product.domain.ProductImgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.product.mapper.TProductMapper;
import com.ruoyi.xindian.product.domain.TProduct;
import com.ruoyi.xindian.product.service.ITProductService;

/**
 * 商品信息Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-07-04
 */
@Service
public class TProductServiceImpl implements ITProductService 
{
    @Autowired
    private TProductMapper tProductMapper;

    /**
     * 查询商品信息
     * 
     * @param productId 商品信息主键
     * @return 商品信息
     */
    @Override
    public TProduct selectTProductByProductId(Long productId)
    {
        return tProductMapper.selectTProductByProductId(productId);
    }
    /**
     * 查询商品详细信息图片
     * */
    @Override
    public List<String> selectAllImages(Long productId){
        return tProductMapper.selectAllImages(productId);
    }
    /**
     * 查询商品信息列表
     * 
     * @param tProduct 商品信息
     * @return 商品信息
     */
    @Override
    public List<TProduct> selectTProductList(TProduct tProduct)
    {
        return tProductMapper.selectTProductList(tProduct);
    }

    /**
     * 新增商品信息
     * 
     * @param tProduct 商品信息
     * @return 结果
     */
    @Override
    public int insertTProduct(TProduct tProduct)
    {
        tProduct.setCreateTime(DateUtils.getNowDate());
        return tProductMapper.insertTProduct(tProduct);
    }

    /**
     * 修改商品信息
     * 
     * @param tProduct 商品信息
     * @return 结果
     */
    @Override
    public int updateTProduct(TProduct tProduct)
    {
        tProduct.setUpdateTime(DateUtils.getNowDate());
        return tProductMapper.updateTProduct(tProduct);
    }

    /**
     * 批量删除商品信息
     * 
     * @param productIds 需要删除的商品信息主键
     * @return 结果
     */
    @Override
    public int deleteTProductByProductIds(Long[] productIds)
    {
        return tProductMapper.deleteTProductByProductIds(productIds);
    }

    /**
     * 删除商品信息信息
     * 
     * @param productId 商品信息主键
     * @return 结果
     */
    @Override
    public int deleteTProductByProductId(Long productId)
    {
        return tProductMapper.deleteTProductByProductId(productId);
    }
}
