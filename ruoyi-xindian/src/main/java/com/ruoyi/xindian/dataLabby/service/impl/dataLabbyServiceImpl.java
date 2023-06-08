package com.ruoyi.xindian.dataLabby.service.impl;

import java.util.List;

import com.ruoyi.xindian.dataLabby.service.IDataLabbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.dataLabby.mapper.dataLabbyMapper;
import com.ruoyi.xindian.dataLabby.domain.dataLabby;
import com.ruoyi.xindian.dataLabby.service.IDataLabbyService;

/**
 * 订单Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-07
 */
@Service
public class dataLabbyServiceImpl implements IDataLabbyService
{
    @Autowired
    private dataLabbyMapper orderMapper;

    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public dataLabby selectOrderByOrderId(Long orderId)
    {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<dataLabby> selectOrderList(dataLabby order)
    {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    @Override
    public int insertOrder(dataLabby order)
    {
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    @Override
    public int updateOrder(dataLabby order)
    {
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderIds(Long[] orderIds)
    {
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderId(Long orderId)
    {
        return orderMapper.deleteOrderByOrderId(orderId);
    }
}
