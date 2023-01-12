package com.ruoyi.xindian.pmEcgData.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
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
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.pmEcgData.service.IPmEcgDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 心电数据Controller
 *
 * @author ruoyi
 * @date 2023-01-12
 */
@RestController
@RequestMapping("/pmEcgData/pmEcgData")
public class PmEcgDataController extends BaseController
{
    @Autowired
    private IPmEcgDataService pmEcgDataService;

    /**
     * 查询心电数据列表
     */
    @PreAuthorize("@ss.hasPermi('pmEcgData:pmEcgData:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmEcgData pmEcgData)
    {
        startPage();
        List<PmEcgData> list = pmEcgDataService.selectPmEcgDataList(pmEcgData);
        return getDataTable(list);
    }

    /**
     * 导出心电数据列表
     */
    @PreAuthorize("@ss.hasPermi('pmEcgData:pmEcgData:export')")
    @Log(title = "心电数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PmEcgData pmEcgData)
    {
        List<PmEcgData> list = pmEcgDataService.selectPmEcgDataList(pmEcgData);
        ExcelUtil<PmEcgData> util = new ExcelUtil<PmEcgData>(PmEcgData.class);
        util.exportExcel(response, list, "心电数据数据");
    }

    /**
     * 获取心电数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('pmEcgData:pmEcgData:query')")
    @GetMapping(value = "/{pId}")
    public AjaxResult getInfo(@PathVariable("pId") String pId)
    {
        return AjaxResult.success(pmEcgDataService.selectPmEcgDataByPId(pId));
    }

    /**
     * 新增心电数据
     */
    @PreAuthorize("@ss.hasPermi('pmEcgData:pmEcgData:add')")
    @Log(title = "心电数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmEcgData pmEcgData)
    {
        return toAjax(pmEcgDataService.insertPmEcgData(pmEcgData));
    }

    /**
     * 修改心电数据
     */
    @PreAuthorize("@ss.hasPermi('pmEcgData:pmEcgData:edit')")
    @Log(title = "心电数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmEcgData pmEcgData)
    {
        return toAjax(pmEcgDataService.updatePmEcgData(pmEcgData));
    }

    /**
     * 删除心电数据
     */
    @PreAuthorize("@ss.hasPermi('pmEcgData:pmEcgData:remove')")
    @Log(title = "心电数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pIds}")
    public AjaxResult remove(@PathVariable String[] pIds)
    {
        return toAjax(pmEcgDataService.deletePmEcgDataByPIds(pIds));
    }

    /**
     * 获取心电数据详细信息
     */
    @GetMapping(value = "get_analysis_ecg/{pId}")
    public AjaxResult get_analysis_ecg(@PathVariable("pId") String pId)
    {
        PmEcgData pmEcgData = pmEcgDataService.selectPmEcgDataByPId(pId);
        String ecgAnalysisData = pmEcgData.getEcgAnalysisData();
        JSONObject json = JSONObject.parseObject(ecgAnalysisData);
//        Map<String, Object> map = JSONObject.parseObject(ecgAnalysisData, new TypeReference<Map<String, Object>>() {});
        System.out.println(1111);
        return AjaxResult.success(json);
    }
}
