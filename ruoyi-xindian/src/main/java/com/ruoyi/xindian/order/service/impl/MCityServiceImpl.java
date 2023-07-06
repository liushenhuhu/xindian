package com.ruoyi.xindian.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.order.domain.MCity;
import com.ruoyi.xindian.order.mapper.MCityMapper;
import com.ruoyi.xindian.order.service.MCityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【m_city(地区表)】的数据库操作Service实现
* @createDate 2023-07-05 14:49:56
*/
@Service
public class MCityServiceImpl extends ServiceImpl<MCityMapper, MCity>
    implements MCityService{


    @Resource
    private MCityMapper mCityMapper;

    @Override
    public List<MCity> findAddress(String regionParentId,Integer level) {

        return mCityMapper.selectAll(regionParentId, level);
    }
}




