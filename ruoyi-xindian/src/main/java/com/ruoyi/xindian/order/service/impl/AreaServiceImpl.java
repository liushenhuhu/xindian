package com.ruoyi.xindian.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.order.domain.Area;
import com.ruoyi.xindian.order.domain.MCity;
import com.ruoyi.xindian.order.service.AreaService;
import com.ruoyi.xindian.order.mapper.AreaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【area(地址区域表)】的数据库操作Service实现
* @createDate 2023-07-07 16:10:18
*/
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area>
    implements AreaService{


    @Resource
    private AreaMapper areaMapper;

    @Override
    public List<Area> findAddress(String parentId, Integer level) {
        return areaMapper.selectAll(parentId, level);
    }

}




