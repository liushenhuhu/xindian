package com.ruoyi.xindian.slideshow.mapper;

import com.ruoyi.xindian.slideshow.domain.AppSlideshow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【app_slideshow】的数据库操作Mapper
* @createDate 2024-08-27 18:12:31
* @Entity com.ruoyi.xindian.slideshow.domain.AppSlideshow
*/
public interface AppSlideshowMapper extends BaseMapper<AppSlideshow> {

    /**
     * 查询首页轮播图
     *
     * @param slideshowId 首页轮播图主键
     * @return 首页轮播图
     */
    public AppSlideshow selectAppSlideshowBySlideshowId(Long slideshowId);

    /**
     * 查询首页轮播图列表
     *
     * @param appSlideshow 首页轮播图
     * @return 首页轮播图集合
     */
    public List<AppSlideshow> selectAppSlideshowList(AppSlideshow appSlideshow);

    /**
     * 新增首页轮播图
     *
     * @param appSlideshow 首页轮播图
     * @return 结果
     */
    public int insertAppSlideshow(AppSlideshow appSlideshow);

    /**
     * 修改首页轮播图
     *
     * @param appSlideshow 首页轮播图
     * @return 结果
     */
    public int updateAppSlideshow(AppSlideshow appSlideshow);

    /**
     * 删除首页轮播图
     *
     * @param slideshowId 首页轮播图主键
     * @return 结果
     */
    public int deleteAppSlideshowBySlideshowId(Long slideshowId);

    /**
     * 批量删除首页轮播图
     *
     * @param slideshowIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppSlideshowBySlideshowIds(Long[] slideshowIds);
}




