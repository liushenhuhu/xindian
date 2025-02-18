package com.ruoyi.xindian.ecgCount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.ecgCount.domain.EcgCountType;
import com.ruoyi.xindian.ecgCount.service.EcgCountTypeService;
import com.ruoyi.xindian.ecgCount.mapper.EcgCountTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 13401
* @description 针对表【ecg_count_type】的数据库操作Service实现
* @createDate 2023-10-10 12:23:53
*/
@Service
public class EcgCountTypeServiceImpl extends ServiceImpl<EcgCountTypeMapper, EcgCountType>
    implements EcgCountTypeService{


    @Resource
    private EcgCountTypeMapper ecgCountTypeMapper;

    @Override
    public EcgCountType getEcgValueButLabel(String label) {
        return ecgCountTypeMapper.selectOne(new QueryWrapper<EcgCountType>().eq("label",label));
    }
}




