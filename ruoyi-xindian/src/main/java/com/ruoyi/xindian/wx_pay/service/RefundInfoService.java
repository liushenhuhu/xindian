package com.ruoyi.xindian.wx_pay.service;






import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.wx_pay.domain.RefundInfo;

import java.util.List;

public interface RefundInfoService extends IService<RefundInfo> {

    RefundInfo createRefundByOrderNo(String orderNo, String reason);

    void updateRefund(String content) throws Exception;

    List<RefundInfo> getNoRefundOrderByDuration(int minutes);
}
