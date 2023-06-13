package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.UserCase;

/**
 * 用户病历Service接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface IUserCaseService 
{
    /**
     * 查询用户病历
     * 
     * @param id 用户病历主键
     * @return 用户病历
     */
    public UserCase selectUserCaseById(Long id);

    /**
     * 查询用户病历列表
     * 
     * @param userCase 用户病历
     * @return 用户病历集合
     */
    public List<UserCase> selectUserCaseList(UserCase userCase);

    /**
     * 新增用户病历
     * 
     * @param userCase 用户病历
     * @return 结果
     */
    public int insertUserCase(UserCase userCase);

    /**
     * 修改用户病历
     * 
     * @param userCase 用户病历
     * @return 结果
     */
    public int updateUserCase(UserCase userCase);

    /**
     * 批量删除用户病历
     * 
     * @param ids 需要删除的用户病历主键集合
     * @return 结果
     */
    public int deleteUserCaseByIds(Long[] ids);

    /**
     * 删除用户病历信息
     * 
     * @param id 用户病历主键
     * @return 结果
     */
    public int deleteUserCaseById(Long id);
}
