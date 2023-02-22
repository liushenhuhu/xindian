package com.ruoyi.xindian.procotol.controller;

import java.util.HashMap;
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
import com.ruoyi.xindian.procotol.domain.Protocol;
import com.ruoyi.xindian.procotol.service.IProtocolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 协议Controller
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/protocol/protocol")
public class ProtocolController extends BaseController
{
    @Autowired
    private IProtocolService protocolService;

    /**
     * 查询协议列表
     */
    @PreAuthorize("@ss.hasPermi('protocol:protocol:list')")
    @GetMapping("/list")
    public TableDataInfo list(Protocol protocol)
    {
        startPage();
        List<Protocol> list = protocolService.selectProtocolList(protocol);
        return getDataTable(list);
    }

    /**
     * 导出协议列表
     */
    @PreAuthorize("@ss.hasPermi('protocol:protocol:export')")
    @Log(title = "协议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Protocol protocol)
    {
        List<Protocol> list = protocolService.selectProtocolList(protocol);
        ExcelUtil<Protocol> util = new ExcelUtil<Protocol>(Protocol.class);
        util.exportExcel(response, list, "协议数据");
    }

    /**
     * 获取协议详细信息
     */
    @PreAuthorize("@ss.hasPermi('protocol:protocol:query')")
    @GetMapping(value = "/{protocolId}")
    public AjaxResult getInfo(@PathVariable("protocolId") Long protocolId)
    {
        return AjaxResult.success(protocolService.selectProtocolByProtocolId(protocolId));
    }

    /**
     * 新增协议
     */
    @PreAuthorize("@ss.hasPermi('protocol:protocol:add')")
    @Log(title = "协议", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Protocol protocol)
    {
        return toAjax(protocolService.insertProtocol(protocol));
    }

    /**
     * 修改协议
     */
    @PreAuthorize("@ss.hasPermi('protocol:protocol:edit')")
    @Log(title = "协议", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Protocol protocol)
    {
        return toAjax(protocolService.updateProtocol(protocol));
    }

    /**
     * 删除协议
     */
    @PreAuthorize("@ss.hasPermi('protocol:protocol:remove')")
    @Log(title = "协议", businessType = BusinessType.DELETE)
	@DeleteMapping("/{protocolIds}")
    public AjaxResult remove(@PathVariable Long[] protocolIds)
    {
        return toAjax(protocolService.deleteProtocolByProtocolIds(protocolIds));
    }

    /**
     * 查看版本号
     * @param protocolId
     * @return
     */
    @GetMapping("/getProtocolVesion/{protocolId}")
    public AjaxResult getProtocolVesion(@PathVariable("protocolId") Long protocolId)
    {
        Protocol protocol = protocolService.selectProtocolByProtocolId(protocolId);
//        protocol.setProtocolContent("");
        HashMap<String, String> res = new HashMap<>();
        res.put("versionNumber", String.valueOf(protocol.getProtocolVersion()));
        res.put("versionName",protocol.getProtocolName());
        return AjaxResult.success(res);
    }

}
