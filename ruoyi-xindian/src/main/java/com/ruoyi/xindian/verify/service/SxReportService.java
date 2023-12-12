package com.ruoyi.xindian.verify.service;

import com.ruoyi.xindian.verify.domain.SxReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【sx_report】的数据库操作Service
* @createDate 2023-12-07 09:52:47
*/
public interface SxReportService extends IService<SxReport> {




    int insertSxReport(SxReport sxReport);


    int updateSxReport(SxReport sxReport);

    List<SxReport> getReportList(SxReport report);

}
