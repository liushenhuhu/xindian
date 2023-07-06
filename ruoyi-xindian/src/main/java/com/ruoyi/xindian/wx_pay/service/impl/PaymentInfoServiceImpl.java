package com.ruoyi.xindian.wx_pay.service.impl;





import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.wx_pay.domain.PaymentInfo;
import com.ruoyi.xindian.wx_pay.mapper.PaymentInfoMapper;
import com.ruoyi.xindian.wx_pay.service.PaymentInfoService;
import com.ruoyi.xindian.wx_pay.util.PayType;
import com.ruoyi.xindian.wx_pay.util.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    /**
     * 记录支付日志
     * @param xml
     */
    @Override
    public void createPaymentInfo(String xml) throws Exception {

        log.info("记录支付日志");

        Map<String, Object> plainTextMap = WXPayUtil.xmlToMap(xml);

        //订单号
        String orderNo = (String)plainTextMap.get("out_trade_no");
        //业务编号
        String transactionId = (String)plainTextMap.get("transaction_id");
        //支付类型
        String tradeType = (String)plainTextMap.get("trade_type");
        //交易状态
        String tradeState = (String)plainTextMap.get("trade_state");
        //用户实际支付金额
        Map<String, Object> amount = (Map)plainTextMap.get("amount");
        BigDecimal payerTotal = BigDecimal.valueOf(((BigDecimal) amount.get("payer_total")).doubleValue());

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNo(orderNo);
        paymentInfo.setPaymentType(PayType.WXPAY.getType());
        paymentInfo.setTransactionId(transactionId);
        paymentInfo.setTradeType(tradeType);
        paymentInfo.setTradeState(tradeState);
        paymentInfo.setPayerTotal(payerTotal);
        paymentInfo.setContent(xml);

        baseMapper.insert(paymentInfo);
    }
}
