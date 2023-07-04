package com.ruoyi.xindian.shipAddress.mapper;

import com.ruoyi.xindian.message.domain.Message;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/4 10:22
 */
public interface ShipAddressMapper {

    List<ShipAddress> selectShipAddressList(ShipAddress shipAddress);

    int insertShipAddress(ShipAddress shipAddress);

    ShipAddress selectShipAddressById(Long id);

    int updateShipAddress(ShipAddress shipAddress);

    int deleteShipAddressByIds(Long[] ids);
}
