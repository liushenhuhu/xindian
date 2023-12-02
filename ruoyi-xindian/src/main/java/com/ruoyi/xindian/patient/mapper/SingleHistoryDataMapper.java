package com.ruoyi.xindian.patient.mapper;

import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 13401
* @description 针对表【single_history_data】的数据库操作Mapper
* @createDate 2023-11-23 15:21:52
* @Entity com.ruoyi.xindian.patient.domain.SingleHistoryData
*/
public interface SingleHistoryDataMapper extends BaseMapper<SingleHistoryData> {


    SingleHistoryData selectSingleHistoryData(SingleHistoryData singleHistoryData);

}




