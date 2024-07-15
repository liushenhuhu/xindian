package com.ruoyi.xindian.sign.service;

import java.util.List;
import com.ruoyi.xindian.sign.domain.UserSignLog;

/**
 * signService接口
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
public interface IUserSignLogService 
{
    /**
     * 查询sign
     * 
     * @param id sign主键
     * @return sign
     */
    public UserSignLog selectUserSignLogById(Long id);

    /**
     * 查询sign列表
     * 
     * @param userSignLog sign
     * @return sign集合
     */
    public List<UserSignLog> selectUserSignLogList(UserSignLog userSignLog);

    /**
     * 新增sign
     * 
     * @param userSignLog sign
     * @return 结果
     */
    public int insertUserSignLog(UserSignLog userSignLog);

    /**
     * 修改sign
     * 
     * @param userSignLog sign
     * @return 结果
     */
    public int updateUserSignLog(UserSignLog userSignLog);

    /**
     * 批量删除sign
     * 
     * @param ids 需要删除的sign主键集合
     * @return 结果
     */
    public int deleteUserSignLogByIds(Long[] ids);

    /**
     * 删除sign信息
     * 
     * @param id sign主键
     * @return 结果
     */
    public int deleteUserSignLogById(Long id);
}
