package com.ruoyi.xindian.hospital.service;

import com.ruoyi.xindian.hospital.domain.VisitAllocation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
* @author 13401
* @description 针对表【visit_allocation】的数据库操作Service
* @createDate 2023-10-19 15:18:52
*/
public interface VisitAllocationService extends IService<VisitAllocation> {


    int addTime();


    HashMap<Object ,Object> addRedis();


    VisitAllocation selectById(Integer slotId);

}
