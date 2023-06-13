package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.UserCaseMapper;
import com.ruoyi.xindian.hospital.domain.UserCase;
import com.ruoyi.xindian.hospital.service.IUserCaseService;

/**
 * 用户病历Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class UserCaseServiceImpl implements IUserCaseService 
{
    @Autowired
    private UserCaseMapper userCaseMapper;

    /**
     * 查询用户病历
     * 
     * @param id 用户病历主键
     * @return 用户病历
     */
    @Override
    public UserCase selectUserCaseById(Long id)
    {
        return userCaseMapper.selectUserCaseById(id);
    }

    /**
     * 查询用户病历列表
     * 
     * @param userCase 用户病历
     * @return 用户病历
     */
    @Override
    public List<UserCase> selectUserCaseList(UserCase userCase)
    {
        return userCaseMapper.selectUserCaseList(userCase);
    }

    /**
     * 新增用户病历
     * 
     * @param userCase 用户病历
     * @return 结果
     */
    @Override
    public int insertUserCase(UserCase userCase)
    {
        return userCaseMapper.insertUserCase(userCase);
    }

    /**
     * 修改用户病历
     * 
     * @param userCase 用户病历
     * @return 结果
     */
    @Override
    public int updateUserCase(UserCase userCase)
    {
        return userCaseMapper.updateUserCase(userCase);
    }

    /**
     * 批量删除用户病历
     * 
     * @param ids 需要删除的用户病历主键
     * @return 结果
     */
    @Override
    public int deleteUserCaseByIds(Long[] ids)
    {
        return userCaseMapper.deleteUserCaseByIds(ids);
    }

    /**
     * 删除用户病历信息
     * 
     * @param id 用户病历主键
     * @return 结果
     */
    @Override
    public int deleteUserCaseById(Long id)
    {
        return userCaseMapper.deleteUserCaseById(id);
    }
}
