package com.ruoyi.xindian.order.service;

import com.ruoyi.xindian.order.domain.SymptomType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【symptom_type】的数据库操作Service
* @createDate 2023-07-20 15:50:33
*/
public interface SymptomTypeService extends IService<SymptomType> {


    List<String> typeList();

}
