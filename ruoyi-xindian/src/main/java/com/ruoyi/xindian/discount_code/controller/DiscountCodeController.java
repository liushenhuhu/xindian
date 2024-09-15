package com.ruoyi.xindian.discount_code.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.discount_code.domain.DiscountCode;
import com.ruoyi.xindian.discount_code.service.DiscountCodeService;
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

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医师优惠Controller
 * 
 * @author ruoyi
 * @date 2024-09-13
 */
@RestController
@RequestMapping("/discount_code/code")
public class DiscountCodeController extends BaseController
{
    @Autowired
    private DiscountCodeService discountCodeService;

    /**
     * 查询医师优惠列表
     */
    @PreAuthorize("@ss.hasPermi('discount_code:code:list')")
    @GetMapping("/list")
    public TableDataInfo list(DiscountCode discountCode)
    {
        startPage();
        List<DiscountCode> list = discountCodeService.selectDiscountCodeList(discountCode);
        return getDataTable(list);
    }

    /**
     * 导出医师优惠列表
     */
    @PreAuthorize("@ss.hasPermi('discount_code:code:export')")
    @Log(title = "医师优惠", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DiscountCode discountCode)
    {
        List<DiscountCode> list = discountCodeService.selectDiscountCodeList(discountCode);
        ExcelUtil<DiscountCode> util = new ExcelUtil<DiscountCode>(DiscountCode.class);
        util.exportExcel(response, list, "医师优惠数据");
    }

    /**
     * 获取医师优惠详细信息
     */
    @PreAuthorize("@ss.hasPermi('discount_code:code:query')")
    @GetMapping(value = "/{promotionId}")
    public AjaxResult getInfo(@PathVariable("promotionId") Long promotionId)
    {
        return AjaxResult.success(discountCodeService.selectDiscountCodeByPromotionId(promotionId));
    }

    /**
     * 新增医师优惠
     */
    @PreAuthorize("@ss.hasPermi('discount_code:code:add')")
    @Log(title = "医师优惠", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DiscountCode discountCode)
    {
        return toAjax(discountCodeService.insertDiscountCode(discountCode));
    }

    /**
     * 修改医师优惠
     */
    @PreAuthorize("@ss.hasPermi('discount_code:code:edit')")
    @Log(title = "医师优惠", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DiscountCode discountCode)
    {
        return toAjax(discountCodeService.updateDiscountCode(discountCode));
    }

    /**
     * 删除医师优惠
     */
    @PreAuthorize("@ss.hasPermi('discount_code:code:remove')")
    @Log(title = "医师优惠", businessType = BusinessType.DELETE)
	@DeleteMapping("/{promotionIds}")
    public AjaxResult remove(@PathVariable Long[] promotionIds)
    {
        return toAjax(discountCodeService.deleteDiscountCodeByPromotionIds(promotionIds));
    }

    @GetMapping("/getCode")
    public AjaxResult getCode(String code)
    {
        return AjaxResult.success(discountCodeService.getCode(code));
    }
}
