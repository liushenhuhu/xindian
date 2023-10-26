package com.ruoyi.xindian.alert_log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.ruoyi.xindian.alert_log.service.JecgSingleService;
import com.ruoyi.xindian.alert_log.mapper.JecgSingleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
        JecgSingle jecgSingle1 = selectById(jecgSingle.getpId());
        if(jecgSingle1.getBeatLabel()==null||"".equals(jecgSingle1.getBeatLabel())){
            jecgSingle1.setBeatLabel(jecgSingle.getBeatLabel());
            return jecgSingleMapper.update(jecgSingle1,new QueryWrapper<JecgSingle>().eq("p_id",jecgSingle1.getpId()));
        }
        JSONObject parse = (JSONObject) JSONObject.parse(jecgSingle1.getBeatLabel());
        JSONObject webText = (JSONObject) JSONObject.parse(jecgSingle.getBeatLabel());
        Iterator<String> iterator = webText.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            JSONObject value = (JSONObject)webText.get(key);
            if(value.size()!=0){
                parse.remove(key);
                parse.put(key,value);
            }
        }
//        Iterator<Map.Entry<String, Object>> iterator = webText.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, Object> next = iterator.next();
//            JSONObject value =(JSONObject) next.getValue();
//            if(value.size()!=0){
//                parse.remove(next.getKey());
//                parse.put(next.getKey(),value);
//            }
//        }
        jecgSingle1.setBeatLabel(JSONObject.toJSONString(parse));
        return jecgSingleMapper.update(jecgSingle1,new QueryWrapper<JecgSingle>().eq("p_id",jecgSingle1.getpId()));
    }

    @Override
    public JecgSingle selectById(String pId) {
        return jecgSingleMapper.selectOne(new QueryWrapper<JecgSingle>().eq("p_id",pId));
    }

    @Override
    public int updateWaveLabel(JecgSingle jecgSingle) {
        JecgSingle jecgSingle1 = selectById(jecgSingle.getpId());

        if(jecgSingle1.getWaveLabel()==null||"".equals(jecgSingle1.getWaveLabel())){
            jecgSingle1.setWaveLabel(jecgSingle.getWaveLabel());
            return jecgSingleMapper.update(jecgSingle1,new QueryWrapper<JecgSingle>().eq("p_id",jecgSingle1.getpId()));
        }

        JSONObject parse = (JSONObject) JSONObject.parse(jecgSingle1.getWaveLabel());
        JSONObject webText = (JSONObject) JSONObject.parse(jecgSingle.getWaveLabel());
        Iterator<String> iterator = webText.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            JSONObject value = (JSONObject)webText.get(key);
            if(value.size()!=0){
                parse.remove(key);
                parse.put(key,value);
            }
        }

        jecgSingle1.setWaveLabel(JSONObject.toJSONString(parse));
        return jecgSingleMapper.update(jecgSingle1,new QueryWrapper<JecgSingle>().eq("p_id",jecgSingle1.getpId()));
    }
}




