package com.ruoyi.xindian.order.mapper;

import com.ruoyi.xindian.order.domain.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xindian.order.domain.MCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 13401
* @description 针对表【area(地址区域表)】的数据库操作Mapper
* @createDate 2023-07-07 16:10:18
* @Entity com.ruoyi.xindian.order.domain.Area
*/
public interface AreaMapper extends BaseMapper<Area> {


    List<Area> selectAll(@Param("parentId") String parentId, @Param("level") Integer level);
}




