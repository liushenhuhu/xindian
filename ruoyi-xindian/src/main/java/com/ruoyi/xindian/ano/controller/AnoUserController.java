package com.ruoyi.xindian.ano.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.xindian.alert_log.domain.AssignedAno;
import com.ruoyi.xindian.log_user.service.IAlertLogUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xindian.ano.domain.AnoUser;
import com.ruoyi.xindian.ano.service.IAnoUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * anoController
 * 
 * @author hanhan
 * @date 2022-12-30
 */
@RestController
@RequestMapping("/ano/ano")
public class AnoUserController extends BaseController
{
    @Autowired
    private IAnoUserService anoUserService;
    @Autowired
    private IAlertLogUserService alertLogUserService;
    /**
     * 查询ano列表
     */
    @PreAuthorize("@ss.hasPermi('ano:ano:list')")
    @GetMapping("/list")
    public TableDataInfo list(AnoUser anoUser)
    {
        startPage();
        List<AnoUser> list = anoUserService.selectAnoUserList(anoUser);
        return getDataTable(list);
    }

    /**
     * 导出ano列表
     */
    @PreAuthorize("@ss.hasPermi('ano:ano:export')")
    @Log(title = "ano", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AnoUser anoUser)
    {
        List<AnoUser> list = anoUserService.selectAnoUserList(anoUser);
        ExcelUtil<AnoUser> util = new ExcelUtil<AnoUser>(AnoUser.class);
        util.exportExcel(response, list, "ano数据");
    }

    /**
     * 获取ano详细信息
     */
    @PreAuthorize("@ss.hasPermi('ano:ano:query')")
    @GetMapping(value = "/{anoUserId}")
    public AjaxResult getInfo(@PathVariable("anoUserId") Long anoUserId)
    {
        return AjaxResult.success(anoUserService.selectAnoUserByAnoUserId(anoUserId));
    }

    /**
     * 新增ano
     */
    @PreAuthorize("@ss.hasPermi('ano:ano:add')")
    @Log(title = "ano", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AnoUser anoUser)
    {
        return toAjax(anoUserService.insertAnoUser(anoUser));
    }

    /**
     * 修改ano
     */
    @PreAuthorize("@ss.hasPermi('ano:ano:edit')")
    @Log(title = "ano", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AnoUser anoUser)
    {
        return toAjax(anoUserService.updateAnoUser(anoUser));
    }

    /**
     * 删除ano
     */
    @PreAuthorize("@ss.hasPermi('ano:ano:remove')")
    @Log(title = "ano", businessType = BusinessType.DELETE)
	@DeleteMapping("/{anoUserIds}")
    public AjaxResult remove(@PathVariable Long[] anoUserIds)
    {
        return toAjax(anoUserService.deleteAnoUserByAnoUserIds(anoUserIds));
    }

    @Log(title = "ano", businessType = BusinessType.DELETE)
    @DeleteMapping("/del")
    public AjaxResult remove2(@RequestBody AnoUser anoUser)
    {
        alertLogUserService.deleteAlertLogUserByUserIdAndPid(anoUser);
        return toAjax(anoUserService.deleteAnoUserByAnoUser(anoUser));
    }
    @PostMapping("/assignedAno")
    public AjaxResult assignedAno(@RequestBody AssignedAno assignedAno) {
        String pId = assignedAno.getpId();
        String userId = assignedAno.getUserId();
        return toAjax(anoUserService.insertAno(userId, pId));
    }

    @PostMapping("/reAssignedAno")
    public int reAssignedAno(@RequestBody AssignedAno assignedAno) {
        String pId = assignedAno.getpId();
        String userId = assignedAno.getUserId();
        return anoUserService.insertAno(userId, pId);
    }

    /**
     * 查询标注部门下的用户
     * @param anoUser
     * @return
     */
    @GetMapping("/list2")
    public TableDataInfo list2(SysUser anoUser)
    {
        startPage();
        List<AnoUser> list = anoUserService.selectAnoUserList2(anoUser);
        return getDataTable(list);
    }

    /**
     * 查询所有未分配的患者id
     * @param anoUser
     * @return
     */
    @GetMapping("/getNotAssign")
    public TableDataInfo getNotAssign(AnoUser anoUser)
    {
        startPage();
        List<AnoUser> list = anoUserService.selectNotAssign(anoUser);
        return getDataTable(list);
    }


    @PostMapping("/assignedAnoList")
    public AjaxResult assignedAnoList(@RequestBody Map<String,Object> map) {
        Object o = map.get("pId");
        List<String> pId = new ArrayList<>();
        if(o instanceof ArrayList<?>){
            pId = (List<String>) map.get("pId");
        }else {
            pId.add((String)map.get("pId"));
        }
        Integer userId = (Integer) map.get("userId");
        for (int i = 0; i < pId.size(); i++) {
            AnoUser anoUser = new AnoUser();
            anoUser.setUserId(userId.longValue());
            anoUser.setpId(pId.get(i));
            anoUserService.insertAnoUser(anoUser);
            anoUserService.insertAno(userId+"", pId.get(i));
        }

        return toAjax(1);
    }



}
