package com.ruoyi.xindian.order.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.order.domain.Area;
import com.ruoyi.xindian.order.domain.MCity;
import com.ruoyi.xindian.order.service.AreaService;
import com.ruoyi.xindian.order.service.MCityService;
import com.ruoyi.xindian.order.service.UserAddressService;
import com.ruoyi.xindian.order.vo.ShipaddressVo;
import com.ruoyi.xindian.order.vo.UserAddressVo;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import com.ruoyi.xindian.wx_pay.domain.SuborderOrderInfo;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
    private AreaService areaService;


    @Resource
    private UserAddressService userAddressService;


    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:list')")
    @GetMapping("/list")
    public TableDataInfo webOrderList(String orderId,String userPhone,String orderState,String orderStatus){

        startPage();
        List<OrderInfo> orderInfoList =  orderInfoService.webOrderList(orderId,userPhone,orderState,orderStatus);
        return getDataTable(orderInfoList);
    }



    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:export')")
    @PostMapping("/export")
    public void webOrderList(HttpServletResponse response){
        List<OrderInfo> orderInfoList = orderInfoService.webOrderList(null,null,null,null);
        ExcelUtil<OrderInfo> util = new ExcelUtil<OrderInfo>(OrderInfo.class);
        util.exportExcel(response, orderInfoList, "订单数据");
    }



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
        List<Area> mCities = areaService.findAddress(regionParentId,level);
       return AjaxResult.success(mCities);
    }


    /**
     * 通过修改地址，或者修改状态进行数据的添加
     * @param shipaddressVo
     * @return
     */
    @PostMapping("/addressAdd")
    public AjaxResult b(@RequestBody ShipaddressVo shipaddressVo){

        Boolean is = orderInfoService.updateAddress(shipaddressVo);
        return AjaxResult.success(is);
    }


    /**
     * 通过订单编号去查询
     * @param id
     * @return
     */
    @GetMapping("/ListOrderId")
    public AjaxResult orderId(String id){
        OrderInfo orderInfo = orderInfoService.ListOrderId(id);
        if (orderInfo!=null) {
            orderInfo.setTotalFee(orderInfo.getTotalFee().multiply(new BigDecimal("0.01")));

            for (SuborderOrderInfo d : orderInfo.getSuborderOrderInfos()) {
                d.getProduct().setPrice(d.getProduct().getPrice().multiply(new BigDecimal("0.01")));
                d.getProduct().setDiscount(d.getProduct().getDiscount().multiply(new BigDecimal("0.01")));
            }
        }
        return AjaxResult.success(orderInfo);
    }




}
