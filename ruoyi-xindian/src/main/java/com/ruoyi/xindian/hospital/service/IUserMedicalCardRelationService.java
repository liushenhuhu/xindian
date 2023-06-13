package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.UserMedicalCardRelation;

/**
 * 用户就诊卡关系表Service接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface IUserMedicalCardRelationService 
{
    /**
     * 查询用户就诊卡关系表
     * 
     * @param id 用户就诊卡关系表主键
     * @return 用户就诊卡关系表
     */
    public UserMedicalCardRelation selectUserMedicalCardRelationById(Long id);

    /**
     * 查询用户就诊卡关系表列表
     * 
     * @param userMedicalCardRelation 用户就诊卡关系表
     * @return 用户就诊卡关系表集合
     */
    public List<UserMedicalCardRelation> selectUserMedicalCardRelationList(UserMedicalCardRelation userMedicalCardRelation);

    /**
     * 新增用户就诊卡关系表
     * 
     * @param userMedicalCardRelation 用户就诊卡关系表
     * @return 结果
     */
    public int insertUserMedicalCardRelation(UserMedicalCardRelation userMedicalCardRelation);

    /**
     * 修改用户就诊卡关系表
     * 
     * @param userMedicalCardRelation 用户就诊卡关系表
     * @return 结果
     */
    public int updateUserMedicalCardRelation(UserMedicalCardRelation userMedicalCardRelation);

    /**
     * 批量删除用户就诊卡关系表
     * 
     * @param ids 需要删除的用户就诊卡关系表主键集合
     * @return 结果
     */
    public int deleteUserMedicalCardRelationByIds(Long[] ids);

    /**
     * 删除用户就诊卡关系表信息
     * 
     * @param id 用户就诊卡关系表主键
     * @return 结果
     */
    public int deleteUserMedicalCardRelationById(Long id);
}
