package com.ruoyi.xindian.order.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.equipment.controller.EquipmentHeadingCodeController;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.Product;
import com.ruoyi.xindian.wx_pay.domain.SuborderOrderInfo;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.service.ProductService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
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
    private IPatientService patientService;


    @Resource
    private IPatientManagementService patientManagementService;


    @Resource
    private EquipmentHeadingCodeController equipmentHeadingCodeController;

    @Resource
    private OrderInfoService orderInfoService;

    private final Lock lock = new ReentrantLock();

    @Resource
    private ProductService productService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IVipPatientService vipPatientService;


    @Resource
    private AesUtils aesUtils;
    /**
     * 查询用户所存在的订单
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:query')")
    @GetMapping("/userOrderList")
    public AjaxResult userOrderList(HttpServletRequest request,OrderInfo orderInfo) throws Exception {
        //获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<OrderInfo> orderInfoList =  orderInfoService.selectUserOrderList(loginUser,orderInfo);
        if (orderInfoList!=null&&!orderInfoList.isEmpty()){
            for (OrderInfo c:orderInfoList){
                c.setTotalFee(c.getTotalFee().multiply(new BigDecimal("0.01")));
                if(c.getPatientPhone() != null&&!"".equals(c.getPatientPhone())){
                    c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
                }
                if(c.getPatientName() != null&&!"".equals(c.getPatientName())){
                    c.setPatientName(aesUtils.decrypt(c.getPatientName()));
                }
                if(c.getStreetAddress() != null&&!"".equals(c.getStreetAddress())){
                    c.setStreetAddress(aesUtils.decrypt(c.getStreetAddress()));
                }
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
//    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:remove')")
    @GetMapping("/delete")
    public AjaxResult deleteOrder(String orderId){
        Boolean isDel  = orderInfoService.deleteOrderById(orderId);
        return AjaxResult.success(isDel);
    }



    /**
     * 添加商品订单
     * @param request
     * @param productId
     * @param sum
     * @param addressId
     * @return
     */
    @PostMapping("/orderAdd")
    public AjaxResult orderAdd(HttpServletRequest request,Long productId,Integer sum,String addressId,String remark){


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

            String stringBuilder = orderInfoService.addOrder(request, productId, sum, id, remark);
            return AjaxResult.success("操作成功",stringBuilder);
        }catch (Exception e){
            System.out.println(e);
            return AjaxResult.error("创建订单失败");
        }finally {
            lock.unlock();
        }


    }

    /**
     * 添加服务订单
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



    /**
     * 添加服务订单
     * @param request
     * @param productId
     * @param sum
     * @return
     */
    @PostMapping("/orderCountReportAdd")
    public AjaxResult orderCountReportAdd(HttpServletRequest request,Long productId,Integer sum){

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

            if ((sysUser.getWeeklyNewspaperNum()+sum)>500){
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

    /**
     * 添加服务订单
     * @param request
     * @param productId
     * @return
     */
    @PostMapping("/addBGOrder")
    public AjaxResult addBGOrder(HttpServletRequest request,Long productId,String pId){

        lock.lock();
        try {
            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复点击");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            if (productId==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            Product product = productService.selectPId(productId);
            if (product==null){
                return AjaxResult.error("商品不存在");
            }
            if (product.getState().equals("2")){
                return AjaxResult.error("商品已下架");
            }

//            if (pId==null){
//                return AjaxResult.error("报告不能为空");
//            }
//            PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);
//            if (patientManagement==null){
//                return AjaxResult.error("报告不存在");
//            }
//            if (StringUtils.isEmpty(patientManagement.getPatientPhone())){
//                return AjaxResult.error("报告不存在");
//            }


//            Patient patient = patientService.selectPatientByPatientPhone(patientManagement.getPatientPhone());
//            String sxUserId = equipmentHeadingCodeController.getSXUserId(patient);
//            if (sxUserId==null){
//                sxUserId = equipmentHeadingCodeController.getSXUserId(patient);
//            }
//
//            LinkedHashMap<String, Object> sxDateList = equipmentHeadingCodeController.getSXDateList(sxUserId, pId);
//            if (sxDateList==null){
//                return AjaxResult.error("数据采集不够24小时,请注意数据是否采集完成");
//            }
//            Integer notifyStatus = (Integer)sxDateList.get("notifyStatus");
//            if (notifyStatus!=null&&notifyStatus==1){
//                return AjaxResult.error("该报告已提交诊断");
//            }
//            Integer fuwaiSendStatus = (Integer)sxDateList.get("fuwaiSendStatus");
//
//            if (fuwaiSendStatus!=null&&fuwaiSendStatus==2){
//                return AjaxResult.error("该报告已提交诊断");
//            }
            String stringBuilder = orderInfoService.addBGOrder(request, productId, pId);
            return AjaxResult.success("操作成功",stringBuilder);
        }catch (Exception e){
            System.out.println(e);
            return AjaxResult.error("创建订单失败");
        }finally {
            lock.unlock();
        }
    }
}
