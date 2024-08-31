package com.ruoyi.xindian.slideshow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.slideshow.domain.AppSlideshow;
import com.ruoyi.xindian.slideshow.service.AppSlideshowService;
import com.ruoyi.xindian.slideshow.mapper.AppSlideshowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author w
* @description 针对表【app_slideshow】的数据库操作Service实现
* @createDate 2024-08-27 18:12:31
*/
@Service
public class AppSlideshowServiceImpl extends ServiceImpl<AppSlideshowMapper, AppSlideshow>
    implements AppSlideshowService{

    @Resource
    private AppSlideshowMapper appSlideshowMapper;

    /**
     * 查询首页轮播图
     *
     * @param slideshowId 首页轮播图主键
     * @return 首页轮播图
     */
    @Override
    public AppSlideshow selectAppSlideshowBySlideshowId(Long slideshowId)
    {
        return appSlideshowMapper.selectAppSlideshowBySlideshowId(slideshowId);
    }

    /**
     * 查询首页轮播图列表
     *
     * @param appSlideshow 首页轮播图
     * @return 首页轮播图
     */
    @Override
    public List<AppSlideshow> selectAppSlideshowList(AppSlideshow appSlideshow)
    {
        return appSlideshowMapper.selectAppSlideshowList(appSlideshow);
    }

    /**
     * 新增首页轮播图
     *
     * @param appSlideshow 首页轮播图
     * @return 结果
     */
    @Override
    public int insertAppSlideshow(AppSlideshow appSlideshow)
    {
        appSlideshow.setCreateTime(DateUtils.getNowDate());
        return appSlideshowMapper.insertAppSlideshow(appSlideshow);
    }

    /**
     * 修改首页轮播图
     *
     * @param appSlideshow 首页轮播图
     * @return 结果
     */
    @Override
    public int updateAppSlideshow(AppSlideshow appSlideshow)
    {
        appSlideshow.setUpdateTime(DateUtils.getNowDate());
        return appSlideshowMapper.updateAppSlideshow(appSlideshow);
    }

    /**
     * 批量删除首页轮播图
     *
     * @param slideshowIds 需要删除的首页轮播图主键
     * @return 结果
     */
    @Override
    public int deleteAppSlideshowBySlideshowIds(Long[] slideshowIds)
    {
        return appSlideshowMapper.deleteAppSlideshowBySlideshowIds(slideshowIds);
    }

    /**
     * 删除首页轮播图信息
     *
     * @param slideshowId 首页轮播图主键
     * @return 结果
     */
    @Override
    public int deleteAppSlideshowBySlideshowId(Long slideshowId)
    {
        return appSlideshowMapper.deleteAppSlideshowBySlideshowId(slideshowId);
    }
}




