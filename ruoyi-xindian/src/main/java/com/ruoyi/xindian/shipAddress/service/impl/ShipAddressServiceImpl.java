package com.ruoyi.xindian.shipAddress.service.impl;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.message.domain.Message;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import com.ruoyi.xindian.shipAddress.mapper.ShipAddressMapper;
import com.ruoyi.xindian.shipAddress.service.IShipAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lixinlong
 * 2023/7/4 10:21
 */
@Service
public class ShipAddressServiceImpl implements IShipAddressService {

    @Autowired
    private ShipAddressMapper shipAddressMapper;

    @Resource
    private TokenService tokenService;


    /**
     * 查询
     * @param shipAddress
     * @return
     */
    @Override
    public List<ShipAddress> selectShipAddressList(ShipAddress shipAddress) {

        return shipAddressMapper.selectShipAddressList(shipAddress);
    }

    /**
     * 新增
     * @param shipAddress
     * @return
     */
    @Override
    public int insertShipAddress(ShipAddress shipAddress) {

        ShipAddress shipAddress1 = new ShipAddress();
        shipAddress1.setUserId(shipAddress.getUserId());
        List<ShipAddress> shipAddresses = shipAddressMapper.selectShipAddressList(shipAddress1);
        if (shipAddresses==null||shipAddresses.isEmpty()){
            shipAddress.setDefaultFlag(1L);
            return shipAddressMapper.insertShipAddress(shipAddress);
        }

        return shipAddressMapper.insertShipAddress(shipAddress);
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    @Override
    public ShipAddress selectShipAddressById(Long id) {
        return shipAddressMapper.selectShipAddressById(id);
    }

    /**
     * 修改
     * @param shipAddress
     * @return
     */
    @Override
    public int updateShipAddress(ShipAddress shipAddress, HttpServletRequest request) {

        if (shipAddress.getDefaultFlag()==1){
            LoginUser loginUser = tokenService.getLoginUser(request);

            shipAddressMapper.updateDefaultFlagInt(loginUser.getUser().getUserId());
        }

        return shipAddressMapper.updateShipAddress(shipAddress);
    }

    /**
     * 批量删除
     * @param id
     * @return
     */
    @Override
    public int deleteShipAddressByIds(String id) {
        return shipAddressMapper.deleteShipAddressByIds(id);
    }
}
