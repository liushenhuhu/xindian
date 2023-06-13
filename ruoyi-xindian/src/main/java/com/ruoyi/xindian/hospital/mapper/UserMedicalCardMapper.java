package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.UserMedicalCard;

/**
 * 用户就诊卡信息表Mapper接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface UserMedicalCardMapper 
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
     * 删除用户就诊卡信息表
     * 
     * @param id 用户就诊卡信息表主键
     * @return 结果
     */
    public int deleteUserMedicalCardById(Long id);

    /**
     * 批量删除用户就诊卡信息表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserMedicalCardByIds(Long[] ids);
}
