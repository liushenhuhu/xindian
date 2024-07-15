package com.ruoyi.xindian.sign.mapper;

import java.util.List;
import com.ruoyi.xindian.sign.domain.UserSign;

/**
 * signMapper接口
 *
 * @author chenpeng
 * @date 2024-06-25
 */
public interface UserSignMapper
{
    /**
     * 查询sign
     *
     * @param id sign主键
     * @return sign
     */
    public UserSign selectUserSignById(Long id);

    /**
     * 查询sign列表
     *
     * @param userSign sign
     * @return sign集合
     */
    public List<UserSign> selectUserSignList(UserSign userSign);

    /**
     * 新增sign
     *
     * @param userSign sign
     * @return 结果
     */
    public int insertUserSign(UserSign userSign);

    /**
     * 修改sign
     *
     * @param userSign sign
     * @return 结果
     */
    public int updateUserSign(UserSign userSign);

    /**
     * 删除sign
     *
     * @param id sign主键
     * @return 结果
     */
    public int deleteUserSignById(Long id);

    /**
     * 批量删除sign
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserSignByIds(Long[] ids);

    UserSign selectUserSignByPhone(String phone);
}
