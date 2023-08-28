package com.ruoyi.xindian.wx_pay.service;

import com.ruoyi.xindian.wx_pay.domain.PaymentInfo;

import java.util.List;

public interface PaymentInfoService {

    void createPaymentInfo(String xml) throws Exception;


    /**
     * 查询支付日志
     *
     * @param id 支付日志主键
     * @return 支付日志
     */
    public PaymentInfo selectTPaymentInfoById(String id);

    /**
     * 查询支付日志列表
     *
     * @param tPaymentInfo 支付日志
     * @return 支付日志集合
     */
    public List<PaymentInfo> selectTPaymentInfoList(PaymentInfo tPaymentInfo);

    /**
     * 新增支付日志
     *
     * @param tPaymentInfo 支付日志
     * @return 结果
     */
    public int insertTPaymentInfo(PaymentInfo tPaymentInfo);

    /**
     * 修改支付日志
     *
     * @param tPaymentInfo 支付日志
     * @return 结果
     */
    public int updateTPaymentInfo(PaymentInfo tPaymentInfo);

    /**
     * 批量删除支付日志
     *
     * @param ids 需要删除的支付日志主键集合
     * @return 结果
     */
    public int deleteTPaymentInfoByIds(String[] ids);

    /**
     * 删除支付日志信息
     *
     * @param id 支付日志主键
     * @return 结果
     */
    public int deleteTPaymentInfoById(String id);
}
