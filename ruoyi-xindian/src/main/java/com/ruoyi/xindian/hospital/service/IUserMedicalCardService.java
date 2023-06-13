package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.UserMedicalCard;

/**
 * 用户就诊卡信息表Service接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface IUserMedicalCardService 
{
    /**
     * 查询用户就诊卡信息表
     * 
     * @param id 用户就诊卡信息表主键
     * @return 用户就诊卡信息表
     */
    public UserMedicalCard selectUserMedicalCardById(Long id);

    /**
     * 查询用户就诊卡信息表列表
     * 
     * @param userMedicalCard 用户就诊卡信息表
     * @return 用户就诊卡信息表集合
     */
    public List<UserMedicalCard> selectUserMedicalCardList(UserMedicalCard userMedicalCard);

    /**
     * 新增用户就诊卡信息表
     * 
     * @param userMedicalCard 用户就诊卡信息表
     * @return 结果
     */
    public int insertUserMedicalCard(UserMedicalCard userMedicalCard);

    /**
     * 修改用户就诊卡信息表
     * 
     * @param userMedicalCard 用户就诊卡信息表
     * @return 结果
     */
    public int updateUserMedicalCard(UserMedicalCard userMedicalCard);

    /**
     * 批量删除用户就诊卡信息表
     * 
     * @param ids 需要删除的用户就诊卡信息表主键集合
     * @return 结果
     */
    public int deleteUserMedicalCardByIds(Long[] ids);

    /**
     * 删除用户就诊卡信息表信息
     * 
     * @param id 用户就诊卡信息表主键
     * @return 结果
     */
    public int deleteUserMedicalCardById(Long id);
}
