package com.ruoyi.xindian.shipAddress.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xindian.message.domain.Message;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import com.ruoyi.xindian.wx_pay.domain.Product;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/4 10:22
 */
public interface ShipAddressMapper  extends BaseMapper<ShipAddress> {

    List<ShipAddress> selectShipAddressList(ShipAddress shipAddress);

    int insertShipAddress(ShipAddress shipAddress);

    ShipAddress selectShipAddressById(Long id);

    int updateShipAddress(ShipAddress shipAddress);

    int deleteShipAddressByIds(String id);
}
