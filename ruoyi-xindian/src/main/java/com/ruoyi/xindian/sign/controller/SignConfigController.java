package com.ruoyi.xindian.sign.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.xindian.sign.domain.SignConfig;
import com.ruoyi.xindian.sign.service.ISignConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * configController
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
@RestController
@RequestMapping("/sign/config")
public class SignConfigController extends BaseController
{
    @Autowired
    private ISignConfigService signConfigService;

    /**
     * 查询config列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SignConfig signConfig)
    {
        startPage();
        List<SignConfig> list = signConfigService.selectSignConfigList(signConfig);
        return getDataTable(list);
    }

    /**
     * 导出config列表
     */
    @Log(title = "config", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SignConfig signConfig)
    {
        List<SignConfig> list = signConfigService.selectSignConfigList(signConfig);
        ExcelUtil<SignConfig> util = new ExcelUtil<SignConfig>(SignConfig.class);
        util.exportExcel(response, list, "config数据");
    }

    /**
     * 获取config详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(signConfigService.selectSignConfigById(id));
    }

    /**
     * 新增config
     */
    @Log(title = "config", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SignConfig signConfig)
    {
        return toAjax(signConfigService.insertSignConfig(signConfig));
    }

    /**
     * 修改config
     */
    @Log(title = "config", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SignConfig signConfig)
    {
        return toAjax(signConfigService.updateSignConfig(signConfig));
    }

    /**
     * 删除config
     */
    @Log(title = "config", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(signConfigService.deleteSignConfigByIds(ids));
    }
}
