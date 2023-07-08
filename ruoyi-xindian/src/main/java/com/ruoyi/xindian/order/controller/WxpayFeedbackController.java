package com.ruoyi.xindian.order.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.order.domain.WxpayFeedback;
import com.ruoyi.xindian.order.service.WxpayFeedbackService;
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
 * 处理订单反馈Controller
 * 
 * @author ruoyi
 * @date 2023-07-08
 */
@RestController
@RequestMapping("/feedback")
public class WxpayFeedbackController extends BaseController
{
    @Autowired
    private WxpayFeedbackService wxpayFeedbackService;

    /**
     * 查询反馈列表
     */
    @PreAuthorize("@ss.hasPermi('payOrder:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxpayFeedback wxpayFeedback)
    {
        startPage();
        List<WxpayFeedback> list = wxpayFeedbackService.selectWxpayFeedbackList(wxpayFeedback);
        return getDataTable(list);
    }


    /**
     * 导出反馈列表
     */
    @PreAuthorize("@ss.hasPermi('payOrder:feedback:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxpayFeedback wxpayFeedback)
    {
        List<WxpayFeedback> list = wxpayFeedbackService.selectWxpayFeedbackList(wxpayFeedback);
        ExcelUtil<WxpayFeedback> util = new ExcelUtil<WxpayFeedback>(WxpayFeedback.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('payOrder:feedback:query')")
    @GetMapping(value = "/{feedbackiId}")
    public AjaxResult getInfo(@PathVariable("feedbackiId") Long feedbackiId)
    {
        return AjaxResult.success(wxpayFeedbackService.selectWxpayFeedbackByFeedbackiId(feedbackiId));
    }

    /**
     * 新增反馈
     */
    @PreAuthorize("@ss.hasPermi('payOrder:feedback:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/feedbackAdd")

    public AjaxResult add(@RequestBody WxpayFeedback wxpayFeedback)
    {
        wxpayFeedback.setStatus("未处理");
        return toAjax(wxpayFeedbackService.insertWxpayFeedback(wxpayFeedback));
    }

    /**
     * 修改反馈状态
     */
    @PreAuthorize("@ss.hasPermi('payOrder:feedback:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxpayFeedback wxpayFeedback)
    {
        return toAjax(wxpayFeedbackService.updateWxpayFeedback(wxpayFeedback));
    }

    /**
     * 删除反馈信息
     */
    @PreAuthorize("@ss.hasPermi('payOrder:feedback:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{feedbackiIds}")
    public AjaxResult remove(@PathVariable Long[] feedbackiIds)
    {
        return toAjax(wxpayFeedbackService.deleteWxpayFeedbackByFeedbackiIds(feedbackiIds));
    }
}
