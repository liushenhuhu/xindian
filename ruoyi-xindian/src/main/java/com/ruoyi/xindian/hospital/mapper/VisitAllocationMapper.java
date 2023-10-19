package com.ruoyi.xindian.hospital.mapper;

import com.ruoyi.xindian.hospital.domain.VisitAllocation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13401
* @description 针对表【visit_allocation】的数据库操作Mapper
* @createDate 2023-10-19 15:18:52
* @Entity com.ruoyi.xindian.hospital.domain.VisitAllocation
*/
public interface VisitAllocationMapper extends BaseMapper<VisitAllocation> {


    int insertTimeSlot(VisitAllocation visitAllocation);
}




