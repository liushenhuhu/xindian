package com.ruoyi.xindian.wx_pay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xindian.wx_pay.domain.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 13401
* @description 针对表【t_order_info】的数据库操作Mapper
* @createDate 2023-07-01 14:48:47
* @Entity com.ruoyi.xindian.wx_pay.domain.OrderInfo
*/
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {


    List<OrderInfo> selectAllList(@Param("userId") Long userId);
    List<OrderInfo> selectWebAllList(@Param("orderId") String orderId,@Param("userPhone") String userPhone,@Param("orderState") String orderState);

    /**
     * 查询【请填写功能名称】
     *
     * @param orderId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public OrderInfo selectTOrderInfoById(@Param("orderId") String orderId);



    OrderInfo searchAllById(@Param("id") String id);

}




