package com.ruoyi.xindian.shipAddress.controller;

import com.ruoyi.common.annotation.Aes;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import com.ruoyi.xindian.shipAddress.service.IShipAddressService;
import com.ruoyi.xindian.util.AesUtils;
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
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:list')")*/
    @Aes
    @GetMapping("/list")
    public AjaxResult list(ShipAddress shipAddress,HttpServletRequest request) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(request);
        shipAddress.setUserId(loginUser.getUser().getUserId());
        List<ShipAddress> list = shipAddressService.selectShipAddressList(shipAddress);
        for (ShipAddress address : list) {
            if(address.getPatientPhone() != null){
                address.setPatientPhone(aesUtils.decrypt(address.getPatientPhone()));
            }
            if(address.getPatientName() != null){
                address.setPatientName(aesUtils.decrypt(address.getPatientName()));
            }
            if(address.getStreetAddress() != null){
                address.setStreetAddress(aesUtils.decrypt(address.getStreetAddress()));
            }
        }
        return AjaxResult.success(list);
    }

    /**
     * web地址管理查询
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:list')")*/
    @GetMapping("/webList")
    public TableDataInfo webList(ShipAddress shipAddress,HttpServletRequest request) throws Exception {
        startPage();
        List<ShipAddress> list = shipAddressService.selectShipAddressList(shipAddress);
        for (ShipAddress address : list) {
            if(address.getPatientPhone() != null){
                address.setPatientPhone(aesUtils.decrypt(address.getPatientPhone()));
            }
            if(address.getPatientName() != null){
                address.setPatientName(aesUtils.decrypt(address.getPatientName()));
            }
            if(address.getStreetAddress() != null){
                address.setStreetAddress(aesUtils.decrypt(address.getStreetAddress()));
            }
        }
        return getDataTable(list);
    }
    /**
     * 新增
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:add')")*/
    @Log(title = "存储", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody ShipAddress shipAddress ,HttpServletRequest request) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(request);
        shipAddress.setUserId(loginUser.getUser().getUserId());
        shipAddress.setDelFlag(0L);
        shipAddress.setPatientPhone(aesUtils.encrypt(shipAddress.getPatientPhone()));
        shipAddress.setPatientName(aesUtils.encrypt(shipAddress.getPatientName()));
        shipAddress.setStreetAddress(aesUtils.encrypt(shipAddress.getStreetAddress()));
        return toAjax(shipAddressService.insertShipAddress(shipAddress));
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:query')")*/
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {
        ShipAddress shipAddress = shipAddressService.selectShipAddressById(id);
        shipAddress.setPatientPhone(aesUtils.decrypt(shipAddress.getPatientPhone()));
        shipAddress.setPatientName(aesUtils.decrypt(shipAddress.getPatientName()));
        shipAddress.setStreetAddress(aesUtils.decrypt(shipAddress.getStreetAddress()));
        return AjaxResult.success(shipAddress);
    }

    /**
     * 修改
     * @param shipAddress
     * @return
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:edit')")*/
    @Log(title = "存储", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShipAddress shipAddress,HttpServletRequest request) throws Exception {
        shipAddress.setPatientPhone(aesUtils.encrypt(shipAddress.getPatientPhone()));
        shipAddress.setPatientName(aesUtils.encrypt(shipAddress.getPatientName()));
        shipAddress.setStreetAddress(aesUtils.encrypt(shipAddress.getStreetAddress()));
        return toAjax(shipAddressService.updateShipAddress(shipAddress,request));
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:remove')")*/
    @Log(title = "物理删除", businessType = BusinessType.DELETE)
    @PutMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
        return toAjax(shipAddressService.deleteShipAddressByIds(id));
    }


}
