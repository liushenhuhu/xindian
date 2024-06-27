package com.ruoyi.xindian.sign.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.sign.mapper.UserSignLogMapper;
import com.ruoyi.xindian.sign.domain.UserSignLog;
import com.ruoyi.xindian.sign.service.IUserSignLogService;

/**
 * signService业务层处理
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
@Service
public class UserSignLogServiceImpl implements IUserSignLogService 
{
    @Autowired
    private UserSignLogMapper userSignLogMapper;

    /**
     * 查询sign
     * 
     * @param id sign主键
     * @return sign
     */
    @Override
    public UserSignLog selectUserSignLogById(Long id)
    {
        return userSignLogMapper.selectUserSignLogById(id);
    }

    /**
     * 查询sign列表
     * 
     * @param userSignLog sign
     * @return sign
     */
    @Override
    public List<UserSignLog> selectUserSignLogList(UserSignLog userSignLog)
    {
        return userSignLogMapper.selectUserSignLogList(userSignLog);
    }

    /**
     * 新增sign
     * 
     * @param userSignLog sign
     * @return 结果
     */
    @Override
    public int insertUserSignLog(UserSignLog userSignLog)
    {
        userSignLog.setCreateTime(DateUtils.getNowDate());
        return userSignLogMapper.insertUserSignLog(userSignLog);
    }

    /**
     * 修改sign
     * 
     * @param userSignLog sign
     * @return 结果
     */
    @Override
    public int updateUserSignLog(UserSignLog userSignLog)
    {
        return userSignLogMapper.updateUserSignLog(userSignLog);
    }

    /**
     * 批量删除sign
     * 
     * @param ids 需要删除的sign主键
     * @return 结果
     */
    @Override
    public int deleteUserSignLogByIds(Long[] ids)
    {
        return userSignLogMapper.deleteUserSignLogByIds(ids);
    }

    /**
     * 删除sign信息
     * 
     * @param id sign主键
     * @return 结果
     */
    @Override
    public int deleteUserSignLogById(Long id)
    {
        return userSignLogMapper.deleteUserSignLogById(id);
    }
}
