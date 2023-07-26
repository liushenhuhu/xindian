package com.ruoyi.xindian.product.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.framework.web.domain.server.SysFile;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.product.domain.ProductImgs;
import com.ruoyi.xindian.product.domain.TProductDto;
;
import com.ruoyi.xindian.wx_pay.domain.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xindian.product.domain.TProduct;
import com.ruoyi.xindian.product.service.ITProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    @Autowired
    private ISysUserService userService;

    @Value("${ruoyi.url}")
    private String url;

    /**
     * 查询商品信息列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(TProduct tProduct)
    {
        tProduct.setType("商品");
        startPage();
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        List<TProductDto> resList = new ArrayList<>();
        for (TProduct product : list) {
            TProductDto tProductDto = new TProductDto();
            product.setPrice(product.getPrice().multiply(new BigDecimal("0.01")));
            product.setDiscount(product.getDiscount().multiply(new BigDecimal("0.01")));
            BeanUtils.copyProperties(product,tProductDto);
            List<String> productImgs = tProductService.selectAllImages(product.getProductId());
            System.out.println(productImgs);
            tProductDto.setBottomImg(productImgs);
            resList.add(tProductDto);
        }
        return getTable(resList,new PageInfo(list).getTotal());
    }


    /**
     * web端 调用接口
     * @param tProduct
     * @return
     */
    @GetMapping("/web/webList")
    public TableDataInfo webList(TProduct tProduct)
    {
        startPage();
        List<TProduct> list = tProductService.selectTProductListToWeb(tProduct);
        List<TProductDto> resList = new ArrayList<>();
        for (TProduct product : list) {
            TProductDto tProductDto = new TProductDto();
            product.setPrice(product.getPrice().multiply(new BigDecimal("0.01")));
            product.setDiscount(product.getDiscount().multiply(new BigDecimal("0.01")));
            BeanUtils.copyProperties(product,tProductDto);
            List<String> productImgs = tProductService.selectAllImages(product.getProductId());
            System.out.println(productImgs);
            tProductDto.setBottomImg(productImgs);
            resList.add(tProductDto);
        }
        return getTable(resList,new PageInfo(list).getTotal());
    }


    /**
     * 查询商品服务列表
     * @param tProduct
     * @return
     */
    @GetMapping("/fwList")
    public TableDataInfo fwList(TProduct tProduct){
        tProduct.setType("服务");
        startPage();
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        for (TProduct product : list) {
            product.setPrice(product.getPrice().multiply(new BigDecimal("0.01")));
            product.setDiscount(product.getDiscount().multiply(new BigDecimal("0.01")));
        }
        return getDataTable(list);
    }
    /**
     * 查询商品服务列表
     * @param tProduct
     * @return
     */
    @GetMapping("/kpList")
    public TableDataInfo kpList(TProduct tProduct){
        tProduct.setType("卡片");
        startPage();
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        for (TProduct product : list) {
            product.setPrice(product.getPrice().multiply(new BigDecimal("0.01")));
            product.setDiscount(product.getDiscount().multiply(new BigDecimal("0.01")));
        }
        return getDataTable(list);
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
    public AjaxResult getInfo(@PathVariable("productId") Long productId) {
        TProduct product = tProductService.selectTProductByProductId(productId);
        List<String> productImgs = tProductService.selectAllImages(product.getProductId());
        product.setStringImg(productImgs);
        return AjaxResult.success(product);
    }
    @GetMapping(value = "/web/getProductInfo/{productId}")
    public AjaxResult getProductInfo(@PathVariable("productId") Long productId) {
        TProduct product = tProductService.selectTProductByProductId(productId);
        return AjaxResult.success(product);
    }
    //查询当前商品的所有介绍图片
    @GetMapping(value = "/web/getImgs/{productId}")
    public AjaxResult getImgs(@PathVariable("productId") Long productId) {
        List<ProductImgs> productImgs = tProductService.selectIdAndImg(productId);
        return AjaxResult.success(productImgs);
    }
    /**
     * 新增商品信息
     */
//    @PreAuthorize("@ss.hasPermi('product:product:add')")
    @Log(title = "商品信息", businessType = BusinessType.INSERT)
    @PostMapping("/web/add")
    public AjaxResult add(@RequestBody TProduct tProduct)
    {
        return toAjax(tProductService.insertTProduct(tProduct));
    }
    @Log(title = "商品信息和图片", businessType = BusinessType.INSERT)
    @PostMapping("/web/addUpload")
    public AjaxResult addUpload(HttpServletRequest request, HttpServletRequest response, HttpSession session) {
        String product = request.getParameter("product");
        TProduct tProduct = JSON.parseObject(product, TProduct.class);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("files");

        try
        {
            for(int i=0;i<files.size();i++) {//循环单个上传
                String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), files.get(i), MimeTypeUtils.IMAGE_EXTENSION);
                if(i==0){
                    tProduct.setProductUrl(url+avatar);
                }else {
                    tProduct.setUrlOne(url+avatar);
                }
            }
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
        return toAjax(tProductService.insertTProduct(tProduct));
    }
    /**
     * 修改商品信息
     */
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProduct tProduct)
    {

        return toAjax(tProductService.updateTProduct(tProduct));
    }
//    @PreAuthorize("@ss.hasPermi('product:product:edit')")
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PostMapping("/web/update")
    public AjaxResult update(@RequestParam(value = "file1",required = false) MultipartFile file1,
                             @RequestParam(value = "file2",required = false) MultipartFile file2,
                             @RequestParam("product")String tProduct)
    {
        TProduct tProduct1 = JSONObject.parseObject(tProduct, TProduct.class);
        try{
            if (!file1.isEmpty())
            {
                String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file1, MimeTypeUtils.IMAGE_EXTENSION);
                tProduct1.setProductUrl(url+avatar);
            }
            if (!file2.isEmpty())
            {
                String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file2, MimeTypeUtils.IMAGE_EXTENSION);
                tProduct1.setUrlOne(url+avatar);
            }
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
        return toAjax(tProductService.updateTProduct(tProduct1));
    }

    /**
     * 删除商品信息
     */
//    @PreAuthorize("@ss.hasPermi('product:product:remove')")
    @Log(title = "商品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/web/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(tProductService.deleteTProductByProductIds(productIds));
    }

    /**
     * 商品图片上传
     */
    @PostMapping("/web/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception
    {
        if (!file.isEmpty())
        {
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", url+avatar);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 批量上传
     * @param files
     * @return
     */
    @PostMapping(value ="/web/batchUploadFile/{productId}")
//    @ApiOperation(value = "文件上传请求")
    public AjaxResult batchUploadFile(@RequestParam(value = "files",required = false) MultipartFile[] files,
                                      @PathVariable("productId") Long productId,
                                      @RequestParam(value = "delImgs",required = false) Integer[] delImgs) {
        if(delImgs!=null){
            //根据id删除商品介绍图片
            tProductService.deleteByIdImg(delImgs);
        }

        if(files==null){
            return AjaxResult.success("操作成功");
        }
        List<ProductImgs> products = new ArrayList<>();
//        //根据id删除所有介绍图片
//        tProductService.deleteAllImages(productId);

    try{
//        System.out.println(files[0].getSize());

            for (int i = 0; i < files.length; i++) {
                //循环单个上传
                String avatar = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), files[i], MimeTypeUtils.IMAGE_EXTENSION);
                ProductImgs productImgs=new ProductImgs();
                productImgs.setProductId(productId);
                productImgs.setImg(url + avatar);
                products.add(productImgs);
            }
        }

        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
        return toAjax(tProductService.insertProductImgs(products));
        }

    }

