package com.ruoyi.xindian.product.service;

import java.util.List;

import com.ruoyi.xindian.product.domain.ProductImgs;
import com.ruoyi.xindian.product.domain.TProduct;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品信息Service接口
 *
 * @author chenpeng
 * @date 2023-07-04
 */
public interface ITProductService
{
    /**
     * 查询商品信息
     *
     * @param productId 商品信息主键
     * @return 商品信息
     */
    public TProduct selectTProductByProductId(Long productId);

    public List<String> selectAllImages(Long productId);

    /**
     * 查询商品信息列表
     *
     * @param tProduct 商品信息
     * @return 商品信息集合
     */
    public List<TProduct> selectTProductList(TProduct tProduct);

    /**
     * 新增商品信息
     *
     * @param tProduct 商品信息
     * @return 结果
     */
    public int insertTProduct(TProduct tProduct);

    /**
     * 修改商品信息
     *
     * @param tProduct 商品信息
     * @return 结果
     */
    public int updateTProduct(TProduct tProduct);

    /**
     * 批量删除商品信息
     *
     * @param productIds 需要删除的商品信息主键集合
     * @return 结果
     */
    public int deleteTProductByProductIds(Long[] productIds);

    /**
     * 删除商品信息信息
     *
     * @param productId 商品信息主键
     * @return 结果
     */
    public int deleteTProductByProductId(Long productId);


    List<TProduct> selectTProductListToWeb(TProduct tProduct);
    //添加多张商品介绍图片
    public int insertProductImgs(List tProducts);

    public void deleteAllImages(Long productId);

    List<ProductImgs> selectIdAndImg(Long productId);

    void deleteByIdImg(Integer[] delImgs);

    List<ProductImgs> selectByIdUrl(Integer[] delImgs);
}
