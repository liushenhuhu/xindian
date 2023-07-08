package com.ruoyi.xindian.vipPatient.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.vipPatient.mapper.VipPatientMapper;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;

/**
 * vip用户Service业务层处理
 *
 * @author ruoyi
 * @date 2023-07-06
 */
@Service
public class VipPatientServiceImpl implements IVipPatientService
{
    @Autowired
    private VipPatientMapper vipPatientMapper;

    /**
     * 查询vip用户
     *
     * @param id vip用户主键
     * @return vip用户
     */
    @Override
    public VipPatient selectVipPatientById(Long id)
    {
        return vipPatientMapper.selectVipPatientById(id);
    }

    /**
     * 查询vip用户列表
     *
     * @param vipPatient vip用户
     * @return vip用户
     */
    @Override
    public List<VipPatient> selectVipPatientList(VipPatient vipPatient)
    {
        return vipPatientMapper.selectVipPatientList(vipPatient);
    }

    /**
     * 新增vip用户
     *
     * @param vipPatient vip用户
     * @return 结果
     */
    @Override
    public int insertVipPatient(VipPatient vipPatient)
    {
        return vipPatientMapper.insertVipPatient(vipPatient);
    }

    /**
     * 修改vip用户
     *
     * @param vipPatient vip用户
     * @return 结果
     */
    @Override
    public int updateVipPatient(VipPatient vipPatient)
    {
        return vipPatientMapper.updateVipPatient(vipPatient);
    }

    /**
     * 批量删除vip用户
     *
     * @param ids 需要删除的vip用户主键
     * @return 结果
     */
    @Override
    public int deleteVipPatientByIds(Long[] ids)
    {
        return vipPatientMapper.deleteVipPatientByIds(ids);
    }

    /**
     * 删除vip用户信息
     *
     * @param id vip用户主键
     * @return 结果
     */
    @Override
    public int deleteVipPatientById(Long id)
    {
        return vipPatientMapper.deleteVipPatientById(id);
    }

    @Override
    public VipPatient findVipPhone(String phone) {
        return vipPatientMapper.selectPhone(phone);
    }
}
