package com.ruoyi.xindian.order.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xindian.order.domain.MCity;
import com.ruoyi.xindian.order.service.MCityService;
import com.ruoyi.xindian.order.service.UserAddressService;
import com.ruoyi.xindian.order.vo.UserAddressVo;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * web页面订单查询
 */
@RestController
@RequestMapping("/webOrder")
public class webOrderController extends BaseController {


    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private MCityService mCityService;


    @Resource
    private UserAddressService userAddressService;


    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:list')")
    @GetMapping("/list")
    public TableDataInfo webOrderList(String orderId,String userPhone,String orderState){

        startPage();
        List<OrderInfo> orderInfoList =  orderInfoService.webOrderList(orderId,userPhone,orderState);
        return getDataTable(orderInfoList);
    }


//    /**
//     * 修改订单状态或者地址
//     */
//    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:edit')")
//    @Log(title = "修改订单状态或者地址", businessType = BusinessType.UPDATE)
//    @PutMapping("/update")
//    public AjaxResult edit(String orderId)
//    {
//        return toAjax(OrderInfoService.updateTOrderInfo(orderId));
//    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "query/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(orderInfoService.selectTOrderInfoById(id));
    }


    /**
     * 获取地址数据
     * @param regionParentId
     * @param level
     * @return
     */
    @GetMapping("/address")
    public AjaxResult a(String regionParentId,Integer level){
        if (regionParentId.equals("null")){
            regionParentId = null;
        }
        List<MCity> mCities = mCityService.findAddress(regionParentId,level);
       return AjaxResult.success(mCities);
    }


    /**
     * 通过修改地址，或者修改状态进行数据的添加
     * @param userAddress
     * @return
     */
    @PostMapping("/addressAdd")
    public AjaxResult b(@RequestBody UserAddressVo userAddress){

        Boolean is = orderInfoService.updateAddress(userAddress);
        return AjaxResult.success(is);
    }


    @GetMapping("/ListOrderId")
    public AjaxResult orderId(String id){
        OrderInfo orderInfo = orderInfoService.ListOrderId(id);
        return AjaxResult.success(orderInfo);
    }

}
