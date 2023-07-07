package com.ruoyi.xindian.vipPatient.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.vipPatient.mapper.VipWayPriceMapper;
import com.ruoyi.xindian.vipPatient.domain.VipWayPrice;
import com.ruoyi.xindian.vipPatient.service.IVipWayPriceService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-07-06
 */
@Service
public class VipWayPriceServiceImpl implements IVipWayPriceService
{
    @Autowired
    private VipWayPriceMapper vipWayPriceMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public VipWayPrice selectVipWayPriceById(Long id)
    {
        return vipWayPriceMapper.selectVipWayPriceById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param vipWayPrice 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<VipWayPrice> selectVipWayPriceList(VipWayPrice vipWayPrice)
    {
        return vipWayPriceMapper.selectVipWayPriceList(vipWayPrice);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param vipWayPrice 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertVipWayPrice(VipWayPrice vipWayPrice)
    {
        return vipWayPriceMapper.insertVipWayPrice(vipWayPrice);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param vipWayPrice 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateVipWayPrice(VipWayPrice vipWayPrice)
    {
        return vipWayPriceMapper.updateVipWayPrice(vipWayPrice);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteVipWayPriceByIds(Long[] ids)
    {
        return vipWayPriceMapper.deleteVipWayPriceByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteVipWayPriceById(Long id)
    {
        return vipWayPriceMapper.deleteVipWayPriceById(id);
    }
}

