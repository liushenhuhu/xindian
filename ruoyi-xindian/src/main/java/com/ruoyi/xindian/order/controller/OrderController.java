package com.ruoyi.xindian.order.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.Product;
import com.ruoyi.xindian.wx_pay.domain.SuborderOrderInfo;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.service.ProductService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/payOrder")
public class OrderController {

    @Resource
    private RedisTemplate<String ,String> redisTemplate;

    @Resource
    private TokenService tokenService;


    @Resource
    private OrderInfoService orderInfoService;

    private final Lock lock = new ReentrantLock();

    @Resource
    private ProductService productService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IVipPatientService vipPatientService;
    /**
     * 查询用户所存在的订单
     * @return
     */
    @GetMapping("/userOrderList")
    public AjaxResult userOrderList(HttpServletRequest request,OrderInfo orderInfo){
        //获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        startPage();
        List<OrderInfo> orderInfoList =  orderInfoService.selectUserOrderList(loginUser,orderInfo);
        if (orderInfoList!=null&&!orderInfoList.isEmpty()){
            for (OrderInfo c:orderInfoList){
                c.setTotalFee(c.getTotalFee().multiply(new BigDecimal("0.01")));

                for (SuborderOrderInfo d : c.getSuborderOrderInfos()){

                    d.getProduct().setPrice(d.getProduct().getPrice().multiply(new BigDecimal("0.01")));
                    d.getProduct().setDiscount(d.getProduct().getDiscount().multiply(new BigDecimal("0.01")));
                }
            }
        }

        return AjaxResult.success(orderInfoList);
    }

    /**
     * 删除订单，通过Id
     * @param orderId
     * @return
     */
    @GetMapping("/delete")
    public AjaxResult deleteOrder(String orderId){
        Boolean isDel  = orderInfoService.deleteOrder(orderId);
        return AjaxResult.success(isDel);
    }

    

    /**
     * 添加订单
     * @param request
     * @param productId
     * @param sum
     * @param addressId
     * @return
     */
    @PostMapping("/orderAdd")
    public AjaxResult orderAdd(HttpServletRequest request,Long productId,Integer sum,String addressId){


        lock.lock();
        try {
            Long id = null;
            try {
                id = Long.valueOf(addressId);
            }catch (Exception e){
                return AjaxResult.error("地址错误，请重新选择地址");
            }

            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复支付");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);
            if (productId==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            if (sum==null){
                return AjaxResult.error("商品购买数量错误，请稍后再试");
            }
            if (addressId==null&&"undefined".equals(addressId)){
                return AjaxResult.error("请选择地址");
            }
            Product product = productService.selectPId(productId);
            if (product==null){
                return AjaxResult.error("商品不存在");
            }
            if (product.getState().equals("2")){
                return AjaxResult.error("商品已下架");
            }
            if (product.getState().equals("3")){
                return AjaxResult.error("商品库存不足");
            }
            if (product.getProductNum().compareTo(sum) <0){
                return AjaxResult.error("商品库存不足");
            }

            String stringBuilder = orderInfoService.addOrder(request, productId, sum, id);
            return AjaxResult.success("操作成功",stringBuilder);
        }catch (Exception e){
            System.out.println(e);
            return AjaxResult.error("创建订单失败");
        }finally {
            lock.unlock();
        }


    }


    /**
     * 添加订单
     * @param request
     * @param productId
     * @param sum
     * @return
     */
    @PostMapping("/orderKpOrFwAdd")
    public AjaxResult orderKpOrFwAdd(HttpServletRequest request,Long productId,Integer sum){

        lock.lock();
        try {
            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复支付");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            if (productId==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            if (sum==null){
                return AjaxResult.error("商品购买数量错误，请稍后再试");
            }
            LoginUser loginUser = tokenService.getLoginUser(request);

            SysUser sysUser = sysUserService.selectUserById(loginUser.getUser().getUserId());
            VipPatient vipPhone = vipPatientService.findVipPhone(sysUser.getPhonenumber());
            if (vipPhone!=null&&(vipPhone.getVipNum()+sum)>500){
                return AjaxResult.error("每人仅限购买500服务次数");
            }
            if (vipPhone==null&&(sysUser.getDetectionNum()+sum)>500){
                return AjaxResult.error("每人仅限购买500服务次数");
            }
            Product product = productService.selectPId(productId);
            if (product==null){
                return AjaxResult.error("商品不存在");
            }
            if (product.getState().equals("2")){
                return AjaxResult.error("商品已下架");
            }

            String stringBuilder = orderInfoService.addKpOrFwOrder(request, productId, sum);
            return AjaxResult.success("操作成功",stringBuilder);
        }catch (Exception e){
            System.out.println(e);
            return AjaxResult.error("创建订单失败");
        }finally {
            lock.unlock();
        }
    }
}
