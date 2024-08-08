package com.ruoyi.xindian.wSuryvey.service;

import com.ruoyi.xindian.wSuryvey.domain.WSurveyHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author w
* @description 针对表【w_survey_history】的数据库操作Service
* @createDate 2024-08-06 08:58:47
*/
public interface WSurveyHistoryService extends IService<WSurveyHistory> {



    int  deleteWSurveyHistoryBywSurveyId(Long wSurveyId);

}
