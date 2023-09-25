package com.ruoyi.xindian.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.statistics.domain.DiagnoseDoc;
import com.ruoyi.xindian.statistics.service.DiagnoseDocService;
import com.ruoyi.xindian.statistics.mapper.DiagnoseDocMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 13401
* @description 针对表【diagnose_doc】的数据库操作Service实现
* @createDate 2023-09-22 10:57:05
*/
@Service
public class DiagnoseDocServiceImpl extends ServiceImpl<DiagnoseDocMapper, DiagnoseDoc>
    implements DiagnoseDocService{


    @Resource
    private DiagnoseDocMapper diagnoseDocMapper;


    @Override
    public int insertDiagnose(DiagnoseDoc diagnoseDoc) {
        Date date = new Date();
        diagnoseDoc.setCreateTime(date);
        diagnoseDoc.setUpdateTime(date);
        return diagnoseDocMapper.insert(diagnoseDoc);
    }

    @Override
    public int updateDiagnose(DiagnoseDoc diagnoseDoc) {
        diagnoseDoc.setUpdateTime(new Date());
        return diagnoseDocMapper.update(diagnoseDoc,new QueryWrapper<DiagnoseDoc>().eq("report_id",diagnoseDoc.getReportId()).eq("doctor_phone",diagnoseDoc.getDoctorPhone()).orderByDesc("diagnose_id").last("limit 1"));
    }
}




