package com.ruoyi.xindian.rotograph.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.rotograph.mapper.RotographMapper;
import com.ruoyi.xindian.rotograph.domain.Rotograph;
import com.ruoyi.xindian.rotograph.service.IRotographService;

/**
 * 轮播广告Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-03-14
 */
@Service
public class RotographServiceImpl implements IRotographService 
{
    @Autowired
    private RotographMapper rotographMapper;

    /**
     * 查询轮播广告
     * 
     * @param id 轮播广告主键
     * @return 轮播广告
     */
    @Override
    public Rotograph selectRotographById(Long id)
    {
        return rotographMapper.selectRotographById(id);
    }

    /**
     * 查询轮播广告列表
     * 
     * @param rotograph 轮播广告
     * @return 轮播广告
     */
    @Override
    public List<Rotograph> selectRotographList(Rotograph rotograph)
    {
        return rotographMapper.selectRotographList(rotograph);
    }

    /**
     * 新增轮播广告
     * 
     * @param rotograph 轮播广告
     * @return 结果
     */
    @Override
    public int insertRotograph(Rotograph rotograph)
    {
        return rotographMapper.insertRotograph(rotograph);
    }

    /**
     * 修改轮播广告
     * 
     * @param rotograph 轮播广告
     * @return 结果
     */
    @Override
    public int updateRotograph(Rotograph rotograph)
    {
        return rotographMapper.updateRotograph(rotograph);
    }

    /**
     * 批量删除轮播广告
     * 
     * @param ids 需要删除的轮播广告主键
     * @return 结果
     */
    @Override
    public int deleteRotographByIds(Long[] ids)
    {
        return rotographMapper.deleteRotographByIds(ids);
    }

    /**
     * 删除轮播广告信息
     * 
     * @param id 轮播广告主键
     * @return 结果
     */
    @Override
    public int deleteRotographById(Long id)
    {
        return rotographMapper.deleteRotographById(id);
    }
}
