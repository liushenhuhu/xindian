package com.ruoyi.xindian.vipPatient.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.vipPatient.domain.SxReportUnscramble;
import com.ruoyi.xindian.vipPatient.service.SxReportUnscrambleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 患者动态心电图解读次数
 */
@RestController
@RequestMapping("/report/unscramble")
public class SxReportUnscrambleController {



    @Resource
    private SxReportUnscrambleService sxReportUnscrambleService;


    /**
     * 查看患者服务次数
     * @param patientPhone
     * @return
     * @throws Exception
     */
    @GetMapping("/getUnscrambleByPhone")
    public AjaxResult getUnscrambleByPhone(String patientPhone) throws Exception {
        SxReportUnscramble sxReportUnscramble = sxReportUnscrambleService.selectSxReportUnscrambleById(patientPhone);
        if (sxReportUnscramble == null){
            return AjaxResult.success(0);
        }
        if (sxReportUnscramble.getVipNum() == null){
            return AjaxResult.success(0);
        }
        return AjaxResult.success(sxReportUnscramble.getVipNum());
    }



}
