package com.ruoyi.xindian.verify.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.verify.domain.SxReport;
import com.ruoyi.xindian.verify.service.SxReportService;
import com.ruoyi.xindian.verify.vo.SxReportVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传报告信息
 */
@RestController
@RequestMapping("/ecg/report")
public class SXReportController {


    @Resource
    private SxReportService sxReportService;


    @Resource
    private AesUtils aesUtils;
    /**
     * 上传报告信息
     * @param sxReportVO
     * @return
     * @throws Exception
     */
    @PostMapping("/addReport")
    public AjaxResult getReport(@RequestBody SxReportVO sxReportVO) throws Exception{

        if (StringUtils.isAnyEmpty(sxReportVO.getSn(), sxReportVO.getOrderId(), sxReportVO.getPatientPhone(), sxReportVO.getUserId(),sxReportVO.getStartTime())){
            return AjaxResult.error("请填写完整参数");
        }

        SxReport sxReport = new SxReport();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(sxReportVO.getStartTime());
        }catch (Exception e){
            return AjaxResult.error("请填写正确的时间格式");
        }
        BeanUtils.copyProperties( sxReportVO,sxReport);
        sxReport.setCreateTime(new Date());
        if (StringUtils.isNotEmpty(sxReport.getPatientPhone())){
            sxReport.setPatientPhone(aesUtils.encrypt(sxReport.getPatientPhone()));
        }
        sxReport.setUploadStart(sxReportVO.getStartTime());
        if (sxReportService.insertSxReport(sxReport) > 0){
            return AjaxResult.success("success");
        }

        return AjaxResult.error();
    }




}
