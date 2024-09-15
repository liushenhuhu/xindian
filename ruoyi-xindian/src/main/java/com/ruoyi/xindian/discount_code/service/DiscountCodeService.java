package com.ruoyi.xindian.discount_code.service;

import com.ruoyi.xindian.discount_code.domain.DiscountCode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【discount_code】的数据库操作Service
* @createDate 2024-09-13 11:29:42
*/
public interface DiscountCodeService extends IService<DiscountCode> {

    /**
     * 查询医师优惠
     *
     * @param promotionId 医师优惠主键
     * @return 医师优惠
     */
    public DiscountCode selectDiscountCodeByPromotionId(Long promotionId);

    /**
     * 查询医师优惠列表
     *
     * @param discountCode 医师优惠
     * @return 医师优惠集合
     */
    public List<DiscountCode> selectDiscountCodeList(DiscountCode discountCode);

    /**
     * 新增医师优惠
     *
     * @param discountCode 医师优惠
     * @return 结果
     */
    public int insertDiscountCode(DiscountCode discountCode);

    /**
     * 修改医师优惠
     *
     * @param discountCode 医师优惠
     * @return 结果
     */
    public int updateDiscountCode(DiscountCode discountCode);

    /**
     * 批量删除医师优惠
     *
     * @param promotionIds 需要删除的医师优惠主键集合
     * @return 结果
     */
    public int deleteDiscountCodeByPromotionIds(Long[] promotionIds);

    /**
     * 删除医师优惠信息
     *
     * @param promotionId 医师优惠主键
     * @return 结果
     */
    public int deleteDiscountCodeByPromotionId(Long promotionId);

    DiscountCode getCode(String code);
}
