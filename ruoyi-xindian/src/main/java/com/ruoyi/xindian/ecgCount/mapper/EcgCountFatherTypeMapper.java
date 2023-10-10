package com.ruoyi.xindian.ecgCount.mapper;

import com.ruoyi.xindian.ecgCount.domain.EcgCountFatherType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 13401
* @description 针对表【ecg_count_father_type】的数据库操作Mapper
* @createDate 2023-10-10 12:23:53
* @Entity com.ruoyi.xindian.ecgCount.domain.EcgCountFatherType
*/
public interface EcgCountFatherTypeMapper extends BaseMapper<EcgCountFatherType> {


    List<EcgCountFatherType> getEcgTypeOrderBy();


}




