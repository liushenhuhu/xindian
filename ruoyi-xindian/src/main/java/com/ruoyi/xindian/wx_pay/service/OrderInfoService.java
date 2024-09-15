package com.ruoyi.xindian.wx_pay.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.xindian.order.vo.OrderVo;
import com.ruoyi.xindian.order.vo.ShipaddressVo;
import com.ruoyi.xindian.order.vo.UserAddressVo;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.enums.OrderStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {


    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    OrderInfo selectTOrderInfoById(String id);


    OrderInfo createOrderByProductId(String orderId);

    void saveCodeUrl(String orderNo, String codeUrl);

    List<OrderInfo> listOrderByCreateTimeDesc();

    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) throws Exception;

    String getOrderStatus(String orderNo);

    List<OrderInfo> getNoPayOrderByDuration(int minutes);

    OrderInfo getOrderByOrderNo(String orderNo);

    /**
     * 通过id查询订单
     * @param id
     * @return
     */
    OrderInfo createOrderByOrderID(String id);

    void updateRefundByOrdersNum(String id);

    /**
     * 通过用户查询对应的所有订单
     * @param loginUser
     * @return
     */
    List<OrderInfo> selectUserOrderList(LoginUser loginUser,OrderInfo orderInfo);

    /**
     * 通过Id删除订单
     * @param orderId
     * @return
     */
    Boolean deleteOrder(String orderId);


    Boolean deleteOrderById(String id);

    /**
     * 添加订单
     * @param request
     * @return
     */
    String addOrder(HttpServletRequest request, OrderVo orderVo, boolean isStatus) throws Exception;


    /**
     * 添加订单
     * @param request
     * @param productId
     * @param sum
     * @return
     */
    String addKpOrFwOrder(HttpServletRequest request, Long productId, Integer sum);
    String addLeaseOrder(HttpServletRequest request,Long productId,Integer sum,String equipmentCode);



    String addBGOrder(HttpServletRequest request, Long productId,String pId);


    void redisOrderKey(String orderId);


    /**
     * web页面查询所有订单数据
     * @param orderId
     * @param userPhone
     * @param orderState
     * @return
     */
    List<OrderInfo> webOrderList(String orderId, String userPhone, String orderState,String orderStatus);

    Boolean updateAddress(ShipaddressVo shipaddressVo);

    /**
     * 通过id查询订单的所有信息
     * @param id
     * @return
     */
    OrderInfo ListOrderId(String id);



    List<OrderInfo> selectOrderByPId(String pId);

    OrderInfo selectTOrderInfoByUserId(Long userId,String equipmentCode);

}
