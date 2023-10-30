package com.ruoyi.xindian.wx_pay.service.impl;


import com.ruoyi.xindian.hospital.domain.VisitAppointment;
import com.ruoyi.xindian.hospital.mapper.VisitAppointmentMapper;
import com.ruoyi.xindian.hospital.service.IVisitAppointmentService;
import com.ruoyi.xindian.wx_pay.enums.OrderStatus;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.service.PaymentInfoService;
import com.ruoyi.xindian.wx_pay.service.RefundInfoService;
import com.ruoyi.xindian.wx_pay.service.WxPayService;
import com.ruoyi.xindian.wx_pay.util.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {


    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private PaymentInfoService paymentInfoService;


    @Resource
    private IVisitAppointmentService visitAppointmentService;

    private final ReentrantLock lock = new ReentrantLock();


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(String xml) throws Exception {
        log.info("处理订单");
        Map<String, Object> bodyMap = WXPayUtil.xmlToMap(xml);
        String orderNo = (String)bodyMap.get("out_trade_no");

        /*在对业务数据进行状态检查和处理之前，
        要采用数据锁进行并发控制，
        以避免函数重入造成的数据混乱*/
        //尝试获取锁：
        // 成功获取则立即返回true，获取失败则立即返回false。不必一直等待锁的释放
        if(lock.tryLock()){
            try {
                //处理重复的通知
                //接口调用的幂等性：无论接口被调用多少次，产生的结果是一致的。
                String orderStatus = orderInfoService.getOrderStatus(orderNo);
                if(!OrderStatus.NOTPAY.getType().equals(orderStatus)){
                    return;
                }

                //更新订单状态
                orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);


                //记录支付日志
                paymentInfoService.createPaymentInfo(xml);
            } finally {
                //要主动释放锁
                lock.unlock();
            }
        }
    }

    @Override
    public void visitPay(String xml) throws Exception {
        log.info("处理预约");
        Map<String, Object> bodyMap = WXPayUtil.xmlToMap(xml);
        String orderNo = (String)bodyMap.get("out_trade_no");

        /*在对业务数据进行状态检查和处理之前，
        要采用数据锁进行并发控制，
        以避免函数重入造成的数据混乱*/
        //尝试获取锁：
        // 成功获取则立即返回true，获取失败则立即返回false。不必一直等待锁的释放
        if(lock.tryLock()){
            try {
                //处理重复的通知
                //接口调用的幂等性：无论接口被调用多少次，产生的结果是一致的。
                VisitAppointment visitAppointmentOrderNo = visitAppointmentService.getVisitAppointmentOrderNo(orderNo);
                if (visitAppointmentOrderNo==null){
                    return;
                }
                if(!"0".equals(visitAppointmentOrderNo.getPayStatus())){
                    return;
                }

                visitAppointmentService.updateVisitAppointmentOrderStatus(orderNo,"1");
                //记录支付日志
//                paymentInfoService.createPaymentInfo(xml);
            } finally {
                //要主动释放锁
                lock.unlock();
            }
        }
    }


//    /**
//     * 对称解密
//     * @param bodyMap
//     * @return
//     */
//    private String decryptFromResource(Map<String, Object> bodyMap) throws GeneralSecurityException {
//
//        log.info("密文解密");
//
//        //通知数据
//        Map<String, String> resourceMap = (Map) bodyMap.get("resource");
//        //数据密文
//        String ciphertext = resourceMap.get("ciphertext");
//        //随机串
//        String nonce = resourceMap.get("nonce");
//        //附加数据
//        String associatedData = resourceMap.get("associated_data");
//
//        log.info("密文 ===> {}", ciphertext);
//        AesUtil aesUtil = new AesUtil(wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
//        String plainText = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
//                nonce.getBytes(StandardCharsets.UTF_8),
//                ciphertext);
//
//        log.info("明文 ===> {}", plainText);
//
//        return plainText;
//    }



}
