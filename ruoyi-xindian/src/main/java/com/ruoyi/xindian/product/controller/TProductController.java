package com.ruoyi.xindian.product.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.ruoyi.xindian.product.domain.ProductImgs;
import com.ruoyi.xindian.product.domain.TProductDto;
import org.springframework.beans.BeanUtils;
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
import com.ruoyi.xindian.product.domain.TProduct;
import com.ruoyi.xindian.product.service.ITProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品信息Controller
 * 
 * @author chenpeng
 * @date 2023-07-04
 */
@RestController
@RequestMapping("/product/product")
public class TProductController extends BaseController
{
    @Autowired
    private ITProductService tProductService;

    /**
     * 查询商品信息列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(TProduct tProduct)
    {
        startPage();
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        List<TProductDto> resList = new ArrayList<>();
        for (TProduct product : list) {
            TProductDto tProductDto = new TProductDto();
            BeanUtils.copyProperties(product,tProductDto);
            List<String> productImgs = tProductService.selectAllImages(product.getProductId());
            System.out.println(productImgs);
            tProductDto.setBottomImg(productImgs);
            resList.add(tProductDto);
        }
        return getTable(resList,new PageInfo(list).getTotal());
    }

    /**
     * 导出商品信息列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:export')")
    @Log(title = "商品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TProduct tProduct)
    {
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        ExcelUtil<TProduct> util = new ExcelUtil<TProduct>(TProduct.class);
        util.exportExcel(response, list, "商品信息数据");
    }

    /**
     * 获取商品信息详细信息
     */
//    @PreAuthorize("@ss.hasPermi('product:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return AjaxResult.success(tProductService.selectTProductByProductId(productId));
    }

    /**
     * 新增商品信息
     */
//    @PreAuthorize("@ss.hasPermi('product:product:add')")
    @Log(title = "商品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProduct tProduct)
    {
        return toAjax(tProductService.insertTProduct(tProduct));
    }

    /**
     * 修改商品信息
     */
//    @PreAuthorize("@ss.hasPermi('product:product:edit')")
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProduct tProduct)
    {
        return toAjax(tProductService.updateTProduct(tProduct));
    }

    /**
     * 删除商品信息
     */
//    @PreAuthorize("@ss.hasPermi('product:product:remove')")
    @Log(title = "商品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(tProductService.deleteTProductByProductIds(productIds));
    }


}
