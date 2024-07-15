package com.ruoyi.xindian.sign.service;

import java.util.List;
import com.ruoyi.xindian.sign.domain.UserSign;

/**
 * signService接口
 *
 * @author chenpeng
 * @date 2024-06-25
 */
public interface IUserSignService
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
     * 批量删除sign
     *
     * @param ids 需要删除的sign主键集合
     * @return 结果
     */
    public int deleteUserSignByIds(Long[] ids);

    /**
     * 删除sign信息
     *
     * @param id sign主键
     * @return 结果
     */
    public int deleteUserSignById(Long id);

    UserSign selectUserSignByPhone(String phone);
}
