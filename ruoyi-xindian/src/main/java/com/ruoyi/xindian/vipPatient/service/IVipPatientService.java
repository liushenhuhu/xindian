package com.ruoyi.xindian.vipPatient.service;

import java.util.List;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;

/**
 * vip用户Service接口
 *
 * @author ruoyi
 * @date 2023-07-06
 */
public interface IVipPatientService
{
    /**
     * 查询vip用户
     *
     * @param id vip用户主键
     * @return vip用户
     */
    public VipPatient selectVipPatientById(Long id);

    /**
     * 查询vip用户列表
     *
     * @param vipPatient vip用户
     * @return vip用户集合
     */
    public List<VipPatient> selectVipPatientList(VipPatient vipPatient);

    /**
     * 新增vip用户
     *
     * @param vipPatient vip用户
     * @return 结果
     */
    public int insertVipPatient(VipPatient vipPatient);

    /**
     * 修改vip用户
     *
     * @param vipPatient vip用户
     * @return 结果
     */
    public int updateVipPatient(VipPatient vipPatient);

    /**
     * 批量删除vip用户
     *
     * @param ids 需要删除的vip用户主键集合
     * @return 结果
     */
    public int deleteVipPatientByIds(Long[] ids);

    /**
     * 删除vip用户信息
     *
     * @param id vip用户主键
     * @return 结果
     */
    public int deleteVipPatientById(Long id);



    VipPatient findVipPhone(String phone);
}
