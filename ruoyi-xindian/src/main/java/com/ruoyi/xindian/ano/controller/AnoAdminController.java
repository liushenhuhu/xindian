package com.ruoyi.xindian.ano.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.ano.domain.AnoAdmin;
import com.ruoyi.xindian.ano.service.AnoAdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ano/admin")
public class AnoAdminController extends BaseController {


    @Resource
    private AnoAdminService anoAdminService;


    /**
     * 查询审核标注分配列表
     */
    @PreAuthorize("@ss.hasPermi('ano:admin:list')")
    @GetMapping("/list")
    public TableDataInfo list(AnoAdmin anoAdmin)
    {
        startPage();
        List<AnoAdmin> list = anoAdminService.selectAnoAdminList(anoAdmin);
        return getDataTable(list);
    }


    @GetMapping("/getAnoAdmin")
    public TableDataInfo getAnoAdmin(AnoAdmin anoAdmin)
    {
        startPage();
        List<AnoAdmin> list = anoAdminService.selectAnoAdminListAll(anoAdmin);
        return getDataTable(list);
    }


    /**
     * 导出审核标注分配列表
     */
    @PreAuthorize("@ss.hasPermi('ano:admin:export')")
    @Log(title = "审核标注分配", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AnoAdmin anoAdmin)
    {
        List<AnoAdmin> list = anoAdminService.selectAnoAdminList(anoAdmin);
        ExcelUtil<AnoAdmin> util = new ExcelUtil<AnoAdmin>(AnoAdmin.class);
        util.exportExcel(response, list, "审核标注分配数据");
    }

    /**
     * 获取审核标注分配详细信息
     */
    @PreAuthorize("@ss.hasPermi('ano:admin:query')")
    @GetMapping(value = "/{anoAdminId}")
    public AjaxResult getInfo(@PathVariable("anoAdminId") Long anoAdminId)
    {
        return AjaxResult.success(anoAdminService.selectAnoAdminByAnoAdminId(anoAdminId));
    }

    /**
     * 新增审核标注分配
     */
    @PreAuthorize("@ss.hasPermi('ano:admin:add')")
    @Log(title = "审核标注分配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Map<String,Object> map)
    {
        Object o = map.get("pId");
        List<String> pId = new ArrayList<>();
        if(o instanceof ArrayList<?>){
            pId = (List<String>) map.get("pId");
        }else {
            pId.add((String)map.get("pId"));
        }
        Integer userId = (Integer) map.get("userId");
        for(String id : pId){
            AnoAdmin anoAdmin = new AnoAdmin();
            anoAdmin.setUserId(userId);
            anoAdmin.setpId(id);
            anoAdminService.insertAnoAdmin(anoAdmin);
        }
        return toAjax(1);
    }

    /**
     * 修改审核标注分配
     */
    @PreAuthorize("@ss.hasPermi('ano:admin:edit')")
    @Log(title = "审核标注分配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AnoAdmin anoAdmin)
    {
        return toAjax(anoAdminService.updateAnoAdmin(anoAdmin));
    }

    /**
     * 删除审核标注分配
     */
    @PreAuthorize("@ss.hasPermi('ano:admin:remove')")
    @Log(title = "审核标注分配", businessType = BusinessType.DELETE)
    @DeleteMapping("/{anoAdminIds}")
    public AjaxResult remove(@PathVariable Long[] anoAdminIds)
    {
        return toAjax(anoAdminService.deleteAnoAdminByAnoAdminIds(anoAdminIds));
    }




}
