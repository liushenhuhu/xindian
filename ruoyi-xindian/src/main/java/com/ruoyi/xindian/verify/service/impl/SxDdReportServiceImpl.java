package com.ruoyi.xindian.verify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.verify.controller.SxDdReportController;
import com.ruoyi.xindian.verify.domain.SxDdReport;
import com.ruoyi.xindian.verify.service.SxDdReportService;
import com.ruoyi.xindian.verify.mapper.SxDdReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author w
* @description 针对表【sx_dd_report】的数据库操作Service实现
* @createDate 2024-07-25 12:05:07
*/
@Service
public class SxDdReportServiceImpl extends ServiceImpl<SxDdReportMapper, SxDdReport>
    implements SxDdReportService{

    @Autowired
    private SxDdReportMapper sxDdReportMapper;

    @Resource
    private SxDdReportController sxDdReportController;

    /**
     * 查询善行单导心电贴
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 善行单导心电贴
     */
    @Override
    public SxDdReport selectSxDdReportBySxReportId(Long sxReportId)
    {
        return sxDdReportMapper.selectSxDdReportBySxReportId(sxReportId);
    }

    /**
     * 查询善行单导心电贴列表
     *
     * @param sxDdReport 善行单导心电贴
     * @return 善行单导心电贴
     */
    @Override
    public List<SxDdReport> selectSxDdReportList(SxDdReport sxDdReport)
    {
        return sxDdReportMapper.selectSxDdReportList(sxDdReport);
    }

    /**
     * 新增善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    @Override
    public int insertSxDdReport(SxDdReport sxDdReport)
    {
        sxDdReport.setCreateTime(DateUtils.getNowDate());
        return sxDdReportMapper.insertSxDdReport(sxDdReport);
    }

    /**
     * 修改善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    @Override
    public int updateSxDdReport(SxDdReport sxDdReport)
    {
        return sxDdReportMapper.updateSxDdReport(sxDdReport);
    }

    /**
     * 批量删除善行单导心电贴
     *
     * @param sxReportIds 需要删除的善行单导心电贴主键
     * @return 结果
     */
    @Override
    public int deleteSxDdReportBySxReportIds(Long[] sxReportIds)
    {
        return sxDdReportMapper.deleteSxDdReportBySxReportIds(sxReportIds);
    }

    /**
     * 删除善行单导心电贴信息
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 结果
     */
    @Override
    public int deleteSxDdReportBySxReportId(Long sxReportId)
    {
        return sxDdReportMapper.deleteSxDdReportBySxReportId(sxReportId);
    }

//    @Override
//    public String getFilePath(SxDdReport sxDdReport) {
//        SxDdReport sxDdReport1 = selectSxDdReportBySxReportId(sxDdReport.getSxReportId());
//        return sxDdReportController.downloadPFD(sxDdReport1);
//    }
}




