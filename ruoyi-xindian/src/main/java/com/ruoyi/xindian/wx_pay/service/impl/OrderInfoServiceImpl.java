package com.ruoyi.xindian.wx_pay.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.Product;
import com.ruoyi.xindian.wx_pay.enums.OrderStatus;
import com.ruoyi.xindian.wx_pay.mapper.OrderInfoMapper;
import com.ruoyi.xindian.wx_pay.mapper.ProductMapper;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.service.RefundInfoService;
import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;


    @Resource
    private SysUserMapper sysUserMapper;





    @Transactional
    @Override
    public OrderInfo createOrderByProductId(Long productId, LoginUser loginUser) {

        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUserId());
        //查找已存在但未支付的订单
        OrderInfo orderInfo = this.getNoPayOrderByProductId(productId,sysUser.getUserId());
        if( orderInfo != null){
            return orderInfo;
        }
        System.out.println(new BaseController().getUserId());

        //获取商品信息
        Product product = productMapper.selectById(productId);

        //生成订单
        orderInfo = new OrderInfo();
        orderInfo.setTitle(product.getTitle());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo()); //订单号
        orderInfo.setProductId(productId);
        orderInfo.setTotalFee(product.getPrice()); //分
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        orderInfo.setBody(product.getTitle());
        orderInfo.setOpenId(sysUser.getOpenId());
        orderInfo.setUserId(loginUser.getUserId());
        baseMapper.insert(orderInfo);
        return orderInfo;
    }

    /**
     * 存储订单二维码
     * @param orderNo
     * @param codeUrl
     */
    @Override
    public void saveCodeUrl(String orderNo, String codeUrl) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCodeUrl(codeUrl);

        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 查询订单列表，并倒序查询
     * @return
     */
    @Override
    public List<OrderInfo> listOrderByCreateTimeDesc() {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>().orderByDesc("create_time");
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据订单号更新订单状态
     * @param orderNo
     * @param orderStatus
     */
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {

        log.info("更新订单状态 ===> {}", orderStatus.getType());

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(orderStatus.getType());


        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 根据订单号获取订单状态
     * @param orderNo
     * @return
     */
    @Override
    public String getOrderStatus(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        if(orderInfo == null){
            return null;
        }
        return orderInfo.getOrderStatus();
    }

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     * @param minutes
     * @return
     */
    @Override
    public List<OrderInfo> getNoPayOrderByDuration(int minutes) {

        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
        queryWrapper.le("create_time", instant);

        List<OrderInfo> orderInfoList = baseMapper.selectList(queryWrapper);

        return orderInfoList;
    }

    /**
     * 根据订单号获取订单
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getOrderByOrderNo(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);

        return orderInfo;
    }

    /**
     * 通过id查寻订单数据
     * @param id
     * @return
     */
    @Override
    public OrderInfo createOrderByOrderID(Long id) {
        OrderInfo orderInfo = orderInfoMapper.selectById(id);
        return orderInfo;
    }

    /**
     * 通过商户编号来进行数据的修改
     * @param orderNum
     */
    @Transactional
    @Override
    public void updateRefundByOrdersNum(String orderNum) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(OrderStatus.REFUND_SUCCESS.getType());
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNum);

        orderInfoMapper.update(orderInfo,queryWrapper);


    }


    /**
     * 根据商品id查询未支付订单
     * 防止重复创建订单对象
     * @param productId
     * @return
     */
    private OrderInfo getNoPayOrderByProductId(Long productId,Long userId) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("product_id", productId);
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
//        queryWrapper.eq("user_id", userId);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        return orderInfo;
    }
}
