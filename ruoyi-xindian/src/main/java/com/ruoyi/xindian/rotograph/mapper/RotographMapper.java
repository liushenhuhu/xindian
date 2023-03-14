package com.ruoyi.xindian.rotograph.mapper;

import java.util.List;
import com.ruoyi.xindian.rotograph.domain.Rotograph;

/**
 * 轮播广告Mapper接口
 * 
 * @author chenpeng
 * @date 2023-03-14
 */
public interface RotographMapper 
{
    /**
     * 查询轮播广告
     * 
     * @param id 轮播广告主键
     * @return 轮播广告
     */
    public Rotograph selectRotographById(Long id);

    /**
     * 查询轮播广告列表
     * 
     * @param rotograph 轮播广告
     * @return 轮播广告集合
     */
    public List<Rotograph> selectRotographList(Rotograph rotograph);

    /**
     * 新增轮播广告
     * 
     * @param rotograph 轮播广告
     * @return 结果
     */
    public int insertRotograph(Rotograph rotograph);

    /**
     * 修改轮播广告
     * 
     * @param rotograph 轮播广告
     * @return 结果
     */
    public int updateRotograph(Rotograph rotograph);

    /**
     * 删除轮播广告
     * 
     * @param id 轮播广告主键
     * @return 结果
     */
    public int deleteRotographById(Long id);

    /**
     * 批量删除轮播广告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRotographByIds(Long[] ids);
}
