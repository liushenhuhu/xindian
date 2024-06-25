package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.ruoyi.xindian.patient.service.SingleHistoryDataService;
import com.ruoyi.xindian.patient.mapper.SingleHistoryDataMapper;
import com.ruoyi.xindian.patient_management.domain.WeeklyCount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【single_history_data】的数据库操作Service实现
* @createDate 2023-11-23 15:21:52
*/
@Service
public class SingleHistoryDataServiceImpl extends ServiceImpl<SingleHistoryDataMapper, SingleHistoryData>
    implements SingleHistoryDataService{



    @Resource
    private SingleHistoryDataMapper singleHistoryDataMapper;

    @Override
    public SingleHistoryData selectSingleHistoryDataByHisId(SingleHistoryData singleHistoryData) throws Exception {
        return singleHistoryDataMapper.selectSingleHistoryData(singleHistoryData);
    }

    @Override
    public List<SingleHistoryData> countDataByPhone(SingleHistoryData info) {
        return singleHistoryDataMapper.countDataByPhone(info);
    }
}




