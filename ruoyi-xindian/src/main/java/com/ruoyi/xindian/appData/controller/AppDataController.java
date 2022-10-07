package com.ruoyi.xindian.appData.controller;

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
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * app相关数据Controller
 *
 * @author hanhan
 * @date 2022-10-06
 */
@RestController
@RequestMapping("/appData/appData")
public class AppDataController extends BaseController
{
    @Autowired
    private IAppDataService appDataService;

    /**
     * 查询app相关数据列表
     */
    @PreAuthorize("@ss.hasPermi('appData:appData:list')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestBody AppData appData)
    {
        startPage();
        List<AppData> list = appDataService.selectAppDataList(appData);
        return getDataTable(list);
    }

    /**
     * 导出app相关数据列表
     */
    @PreAuthorize("@ss.hasPermi('appData:appData:export')")
    @Log(title = "app相关数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppData appData)
    {
        List<AppData> list = appDataService.selectAppDataList(appData);
        ExcelUtil<AppData> util = new ExcelUtil<AppData>(AppData.class);
        util.exportExcel(response, list, "app相关数据数据");
    }

    /**
     * 获取app相关数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('appData:appData:query')")
    @GetMapping(value = "/{appDataId}")
    public AjaxResult getInfo(@PathVariable("appDataId") Long appDataId)
    {
        return AjaxResult.success(appDataService.selectAppDataByAppDataId(appDataId));
    }

    /**
     * 新增app相关数据
     */
    @PreAuthorize("@ss.hasPermi('appData:appData:add')")
    @Log(title = "app相关数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppData appData)
    {
        return toAjax(appDataService.insertAppData(appData));
    }

    /**
     * 修改app相关数据
     */
    @PreAuthorize("@ss.hasPermi('appData:appData:edit')")
    @Log(title = "app相关数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppData appData)
    {
        return toAjax(appDataService.updateAppData(appData));
    }

    /**
     * 删除app相关数据
     */
    @PreAuthorize("@ss.hasPermi('appData:appData:remove')")
    @Log(title = "app相关数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{appDataIds}")
    public AjaxResult remove(@PathVariable Long[] appDataIds)
    {
        return toAjax(appDataService.deleteAppDataByAppDataIds(appDataIds));
    }
}
