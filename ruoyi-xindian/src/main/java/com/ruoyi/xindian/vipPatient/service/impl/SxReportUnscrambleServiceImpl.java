package com.ruoyi.xindian.vipPatient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.vipPatient.domain.SxReportUnscramble;
import com.ruoyi.xindian.vipPatient.service.SxReportUnscrambleService;
import com.ruoyi.xindian.vipPatient.mapper.SxReportUnscrambleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 13401
* @description 针对表【sx_report_unscramble(动态心电图解读服务次数)】的数据库操作Service实现
* @createDate 2023-12-23 10:14:36
*/
@Service
public class SxReportUnscrambleServiceImpl extends ServiceImpl<SxReportUnscrambleMapper, SxReportUnscramble>
    implements SxReportUnscrambleService{


    @Resource
    private AesUtils aesUtils;

    @Resource
    private SxReportUnscrambleMapper sxReportUnscrambleMapper;

    @Override
    public SxReportUnscramble selectSxReportUnscrambleById(String patientPhone) throws Exception {
        return sxReportUnscrambleMapper.selectOne(new QueryWrapper<SxReportUnscramble>().eq("patient_phone", aesUtils.encrypt(patientPhone)));
    }

    @Override
    public int insertSxReportUnscramble(String patientPhone,Long num) throws Exception {

        SxReportUnscramble sxReportUnscramble = selectSxReportUnscrambleById(patientPhone);
        if (sxReportUnscramble == null){
            SxReportUnscramble sxReportUnscramble1 = new SxReportUnscramble();
            sxReportUnscramble1.setPatientPhone(patientPhone);
            sxReportUnscramble1.setVipNum(num);
            return sxReportUnscrambleMapper.insert(sxReportUnscramble1);
        }
        if (sxReportUnscramble.getVipNum() == null) {
            sxReportUnscramble.setVipNum(num);
        } else {
            sxReportUnscramble.setVipNum(sxReportUnscramble.getVipNum() + num);
        }
        return updateSxReportUnscramble(sxReportUnscramble);
    }

    @Override
    public int updateSxReportUnscramble(SxReportUnscramble sxReportUnscramble) throws Exception {
        return sxReportUnscrambleMapper.update(sxReportUnscramble,new QueryWrapper<SxReportUnscramble>().eq("sx_report_id", sxReportUnscramble.getSxReportId()));
    }

    @Override
    public int updateSxReportUnscrambleByNumReduce(String patientPhone) throws Exception {
        SxReportUnscramble sxReportUnscramble = selectSxReportUnscrambleById(patientPhone);
        if (sxReportUnscramble==null){
           throw new ServiceException("服务次数不够");
        }
        if (sxReportUnscramble.getVipNum()==null||sxReportUnscramble.getVipNum()==0){
            throw new ServiceException("服务次数不够");
        }
        if (sxReportUnscramble.getVipNum()>0){
            sxReportUnscramble.setVipNum(sxReportUnscramble.getVipNum()-1);
            return updateSxReportUnscramble(sxReportUnscramble);
        }
        throw new ServiceException("服务次数不够");
    }
}




