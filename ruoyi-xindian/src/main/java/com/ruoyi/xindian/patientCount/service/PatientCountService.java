package com.ruoyi.xindian.patientCount.service;

import com.ruoyi.xindian.patientCount.domain.PatientCount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface PatientCountService extends IService<PatientCount> {



    List<PatientCount> selectWFLogAndVip(PatientCount patientCount);


}
