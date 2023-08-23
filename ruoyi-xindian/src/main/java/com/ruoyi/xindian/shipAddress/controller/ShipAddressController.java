package com.ruoyi.xindian.shipAddress.controller;

import com.ruoyi.common.annotation.Aes;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import com.ruoyi.xindian.shipAddress.service.IShipAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lixinlong
 */
@RestController
@RequestMapping("/shipAddress/shipAddress")
public class ShipAddressController extends BaseController {

    @Autowired
    private IShipAddressService shipAddressService;


    @Resource
    private TokenService tokenService;

    @Resource
    private AesUtils aesUtils;

    /**
     * 查询
     */
//    @PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:list')")

    @GetMapping("/list")
    public AjaxResult list(ShipAddress shipAddress,HttpServletRequest request) throws Exception {
        if(shipAddress.getPatientPhone()!=null&&!"".equals(shipAddress.getPatientPhone())){
            shipAddress.setPatientPhone(aesUtils.encrypt(shipAddress.getPatientPhone()));
        }
        if(shipAddress.getPatientName()!=null&&!"".equals(shipAddress.getPatientName())){
            shipAddress.setPatientName(aesUtils.encrypt(shipAddress.getPatientName()));
        }
        if(shipAddress.getStreetAddress()!=null&&!"".equals(shipAddress.getStreetAddress())){
            shipAddress.setStreetAddress(aesUtils.encrypt(shipAddress.getStreetAddress()));
        }
        LoginUser loginUser = tokenService.getLoginUser(request);
        shipAddress.setUserId(loginUser.getUser().getUserId());
        List<ShipAddress> list = shipAddressService.selectShipAddressList(shipAddress);
        for (ShipAddress address : list) {
            if(address.getPatientPhone() != null&&!"".equals(address.getPatientPhone())){
                address.setPatientPhone(aesUtils.decrypt(address.getPatientPhone()));
            }
            if(address.getPatientName() != null&&!"".equals(address.getPatientName())){
                address.setPatientName(aesUtils.decrypt(address.getPatientName()));
            }
            if(address.getStreetAddress() != null&&!"".equals(address.getStreetAddress())){
                address.setStreetAddress(aesUtils.decrypt(address.getStreetAddress()));
            }
        }
        return AjaxResult.success(list);
    }

    /**
     * web地址管理查询
     */
    @PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:list')")
    @GetMapping("/webList")
    public TableDataInfo webList(ShipAddress shipAddress,HttpServletRequest request) throws Exception {
        if(shipAddress.getPatientPhone()!=null&&!"".equals(shipAddress.getPatientPhone())){
            shipAddress.setPatientPhone(aesUtils.encrypt(shipAddress.getPatientPhone()));
        }
        if(shipAddress.getPatientName()!=null&&!"".equals(shipAddress.getPatientName())){
            shipAddress.setPatientName(aesUtils.encrypt(shipAddress.getPatientName()));
        }
        if(shipAddress.getStreetAddress()!=null&&!"".equals(shipAddress.getStreetAddress())){
            shipAddress.setStreetAddress(aesUtils.encrypt(shipAddress.getStreetAddress()));
        }
        startPage();
        List<ShipAddress> list = shipAddressService.selectShipAddressList(shipAddress);
        for (ShipAddress address : list) {
            if(address.getPatientPhone() != null&&!"".equals(address.getPatientPhone())){
                address.setPatientPhone(aesUtils.decrypt(address.getPatientPhone()));
            }
            if(address.getPatientName() != null&&!"".equals(address.getPatientName())){
                address.setPatientName(aesUtils.decrypt(address.getPatientName()));
            }
            if(address.getStreetAddress() != null&&!"".equals(address.getStreetAddress())){
                address.setStreetAddress(aesUtils.decrypt(address.getStreetAddress()));
            }
        }
        return getDataTable(list);
    }
    /**
     * 新增
     */
//    @PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:add')")
    @Log(title = "存储", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody ShipAddress shipAddress ,HttpServletRequest request) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(request);
        shipAddress.setUserId(loginUser.getUser().getUserId());
        shipAddress.setDelFlag(0L);
        if (shipAddress.getPatientPhone()!=null&&!"".equals(shipAddress.getPatientPhone())){
            shipAddress.setPatientPhone(aesUtils.encrypt(shipAddress.getPatientPhone()));
        }
        if (shipAddress.getPatientName()!=null&&!"".equals(shipAddress.getPatientName())){
            shipAddress.setPatientName(aesUtils.encrypt(shipAddress.getPatientName()));
        }
        if (shipAddress.getStreetAddress()!=null&&!"".equals(shipAddress.getStreetAddress())){
            shipAddress.setStreetAddress(aesUtils.encrypt(shipAddress.getStreetAddress()));
        }

        return toAjax(shipAddressService.insertShipAddress(shipAddress));
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {
        ShipAddress shipAddress = shipAddressService.selectShipAddressById(id);
        if (shipAddress.getPatientPhone()!=null&&!"".equals(shipAddress.getPatientPhone())){
            shipAddress.setPatientPhone(aesUtils.decrypt(shipAddress.getPatientPhone()));
        }
        if (shipAddress.getPatientName()!=null&&!"".equals(shipAddress.getPatientName())){
            shipAddress.setPatientName(aesUtils.decrypt(shipAddress.getPatientName()));
        }
        if (shipAddress.getStreetAddress()!=null&&!"".equals(shipAddress.getStreetAddress())){
            shipAddress.setStreetAddress(aesUtils.decrypt(shipAddress.getStreetAddress()));
        }

        return AjaxResult.success(shipAddress);
    }

    /**
     * 修改
     * @param shipAddress
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:edit')")
    @Log(title = "存储", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShipAddress shipAddress,HttpServletRequest request) throws Exception {
        if (shipAddress.getPatientPhone()!=null&&!"".equals(shipAddress.getPatientPhone())){
            shipAddress.setPatientPhone(aesUtils.encrypt(shipAddress.getPatientPhone()));
        }
        if (shipAddress.getPatientName()!=null&&!"".equals(shipAddress.getPatientName())){
            shipAddress.setPatientName(aesUtils.encrypt(shipAddress.getPatientName()));
        }
        if (shipAddress.getStreetAddress()!=null&&!"".equals(shipAddress.getStreetAddress())){
            shipAddress.setStreetAddress(aesUtils.encrypt(shipAddress.getStreetAddress()));
        }


        return toAjax(shipAddressService.updateShipAddress(shipAddress,request));
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:remove')")
    @Log(title = "物理删除", businessType = BusinessType.DELETE)
    @PutMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
        return toAjax(shipAddressService.deleteShipAddressByIds(id));
    }


}
