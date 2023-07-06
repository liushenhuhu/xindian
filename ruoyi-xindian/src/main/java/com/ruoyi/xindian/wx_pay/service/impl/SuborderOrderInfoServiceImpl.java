package com.ruoyi.xindian.wx_pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.wx_pay.domain.SuborderOrderInfo;
import com.ruoyi.xindian.wx_pay.mapper.SuborderOrderInfoMapper;
import com.ruoyi.xindian.wx_pay.service.SuborderOrderInfoService;
import org.springframework.stereotype.Service;

/**
* @author 13401
* @description 针对表【t_suborder_order_info(子订单，通过order_father来绑定主订单)】的数据库操作Service实现
* @createDate 2023-07-03 11:36:42
*/
@Service
public class SuborderOrderInfoServiceImpl extends ServiceImpl<SuborderOrderInfoMapper, SuborderOrderInfo>
    implements SuborderOrderInfoService{

}




