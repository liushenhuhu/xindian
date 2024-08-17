package com.ruoyi.xindian.wSuryvey.service;

import com.ruoyi.xindian.wSuryvey.domain.PurchaseLimitation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【purchase_limitation】的数据库操作Service
* @createDate 2024-08-16 15:09:28
*/
public interface PurchaseLimitationService extends IService<PurchaseLimitation> {



    List<PurchaseLimitation> selectPurchaseLimitationList(PurchaseLimitation purchaseLimitation);

    int insertPurchaseLimitation(PurchaseLimitation purchaseLimitation);

}
