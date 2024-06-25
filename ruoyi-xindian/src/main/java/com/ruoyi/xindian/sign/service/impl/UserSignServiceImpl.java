package com.ruoyi.xindian.sign.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.sign.mapper.UserSignMapper;
import com.ruoyi.xindian.sign.domain.UserSign;
import com.ruoyi.xindian.sign.service.IUserSignService;

/**
 * signService业务层处理
 *
 * @author chenpeng
 * @date 2024-06-25
 */
@Service
public class UserSignServiceImpl implements IUserSignService
{
    @Autowired
    private UserSignMapper userSignMapper;

    /**
     * 查询sign
     *
     * @param id sign主键
     * @return sign
     */
    @Override
    public UserSign selectUserSignById(Long id)
    {
        return userSignMapper.selectUserSignById(id);
    }

    /**
     * 查询sign列表
     *
     * @param userSign sign
     * @return sign
     */
    @Override
    public List<UserSign> selectUserSignList(UserSign userSign)
    {
        return userSignMapper.selectUserSignList(userSign);
    }

    /**
     * 新增sign
     *
     * @param userSign sign
     * @return 结果
     */
    @Override
    public int insertUserSign(UserSign userSign)
    {
        return userSignMapper.insertUserSign(userSign);
    }

    /**
     * 修改sign
     *
     * @param userSign sign
     * @return 结果
     */
    @Override
    public int updateUserSign(UserSign userSign)
    {
        return userSignMapper.updateUserSign(userSign);
    }

    /**
     * 批量删除sign
     *
     * @param ids 需要删除的sign主键
     * @return 结果
     */
    @Override
    public int deleteUserSignByIds(Long[] ids)
    {
        return userSignMapper.deleteUserSignByIds(ids);
    }

    /**
     * 删除sign信息
     *
     * @param id sign主键
     * @return 结果
     */
    @Override
    public int deleteUserSignById(Long id)
    {
        return userSignMapper.deleteUserSignById(id);
    }

    @Override
    public UserSign selectUserSignByPhone(String phone) {
        return userSignMapper.selectUserSignByPhone(phone);
    }
}
