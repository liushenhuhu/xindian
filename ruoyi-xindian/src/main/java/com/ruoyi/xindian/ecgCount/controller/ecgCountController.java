package com.ruoyi.xindian.ecgCount.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.ecgCount.domain.EcgCount;
import com.ruoyi.xindian.ecgCount.service.EcgCountService;
import com.ruoyi.xindian.ecgCount.vo.TypeListVo;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库统计
 */
@RestController
@RequestMapping("/ecgCount/ecgCount")
public class ecgCountController extends BaseController {


    @Resource
    private EcgCountService ecgCountService;

    /**
     * 数据库导入
     * @param path
     * @throws Exception
     */
    @GetMapping("/importEcg")
    public void importEcg(String path) throws Exception {
        ecgCountService.importEcgCount(path);
    }

    /**
     * 查询数据库统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:count:list')")
    @GetMapping("/list")
    public TableDataInfo list(EcgCount ecgCount)
    {
        startPage();
        List<EcgCount> list = ecgCountService.selectEcgCountList(ecgCount);
        return getDataTable(list);
    }

    /**
     * 导出数据库统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:count:export')")
    @Log(title = "数据库统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EcgCount ecgCount)
    {
        List<EcgCount> list = ecgCountService.selectEcgCountList(ecgCount);
        ExcelUtil<EcgCount> util = new ExcelUtil<EcgCount>(EcgCount.class);
        util.exportExcel(response, list, "数据库统计数据");
    }

    /**
     * 获取数据库统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:count:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ecgCountService.selectEcgCountById(id));
    }

    /**
     * 新增数据库统计
     */
    @PreAuthorize("@ss.hasPermi('system:count:add')")
    @Log(title = "数据库统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EcgCount ecgCount)
    {
        return toAjax(ecgCountService.insertEcgCount(ecgCount));
    }

    /**
     * 修改数据库统计
     */
    @PreAuthorize("@ss.hasPermi('system:count:edit')")
    @Log(title = "数据库统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EcgCount ecgCount)
    {
        return toAjax(ecgCountService.updateEcgCount(ecgCount));
    }

    /**
     * 删除数据库统计
     */
    @PreAuthorize("@ss.hasPermi('system:count:remove')")
    @Log(title = "数据库统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ecgCountService.deleteEcgCountByIds(ids));
    }


    /**
     * 查找数据库统计中的所有类型
     * @return
     */
    @GetMapping("/getLogType")
    public AjaxResult getLogType(){
        List<ListValueAndLabelVO> logType = ecgCountService.getLogType();
        return AjaxResult.success(logType);
    }


    /**
     * 数据库统计表进行男女人数分布
     */
    @GetMapping ("/ageList")
    public Map<String,Object> ageList(AgeStatistics str){
        /* 查询性别男 */
        List<AgeStatistics> manList = ecgCountService.ageListByMan(str);
        /* 查询性别女 */
        List<AgeStatistics> womanList = ecgCountService.ageListByWoman(str);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("men",manList);
        map.put("women",womanList);
        return map;
    }


    @GetMapping("/getTypeList")
    public AjaxResult getTypeList() throws InstantiationException, IllegalAccessException {
        List<TypeListVo> typeList = ecgCountService.getTypeList();

        return AjaxResult.success(typeList);
    }


}
