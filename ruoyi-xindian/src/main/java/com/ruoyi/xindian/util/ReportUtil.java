package com.ruoyi.xindian.util;

import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;

import java.time.LocalTime;
import java.util.List;

public class ReportUtil implements Runnable {
    private IReportService reportService;
    private String pId;
    private List<Doctor> doctors;
    private final LocalTime start = LocalTime.of(8, 0); // 8:00 AM
    private final LocalTime end = LocalTime.of(18, 0); // 6:00 PM
    public void setParameter(String pId, List<Doctor> doctors,IReportService reportService) {
        this.pId = pId;
        this.doctors = doctors;
        this.reportService=reportService;
    }
    @Override
    public void run() {
        Report report;
        while (true) {
            try {
                Thread.sleep(60000*10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LocalTime now = LocalTime.now();
            if (now.isAfter(start) && now.isBefore(end)) {
                report = reportService.selectReportByPId(this.pId);
                if(report.getdPhone()!=null&&!"".equals(report.getdPhone())){
                    return;
                }
                int rand = StrUtil.randomInt(doctors.size());
                String dPhone=doctors.get(rand).getDoctorPhone();
                report.setdPhone(dPhone);
                report.setDiagnosisDoctor(doctors.get(rand).getDoctorName());
                WxUtil.send(dPhone);
                reportService.updateReport(report);
                //定时器, 30分钟无医生诊断, 换医生诊断.
                ThreadUtil threadUtil = new ThreadUtil();
                threadUtil.setParameter(report.getpId(), doctors, reportService);
//                    threadUtil.run();
                Thread thread = new Thread(threadUtil);
                thread.start();
            }
        }
    }


}
