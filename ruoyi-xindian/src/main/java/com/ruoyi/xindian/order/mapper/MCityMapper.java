package com.ruoyi.xindian.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xindian.order.domain.MCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 13401
* @description 针对表【m_city(地区表)】的数据库操作Mapper
* @createDate 2023-07-05 14:49:56
* @Entity com.ruoyi.xindian.order.domain.MCity
*/
public interface MCityMapper extends BaseMapper<MCity> {



    List<MCity> selectAll(@Param("regionParentId") String regionParentId, @Param("level") Integer level);


}




