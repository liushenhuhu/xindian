package com.ruoyi.xindian.stLabel.service;

import com.ruoyi.xindian.stLabel.domain.StLabel;

import java.util.List;


/**
 * ST标注Service接口
 * 
 * @author liuyi
 * @date 2024-01-26
 */
public interface IStLabelService 
{
    /**
     * 查询ST标注
     * 
     * @param logId ST标注主键
     * @return ST标注
     */
    public StLabel selectStLabelByLogId(String logId);

    /**
     * 查询ST标注列表
     * 
     * @param stLabel ST标注
     * @return ST标注集合
     */
    public List<StLabel> selectStLabelList(StLabel stLabel);

    /**
     * 新增ST标注
     * 
     * @param stLabel ST标注
     * @return 结果
     */
    public int insertStLabel(StLabel stLabel);

    /**
     * 修改ST标注
     * 
     * @param stLabel ST标注
     * @return 结果
     */
    public int updateStLabel(StLabel stLabel);

    /**
     * 批量删除ST标注
     * 
     * @param logIds 需要删除的ST标注主键集合
     * @return 结果
     */
    public int deleteStLabelByLogIds(String[] logIds);

    /**
     * 删除ST标注信息
     * 
     * @param logId ST标注主键
     * @return 结果
     */
    public int deleteStLabelByLogId(String logId);
}
