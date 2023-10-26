package com.ruoyi.xindian.alert_log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.alert_log.domain.Jecg12;
import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.ruoyi.xindian.alert_log.service.Jecg12Service;
import com.ruoyi.xindian.alert_log.mapper.Jecg12Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;

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
        Jecg12 jecg121 = selectById(jecg12.getpId());
        if(jecg121.getWaveLabel()==null||"".equals(jecg121.getWaveLabel())){
            jecg121.setWaveLabel(jecg12.getWaveLabel());
            return jecg12Mapper.update(jecg121,new QueryWrapper<Jecg12>().eq("p_id",jecg12.getpId()));
        }
        JSONObject parse = (JSONObject) JSONObject.parse(jecg121.getWaveLabel());
        JSONObject webText = (JSONObject) JSONObject.parse(jecg12.getWaveLabel());
        Iterator<String> iterator = webText.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            JSONObject value = (JSONObject)webText.get(key);
            if(value.size()!=0){
                parse.remove(key);
                parse.put(key,value);
            }
        }
        jecg121.setWaveLabel(JSONObject.toJSONString(parse));
        return jecg12Mapper.update(jecg121,new QueryWrapper<Jecg12>().eq("p_id",jecg12.getpId()));
    }

    @Override
    public int updateBeatLabel(Jecg12 jecg12) {
        Jecg12 jecg121 = selectById(jecg12.getpId());
        if(jecg121.getBeatLabel()==null||"".equals(jecg121.getBeatLabel())){
            jecg121.setBeatLabel(jecg12.getBeatLabel());
            return jecg12Mapper.update(jecg121,new QueryWrapper<Jecg12>().eq("p_id",jecg12.getpId()));
        }
        JSONObject parse = (JSONObject) JSONObject.parse(jecg121.getBeatLabel());
        JSONObject webText = (JSONObject) JSONObject.parse(jecg12.getBeatLabel());
        Iterator<String> iterator = webText.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            JSONObject value = (JSONObject)webText.get(key);
            if(value.size()!=0){
                parse.remove(key);
                parse.put(key,value);
            }
        }
        jecg121.setBeatLabel(JSONObject.toJSONString(parse));

        return jecg12Mapper.update(jecg121,new QueryWrapper<Jecg12>().eq("p_id",jecg12.getpId()));
    }
}




