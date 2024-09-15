package com.ruoyi.xindian.discount_code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.discount_code.domain.DiscountCode;
import com.ruoyi.xindian.discount_code.service.DiscountCodeService;
import com.ruoyi.xindian.discount_code.mapper.DiscountCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author w
* @description 针对表【discount_code】的数据库操作Service实现
* @createDate 2024-09-13 11:29:42
*/
@Service
public class DiscountCodeServiceImpl extends ServiceImpl<DiscountCodeMapper, DiscountCode>
    implements DiscountCodeService{

    @Autowired
    private DiscountCodeMapper discountCodeMapper;

    /**
     * 查询医师优惠
     *
     * @param promotionId 医师优惠主键
     * @return 医师优惠
     */
    @Override
    public DiscountCode selectDiscountCodeByPromotionId(Long promotionId)
    {
        return discountCodeMapper.selectDiscountCodeByPromotionId(promotionId);
    }

    /**
     * 查询医师优惠列表
     *
     * @param discountCode 医师优惠
     * @return 医师优惠
     */
    @Override
    public List<DiscountCode> selectDiscountCodeList(DiscountCode discountCode)
    {
        return discountCodeMapper.selectDiscountCodeList(discountCode);
    }

    /**
     * 新增医师优惠
     *
     * @param discountCode 医师优惠
     * @return 结果
     */
    @Override
    public int insertDiscountCode(DiscountCode discountCode)
    {
        discountCode.setCreateTime(DateUtils.getNowDate());
        return discountCodeMapper.insertDiscountCode(discountCode);
    }

    /**
     * 修改医师优惠
     *
     * @param discountCode 医师优惠
     * @return 结果
     */
    @Override
    public int updateDiscountCode(DiscountCode discountCode)
    {
        return discountCodeMapper.updateDiscountCode(discountCode);
    }

    /**
     * 批量删除医师优惠
     *
     * @param promotionIds 需要删除的医师优惠主键
     * @return 结果
     */
    @Override
    public int deleteDiscountCodeByPromotionIds(Long[] promotionIds)
    {
        return discountCodeMapper.deleteDiscountCodeByPromotionIds(promotionIds);
    }

    /**
     * 删除医师优惠信息
     *
     * @param promotionId 医师优惠主键
     * @return 结果
     */
    @Override
    public int deleteDiscountCodeByPromotionId(Long promotionId)
    {
        return discountCodeMapper.deleteDiscountCodeByPromotionId(promotionId);
    }

    @Override
    public DiscountCode getCode(String code) {
        return discountCodeMapper.selectOne(new LambdaQueryWrapper<DiscountCode>().eq(DiscountCode::getPromotionCode,code).eq(DiscountCode::getStatus,0).last("limit 1"));
    }
}




