package com.ruoyi.xindian.rotograph.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
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
import com.ruoyi.xindian.rotograph.domain.Rotograph;
import com.ruoyi.xindian.rotograph.service.IRotographService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 轮播广告Controller
 * 
 * @author chenpeng
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/rotograph/rotograph")
public class RotographController extends BaseController
{
    @Autowired
    private IRotographService rotographService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询轮播广告列表
     */
    @PreAuthorize("@ss.hasPermi('rotograph:rotograph:list')")
    @GetMapping("/list")
    public TableDataInfo list(Rotograph rotograph)
    {
        rotograph.setDeleteflag(0);
        startPage();
        List<Rotograph> list = rotographService.selectRotographList(rotograph);
        return getDataTable(list);
    }

    /**
     * 导出轮播广告列表
     */
    @PreAuthorize("@ss.hasPermi('rotograph:rotograph:export')")
    @Log(title = "轮播广告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Rotograph rotograph)
    {
        List<Rotograph> list = rotographService.selectRotographList(rotograph);
        ExcelUtil<Rotograph> util = new ExcelUtil<Rotograph>(Rotograph.class);
        util.exportExcel(response, list, "轮播广告数据");
    }

    /**
     * 获取轮播广告详细信息
     */
    @PreAuthorize("@ss.hasPermi('rotograph:rotograph:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(rotographService.selectRotographById(id));
    }

    /**
     * 新增轮播广告
     */
    @PreAuthorize("@ss.hasPermi('rotograph:rotograph:add')")
    @Log(title = "轮播广告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Rotograph rotograph)
    {
        rotograph.setDeleteflag(0);
        return toAjax(rotographService.insertRotograph(rotograph));
    }

    /**
     * 修改轮播广告
     */
    @PreAuthorize("@ss.hasPermi('rotograph:rotograph:edit')")
    @Log(title = "轮播广告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Rotograph rotograph)
    {
        return toAjax(rotographService.updateRotograph(rotograph));
    }

    /**
     * 删除轮播广告
     */
    @PreAuthorize("@ss.hasPermi('rotograph:rotograph:remove')")
    @Log(title = "轮播广告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(rotographService.deleteRotographByIds(ids));
    }


    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(Rotograph rotograph) throws Exception
    {
        try
        {
            MultipartFile file = rotograph.getFile();
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
