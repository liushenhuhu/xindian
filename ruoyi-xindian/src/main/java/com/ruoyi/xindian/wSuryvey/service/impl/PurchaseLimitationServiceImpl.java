package com.ruoyi.xindian.wSuryvey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.wSuryvey.domain.PurchaseLimitation;
import com.ruoyi.xindian.wSuryvey.service.PurchaseLimitationService;
import com.ruoyi.xindian.wSuryvey.mapper.PurchaseLimitationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author w
* @description 针对表【purchase_limitation】的数据库操作Service实现
* @createDate 2024-08-16 15:09:28
*/
@Service
public class PurchaseLimitationServiceImpl extends ServiceImpl<PurchaseLimitationMapper, PurchaseLimitation>
    implements PurchaseLimitationService{




    @Resource
    private PurchaseLimitationMapper purchaseLimitationMapper;


    @Override
    public List<PurchaseLimitation> selectPurchaseLimitationList(PurchaseLimitation purchaseLimitation) {
        return purchaseLimitationMapper.selectList(new LambdaQueryWrapper<PurchaseLimitation>().eq(PurchaseLimitation::getPatientPhone,purchaseLimitation.getPatientPhone()).eq(PurchaseLimitation::getProductId,purchaseLimitation.getProductId()));
    }

    @Override
    public int insertPurchaseLimitation(PurchaseLimitation purchaseLimitation) {
        return purchaseLimitationMapper.insert(purchaseLimitation);
    }
}




