package com.ruoyi.xindian.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.order.domain.SymptomType;
import com.ruoyi.xindian.order.service.SymptomTypeService;
import com.ruoyi.xindian.order.mapper.SymptomTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 13401
* @description 针对表【symptom_type】的数据库操作Service实现
* @createDate 2023-07-20 15:50:33
*/
@Service
public class SymptomTypeServiceImpl extends ServiceImpl<SymptomTypeMapper, SymptomType>
    implements SymptomTypeService{


    @Resource
    private SymptomTypeMapper symptomTypeMapper;

    @Override
    public List<String> typeList() {
        List<SymptomType> symptomTypes = symptomTypeMapper.selectList(null);
        List<String> list = new ArrayList<>();
        for (SymptomType c:symptomTypes){
            list.add(c.getType());
        }
        return list;
    }
}




