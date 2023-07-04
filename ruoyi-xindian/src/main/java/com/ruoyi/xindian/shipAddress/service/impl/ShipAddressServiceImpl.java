package com.ruoyi.xindian.shipAddress.service.impl;

import com.ruoyi.xindian.message.domain.Message;
import com.ruoyi.xindian.shipAddress.domain.ShipAddress;
import com.ruoyi.xindian.shipAddress.mapper.ShipAddressMapper;
import com.ruoyi.xindian.shipAddress.service.IShipAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixinlong
 * 2023/7/4 10:21
 */
@Service
public class ShipAddressServiceImpl implements IShipAddressService {

    @Autowired
    private ShipAddressMapper shipAddressMapper;

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
    public int updateShipAddress(ShipAddress shipAddress) {
        return shipAddressMapper.updateShipAddress(shipAddress);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public int deleteShipAddressByIds(Long[] ids) {
        return shipAddressMapper.deleteShipAddressByIds(ids);
    }
}
