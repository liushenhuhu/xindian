package com.ruoyi.xindian.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.order.domain.WxpayFeedback;
import com.ruoyi.xindian.order.service.WxpayFeedbackService;
import com.ruoyi.xindian.order.mapper.WxpayFeedbackMapper;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.enums.OrderStatus;
import com.ruoyi.xindian.wx_pay.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 13401
* @description 针对表【wxpay_feedback】的数据库操作Service实现
* @createDate 2023-07-08 14:32:48
*/
@Service
public class WxpayFeedbackServiceImpl extends ServiceImpl<WxpayFeedbackMapper, WxpayFeedback>
    implements WxpayFeedbackService{


    @Autowired
    private WxpayFeedbackMapper wxpayFeedbackMapper;


    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param feedbackiId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public WxpayFeedback selectWxpayFeedbackByFeedbackiId(Long feedbackiId)
    {
        return wxpayFeedbackMapper.selectWxpayFeedbackByFeedbackiId(feedbackiId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param wxpayFeedback 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<WxpayFeedback> selectWxpayFeedbackList(WxpayFeedback wxpayFeedback)
    {
        return wxpayFeedbackMapper.selectWxpayFeedbackList(wxpayFeedback);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param wxpayFeedback 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertWxpayFeedback(WxpayFeedback wxpayFeedback)
    {
        wxpayFeedback.setCreateTime(DateUtils.getNowDate());
        wxpayFeedbackMapper.insertWxpayFeedback(wxpayFeedback);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(wxpayFeedback.getOrderId());
        orderInfo.setOrderStatus(OrderStatus.ORDER_BACK.getType());

        return  orderInfoMapper.updateById(orderInfo);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param wxpayFeedback 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateWxpayFeedback(WxpayFeedback wxpayFeedback)
    {
        wxpayFeedback.setUpdateTime(DateUtils.getNowDate());
        wxpayFeedbackMapper.updateWxpayFeedback(wxpayFeedback);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(wxpayFeedback.getOrderId());
        orderInfo.setOrderStatus(wxpayFeedback.getStatus());
        return orderInfoMapper.updateById(orderInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param feedbackiIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteWxpayFeedbackByFeedbackiIds(Long[] feedbackiIds)
    {
        return wxpayFeedbackMapper.deleteWxpayFeedbackByFeedbackiIds(feedbackiIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param feedbackiId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteWxpayFeedbackByFeedbackiId(Long feedbackiId)
    {
        return wxpayFeedbackMapper.deleteWxpayFeedbackByFeedbackiId(feedbackiId);
    }


}




