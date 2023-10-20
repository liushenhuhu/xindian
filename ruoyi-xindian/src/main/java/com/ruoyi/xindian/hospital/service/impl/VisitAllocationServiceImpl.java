package com.ruoyi.xindian.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.hospital.domain.VisitAllocation;
import com.ruoyi.xindian.hospital.service.VisitAllocationService;
import com.ruoyi.xindian.hospital.mapper.VisitAllocationMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
* @author 13401
* @description 针对表【visit_allocation】的数据库操作Service实现
* @createDate 2023-10-19 15:18:52
*/
@Service
public class VisitAllocationServiceImpl extends ServiceImpl<VisitAllocationMapper, VisitAllocation>
    implements VisitAllocationService{


    @Resource
    private VisitAllocationMapper visitAllocationMapper;


    @Resource
    private RedisTemplate<String ,Object> redisTemplate;

    @Override
    public int addTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // 创建时间段对象并插入数据库
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        while (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
            VisitAllocation visitAllocation = new VisitAllocation();
            visitAllocation.setStartTime(sdf.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 10);
            visitAllocation.setEndTime(sdf.format(calendar.getTime()));
            visitAllocation.setCategory(1);
            visitAllocationMapper.insertTimeSlot(visitAllocation);
        }

        // 重置日历对象
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 0);

        while (calendar.get(Calendar.HOUR_OF_DAY) < 17 || (calendar.get(Calendar.HOUR_OF_DAY) == 17 && calendar.get(Calendar.MINUTE) <= 30)) {
            VisitAllocation visitAllocation = new VisitAllocation();
            visitAllocation.setStartTime(sdf.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 10);
            visitAllocation.setEndTime(sdf.format(calendar.getTime()));
            visitAllocation.setCategory(2);
            visitAllocationMapper.insertTimeSlot(visitAllocation);
        }
        return 1;
    }

    @Override
    public HashMap<Object, Object> addRedis() {

        HashMap<Object, Object> stringObjectHashMap = new HashMap<>();
        List<VisitAllocation> category = visitAllocationMapper.selectList(new QueryWrapper<VisitAllocation>().eq("category", 1));
        stringObjectHashMap.put("am",category);
        List<VisitAllocation> categoryPm = visitAllocationMapper.selectList(new QueryWrapper<VisitAllocation>().eq("category", 2));
        stringObjectHashMap.put("pm",categoryPm);
        return stringObjectHashMap;
    }
}




