package com.ruoyi.xindian.wx_pay.service.impl;
import com.google.common.collect.Lists;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.equipment.controller.EquipmentHeadingCodeController;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.fw_log.mapper.FwLogMapper;
import com.ruoyi.xindian.order.vo.ShipaddressVo;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import com.ruoyi.xindian.shipAddress.mapper.ShipAddressMapper;
import com.ruoyi.xindian.util.WxUtil;
import com.ruoyi.xindian.vipPatient.controller.SxReportUnscrambleController;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.vipPatient.service.SxReportUnscrambleService;
import com.ruoyi.xindian.wx_pay.controller.WXPayController;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private TokenService tokenService;
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private IPatientService patientService;
    @Resource
    private SuborderOrderInfoMapper suborderOrderInfoMapper;


    @Autowired
    private IVipPatientService vipPatientService;

    @Resource
    private SysUserMapper sysUserMapper;


    @Resource
    private SxReportUnscrambleService sxReportUnscrambleService;

    @Resource
    private EquipmentHeadingCodeController equipmentHeadingCodeController;

    @Resource
    private FwLogMapper fwLogMapper;

    @Resource
    private ProductMapper productMapper;


    @Resource
    private ShipAddressMapper shipAddressMapper;


    @Resource
    private WXPayController wxPayController;


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
    @Transactional
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) throws Exception {

        OrderInfo orderByOrderNo = getOrderByOrderNo(orderNo);
        List<SuborderOrderInfo> suborderOrderInfos = orderIdAndSuborder(orderByOrderNo.getId());
        for(SuborderOrderInfo c: suborderOrderInfos){

            Product product = productMapper.selectById(c.getProductId());
            log.info("更新订单状态 ===> {}", orderStatus.getType());
            QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
            if (product.getType().equals("服务")||product.getType().equals("卡片")){

                queryWrapper.eq("order_no", orderNo);

                OrderInfo orderInfo = new OrderInfo();

                orderInfo.setOrderStatus(OrderStatus.SERVE_ORDER.getType());

                orderInfo.setOrderState(OrderStatus.ORDER_STATUS.getType());
                baseMapper.update(orderInfo, queryWrapper);

                vipPatient(product,orderByOrderNo.getUserId(),c);

            }else if (product.getType().equals("报告服务")){
                queryWrapper.eq("order_no", orderNo);

                OrderInfo orderInfo = new OrderInfo();

                orderInfo.setOrderStatus(OrderStatus.REPORT_ORDER.getType());

                orderInfo.setOrderState(OrderStatus.ORDER_STATUS.getType());
                baseMapper.update(orderInfo, queryWrapper);

                equipmentHeadingCodeController.ifSubmitOrder(orderByOrderNo.getPId());
            }
            else {
                queryWrapper.eq("order_no", orderNo);
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrderStatus(orderStatus.getType());
                orderInfo.setOrderState(orderStatus.getType());
                baseMapper.update(orderInfo, queryWrapper);
//                WxUtil.send("15286981260");
            }
        }


    }



    public Boolean addSXOrder(Product product ,Long userId,SuborderOrderInfo suborderOrderInfo) throws Exception {
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        return  sxReportUnscrambleService.insertSxReportUnscramble(sysUser.getPhonenumber(),product.getFrequency()*suborderOrderInfo.getSum())>0;
    }


    /**
     * 判断购买的服务次数，来对用户添加服务
     * @param product
     * @return
     */
    public Boolean vipPatient(Product product ,Long userId,SuborderOrderInfo suborderOrderInfo){

        SysUser sysUser = sysUserMapper.selectUserById(userId);
        VipPatient vipPhone = vipPatientService.findVipPhone(sysUser.getPhonenumber());

        if (vipPhone!=null){
            VipPatient vipPatient = new VipPatient();
            //如果用户已经为vip，在原来的基础上增加次数
            vipPatient.setId(vipPhone.getId());
            vipPatient.setVipNum(vipPhone.getVipNum() +(product.getFrequency()*suborderOrderInfo.getSum()) );
            Date endDate = vipPhone.getEndDate();
            if (product.getFrequency()>=20&&product.getFrequency()<30){
                Calendar calendar = Calendar.getInstance();
                // 设置日期
                calendar.setTime(endDate);
                // 加一个月
                calendar.add(Calendar.MONTH, 6);
                Date time = calendar.getTime();
                vipPatient.setEndDate(time);

            }else if (product.getFrequency()>=30){
                Date data = getData(endDate);
                vipPatient.setEndDate(data);
            }
            else {
                Calendar calendar = Calendar.getInstance();
                // 设置日期
                calendar.setTime(endDate);
                // 加一个月
                calendar.add(Calendar.MONTH, 1);
                Date time = calendar.getTime();
                vipPatient.setEndDate(time);
            }


            vipPatientService.updateVipPatient(vipPatient);
            SysUser sysUser1 = new SysUser();
            sysUser1.setUserId(sysUser.getUserId());
            sysUser1.setDetectionTime(vipPatient.getEndDate());
            sysUserMapper.updateUser(sysUser1);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            CompletableFuture.runAsync(() ->{
                System.out.println("异步线程 =====> 开始添加购买服务日志 =====> " + new Date());
                try{
                    FwLog fwLog = new FwLog();
                    fwLog.setUserName(sysUser.getPhonenumber());
                    fwLog.setMsg("购买心电解读服务");
                    fwLog.setStatus("1");
                    fwLog.setLogTime(new Date());
                    fwLog.setFwStatus("1");
                    fwLog.setFwNum(product.getFrequency());
                    fwLogMapper.insert(fwLog);
                }catch (Exception e){
                    System.out.println(e);
                }
                System.out.println("异步线程 =====> 结束添加购买服务日志 =====> " + new Date());
            },executorService);
            executorService.shutdown(); // 回收线程池
        }else {
            if (product.getIsVip()==0){

                VipPatient vipPatient = new VipPatient();
                SysUser sysUser1 = sysUserMapper.selectUserByPhone(sysUser.getPhonenumber());
                vipPatient.setPatientPhone(sysUser.getPhonenumber());
                vipPatient.setVipNum(sysUser1.getDetectionNum()+(product.getFrequency()*suborderOrderInfo.getSum()) );
                Date date = new Date();
                if (product.getFrequency()>=20&&product.getFrequency()<30){
                    Calendar calendar = Calendar.getInstance();
                    // 设置日期
                    calendar.setTime(date);
                    // 加一个月
                    calendar.add(Calendar.MONTH, 6);
                    Date time = calendar.getTime();
                    vipPatient.setEndDate(time);

                }else if (product.getFrequency()>=30){
                    Date data = getData(date);
                    vipPatient.setEndDate(data);
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    // 设置日期
                    calendar.setTime(date);
                    // 加一个月
                    calendar.add(Calendar.MONTH, 1);
                    Date time = calendar.getTime();
                    vipPatient.setEndDate(time);
                }
                vipPatient.setCreateTime(date);
                vipPatientService.insertVipPatient(vipPatient);
                sysUserMapper.updateDeteTime(sysUser.getPhonenumber());
                SysUser sysUser2 = new SysUser();
                sysUser2.setUserId(sysUser.getUserId());
                sysUser2.setDetectionTime(vipPatient.getEndDate());
                sysUserMapper.updateUser(sysUser2);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                CompletableFuture.runAsync(() ->{
                    System.out.println("异步线程 =====> 开始添加购买服务日志 =====> " + new Date());
                    try{
                        FwLog fwLog = new FwLog();
                        fwLog.setUserName(sysUser.getPhonenumber());
                        fwLog.setMsg("购买心电解读服务");
                        fwLog.setStatus("1");
                        fwLog.setLogTime(new Date());
                        fwLog.setFwStatus("1");
                        fwLog.setFwNum(product.getFrequency());
                        fwLogMapper.insert(fwLog);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    System.out.println("异步线程 =====> 结束添加购买服务日志 =====> " + new Date());
                },executorService);
                executorService.shutdown(); // 回收线程池
            }else {
                SysUser p = sysUserMapper.selectUserByPhone(sysUser.getPhonenumber());
                p.setDetectionNum(p.getDetectionNum() + (product.getFrequency()*suborderOrderInfo.getSum()) );
                if (product.getFrequency()>=20&&product.getFrequency()<30){
                    Calendar calendar = Calendar.getInstance();
                    // 设置日期
                    calendar.setTime(p.getDetectionTime());
                    // 加一个月
                    calendar.add(Calendar.MONTH, 6);
                    Date time = calendar.getTime();
                    p.setDetectionTime(time);
                }else if (product.getFrequency()>=30){
                    Date data = getData(p.getDetectionTime());
                    p.setDetectionTime(data);
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    // 设置日期
                    calendar.setTime(p.getDetectionTime());
                    // 加一个月
                    calendar.add(Calendar.MONTH, 1);
                    Date time = calendar.getTime();
                    p.setDetectionTime(time);

                }
                sysUserMapper.updateUser(p);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                CompletableFuture.runAsync(() ->{
                    System.out.println("异步线程 =====> 开始添加购买服务日志 =====> " + new Date());
                    try{
                        FwLog fwLog = new FwLog();
                        fwLog.setUserName(sysUser.getPhonenumber());
                        fwLog.setMsg("购买心电解读服务");
                        fwLog.setStatus("1");
                        fwLog.setLogTime(new Date());
                        fwLog.setFwStatus("1");
                        fwLog.setFwNum(product.getFrequency());
                        fwLogMapper.insert(fwLog);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    System.out.println("异步线程 =====> 结束添加购买服务日志 =====> " + new Date());
                },executorService);
                executorService.shutdown(); // 回收线程池
            }
        }

        return true;
    }


    /**
     * 将时间往后加一年
     * @param date
     * @return
     */
    public Date getData(Date date){
        // 创建 Calendar 实例并设置原始日期
        Calendar calendar = Calendar.getInstance();
        // 设置日期
        calendar.setTime(date);
        // 加一年
        calendar.add(Calendar.YEAR, 1);
        // 获取加一年后的日期
       return calendar.getTime();

    }

    /**
     * 通过订单号获取订单信息
     * @param orderId
     * @return
     */
    public List<SuborderOrderInfo> orderIdAndSuborder(String orderId){

        List<SuborderOrderInfo> orderFather = suborderOrderInfoMapper.selectList(new QueryWrapper<SuborderOrderInfo>().eq("order_father", orderId));
        return orderFather;
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
        orderInfo.setOrderState(OrderStatus.REFUND_SUCCESS.getType());
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);

        orderInfoMapper.update(orderInfo,queryWrapper);


        List<SuborderOrderInfo> suborderOrderInfo = suborderOrderInfoMapper.selectList(new QueryWrapper<SuborderOrderInfo>().eq("order_father", id));


        for(SuborderOrderInfo c : suborderOrderInfo){
            updateProductDel(c.getSum().intValue(),c.getProductId());
        }

    }

    /**
     * 查询当前用户的所有订单
     * @param loginUser
     * @return
     */
    @Override
    public List<OrderInfo> selectUserOrderList(LoginUser loginUser,OrderInfo orderInfo) {
        List<OrderInfo> orderInfoList = orderInfoMapper.selectAllList(loginUser.getUser().getUserId(),orderInfo);
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
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if (orderInfo.getOrderStatus().equals(OrderStatus.NOTPAY.getType())){
            List<SuborderOrderInfo> suborderOrderInfo = suborderOrderInfoMapper.selectList(new QueryWrapper<SuborderOrderInfo>().eq("order_father", orderId));

            for(SuborderOrderInfo c : suborderOrderInfo){
                updateProductDel(c.getSum().intValue(),c.getProductId());
            }
        }

        int i = orderInfoMapper.deleteById(orderId);
        if (i>0){
            suborderOrderInfoMapper.delete(new QueryWrapper<SuborderOrderInfo>().eq("order_father", orderId));
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteOrderById(String id) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(id);
        orderInfo.setDelFlag(1);
        return orderInfoMapper.update(orderInfo,new QueryWrapper<OrderInfo>().eq("id",id))>0;
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
    public String addOrder(HttpServletRequest request, Long productId, Integer sum, Long addressId) throws Exception {
        Product product = productMapper.selectById(productId);

        if(product.getType().equals("服务")){
            addressId = 11L;
        }

        ShipAddress shipAddress = shipAddressMapper.selectShipAddressById(addressId);

        if (shipAddress==null){
            throw new Exception("用户地址不存在");
        }
        //获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);

        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setPatientPhone(shipAddress.getPatientPhone());
        orderInfo.setPatientName(shipAddress.getPatientName());
        orderInfo.setStreetAddress(shipAddress.getStreetAddress());
        orderInfo.setStreet(shipAddress.getStreet());
        orderInfo.setCity(shipAddress.getCity());
        orderInfo.setState(shipAddress.getState());
        orderInfo.setCountry(shipAddress.getCountry());
        orderInfo.setPostalCode(shipAddress.getPostalCode());
        orderInfo.setId(OrderNoUtils.getNo());
        orderInfo.setTitle("购买"+product.getProductName());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
        orderInfo.setUserId(loginUser.getUser().getUserId());
        orderInfo.setTotalFee(new BigDecimal(sum).multiply(product.getDiscount()));
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        orderInfo.setOpenId(sysUser.getOpenId());
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfo.setOrderState(OrderStatus.NOTPAY.getType());
        orderInfo.setDelFlag(0);
        orderInfoMapper.insert(orderInfo);

        SuborderOrderInfo suborderOrderInfo = new SuborderOrderInfo();
        suborderOrderInfo.setOrderFather(orderInfo.getId());
        suborderOrderInfo.setProductId(productId);
        suborderOrderInfo.setSum(Long.valueOf(sum));
        suborderOrderInfo.setCreateTime(new Date());
        suborderOrderInfo.setUpdateTime(new Date());
        suborderOrderInfo.setProductPrice(product.getDiscount());
        suborderOrderInfo.setProductName(product.getProductName());
        int insert = suborderOrderInfoMapper.insert(suborderOrderInfo);
        if(!product.getType().equals("服务")){
            updateProductAdd(sum,productId);
        }


        redisTemplate.opsForValue().set("order:"+orderInfo.getId(),orderInfo,15, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("orderQuery:"+orderInfo.getId(),orderInfo,20, TimeUnit.SECONDS);

        return orderInfo.getId();

    }
    /**
     * 添加订单
     * @param request
     * @param productId
     * @param sum
     * @return
     */
    @Transactional
    @Override
    public String addKpOrFwOrder(HttpServletRequest request, Long productId, Integer sum) {
        Product product = productMapper.selectById(productId);

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
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfo.setOrderState(OrderStatus.NOTPAY.getType());
        orderInfo.setDelFlag(0);
        orderInfoMapper.insert(orderInfo);

        SuborderOrderInfo suborderOrderInfo = new SuborderOrderInfo();
        suborderOrderInfo.setOrderFather(orderInfo.getId());
        suborderOrderInfo.setProductId(productId);
        suborderOrderInfo.setSum(Long.valueOf(sum));
        suborderOrderInfo.setCreateTime(new Date());
        suborderOrderInfo.setUpdateTime(new Date());
        suborderOrderInfo.setProductPrice(product.getDiscount());
        suborderOrderInfo.setProductName(product.getProductName());
        int insert = suborderOrderInfoMapper.insert(suborderOrderInfo);
        if(!product.getType().equals("服务")){
            updateProductAdd(sum,productId);
        }

        redisTemplate.opsForValue().set("order:"+orderInfo.getId(),orderInfo,15, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("orderQuery:"+orderInfo.getId(),orderInfo,20, TimeUnit.SECONDS);
        return orderInfo.getId();
    }

    @Transactional
    @Override
    public String addBGOrder(HttpServletRequest request, Long productId,String pId) {
        Product product = productMapper.selectById(productId);

        //获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);

        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUser().getUserId());

        Date date = new Date();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(OrderNoUtils.getNo());
        orderInfo.setTitle("购买"+product.getProductName());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
        orderInfo.setUserId(loginUser.getUser().getUserId());
        orderInfo.setTotalFee(product.getDiscount());
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        orderInfo.setOpenId(sysUser.getOpenId());
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
        orderInfo.setOrderState(OrderStatus.NOTPAY.getType());
        orderInfo.setDelFlag(0);
        orderInfo.setPId(pId);
        orderInfoMapper.insert(orderInfo);

        SuborderOrderInfo suborderOrderInfo = new SuborderOrderInfo();
        suborderOrderInfo.setOrderFather(orderInfo.getId());
        suborderOrderInfo.setProductId(productId);
        suborderOrderInfo.setSum(1L);
        suborderOrderInfo.setCreateTime(date);
        suborderOrderInfo.setUpdateTime(date);
        suborderOrderInfo.setProductPrice(product.getDiscount());
        suborderOrderInfo.setProductName(product.getProductName());
        int insert = suborderOrderInfoMapper.insert(suborderOrderInfo);
        redisTemplate.opsForValue().set("order:"+orderInfo.getId(),orderInfo,15, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("orderQuery:"+orderInfo.getId(),orderInfo,20, TimeUnit.SECONDS);
        return orderInfo.getId();
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
        if (orderInfo==null){
            return;
        }
        if (orderInfo.getOrderStatus().equals(OrderStatus.NOTPAY.getType())){
            System.out.println("开始删除失效订单");
            deleteOrder(orderId);
        }
        wxPayController.delOrderQuery(orderId);
    }









    @Override
    public List<OrderInfo> webOrderList(String orderId, String userPhone, String orderState,String orderStatus) {
        List<OrderInfo> orderInfoList = orderInfoMapper.selectWebAllList(orderId, userPhone, orderState,orderStatus);
        return orderInfoList;
    }

    @Transactional
    @Override
    public Boolean updateAddress(ShipaddressVo shipaddressVo) {

        if (shipaddressVo.getOrderStatus()==null&&shipaddressVo.getIsUpdate()==null){
            return true;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUpdateTime(new Date());
        if (shipaddressVo.getIsUpdate()!=null&&!"".equals(shipaddressVo.getIsUpdate())){
            orderInfo.setId(shipaddressVo.getId());
            orderInfo.setPatientPhone(shipaddressVo.getPatientPhone());
            orderInfo.setPatientName(shipaddressVo.getPatientName());
            orderInfo.setStreetAddress(shipaddressVo.getStreetAddress());
            orderInfo.setStreet(shipaddressVo.getStreet());
            orderInfo.setCity(shipaddressVo.getCity());
            orderInfo.setState(shipaddressVo.getState());
            orderInfo.setCountry(shipaddressVo.getCountry());
            orderInfo.setPostalCode(shipaddressVo.getPostalCode());
            orderInfo.setCourierCompany(shipaddressVo.getCourierCompany());
            orderInfo.setCourierNumber(shipaddressVo.getCourierNumber());
            OrderInfo orderInfo1 = orderInfoMapper.selectById(shipaddressVo.getId());
            if (!orderInfo1.getOrderState().equals("申请退款")){
                orderInfo.setOrderState(shipaddressVo.getState());
            }
            orderInfoMapper.updateById(orderInfo);

            return true;
        }
        orderInfo.setCourierCompany(shipaddressVo.getCourierCompany());
        orderInfo.setCourierNumber(shipaddressVo.getCourierNumber());
        OrderInfo orderInfo1 = orderInfoMapper.selectById(shipaddressVo.getId());
        if (orderInfo1.getOrderState()!=null&&!orderInfo1.getOrderState().equals("申请退款")){
            orderInfo.setOrderState(shipaddressVo.getOrderStatus());
        }
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

    @Override
    public List<OrderInfo> selectOrderByPId(String pId) {
        return orderInfoMapper.selectList(new QueryWrapper<OrderInfo>().eq("p_id",pId).eq("order_state","交易成功"));
    }


    /**
     * 创建订单时，修改商品库存
     * @param sum
     * @param productId
     * @return
     */
    private int updateProductAdd(Integer sum,Long productId){
        Product product = productMapper.selectById(productId);
        UpdateWrapper<Product> updateWrapper = Wrappers.update();

        if (product!=null&&(product.getProductNum()-sum)==0){
            // 创建一个 UpdateWrapper 对象，指定要更新的表和条件
            updateWrapper
                    .eq("product_id", productId) // 设置更新的条件，例如根据商品ID进行更新
                    .setSql("product_num = product_num - "+sum)
                    .setSql("sales = sales + "+sum)
                    .setSql("state = '3'"); // 设置库存减一的更新操作
        }else {
            updateWrapper
                    .eq("product_id", productId) // 设置更新的条件，例如根据商品ID进行更新
                    .setSql("product_num = product_num - "+sum)
                    .setSql("sales = sales + "+sum); // 设置库存减一的更新操作
        }
        // 执行更新操作


        return productMapper.update(null, updateWrapper);
    }


    /**
     * 删除订单时，修改商品库存
     * @param sum
     * @param productId
     * @return
     */
    private int updateProductDel(Integer sum,Long productId){


        Product product = productMapper.selectById(productId);
        if (product!=null&&product.getType().equals("服务")){
            return 1;
        }

        UpdateWrapper<Product> updateWrapper = Wrappers.update();


        if (product!=null&&product.getProductNum()==0){
            // 创建一个 UpdateWrapper 对象，指定要更新的表和条件
            updateWrapper
                    .eq("product_id", productId) // 设置更新的条件，例如根据商品ID进行更新
                    .setSql("product_num = product_num + "+sum)
                    .setSql("sales = sales - "+sum)
                    .setSql("state = '1'"); // 设置库存减一的更新操作
        }else {
            updateWrapper
                    .eq("product_id", productId) // 设置更新的条件，例如根据商品ID进行更新
                    .setSql("product_num = product_num + "+sum)
                    .setSql("sales = sales - "+sum);// 设置库存减一的更新操作

        }
        // 执行更新操作
        return productMapper.update(null, updateWrapper);
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
