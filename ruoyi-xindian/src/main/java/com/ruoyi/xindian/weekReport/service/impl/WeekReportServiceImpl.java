package com.ruoyi.xindian.weekReport.service.impl;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;
import com.ruoyi.xindian.util.DateUtil;
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

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IReportService reportService;

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
            if (weekReport.getDoctorPhone() != null)
                weekReport.setDoctorPhone(aesUtils.encrypt(weekReport.getDoctorPhone()));
        } catch (Exception ignored) {
        }
        List<WeekReport> weekReports;
        if (weekReport.getStartTime() != null) {
            LocalDate s = DateUtil.getLocalDate(weekReport.getStartTime()).minusWeeks(1);
            LocalDate e = DateUtil.getLocalDate(weekReport.getEndTime()).minusWeeks(1);
            weekReport.setStartTime(DateUtil.getDateByLocalDate(s));
            weekReport.setEndTime(DateUtil.getDateByLocalDate(e));
            weekReports = weekReportMapper.selectWeekReportTimeList(weekReport);
        } else {
            weekReports = weekReportMapper.selectWeekReportList(weekReport);
        }

        try {
            for (WeekReport report : weekReports) {
                if (report.getPatientPhone() != null)
                    report.setPatientPhone(aesUtils.decrypt(report.getPatientPhone()));
                if (report.getDoctorPhone() != null)
                    report.setDoctorPhone(aesUtils.decrypt(report.getDoctorPhone()));
                LocalDate s = DateUtil.getLocalDate(report.getWeekpdftime()).with(DayOfWeek.MONDAY);
                LocalDate e = DateUtil.getLocalDate(report.getWeekpdftime()).with(DayOfWeek.SUNDAY);
                report.setStartTime(DateUtil.getDateByLocalDate(s));
                report.setEndTime(DateUtil.getDateByLocalDate(e));
            }
        } catch (Exception ignored) {
        }
        return weekReports;
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

                WeekReport wP = weekReportMapper.selectWeekReportByWeekId(weekReport.getWeekid());

//                Patient patient = patientService.selectPatientByPatientPhone(wP.getDoctorPhone());
                Doctor doctor = doctorService.selectDoctorByDoctorPhone(wP.getDoctorPhone());

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
                float y = 585;
                for (String s : split) {
                    canvas.beginText().moveText(x, y).setFontAndSize(font, 10)
                            .showText(s).endText();
                    y -= 10;
                }
                canvas.beginText().moveText(80, 475).setFontAndSize(font, 8)
                        .showText(aesUtils.decrypt(doctor.getDoctorName())).endText();

                //添加电子签
                if (doctor.getDzVisa() != null) {
                    String wdir = "/home/chenpeng/workspace/system/xindian/uploadPath" + doctor.getDzVisa();
                    ImageData imageData = ImageDataFactory.create(wdir);
                    Image image = new Image(imageData)
                            .setFixedPosition(440, 490)
                            .scaleToFit(100, 100)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setRotationAngle(Math.toRadians(90));
                    document.add(image);
                }
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

    @Override
    public WeekReport selectWeekReportByTime(String mon) {
        return weekReportMapper.selectWeekReportByTime(mon);
    }

    @Override
    public List<WeekReport> selectWeekReportMonthList(WeekReport weekReport) {
        try {
            if (weekReport.getPatientPhone() != null)
                weekReport.setPatientPhone(aesUtils.encrypt(weekReport.getPatientPhone()));
            if (weekReport.getDoctorPhone() != null)
                weekReport.setDoctorPhone(aesUtils.encrypt(weekReport.getDoctorPhone()));
        } catch (Exception ignored) {
        }
        List<WeekReport> weekReports;
        if (weekReport.getStartTime() != null) {
            LocalDate s = DateUtil.getLocalDate(weekReport.getStartTime()).minusWeeks(1);
            LocalDate e = DateUtil.getLocalDate(weekReport.getEndTime()).minusWeeks(1);
            weekReport.setStartTime(DateUtil.getDateByLocalDate(s));
            weekReport.setEndTime(DateUtil.getDateByLocalDate(e));
            weekReports = weekReportMapper.selectWeekReportTimeList(weekReport);
        } else {
            weekReports = weekReportMapper.selectWeekReportList(weekReport);
        }

        try {
            for (WeekReport report : weekReports) {
                if (report.getPatientPhone() != null)
                    report.setPatientPhone(aesUtils.decrypt(report.getPatientPhone()));
                if (report.getDoctorPhone() != null)
                    report.setDoctorPhone(aesUtils.decrypt(report.getDoctorPhone()));
                LocalDate s = DateUtil.getLocalDate(report.getWeekpdftime()).with(DayOfWeek.MONDAY);
                LocalDate e = DateUtil.getLocalDate(report.getWeekpdftime()).with(DayOfWeek.SUNDAY);
                report.setStartTime(DateUtil.getDateByLocalDate(s));
                report.setEndTime(DateUtil.getDateByLocalDate(e));
            }

            Date dd = DateUtil.getNextMonDay(weekReport.getStartTime());
            long startTime = dd.getTime();
            int flag = 0;
            for (long t = startTime; t <= weekReport.getEndTime().getTime(); t += 1000 * 60 * 60 * 24 * 7) {
                System.out.println(new Date(t));
                flag = 0;
                for (WeekReport report : weekReports) {
                    if (report.getStartTime().getTime() == t) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    WeekReport wR = new WeekReport();
                    Date date = new Date(t);
                    LocalDate s = DateUtil.getLocalDate(date).with(DayOfWeek.MONDAY);
                    LocalDate e = DateUtil.getLocalDate(date).with(DayOfWeek.SUNDAY);
                    wR.setStartTime(DateUtil.getDateByLocalDate(s));
                    wR.setEndTime(DateUtil.getDateByLocalDate(e));
                    weekReports.add(wR);
                }
            }
        } catch (Exception ignored) {
        }
        Report report = new Report();
        report.setPPhone(weekReport.getPatientPhone());
        report.setReportType("JECGsingleWL");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (WeekReport wreport : weekReports) {
            String today = DateUtil.getLocalDate(wreport.getStartTime()).format(formatter);
            String startDay = DateUtil.getLocalDate(wreport.getEndTime()).format(formatter);
            report.setStartReportTime(today);
            report.setEndReportTime(startDay);
            List<Report> reports = reportService.selectReportList(report);
            wreport.setHData(reports != null && reports.size() != 0);
        }
        List<WeekReport> collect = weekReports.stream().sorted(Comparator.comparing(WeekReport::getStartTime)).collect(Collectors.toList());
        return collect;
    }
}
