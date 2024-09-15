package com.ruoyi.xindian.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.equipment.controller.EquipmentHeadingCodeController;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.mapper.LeaseDetailsMapper;
import com.ruoyi.xindian.lease.service.LeaseDetailsService;
import com.ruoyi.xindian.order.vo.OrderVo;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.wSuryvey.domain.PurchaseLimitation;
import com.ruoyi.xindian.wSuryvey.domain.WSurvey;
import com.ruoyi.xindian.wSuryvey.service.IWSurveyService;
import com.ruoyi.xindian.wSuryvey.service.PurchaseLimitationService;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.Product;
import com.ruoyi.xindian.wx_pay.domain.SuborderOrderInfo;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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


    @Autowired
    private LeaseDetailsService leaseDetailsService;

    @Resource
    private LeaseDetailsMapper leaseDetailsMapper;


    @Autowired
    private IWSurveyService wSurveyService;

    @Resource
    private PurchaseLimitationService purchaseLimitationService;
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
     * @return
     */
    @PostMapping("/orderAdd")
    public AjaxResult orderAdd(HttpServletRequest request,@RequestBody OrderVo orderVo){


        lock.lock();
        try {
            Long id = null;
            try {
                id = orderVo.getAddressId();
            }catch (Exception e){
                return AjaxResult.error("地址错误，请重新选择地址");
            }

            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复支付");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);
            if (orderVo.getProductId()==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            if (orderVo.getSum()==null){
                return AjaxResult.error("商品购买数量错误，请稍后再试");
            }
            Product product = productService.selectPId(orderVo.getProductId());
            if (product==null){
                return AjaxResult.error("商品不存在");
            }
            if (product.getState().equals("2")){
                return AjaxResult.error("商品已下架");
            }
            if (product.getState().equals("3")){
                return AjaxResult.error("商品库存不足");
            }
            if (product.getProductNum().compareTo(orderVo.getSum()) <0){
                return AjaxResult.error("商品库存不足");
            }


            SysUser sysUser = sysUserService.selectUserById(userId);

            WSurvey wSurvey = new WSurvey();
            wSurvey.setPatientPhone(sysUser.getPhonenumber());
            List<WSurvey> wSurveyList = wSurveyService.selectWSurveyList(wSurvey);

            boolean isVip = false;
            if (wSurveyList!=null&&!wSurveyList.isEmpty()) {

                PurchaseLimitation purchaseLimitation = new PurchaseLimitation();
                purchaseLimitation.setProductId(orderVo.getProductId());
                purchaseLimitation.setPatientPhone(sysUser.getPhonenumber());
                List<PurchaseLimitation> purchaseLimitations = purchaseLimitationService.selectPurchaseLimitationList(purchaseLimitation);
                if (purchaseLimitations.isEmpty()){
                    isVip = true;
                    if (orderVo.getSum()>1) {
                        return AjaxResult.error("打折商品限购一台");
                    }
                }
            }

            String stringBuilder = orderInfoService.addOrder(request, orderVo,isVip);
            if (isVip){
                redisTemplate.opsForValue().set("purchase_limitation:"+userId+":"+orderVo.getProductId(),stringBuilder);
            }

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
     * @return
     */
    @PostMapping("/orderKpOrFwAdd")
    public AjaxResult orderKpOrFwAdd(HttpServletRequest request,@RequestBody OrderVo orderVo){

        lock.lock();
        try {
            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复支付");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            if (orderVo.getProductId()==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            if (orderVo.getSum()==null){
                return AjaxResult.error("商品购买数量错误，请稍后再试");
            }
            LoginUser loginUser = tokenService.getLoginUser(request);

            SysUser sysUser = sysUserService.selectUserById(loginUser.getUser().getUserId());
            VipPatient vipPhone = vipPatientService.findVipPhone(sysUser.getPhonenumber());
            if (vipPhone!=null&&(vipPhone.getVipNum()==null?0:vipPhone.getVipNum()+orderVo.getSum())>500){
                return AjaxResult.error("每人仅限购买500服务次数");
            }
            if (vipPhone==null&&(sysUser.getDetectionNum()==null?0:sysUser.getDetectionNum()+orderVo.getSum())>500){
                return AjaxResult.error("每人仅限购买500服务次数");
            }
            Product product = productService.selectPId(orderVo.getProductId());
            if (product==null){
                return AjaxResult.error("商品不存在");
            }
            if (product.getState().equals("2")){
                return AjaxResult.error("商品已下架");
            }

            String stringBuilder = orderInfoService.addKpOrFwOrder(request, orderVo.getProductId(), orderVo.getSum());
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
     * @return
     */
    @PostMapping("/orderLeaseAdd")
    public AjaxResult orderLeaseAdd(HttpServletRequest request,@RequestBody OrderVo orderVo){
        lock.lock();
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            Long userId = loginUser.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复支付");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            Long productId = orderVo.getProductId();
            String equipmentCode = orderVo.getEquipmentCode();
            if (productId==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            if (StringUtils.isEmpty(equipmentCode)){
                return AjaxResult.error("设备不存在，请稍后再试");
            }
            LeaseDetails leaseDetails = leaseDetailsService.selectLeaseDetailsByEquipmentCode(equipmentCode);
            if (leaseDetails==null){
                return AjaxResult.error("设备不存在");
            }
            if (leaseDetails.getStatus().equals("1")){
                return AjaxResult.error("设备已被租赁");
            }
            Product product = productService.selectPId(productId);
            if (product==null){
                return AjaxResult.error("商品不存在");
            }
            if (product.getState().equals("2")){
                return AjaxResult.error("商品已下架");
            }
            SysUser sysUser = sysUserService.selectUserById(loginUser.getUser().getUserId());

            Patient patient = patientService.selectPatientByPatientPhone(sysUser.getPhonenumber());
            if (patient==null){
                return AjaxResult.error("用户信息不存在");
            }
            String stringBuilder = orderInfoService.addLeaseOrder(request, productId, 1, equipmentCode);
            LeaseDetails leaseDetail = new LeaseDetails();
            leaseDetail.setUsername(aesUtils.decrypt(patient.getPatientName()));
            leaseDetail.setCreateTime(new Date());
            leaseDetail.setPhone(aesUtils.decrypt(patient.getPatientPhone()));
            leaseDetail.setEquipmentCode(equipmentCode);
            leaseDetail.setStatus("1");
            int update = leaseDetailsMapper.update(leaseDetail, new LambdaQueryWrapper<LeaseDetails>().eq(LeaseDetails::getEquipmentCode, leaseDetail.getEquipmentCode()));
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
     * @return
     */
    @PostMapping("/orderCountReportAdd")
    public AjaxResult orderCountReportAdd(HttpServletRequest request,@RequestBody OrderVo orderVo){

        lock.lock();
        try {
            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复支付");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            if (orderVo.getProductId()==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            if (orderVo.getSum()==null){
                return AjaxResult.error("商品购买数量错误，请稍后再试");
            }
            LoginUser loginUser = tokenService.getLoginUser(request);

            SysUser sysUser = sysUserService.selectUserById(loginUser.getUser().getUserId());

            if ((sysUser.getWeeklyNewspaperNum()==null?0:sysUser.getWeeklyNewspaperNum()+orderVo.getSum())>500){
                return AjaxResult.error("每人仅限购买500服务次数");
            }
            Product product = productService.selectPId(orderVo.getProductId());
            if (product==null){
                return AjaxResult.error("商品不存在");
            }
            if (product.getState().equals("2")){
                return AjaxResult.error("商品已下架");
            }

            String stringBuilder = orderInfoService.addKpOrFwOrder(request, orderVo.getProductId(), orderVo.getSum());
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
     * @return
     */
    @PostMapping("/addBGOrder")
    public AjaxResult addBGOrder(HttpServletRequest request,@RequestBody OrderVo orderVo){

        lock.lock();
        try {
            LoginUser loginUser1 = tokenService.getLoginUser(request);
            Long userId = loginUser1.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getOrderId"+userId))){
                return AjaxResult.error("请勿重复点击");
            }
            redisTemplate.opsForValue().set("getOrderId"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            if (orderVo.getProductId()==null){
                return AjaxResult.error("商品参数错误，请稍后再试");
            }
            Product product = productService.selectPId(orderVo.getProductId());
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
            String stringBuilder = orderInfoService.addBGOrder(request, orderVo.getProductId(), orderVo.getPId());
            return AjaxResult.success("操作成功",stringBuilder);
        }catch (Exception e){
            System.out.println(e);
            return AjaxResult.error("创建订单失败");
        }finally {
            lock.unlock();
        }
    }

    @PostMapping("/updateOrderType")
    public AjaxResult updateOrderType(@RequestBody OrderVo orderVo){

        if (orderVo.getOrderId()==null){
            return AjaxResult.error();
        }
        redisTemplate.delete("orderQuery:"+orderVo.getOrderId());
        redisTemplate.delete("order:"+orderVo.getOrderId());
        orderInfoService.redisOrderKey(orderVo.getOrderId());
        return AjaxResult.success();
    }
}
