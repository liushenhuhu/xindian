package com.ruoyi.xindian.alert_log.service;

import com.ruoyi.xindian.alert_log.domain.AlertLogJsonData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【alert_log_json_data】的数据库操作Service
* @createDate 2023-09-23 11:41:54
*/
public interface AlertLogJsonDataService extends IService<AlertLogJsonData> {


    /**
     * 通过logId和user_id去查询对应的json数据
     */
    AlertLogJsonData selectJsonData(AlertLogJsonData alertLogJsonData);


    /**
     * 通过logId和user_id去判断数据是该添加还是重组修改
     */

    Boolean updateOrInsertAlertLogJsonData(AlertLogJsonData alertLogJsonData);

}
