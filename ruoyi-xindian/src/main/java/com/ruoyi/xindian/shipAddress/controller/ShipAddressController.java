package com.ruoyi.xindian.shipAddress.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
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
    /**
     * 查询
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:list')")*/
    @GetMapping("/list")
    public TableDataInfo list(ShipAddress shipAddress,HttpServletRequest request){
        LoginUser loginUser = tokenService.getLoginUser(request);
        shipAddress.setUserId(loginUser.getUser().getUserId());
        startPage();
        List<ShipAddress> list = shipAddressService.selectShipAddressList(shipAddress);
        return getDataTable(list);
    }

    /**
     * 新增
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:add')")*/
    @Log(title = "存储", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody ShipAddress shipAddress ,HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        shipAddress.setUserId(loginUser.getUser().getUserId());
        shipAddress.setDelFlag(0L);
        return toAjax(shipAddressService.insertShipAddress(shipAddress));
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:query')")*/
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(shipAddressService.selectShipAddressById(id));
    }

    /**
     * 修改
     * @param shipAddress
     * @return
     */
    /*@PreAuthorize("@ss.hasPermi('shipAddress:shipAddress:edit')")*/
    @Log(title = "存储", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShipAddress shipAddress) {
        return toAjax(shipAddressService.updateShipAddress(shipAddress));
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
