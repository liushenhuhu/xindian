package com.ruoyi.xindian.wSuryvey.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.wSuryvey.mapper.WSurveyMapper;
import com.ruoyi.xindian.wSuryvey.domain.WSurvey;
import com.ruoyi.xindian.wSuryvey.service.IWSurveyService;

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
        return wSurveyMapper.insertWSurvey(wSurvey);
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
        return wSurveyMapper.updateWSurvey(wSurvey);
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
}
