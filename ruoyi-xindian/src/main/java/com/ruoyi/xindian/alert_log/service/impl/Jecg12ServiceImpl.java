package com.ruoyi.xindian.alert_log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.alert_log.domain.Jecg12;
import com.ruoyi.xindian.alert_log.service.Jecg12Service;
import com.ruoyi.xindian.alert_log.mapper.Jecg12Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 13401
* @description 针对表【jecg_12(静态12导心电数据)】的数据库操作Service实现
* @createDate 2023-10-16 14:10:47
*/
@Service
public class Jecg12ServiceImpl extends ServiceImpl<Jecg12Mapper, Jecg12>
    implements Jecg12Service{


    @Resource
    private Jecg12Mapper jecg12Mapper;

    @Override
    public Jecg12 selectById(String pId) {
        return jecg12Mapper.selectOne(new QueryWrapper<Jecg12>().eq("p_id",pId));
    }

    @Override
    public int updateWaveLabel(Jecg12 jecg12) {
        UpdateWrapper<Jecg12> update = Wrappers.update();
        update.eq("p_id",jecg12.getPId()).set("wave_label",jecg12.getWaveLabel());
        return jecg12Mapper.update(null,update);
    }

    @Override
    public int updateBeatLabel(Jecg12 jecg12) {
        UpdateWrapper<Jecg12> update = Wrappers.update();
        update.eq("p_id",jecg12.getPId()).set("beat_labe",jecg12.getBeatLabe());
        return jecg12Mapper.update(null,update);
    }
}




