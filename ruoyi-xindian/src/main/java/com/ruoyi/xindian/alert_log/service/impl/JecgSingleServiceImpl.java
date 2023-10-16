package com.ruoyi.xindian.alert_log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.ruoyi.xindian.alert_log.service.JecgSingleService;
import com.ruoyi.xindian.alert_log.mapper.JecgSingleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 13401
* @description 针对表【jecg_single(静态心电数据)】的数据库操作Service实现
* @createDate 2023-10-16 13:03:15
*/
@Service
public class JecgSingleServiceImpl extends ServiceImpl<JecgSingleMapper, JecgSingle>
    implements JecgSingleService{


    @Resource
    private JecgSingleMapper jecgSingleMapper;

    @Override
    public int updateBeatLabel(JecgSingle jecgSingle) {
        JecgSingle jecgSingle1 = new JecgSingle();
        jecgSingle1.setpId(jecgSingle.getpId());
        jecgSingle1.setBeatLabel(jecgSingle.getBeatLabel());
        return jecgSingleMapper.update(jecgSingle1,new QueryWrapper<JecgSingle>().eq("p_id",jecgSingle1.getpId()));
    }

    @Override
    public JecgSingle selectById(String pId) {
        return jecgSingleMapper.selectOne(new QueryWrapper<JecgSingle>().eq("p_id",pId));
    }

    @Override
    public int updateWaveLabel(JecgSingle jecgSingle) {

        JecgSingle jecgSingle1 = selectById(jecgSingle.getpId());

        JSONObject parse = (JSONObject) JSONObject.parse(jecgSingle1.getWaveLabel());


        parse.remove(jecgSingle.getField());
        parse.put(jecgSingle.getField(),JSONObject.parse(jecgSingle.getWaveLabel()));

        jecgSingle1.setWaveLabel(JSONObject.toJSONString(parse));

        return jecgSingleMapper.update(jecgSingle1,new QueryWrapper<JecgSingle>().eq("p_id",jecgSingle1.getpId()));
    }
}




