package com.ruoyi.xindian.wx_pay.service;

import java.security.GeneralSecurityException;
import java.util.Map;

public interface WxPayService {

    void processOrder(String xml) throws Exception;


}
