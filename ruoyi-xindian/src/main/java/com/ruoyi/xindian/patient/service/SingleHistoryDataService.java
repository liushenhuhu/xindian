package com.ruoyi.xindian.patient.service;

import com.ruoyi.xindian.patient.domain.SingleHistoryData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【single_history_data】的数据库操作Service
* @createDate 2023-11-23 15:21:52
*/
public interface SingleHistoryDataService extends IService<SingleHistoryData> {


    SingleHistoryData selectSingleHistoryDataByHisId(SingleHistoryData singleHistoryData) throws Exception;

}
