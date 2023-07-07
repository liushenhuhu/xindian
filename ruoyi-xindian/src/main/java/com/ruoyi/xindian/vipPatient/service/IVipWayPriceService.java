package com.ruoyi.xindian.vipPatient.service;

import java.util.List;
import com.ruoyi.xindian.vipPatient.domain.VipWayPrice;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-07-06
 */
public interface IVipWayPriceService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public VipWayPrice selectVipWayPriceById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param vipWayPrice 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<VipWayPrice> selectVipWayPriceList(VipWayPrice vipWayPrice);

    /**
     * 新增【请填写功能名称】
     *
     * @param vipWayPrice 【请填写功能名称】
     * @return 结果
     */
    public int insertVipWayPrice(VipWayPrice vipWayPrice);

    /**
     * 修改【请填写功能名称】
     *
     * @param vipWayPrice 【请填写功能名称】
     * @return 结果
     */
    public int updateVipWayPrice(VipWayPrice vipWayPrice);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteVipWayPriceByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteVipWayPriceById(Long id);
}

