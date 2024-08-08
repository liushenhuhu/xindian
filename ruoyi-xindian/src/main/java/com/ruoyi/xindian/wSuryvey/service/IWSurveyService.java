package com.ruoyi.xindian.wSuryvey.service;

import java.util.List;
import com.ruoyi.xindian.wSuryvey.domain.WSurvey;

/**
 * wSuryveyService接口
 * 
 * @author chenpeng
 * @date 2024-07-18
 */
public interface IWSurveyService 
{
    /**
     * 查询wSuryvey
     * 
     * @param id wSuryvey主键
     * @return wSuryvey
     */
    public WSurvey selectWSurveyById(Long id);

    /**
     * 查询wSuryvey列表
     * 
     * @param wSurvey wSuryvey
     * @return wSuryvey集合
     */
    public List<WSurvey> selectWSurveyList(WSurvey wSurvey);

    /**
     * 新增wSuryvey
     * 
     * @param wSurvey wSuryvey
     * @return 结果
     */
    public int insertWSurvey(WSurvey wSurvey);

    /**
     * 修改wSuryvey
     * 
     * @param wSurvey wSuryvey
     * @return 结果
     */
    public int updateWSurvey(WSurvey wSurvey);

    /**
     * 批量删除wSuryvey
     * 
     * @param ids 需要删除的wSuryvey主键集合
     * @return 结果
     */
    public int deleteWSurveyByIds(Long[] ids);

    /**
     * 删除wSuryvey信息
     * 
     * @param id wSuryvey主键
     * @return 结果
     */
    public int deleteWSurveyById(Long id);

    WSurvey getScreening(String patientPhone);
}
