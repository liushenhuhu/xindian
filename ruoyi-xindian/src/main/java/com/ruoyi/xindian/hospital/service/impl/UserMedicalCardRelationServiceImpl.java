package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.UserMedicalCardRelationMapper;
import com.ruoyi.xindian.hospital.domain.UserMedicalCardRelation;
import com.ruoyi.xindian.hospital.service.IUserMedicalCardRelationService;

/**
 * 用户就诊卡关系表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class UserMedicalCardRelationServiceImpl implements IUserMedicalCardRelationService 
{
    @Autowired
    private UserMedicalCardRelationMapper userMedicalCardRelationMapper;

    /**
     * 查询用户就诊卡关系表
     * 
     * @param id 用户就诊卡关系表主键
     * @return 用户就诊卡关系表
     */
    @Override
    public UserMedicalCardRelation selectUserMedicalCardRelationById(Long id)
    {
        return userMedicalCardRelationMapper.selectUserMedicalCardRelationById(id);
    }

    /**
     * 查询用户就诊卡关系表列表
     * 
     * @param userMedicalCardRelation 用户就诊卡关系表
     * @return 用户就诊卡关系表
     */
    @Override
    public List<UserMedicalCardRelation> selectUserMedicalCardRelationList(UserMedicalCardRelation userMedicalCardRelation)
    {
        return userMedicalCardRelationMapper.selectUserMedicalCardRelationList(userMedicalCardRelation);
    }

    /**
     * 新增用户就诊卡关系表
     * 
     * @param userMedicalCardRelation 用户就诊卡关系表
     * @return 结果
     */
    @Override
    public int insertUserMedicalCardRelation(UserMedicalCardRelation userMedicalCardRelation)
    {
        return userMedicalCardRelationMapper.insertUserMedicalCardRelation(userMedicalCardRelation);
    }

    /**
     * 修改用户就诊卡关系表
     * 
     * @param userMedicalCardRelation 用户就诊卡关系表
     * @return 结果
     */
    @Override
    public int updateUserMedicalCardRelation(UserMedicalCardRelation userMedicalCardRelation)
    {
        return userMedicalCardRelationMapper.updateUserMedicalCardRelation(userMedicalCardRelation);
    }

    /**
     * 批量删除用户就诊卡关系表
     * 
     * @param ids 需要删除的用户就诊卡关系表主键
     * @return 结果
     */
    @Override
    public int deleteUserMedicalCardRelationByIds(Long[] ids)
    {
        return userMedicalCardRelationMapper.deleteUserMedicalCardRelationByIds(ids);
    }

    /**
     * 删除用户就诊卡关系表信息
     * 
     * @param id 用户就诊卡关系表主键
     * @return 结果
     */
    @Override
    public int deleteUserMedicalCardRelationById(Long id)
    {
        return userMedicalCardRelationMapper.deleteUserMedicalCardRelationById(id);
    }
}
