package com.ruoyi.xindian.wx_pay.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xindian.wx_pay.domain.PaymentInfo;

import java.util.List;

/**
* @author 13401
* @description 针对表【t_payment_info】的数据库操作Mapper
* @createDate 2023-07-01 14:48:47
* @Entity com.ruoyi.xindian.wx_pay.domain.PaymentInfo
*/
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

    List<PaymentInfo> selectTPaymentInfoList(PaymentInfo tPaymentInfo);
}




