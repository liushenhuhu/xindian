package com.ruoyi.xindian.verify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.verify.domain.SxReport;
import com.ruoyi.xindian.verify.service.SxReportService;
import com.ruoyi.xindian.verify.mapper.SxReportMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 13401
* @description 针对表【sx_report】的数据库操作Service实现
* @createDate 2023-12-07 09:52:47
*/
@Service
public class SxReportServiceImpl extends ServiceImpl<SxReportMapper, SxReport>
    implements SxReportService{

    @Resource
    private SxReportMapper sxReportMapper;

    @Override
    public int insertSxReport(SxReport sxReport) {
        return sxReportMapper.insert(sxReport);
    }
}




