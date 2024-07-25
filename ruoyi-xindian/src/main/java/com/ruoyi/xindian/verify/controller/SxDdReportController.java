package com.ruoyi.xindian.verify.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.xindian.verify.domain.SxDdReport;
import com.ruoyi.xindian.verify.domain.SxReport;
import com.ruoyi.xindian.verify.service.SxDdReportService;
import com.ruoyi.xindian.verify.vo.SxDdReportVO;
import com.ruoyi.xindian.verify.vo.SxReportVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/ecg/report")
public class SxDdReportController {

    @Resource
    private SxDdReportService sxDdReportService;

    @PostMapping("/addDdReport")
    public AjaxResult getReport(@Validated @RequestBody SxDdReportVO sxReportVO) throws Exception{

        SxDdReport sxDdReport = new SxDdReport();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(sxReportVO.getUploadStart());
        }catch (Exception e){
            return AjaxResult.error("请填写正确的时间格式");
        }
        BeanUtils.copyProperties( sxReportVO,sxDdReport);
        sxDdReport.setCreateTime(new Date());

        sxDdReportService.save(sxDdReport);

        return AjaxResult.success();

    }


}
