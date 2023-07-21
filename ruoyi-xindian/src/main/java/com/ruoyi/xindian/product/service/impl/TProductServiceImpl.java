package com.ruoyi.xindian.product.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.product.domain.ProductImgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.product.mapper.TProductMapper;
import com.ruoyi.xindian.product.domain.TProduct;
import com.ruoyi.xindian.product.service.ITProductService;
import org.springframework.web.multipart.MultipartFile;

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
        TProduct product = tProductMapper.selectTProductByProductId(productId);
        product.setPrice(product.getPrice().multiply(new BigDecimal("0.01")));
        product.setDiscount(product.getDiscount().multiply(new BigDecimal("0.01")));
        return product;
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

    @Override
    public List<TProduct> selectTProductListToWeb(TProduct tProduct) {
        return tProductMapper.selectTProductListToWeb(tProduct);
    }

    @Override
    public int insertProductImgs(List tProducts) {
        return tProductMapper.insertProductImgs(tProducts);
    }

    //根据id删除商品所有介绍图片
    @Override
    public void deleteAllImages(Long productId) {
        tProductMapper.deleteAllImgs(productId);
    }

    @Override
    public List<ProductImgs> selectIdAndImg(Long productId) {
        return tProductMapper.selectIdAndImg(productId);
    }

    @Override
    public void deleteByIdImg(Integer[] delImgs) {
        tProductMapper.deleteByIdImg(delImgs);
    }
}
