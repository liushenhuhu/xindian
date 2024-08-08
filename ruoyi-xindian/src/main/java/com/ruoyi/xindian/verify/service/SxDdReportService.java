package com.ruoyi.xindian.verify.service;

import com.ruoyi.xindian.verify.domain.SxDdReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【sx_dd_report】的数据库操作Service
* @createDate 2024-07-25 12:05:07
*/
public interface SxDdReportService extends IService<SxDdReport> {

    /**
     * 查询善行单导心电贴
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 善行单导心电贴
     */
    public SxDdReport selectSxDdReportBySxReportId(Long sxReportId);

    /**
     * 查询善行单导心电贴列表
     *
     * @param sxDdReport 善行单导心电贴
     * @return 善行单导心电贴集合
     */
    public List<SxDdReport> selectSxDdReportList(SxDdReport sxDdReport);

    /**
     * 新增善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    public int insertSxDdReport(SxDdReport sxDdReport);

    /**
     * 修改善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    public int updateSxDdReport(SxDdReport sxDdReport);

    /**
     * 批量删除善行单导心电贴
     *
     * @param sxReportIds 需要删除的善行单导心电贴主键集合
     * @return 结果
     */
    public int deleteSxDdReportBySxReportIds(Long[] sxReportIds);

    /**
     * 删除善行单导心电贴信息
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 结果
     */
    public int deleteSxDdReportBySxReportId(Long sxReportId);

//    String getFilePath(SxDdReport sxDdReport);
}
