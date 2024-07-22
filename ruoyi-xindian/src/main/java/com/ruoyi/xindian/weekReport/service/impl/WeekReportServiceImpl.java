package com.ruoyi.xindian.weekReport.service.impl;

import java.util.List;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.weekReport.mapper.WeekReportMapper;
import com.ruoyi.xindian.weekReport.domain.WeekReport;
import com.ruoyi.xindian.weekReport.service.IWeekReportService;

/**
 * weekReportService业务层处理
 *
 * @author chenpeng
 * @date 2024-07-19
 */
@Service
public class WeekReportServiceImpl implements IWeekReportService {
    @Autowired
    private WeekReportMapper weekReportMapper;

    @Autowired
    private AesUtils aesUtils;

    @Autowired
    private IPatientService patientService;

    /**
     * 查询weekReport
     *
     * @param id weekReport主键
     * @return weekReport
     */
    @Override
    public WeekReport selectWeekReportById(Long id) {
        return weekReportMapper.selectWeekReportById(id);
    }

    /**
     * 查询weekReport列表
     *
     * @param weekReport weekReport
     * @return weekReport
     */
    @Override
    public List<WeekReport> selectWeekReportList(WeekReport weekReport) {
        try {
            if (weekReport.getPatientPhone() != null)
                weekReport.setPatientPhone(aesUtils.encrypt(weekReport.getPatientPhone()));
        } catch (Exception ignored) {
        }
        return weekReportMapper.selectWeekReportList(weekReport);
    }

    /**
     * 新增weekReport
     *
     * @param weekReport weekReport
     * @return 结果
     */
    @Override
    public int insertWeekReport(WeekReport weekReport) {
        return weekReportMapper.insertWeekReport(weekReport);
    }

    /**
     * 修改weekReport
     *
     * @param weekReport weekReport
     * @return 结果
     */
    @Override
    public int updateWeekReport(WeekReport weekReport) {
        try {
            if (weekReport.getDoctorPhone() != null)
                weekReport.setDoctorPhone(aesUtils.encrypt(weekReport.getDoctorPhone()));
            //修改pdf
            if (weekReport.getDiagnosisConclusion() != null) {

                WeekReport wP = weekReportMapper.selectWeekReportById(weekReport.getId());
                Patient patient = patientService.selectPatientByPatientPhone(weekReport.getDoctorPhone());
                String patientPhone = aesUtils.decrypt(wP.getPatientPhone());
                String src = "/home/chenpeng/workspace/system/xindian/data/weekpdf/" + patientPhone + "/" + wP.getWeekid() + ".pdf";
                String dest = "/home/chenpeng/workspace/system/xindian/data/weekpdf/" + patientPhone + "/" + wP.getWeekid() + "_md.pdf";

                PdfReader reader = new PdfReader(src);
                PdfWriter writer = new PdfWriter(dest);
                // 使用PdfDocument和Document来添加内容
                PdfDocument pdfDoc = new PdfDocument(reader, writer);
                Document document = new Document(pdfDoc);
//                PdfFont font = PdfFontFactory.createFont("./ruoyi-xindian/src/main/java/com/ruoyi/xindian/pdf/utils/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
                PdfFont font = PdfFontFactory.createFont("/home/chenpeng/workspace/system/xindian/ttf/STXIHEI.TTF", PdfEncodings.IDENTITY_H, true);
                PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
//                String con = "本报告由互联网医疗与健康服务河南省协同创新中心人工智能平台自动生成, 未经临床验证, 仅供参考, 请根据医生诊\n断进一步确认.";
                String[] split = weekReport.getDiagnosisConclusion().split("\n");
                float x = 50;
                float y = 605;
                for (String s : split) {
                    canvas.beginText().moveText(x, y).setFontAndSize(font, 10)
                            .showText(s).endText();
                    y -= 10;
                }
                canvas.beginText().moveText(80, 493).setFontAndSize(font, 8)
                        .showText(aesUtils.decrypt(patient.getPatientName())).endText();
                // 关闭文档
                document.close();
                pdfDoc.close();
                //复制pdf
                pdfDoc = new PdfDocument(new PdfReader(dest), new PdfWriter(src));
                pdfDoc.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return weekReportMapper.updateWeekReport(weekReport);
    }

    /**
     * 批量删除weekReport
     *
     * @param ids 需要删除的weekReport主键
     * @return 结果
     */
    @Override
    public int deleteWeekReportByIds(Long[] ids) {
        return weekReportMapper.deleteWeekReportByIds(ids);
    }

    /**
     * 删除weekReport信息
     *
     * @param id weekReport主键
     * @return 结果
     */
    @Override
    public int deleteWeekReportById(Long id) {
        return weekReportMapper.deleteWeekReportById(id);
    }

    @Override
    public List<WeekReport> groupByPatientPhone(String getdPhone) {
        return weekReportMapper.groupByPatientPhone(getdPhone);
    }
}
