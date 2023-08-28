package com.ruoyi.xindian.order.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
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
    private AesUtils aesUtils;

    @Resource
    private UserAddressService userAddressService;


    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:list')")
    @GetMapping("/list")
    public TableDataInfo webOrderList(String orderId,String userPhone,String orderState,String orderStatus) throws Exception {
        if (userPhone!=null&&!"".equals(userPhone)){
            userPhone=aesUtils.encrypt(userPhone);
        }
        startPage();
        List<OrderInfo> orderInfoList =  orderInfoService.webOrderList(orderId,userPhone,orderState,orderStatus);
        for (OrderInfo c :orderInfoList){
            SysUser sysUser = c.getSysUser();
            if (sysUser!=null&&sysUser.getPhonenumber()!=null&&!"".equals(sysUser.getPhonenumber())){
                c.getSysUser().setPhonenumber(aesUtils.decrypt(sysUser.getPhonenumber()));
            }
            if(c.getPatientPhone() != null&&!"".equals(c.getPatientPhone())){
                c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
            }
            if(c.getPatientName() != null&&!"".equals(c.getPatientName())){
                c.setPatientName(aesUtils.decrypt(c.getPatientName()));
            }
            if(c.getStreetAddress() != null&&!"".equals(c.getStreetAddress())){
                c.setStreetAddress(aesUtils.decrypt(c.getStreetAddress()));
            }
        }
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
//    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "query/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) throws Exception {
        OrderInfo c = orderInfoService.selectTOrderInfoById(id);
        if(c.getPatientPhone() != null&&!"".equals(c.getPatientPhone())){
            c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
        }
        if(c.getPatientName() != null&&!"".equals(c.getPatientName())){
            c.setPatientName(aesUtils.decrypt(c.getPatientName()));
        }
        if(c.getStreetAddress() != null&&!"".equals(c.getStreetAddress())){
            c.setStreetAddress(aesUtils.decrypt(c.getStreetAddress()));
        }
        return AjaxResult.success(c);
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
    public AjaxResult b(@RequestBody ShipaddressVo shipaddressVo) throws Exception {
        if(shipaddressVo.getPatientPhone() != null&&!"".equals(shipaddressVo.getPatientPhone())){
            shipaddressVo.setPatientPhone(aesUtils.decrypt(shipaddressVo.getPatientPhone()));
        }
        if(shipaddressVo.getPatientName() != null&&!"".equals(shipaddressVo.getPatientName())){
            shipaddressVo.setPatientName(aesUtils.decrypt(shipaddressVo.getPatientName()));
        }
        if(shipaddressVo.getStreetAddress() != null&&!"".equals(shipaddressVo.getStreetAddress())){
            shipaddressVo.setStreetAddress(aesUtils.decrypt(shipaddressVo.getStreetAddress()));
        }
        Boolean is = orderInfoService.updateAddress(shipaddressVo);
        return AjaxResult.success(is);
    }


    /**
     * 通过订单编号去查询
     * @param id
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('payOrder:payOrder:findId')")
    @GetMapping("/ListOrderId")
    public AjaxResult orderId(String id) throws Exception {
        OrderInfo orderInfo = orderInfoService.ListOrderId(id);
        if (orderInfo!=null) {
            orderInfo.setTotalFee(orderInfo.getTotalFee().multiply(new BigDecimal("0.01")));

            for (SuborderOrderInfo d : orderInfo.getSuborderOrderInfos()) {
                d.getProduct().setPrice(d.getProduct().getPrice().multiply(new BigDecimal("0.01")));
                d.getProduct().setDiscount(d.getProduct().getDiscount().multiply(new BigDecimal("0.01")));
            }
            if (orderInfo.getPatientPhone()!=null&&!"".equals(orderInfo.getPatientPhone())){
                orderInfo.setPatientPhone(aesUtils.decrypt(orderInfo.getPatientPhone()));
            }
            if (orderInfo.getPatientName()!=null&&!"".equals(orderInfo.getPatientName())){
                orderInfo.setPatientName(aesUtils.decrypt(orderInfo.getPatientName()));
            }
            if (orderInfo.getStreetAddress()!=null&&!"".equals(orderInfo.getStreetAddress())){
                orderInfo.setStreetAddress(aesUtils.decrypt(orderInfo.getStreetAddress()));
            }
        }


        return AjaxResult.success(orderInfo);
    }




}
