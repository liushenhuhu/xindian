package com.ruoyi.xindian.weekReport.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.util.WxUtil;
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
import com.ruoyi.xindian.weekReport.domain.WeekReport;
import com.ruoyi.xindian.weekReport.service.IWeekReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * weekReportController
 *
 * @author chenpeng
 * @date 2024-07-19
 */
@RestController
@RequestMapping("/weekReport/report")
public class WeekReportController extends BaseController {
    @Autowired
    private IWeekReportService weekReportService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询weekReport列表
     */
//    @PreAuthorize("@ss.hasPermi('weekReport:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(WeekReport weekReport) {
        startPage();
        List<WeekReport> list = weekReportService.selectWeekReportList(weekReport);
        return getDataTable(list);
    }

    /**
     * 导出weekReport列表
     */
//    @PreAuthorize("@ss.hasPermi('weekReport:report:export')")
    @Log(title = "weekReport", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WeekReport weekReport) {
        List<WeekReport> list = weekReportService.selectWeekReportList(weekReport);
        ExcelUtil<WeekReport> util = new ExcelUtil<WeekReport>(WeekReport.class);
        util.exportExcel(response, list, "weekReport数据");
    }

    /**
     * 获取weekReport详细信息
     */
//    @PreAuthorize("@ss.hasPermi('weekReport:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(weekReportService.selectWeekReportById(id));
    }

    /**
     * 新增weekReport
     */
//    @PreAuthorize("@ss.hasPermi('weekReport:report:add')")
    @Log(title = "weekReport", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WeekReport weekReport) {
        return toAjax(weekReportService.insertWeekReport(weekReport));
    }

    /**
     * 修改weekReport
     */
//    @PreAuthorize("@ss.hasPermi('weekReport:report:edit')")
    @Log(title = "weekReport", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WeekReport weekReport) {
        if (weekReport.getDoctorPhone() != null) {
            WeekReport wR = weekReportService.selectWeekReportById(weekReport.getId());
            SysUser sysUser = sysUserService.selectUserByPhone(wR.getPatientPhone());
            if (sysUser.getWeeklyNewspaperNum() == null || sysUser.getWeeklyNewspaperNum() <= 0)
                return AjaxResult.error("无服务次数");
            sysUser.setWeeklyNewspaperNum(sysUser.getWeeklyNewspaperNum() - 1);
            sysUserService.updateNum(sysUser);
            //发短信给医生
            WxUtil.send(weekReport.getDoctorPhone());
        }
        return toAjax(weekReportService.updateWeekReport(weekReport));
    }

    /**
     * 删除weekReport
     */
//    @PreAuthorize("@ss.hasPermi('weekReport:report:remove')")
    @Log(title = "weekReport", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(weekReportService.deleteWeekReportByIds(ids));
    }

    @GetMapping("/test")
    public AjaxResult test() throws IOException {
        String src = "E:\\test.pdf";
        String dest = "E:\\test_new.pdf";
        PdfReader reader = new PdfReader(src);
        PdfWriter writer = new PdfWriter(dest);
        // 使用PdfDocument和Document来添加内容
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        Document document = new Document(pdfDoc);

        PdfFont font = PdfFontFactory.createFont("./ruoyi-xindian/src/main/java/com/ruoyi/xindian/pdf/utils/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
//        PdfFont font = PdfFontFactory.createFont("/home/chenpeng/workspace/system/xindian/ttf/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);


        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        String con = "本报告由互联网医疗与健康服务河南省协同创新中心人工智能平台自动生成, 未经临床验证, 仅供参考, 请根据医生诊\n断进一步确认.";
        String[] split = con.split("\n");
        float x = 50;
        float y = 605;
        for (String s : split) {
            canvas.beginText().moveText(x, y).setFontAndSize(font, 10)
                    .showText(s).endText();
            y -= 10;
        }
        canvas.beginText().moveText(80, 493).setFontAndSize(font, 8)
                .showText("陈鹏").endText();

        // 关闭文档
        document.close();
        pdfDoc.close();

        //复制pdf
        pdfDoc = new PdfDocument(new PdfReader(dest), new PdfWriter(src));
        pdfDoc.close();

        return AjaxResult.success();
    }
}
