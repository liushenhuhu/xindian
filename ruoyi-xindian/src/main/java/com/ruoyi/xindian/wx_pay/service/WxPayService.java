package com.ruoyi.xindian.wx_pay.service;

public interface WxPayService {

    void processOrder(String xml) throws Exception;



    void visitPay(String xml) throws Exception;
}
