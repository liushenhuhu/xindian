package com.ruoyi.xindian.util;


import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;

import java.util.List;

public class ThreadUtil implements Runnable{

    private IReportService reportService;
    private String pId;
    private List<Doctor> doctors;
    public void setParameter(String pId, List<Doctor> doctors,IReportService reportService) {
        this.pId = pId;
        this.doctors = doctors;
        this.reportService=reportService;
    }

    @Override
    public void run() {
        Report report = null;
        while (true) {
            try {
                Thread.sleep(60000*30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            report = reportService.selectReportByPId(this.pId);
            if(report.getDiagnosisStatus()!=2){
                return;
            }
            int rand = StrUtil.randomInt(doctors.size());
            String dPhone=doctors.get(rand).getDoctorPhone();
            report.setdPhone(dPhone);
            report.setDiagnosisDoctor(doctors.get(rand).getDoctorName());
            WxUtil.send(dPhone);
            reportService.updateReport(report);
        }
    }
}
