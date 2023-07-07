package com.ruoyi.xindian.order.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/payOrder")
public class OrderController {


    @Resource
    private TokenService tokenService;


    @Resource
    private OrderInfoService orderInfoService;
    /**
     * 查询用户所存在的订单
     * @return
     */
    @GetMapping("/userOrderList")
    public AjaxResult userOrderList(HttpServletRequest request){
        //获取token中发送请求的用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        startPage();
        List<OrderInfo> orderInfoList =  orderInfoService.selectUserOrderList(loginUser);
        return AjaxResult.success(orderInfoList);
    }

    /**
     * 删除订单，通过Id
     * @param request
     * @param orderId
     * @return
     */
    @GetMapping("/delete")
    public AjaxResult deleteOrder(HttpServletRequest request , String orderId){
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
    public AjaxResult orderAdd(HttpServletRequest request,Long productId,Integer sum,Long addressId){

        String isAdd  = orderInfoService.addOrder(request,productId,sum,addressId);

        return AjaxResult.success("操作成功",isAdd);
    }


}
