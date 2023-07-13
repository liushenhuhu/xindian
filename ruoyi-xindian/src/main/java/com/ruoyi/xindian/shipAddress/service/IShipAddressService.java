package com.ruoyi.xindian.shipAddress.service;

import com.ruoyi.xindian.message.domain.Message;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lixinlong
 * 2023/7/4 10:21
 */
public interface IShipAddressService {


    List<ShipAddress> selectShipAddressList(ShipAddress shipAddress);

    int insertShipAddress(ShipAddress shipAddress);

    ShipAddress selectShipAddressById(Long id);

    int updateShipAddress(ShipAddress shipAddress, HttpServletRequest request);

    int deleteShipAddressByIds(String id);

}
