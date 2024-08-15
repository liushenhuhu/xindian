package com.ruoyi.xindian.wSuryvey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.wSuryvey.domain.WSurveyHistory;
import com.ruoyi.xindian.wSuryvey.service.WSurveyHistoryService;
import com.ruoyi.xindian.wSuryvey.mapper.WSurveyHistoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author w
* @description 针对表【w_survey_history】的数据库操作Service实现
* @createDate 2024-08-06 08:58:47
*/
@Service
public class WSurveyHistoryServiceImpl extends ServiceImpl<WSurveyHistoryMapper, WSurveyHistory>
    implements WSurveyHistoryService{



    @Resource
    private WSurveyHistoryMapper wSurveyHistoryMapper;

    @Override
    public int deleteWSurveyHistoryBywSurveyId(Long wSurveyId) {
        return wSurveyHistoryMapper.delete(new LambdaQueryWrapper<WSurveyHistory>().eq(WSurveyHistory::getwSurveyId,wSurveyId));
    }
}




