package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.UserMedicalCardMapper;
import com.ruoyi.xindian.hospital.domain.UserMedicalCard;
import com.ruoyi.xindian.hospital.service.IUserMedicalCardService;

/**
 * 用户就诊卡信息表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class UserMedicalCardServiceImpl implements IUserMedicalCardService 
{
    @Autowired
    private UserMedicalCardMapper userMedicalCardMapper;

    /**
     * 查询用户就诊卡信息表
     * 
     * @param id 用户就诊卡信息表主键
     * @return 用户就诊卡信息表
     */
    @Override
    public UserMedicalCard selectUserMedicalCardById(Long id)
    {
        return userMedicalCardMapper.selectUserMedicalCardById(id);
    }

    /**
     * 查询用户就诊卡信息表列表
     * 
     * @param userMedicalCard 用户就诊卡信息表
     * @return 用户就诊卡信息表
     */
    @Override
    public List<UserMedicalCard> selectUserMedicalCardList(UserMedicalCard userMedicalCard)
    {
        return userMedicalCardMapper.selectUserMedicalCardList(userMedicalCard);
    }

    /**
     * 新增用户就诊卡信息表
     * 
     * @param userMedicalCard 用户就诊卡信息表
     * @return 结果
     */
    @Override
    public int insertUserMedicalCard(UserMedicalCard userMedicalCard)
    {
        return userMedicalCardMapper.insertUserMedicalCard(userMedicalCard);
    }

    /**
     * 修改用户就诊卡信息表
     * 
     * @param userMedicalCard 用户就诊卡信息表
     * @return 结果
     */
    @Override
    public int updateUserMedicalCard(UserMedicalCard userMedicalCard)
    {
        return userMedicalCardMapper.updateUserMedicalCard(userMedicalCard);
    }

    /**
     * 批量删除用户就诊卡信息表
     * 
     * @param ids 需要删除的用户就诊卡信息表主键
     * @return 结果
     */
    @Override
    public int deleteUserMedicalCardByIds(Long[] ids)
    {
        return userMedicalCardMapper.deleteUserMedicalCardByIds(ids);
    }

    /**
     * 删除用户就诊卡信息表信息
     * 
     * @param id 用户就诊卡信息表主键
     * @return 结果
     */
    @Override
    public int deleteUserMedicalCardById(Long id)
    {
        return userMedicalCardMapper.deleteUserMedicalCardById(id);
    }
}
