package com.ruoyi.xindian.report.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.model.LoginUser;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
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
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报告Controller
 * 
 * @author hanhan
 * @date 2022-10-05
 */
@RestController
@RequestMapping("/report/report")
public class ReportController extends BaseController
{
    @Autowired
    private IReportService reportService;

    /**
     * 查询报告列表
     */
    @PreAuthorize("@ss.hasPermi('report:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(Report report)
    {
        startPage();
        List<Report> list = reportService.selectReportList(report);
        return getDataTable(list);
    }

    /**
     * 导出报告列表
     */
    @PreAuthorize("@ss.hasPermi('report:report:export')")
    @Log(title = "报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Report report)
    {
        List<Report> list = reportService.selectReportList(report);
        ExcelUtil<Report> util = new ExcelUtil<Report>(Report.class);
        util.exportExcel(response, list, "报告数据");
    }

    /**
     * 获取报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/{reportId}")
    public AjaxResult getInfo(@PathVariable("reportId") Long reportId)
    {
        return AjaxResult.success(reportService.selectReportByReportId(reportId));
    }

    /**
     * 获取报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:report:query')")
    @GetMapping(value = "/getInfoByPid/{pId}")
    public AjaxResult getInfoByPid(@PathVariable("pId") String pId)
    {
        return AjaxResult.success(reportService.selectReportByPId(pId));
    }

    /**
     * 新增报告
     */
    @PreAuthorize("@ss.hasPermi('report:report:add')")
    @Log(title = "报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Report report)
    {
        return toAjax(reportService.insertReport(report));
    }

    /**
     * 修改报告
     */
    @PreAuthorize("@ss.hasPermi('report:report:edit')")
    @Log(title = "报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Report report)
    {
        String s = report.getpId();
        Report report1 = reportService.selectReportByPId(s);
        report1.setDiagnosisDoctor(report.getDiagnosisDoctor());
        report1.setdPhone(report.getdPhone());
        //医生结论
        report1.setHandlingSuggestion(report.getHandlingSuggestion());
        report1.setHealthAdvice(report.getHealthAdvice());
        if(report.getDiagnosisConclusion()!=null)
            report1.setDiagnosisConclusion(report.getDiagnosisConclusion());
        if(report.getDiagnosisStatus()!=null)
            report1.setDiagnosisStatus(report.getDiagnosisStatus());
        return toAjax(reportService.updateReport(report1));
    }

    /**
     * 删除报告
     */
    @PreAuthorize("@ss.hasPermi('report:report:remove')")
    @Log(title = "报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reportIds}")
    public AjaxResult remove(@PathVariable Long[] reportIds)
    {
        return toAjax(reportService.deleteReportByReportIds(reportIds));
    }

    /**
     * 常用术语
     */
    @GetMapping("/getCommonTerms")
    public AjaxResult getCommonTerms()
    {
        ArrayList<String> terms = new ArrayList<>();
        terms.add("窦性心律正常心电图");
        terms.add("窦房结内游走性节律");
        terms.add("房性逸搏");
        terms.add("房性早搏");
        terms.add("室性逸搏");
        terms.add("交界性逸搏");
        terms.add("室性早搏");
        terms.add("交界性早搏");
        terms.add("房性心动过速");
        terms.add("室性心动过速");
        terms.add("室上性心动过速");
        terms.add("交界性心动过速");
        terms.add("心房颤动");
        terms.add("心房扑动");
        terms.add("完全性左束支传导阻滞");
        terms.add("完全性右束支传导阻滞");
        terms.add("II度I型房室传导阻滞");
        terms.add("II度II型房室传导阻滞");
        terms.add("III度房室传导阻滞");
        terms.add("I度房室传导阻滞");
        terms.add("窦性心动过缓");
        terms.add("窦性心动过速");
        return AjaxResult.success(terms);
    }
    /**
     * 获取时间信息
     */
    @GetMapping("/get_week_info")
    public AjaxResult getWeekInfo(Report rep) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        int weekIdx = calendar.get(Calendar.DAY_OF_WEEK) - 1;//1 星期一
//        if(weekIdx<0) weekIdx=0;
//        int flag=1;
//        int index=0;
//        ArrayList<String> weekDay = new ArrayList<>();
        HashMap<String, Integer> normal = new HashMap<>();
        HashMap<String, Integer> abnormal = new HashMap<>();
//        HashMap<String, Object> params = new HashMap<>();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String maxTime="0000";
//        String minTime="9999";
//        for (int i = 0; i < 7; i++) {
//            Date date = DateUtils.addDays(new Date(), index);
//            String formatDate = format.format(date);
//            if(maxTime.compareTo(formatDate)<0) maxTime=formatDate;
//            if(minTime.compareTo(formatDate)>0) minTime=formatDate;
//            normal.put(formatDate,0);
//            abnormal.put(formatDate,0);
//            weekIdx+=flag;
//            index+=flag;
//            if(weekIdx==8){
//                weekIdx=0;
//                flag=-1;
//                index=-1;
//            }
//        }

        Report report = new Report();
//        params.put("beginReportTime",minTime);
//        params.put("endReportTime",maxTime);
//        report.setParams(params);
        report.setPPhone(rep.getPPhone());
//        report.set
        List<Report> reports = reportService.selectReportList(report);
        String key="";
        HashMap<String, Object> result = new HashMap<>();

        for (Report re : reports) {
            key= new SimpleDateFormat("yyyy-MM-dd").format(re.getReportTime());

            if(re.getIntelligentDiagnosis()!=null && re.getIntelligentDiagnosis().contains("正常")){
                if(abnormal.containsKey(key)){
                    normal.put(key,normal.get(key)+1);
                } else{
                    normal.put(key,1);
                }
            }
            else {
                if(abnormal.containsKey(key)){
                    abnormal.put(key,abnormal.get(key)+1);
                } else{
                    abnormal.put(key,1);
                }
            }
        }
        result.put("normal",normal);
        result.put("abnormal",abnormal);
        return AjaxResult.success(result);
    }
}
