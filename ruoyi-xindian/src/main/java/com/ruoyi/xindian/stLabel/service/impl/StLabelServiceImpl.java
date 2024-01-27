package com.ruoyi.xindian.stLabel.service.impl;

import java.util.List;

import com.ruoyi.xindian.stLabel.domain.StLabel;
import com.ruoyi.xindian.stLabel.mapper.StLabelMapper;
import com.ruoyi.xindian.stLabel.service.IStLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ST标注Service业务层处理
 * 
 * @author liuyi
 * @date 2024-01-26
 */
@Service
public class StLabelServiceImpl implements IStLabelService
{
    @Autowired
    private StLabelMapper stLabelMapper;

    /**
     * 查询ST标注
     * 
     * @param logId ST标注主键
     * @return ST标注
     */
    @Override
    public StLabel selectStLabelByLogId(String logId)
    {
        return stLabelMapper.selectStLabelByLogId(logId);
    }

    /**
     * 查询ST标注列表
     * 
     * @param stLabel ST标注
     * @return ST标注
     */
    @Override
    public List<StLabel> selectStLabelList(StLabel stLabel)
    {
        return stLabelMapper.selectStLabelList(stLabel);
    }

    /**
     * 新增ST标注
     * 
     * @param stLabel ST标注
     * @return 结果
     */
    @Override
    public int insertStLabel(StLabel stLabel)
    {
        return stLabelMapper.insertStLabel(stLabel);
    }

    /**
     * 修改ST标注
     * 
     * @param stLabel ST标注
     * @return 结果
     */
    @Override
    public int updateStLabel(StLabel stLabel)
    {
        return stLabelMapper.updateStLabel(stLabel);
    }

    /**
     * 批量删除ST标注
     * 
     * @param logIds 需要删除的ST标注主键
     * @return 结果
     */
    @Override
    public int deleteStLabelByLogIds(String[] logIds)
    {
        return stLabelMapper.deleteStLabelByLogIds(logIds);
    }

    /**
     * 删除ST标注信息
     * 
     * @param logId ST标注主键
     * @return 结果
     */
    @Override
    public int deleteStLabelByLogId(String logId)
    {
        return stLabelMapper.deleteStLabelByLogId(logId);
    }
}
