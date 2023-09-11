package com.ruoyi.xindian.patientCount.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.patientCount.domain.PatientCount;
import com.ruoyi.xindian.patientCount.service.PatientCountService;
import com.ruoyi.xindian.patientCount.mapper.PatientCountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class PatientCountServiceImpl extends ServiceImpl<PatientCountMapper, PatientCount>
    implements PatientCountService {


    @Resource
    private PatientCountMapper patientCountMapper;

    @Override
    public List<PatientCount> selectWFLogAndVip(PatientCount patientCount) {
        return patientCountMapper.selectAllByList(patientCount);
    }
}




