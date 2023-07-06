package com.ruoyi.xindian.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.order.domain.MCity;

import java.util.List;

/**
* @author 13401
* @description 针对表【m_city(地区表)】的数据库操作Service
* @createDate 2023-07-05 14:49:56
*/
public interface MCityService extends IService<MCity> {

    /**
     * 将省市县三级联动
     * @return
     */
    List<MCity> findAddress(String regionParentId,Integer level);


}
