package com.ruoyi.xindian.slideshow.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.slideshow.domain.AppSlideshow;
import com.ruoyi.xindian.slideshow.service.AppSlideshowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 首页轮播图Controller
 * 
 * @author ruoyi
 * @date 2024-08-27
 */
@RestController
@RequestMapping("/slideshow/slideshow")
public class AppSlideshowController extends BaseController
{
    @Autowired
    private AppSlideshowService appSlideshowService;

    /**
     * 查询首页轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('slideshow:slideshow:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppSlideshow appSlideshow)
    {
        startPage();
        List<AppSlideshow> list = appSlideshowService.selectAppSlideshowList(appSlideshow);
        return getDataTable(list);
    }

    /**
     * 首页查询轮播图
     * @return
     */
    @GetMapping("/getAppSlideshow")
    public AjaxResult getAppSlideshow() {
        AppSlideshow appSlideshow = new AppSlideshow();
        appSlideshow.setStatus("0");
        return AjaxResult.success(appSlideshowService.selectAppSlideshowList(appSlideshow));
    }

    /**
     * 导出首页轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('slideshow:slideshow:export')")
    @Log(title = "首页轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppSlideshow appSlideshow)
    {
        List<AppSlideshow> list = appSlideshowService.selectAppSlideshowList(appSlideshow);
        ExcelUtil<AppSlideshow> util = new ExcelUtil<AppSlideshow>(AppSlideshow.class);
        util.exportExcel(response, list, "首页轮播图数据");
    }

    /**
     * 获取首页轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('slideshow:slideshow:query')")
    @GetMapping(value = "/{slideshowId}")
    public AjaxResult getInfo(@PathVariable("slideshowId") Long slideshowId)
    {
        return AjaxResult.success(appSlideshowService.selectAppSlideshowBySlideshowId(slideshowId));
    }

    /**
     * 新增首页轮播图
     */
    @PreAuthorize("@ss.hasPermi('slideshow:slideshow:add')")
    @Log(title = "首页轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppSlideshow appSlideshow)
    {
        return toAjax(appSlideshowService.insertAppSlideshow(appSlideshow));
    }

    /**
     * 修改首页轮播图
     */
    @PreAuthorize("@ss.hasPermi('slideshow:slideshow:edit')")
    @Log(title = "首页轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppSlideshow appSlideshow)
    {
        return toAjax(appSlideshowService.updateAppSlideshow(appSlideshow));
    }

    /**
     * 删除首页轮播图
     */
    @PreAuthorize("@ss.hasPermi('slideshow:slideshow:remove')")
    @Log(title = "首页轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{slideshowIds}")
    public AjaxResult remove(@PathVariable Long[] slideshowIds)
    {
        return toAjax(appSlideshowService.deleteAppSlideshowBySlideshowIds(slideshowIds));
    }
}
