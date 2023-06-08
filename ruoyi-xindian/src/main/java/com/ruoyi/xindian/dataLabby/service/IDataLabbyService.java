package com.ruoyi.xindian.dataLabby.service;

import java.util.List;
import com.ruoyi.xindian.dataLabby.domain.dataLabby;

/**
 * 订单Service接口
 * 
 * @author chenpeng
 * @date 2023-06-07
 */
public interface IDataLabbyService
{
    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    public dataLabby selectOrderByOrderId(Long orderId);

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单集合
     */
    public List<dataLabby> selectOrderList(dataLabby order);

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(dataLabby order);

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(dataLabby order);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(Long[] orderIds);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(Long orderId);
}
