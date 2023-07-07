package com.ruoyi.xindian.order.service;

import com.ruoyi.xindian.order.domain.Area;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.order.domain.MCity;

import java.util.List;

/**
* @author 13401
* @description 针对表【area(地址区域表)】的数据库操作Service
* @createDate 2023-07-07 16:10:18
*/
public interface AreaService extends IService<Area> {


    /**
     * 将省市县三级联动
     * @return
     */
    List<Area> findAddress(String parentId, Integer level);

}
