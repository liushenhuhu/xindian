package com.ruoyi.xindian.wSuryvey.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ruoyi.xindian.wSuryvey.domain.WSurveyHistory;
import com.ruoyi.xindian.wSuryvey.service.WSurveyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.wSuryvey.mapper.WSurveyMapper;
import com.ruoyi.xindian.wSuryvey.domain.WSurvey;
import com.ruoyi.xindian.wSuryvey.service.IWSurveyService;

import javax.annotation.Resource;

/**
 * wSuryveyService业务层处理
 * 
 * @author chenpeng
 * @date 2024-07-18
 */
@Service
public class WSurveyServiceImpl implements IWSurveyService 
{
    @Autowired
    private WSurveyMapper wSurveyMapper;

    @Resource
    private WSurveyHistoryService wSurveyHistoryService;

    /**
     * 查询wSuryvey
     * 
     * @param id wSuryvey主键
     * @return wSuryvey
     */
    @Override
    public WSurvey selectWSurveyById(Long id)
    {
        return wSurveyMapper.selectWSurveyById(id);
    }

    /**
     * 查询wSuryvey列表
     * 
     * @param wSurvey wSuryvey
     * @return wSuryvey
     */
    @Override
    public List<WSurvey> selectWSurveyList(WSurvey wSurvey)
    {
        return wSurveyMapper.selectWSurveyList(wSurvey);
    }

    /**
     * 新增wSuryvey
     * 
     * @param wSurvey wSuryvey
     * @return 结果
     */
    @Override
    public int insertWSurvey(WSurvey wSurvey)
    {
        wSurvey.setCreateTime(new Date());
        int i = wSurveyMapper.insertWSurvey(wSurvey);
        if (i>0){
            updateByWSurveyHistory(wSurvey);
        }
        return i;
    }

    private void  updateByWSurveyHistory(WSurvey wSurvey) {
        List<WSurveyHistory> wSurveyHistories = wSurvey.getwSurveyHistories();
        wSurveyHistoryService.deleteWSurveyHistoryBywSurveyId(wSurvey.getId());
        for (WSurveyHistory wSurveyHistory : wSurveyHistories) {
            wSurveyHistory.setwSurveyId(wSurvey.getId());
        }
        if (!wSurveyHistories.isEmpty()){
            wSurveyHistoryService.saveBatch(wSurveyHistories);
        }

    }

    /**
     * 修改wSuryvey
     * 
     * @param wSurvey wSuryvey
     * @return 结果
     */
    @Override
    public int updateWSurvey(WSurvey wSurvey)
    {
        int i = wSurveyMapper.updateWSurvey(wSurvey);
        if (i>0){
            updateByWSurveyHistory(wSurvey);
        }
        return i;
    }

    /**
     * 批量删除wSuryvey
     * 
     * @param ids 需要删除的wSuryvey主键
     * @return 结果
     */
    @Override
    public int deleteWSurveyByIds(Long[] ids)
    {
        return wSurveyMapper.deleteWSurveyByIds(ids);
    }

    /**
     * 删除wSuryvey信息
     * 
     * @param id wSuryvey主键
     * @return 结果
     */
    @Override
    public int deleteWSurveyById(Long id)
    {
        return wSurveyMapper.deleteWSurveyById(id);
    }

    @Override
    public WSurvey getScreening(String patientPhone) {
        List<WSurvey> wSurveys = wSurveyMapper.selectWSurveyByPhone(patientPhone);
        if (wSurveys.isEmpty()){
            return null;
        }
        return wSurveys.get(0);
    }
}
