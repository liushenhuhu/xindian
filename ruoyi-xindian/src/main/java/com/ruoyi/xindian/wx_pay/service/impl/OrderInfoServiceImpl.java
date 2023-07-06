package com.ruoyi.xindian.wx_pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.order.domain.UserAddress;
import com.ruoyi.xindian.order.mapper.UserAddressMapper;
import com.ruoyi.xindian.order.vo.ShipaddressVo;
import com.ruoyi.xindian.order.vo.UserAddressVo;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import com.ruoyi.xindian.shipAddress.mapper.ShipAddressMapper;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.Product;
import com.ruoyi.xindian.wx_pay.domain.SuborderOrderInfo;
import com.ruoyi.xindian.wx_pay.enums.OrderStatus;
import com.ruoyi.xindian.wx_pay.mapper.OrderInfoMapper;
import com.ruoyi.xindian.wx_pay.mapper.ProductMapper;
import com.ruoyi.xindian.wx_pay.mapper.SuborderOrderInfoMapper;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private TokenService tokenService;
    @Resource
    private OrderInfoMapper orderInfoMapper;


    @Resource
    private SuborderOrderInfoMapper suborderOrderInfoMapper;


    @Resource
    private SysUserMapper sysUserMapper;



    @Resource
    private ProductMapper productMapper;


    @Resource
    private ShipAddressMapper shipAddressMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public OrderInfo selectTOrderInfoById(String id) {

        return orderInfoMapper.selectTOrderInfoById(id);
    }

    /**
     * 通过订单id查询订单信息
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo createOrderByProductId(String orderId) {

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

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
    public OrderInfo createOrderByOrderID(String id) {
        OrderInfo orderInfo = orderInfoMapper.selectById(id);
        return orderInfo;
    }

    /**
     * 通过商户编号来进行数据的修改
     * @param id
     */
    @Transactional
    @Override
    public void updateRefundByOrdersNum(String id) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(OrderStatus.REFUND_SUCCESS.getType());
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);

        orderInfoMapper.update(orderInfo,queryWrapper);


    }

    /**
     * 查询当前用户的所有订单
     * @param loginUser
     * @return
     */
    @Override
    public List<OrderInfo> selectUserOrderList(LoginUser loginUser) {
        List<OrderInfo> orderInfoList = orderInfoMapper.selectAllList(loginUser.getUser().getUserId());
        return orderInfoList;
    }

    /**
     * 通过id删除订单以及订单子表
     * @param orderId
     * @return
     */
    @Transactional
    @Override
    public Boolean deleteOrder(String orderId) {

        int i = orderInfoMapper.deleteById(orderId);
        if (i>0){
            suborderOrderInfoMapper.delete(new QueryWrapper<SuborderOrderInfo>().eq("order_father", orderId));
            return true;
        }
        return false;
    }

    /**
     * 添加订单
     * @param request
     * @param productId
     * @param sum
     * @param addressId
     * @return
     */
    @Transactional
    @Override
    public Boolean addOrder(HttpServletRequest request, Long productId, Integer sum, Long addressId) {
        Product product = productMapper.selectById(productId);
        if ((product.getProductNum().compareTo(sum) <0)){
            throw new ServiceException("库存不够");
        }

        //获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);

        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(OrderNoUtils.getNo());
        orderInfo.setTitle("购买"+product.getProductName());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
        orderInfo.setUserId(loginUser.getUser().getUserId());
        orderInfo.setTotalFee(new BigDecimal(sum).multiply(product.getDiscount()));
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        orderInfo.setOpenId(sysUser.getOpenId());
        orderInfo.setAddressId(addressId);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfoMapper.insert(orderInfo);

        SuborderOrderInfo suborderOrderInfo = new SuborderOrderInfo();
        suborderOrderInfo.setOrderFather(orderInfo.getId());
        suborderOrderInfo.setProductId(productId);
        suborderOrderInfo.setSum(Long.valueOf(sum));
        suborderOrderInfo.setCreateTime(new Date());
        suborderOrderInfo.setUpdateTime(new Date());

        int insert = suborderOrderInfoMapper.insert(suborderOrderInfo);
        updateProductAdd(sum,productId);


        redisTemplate.opsForValue().set("order:"+orderInfo.getId(),orderInfo,15, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(orderInfo.getId(),productId+","+sum);
        if (insert>0){
            return true;
        }
        return false;

    }

    /**
     * 判断订单过期，删除订单，以及添加库存
     * @param orderId
     */
    @Transactional
    @Override
    public void redisOrderKey(String orderId) {
        //通过订单id查找订单是否已经被支付，如果未支付，则删除
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if (orderInfo.getOrderStatus().equals(OrderStatus.NOTPAY.getType())){
            //查询通过监听过期key来获取到订单id
            String productIdAndSum = (String) redisTemplate.opsForValue().get(orderId);

            deleteOrder(orderId);

            if (productIdAndSum!=null){
                String[] split = productIdAndSum.split(",");
                updateProductDel(Integer.valueOf(split[1]),Long.valueOf(split[0]));
            }
        }
        //删除同时存进去的key，释放资源
        redisTemplate.opsForValue().getOperations().delete(orderId);

    }

    @Override
    public List<OrderInfo> webOrderList(String orderId, String userPhone, String orderState) {
        List<OrderInfo> orderInfoList = orderInfoMapper.selectWebAllList(orderId, userPhone, orderState);
        return orderInfoList;
    }

    @Transactional
    @Override
    public Boolean updateAddress(ShipaddressVo shipaddressVo) {

        if (shipaddressVo.getOrderStatus()==null&&shipaddressVo.getIsUpdate()==null){
            return true;
        }
        if (shipaddressVo.getIsUpdate()!=null&&!"".equals(shipaddressVo.getIsUpdate())){
            ShipAddress shipAddress = new ShipAddress();
            BeanUtils.copyProperties(shipaddressVo,shipAddress);
            shipAddressMapper.insertShipAddress(shipAddress);
               OrderInfo orderInfo = new OrderInfo();
               if (shipaddressVo.getOrderStatus()!=null&&!"".equals(shipaddressVo.getOrderStatus())){
                   orderInfo.setOrderStatus(shipaddressVo.getOrderStatus());
               }
            orderInfo.setAddressId(shipAddress.getAddressId());
            orderInfoMapper.update(orderInfo,new QueryWrapper<OrderInfo>().eq("id",shipaddressVo.getId()));
            return true;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(shipaddressVo.getOrderStatus());
        orderInfoMapper.update(orderInfo,new QueryWrapper<OrderInfo>().eq("id",shipaddressVo.getId()));
        return true;
    }

    /**
     * 通过id查询订单详情
     * @param id
     * @return
     */
    @Override
    public OrderInfo ListOrderId(String id) {
        OrderInfo orderInfo = orderInfoMapper.searchAllById(id);

        return orderInfo;
    }


    /**
     * 创建订单时，修改商品库存
     * @param sum
     * @param productId
     * @return
     */
    private int updateProductAdd(Integer sum,Long productId){

        // 创建一个 UpdateWrapper 对象，指定要更新的表和条件
        UpdateWrapper<Product> updateWrapper = Wrappers.update();
        updateWrapper
                .eq("product_id", productId) // 设置更新的条件，例如根据商品ID进行更新
                .setSql("product_num = product_num - "+sum)
                .setSql("sales = sales + "+sum); // 设置库存减一的更新操作

        // 执行更新操作
        int affectedRows = productMapper.update(null, updateWrapper);
        return affectedRows;
    }

    /**
     * 创建订单时，修改商品库存
     * @param sum
     * @param productId
     * @return
     */
    private int updateProductDel(Integer sum,Long productId){

        // 创建一个 UpdateWrapper 对象，指定要更新的表和条件
        UpdateWrapper<Product> updateWrapper = Wrappers.update();
        updateWrapper
                .eq("product_id", productId) // 设置更新的条件，例如根据商品ID进行更新
                .setSql("product_num = product_num + "+sum)
                .setSql("sales = sales - "+sum); // 设置库存减一的更新操作

        // 执行更新操作
        int affectedRows = productMapper.update(null, updateWrapper);
        return affectedRows;
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
