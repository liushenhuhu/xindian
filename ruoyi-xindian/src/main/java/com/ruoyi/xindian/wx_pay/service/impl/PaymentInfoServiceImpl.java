package com.ruoyi.xindian.wx_pay.service.impl;





import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.PaymentInfo;
import com.ruoyi.xindian.wx_pay.mapper.OrderInfoMapper;
import com.ruoyi.xindian.wx_pay.mapper.PaymentInfoMapper;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.service.PaymentInfoService;
import com.ruoyi.xindian.wx_pay.util.PayType;
import com.ruoyi.xindian.wx_pay.util.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {


    @Resource
    private PaymentInfoMapper paymentInfoMapper;

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private SysUserMapper sysUserMapper;
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
        String tradeState = (String)plainTextMap.get("result_code");
        String totalFee = (String)plainTextMap.get("total_fee");
        //用户实际支付金额

        OrderInfo orderByOrderNo = orderInfoService.getOrderByOrderNo(orderNo);

        SysUser sysUser = sysUserMapper.selectUserById(orderByOrderNo.getUserId());


        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNo(orderNo);
        paymentInfo.setPaymentType(PayType.WXPAY.getType());
        paymentInfo.setTransactionId(transactionId);
        paymentInfo.setTradeType(tradeType);
        paymentInfo.setTradeState(tradeState);
        paymentInfo.setPayerTotal(new BigDecimal(totalFee));
        paymentInfo.setContent(xml);
        paymentInfo.setUserName(sysUser.getUserName());
        paymentInfo.setOrderTitle(orderByOrderNo.getTitle());

        baseMapper.insert(paymentInfo);
    }



    /**
     * 查询支付日志
     *
     * @param id 支付日志主键
     * @return 支付日志
     */
    @Override
    public PaymentInfo selectTPaymentInfoById(String id)
    {
        return paymentInfoMapper.selectById(id);
    }

    /**
     * 查询支付日志列表
     *
     * @param tPaymentInfo 支付日志
     * @return 支付日志
     */
    @Override
    public List<PaymentInfo> selectTPaymentInfoList(PaymentInfo tPaymentInfo)
    {
        return paymentInfoMapper.selectTPaymentInfoList(tPaymentInfo);
    }

    /**
     * 新增支付日志
     *
     * @param tPaymentInfo 支付日志
     * @return 结果
     */
    @Override
    public int insertTPaymentInfo(PaymentInfo tPaymentInfo)
    {
        tPaymentInfo.setCreateTime(DateUtils.getNowDate());
        return paymentInfoMapper.insert(tPaymentInfo);
    }

    /**
     * 修改支付日志
     *
     * @param tPaymentInfo 支付日志
     * @return 结果
     */
    @Override
    public int updateTPaymentInfo(PaymentInfo tPaymentInfo)
    {
        tPaymentInfo.setUpdateTime(DateUtils.getNowDate());
        return paymentInfoMapper.updateById(tPaymentInfo);
    }

    /**
     * 批量删除支付日志
     *
     * @param ids 需要删除的支付日志主键
     * @return 结果
     */
    @Override
    public int deleteTPaymentInfoByIds(String[] ids)
    {
        return paymentInfoMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除支付日志信息
     *
     * @param id 支付日志主键
     * @return 结果
     */
    @Override
    public int deleteTPaymentInfoById(String id)
    {
        return paymentInfoMapper.deleteById(id);
    }
}
