package com.ruoyi.xindian.log_user.service.impl;

import java.util.List;

import com.ruoyi.xindian.log_user.domain.SelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.log_user.mapper.AlertLogUserMapper;
import com.ruoyi.xindian.log_user.domain.AlertLogUser;
import com.ruoyi.xindian.log_user.service.IAlertLogUserService;

/**
 * 标注分配Service业务层处理
 * 
 * @author hanhan
 * @date 2022-12-30
 */
@Service
public class AlertLogUserServiceImpl implements IAlertLogUserService 
{
    @Autowired
    private AlertLogUserMapper alertLogUserMapper;

    /**
     * 查询标注分配
     * 
     * @param logId 标注分配主键
     * @return 标注分配
     */
    @Override
    public AlertLogUser selectAlertLogUserByLogId(String logId,String userId)
    {
        return alertLogUserMapper.selectAlertLogUserByLogId(logId,userId);
    }

    /**
     * 查询标注分配列表
     * 
     * @param alertLogUser 标注分配
     * @return 标注分配
     */
    @Override
    public List<AlertLogUser> selectAlertLogUserList(AlertLogUser alertLogUser)
    {
        return alertLogUserMapper.selectAlertLogUserList(alertLogUser);
    }

    /**
     * 新增标注分配
     * 
     * @param alertLogUser 标注分配
     * @return 结果
     */
    @Override
    public int insertAlertLogUser(AlertLogUser alertLogUser)
    {
        return alertLogUserMapper.insertAlertLogUser(alertLogUser);
    }

    /**
     * 修改标注分配
     * 
     * @param alertLogUser 标注分配
     * @return 结果
     */
    @Override
    public int updateAlertLogUser(AlertLogUser alertLogUser)
    {
        return alertLogUserMapper.updateAlertLogUser(alertLogUser);
    }

    /**
     * 批量删除标注分配
     * 
     * @param logIds 需要删除的标注分配主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogUserByLogIds(String[] logIds)
    {
        return alertLogUserMapper.deleteAlertLogUserByLogIds(logIds);
    }

    /**
     * 删除标注分配信息
     * 
     * @param logId 标注分配主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogUserByLogId(String logId)
    {
        return alertLogUserMapper.deleteAlertLogUserByLogId(logId);
    }

    @Override
    public List<SelectVO> selectList() {
        return alertLogUserMapper.selectList();
    }
}
