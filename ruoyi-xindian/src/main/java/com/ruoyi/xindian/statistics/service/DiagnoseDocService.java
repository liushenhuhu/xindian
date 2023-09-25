package com.ruoyi.xindian.statistics.service;

import com.ruoyi.xindian.statistics.domain.DiagnoseDoc;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【diagnose_doc】的数据库操作Service
* @createDate 2023-09-22 10:57:05
*/
public interface DiagnoseDocService extends IService<DiagnoseDoc> {


    /**
     * 新增一条医生诊断记录
     * @param diagnoseDoc
     * @return
     */
    int insertDiagnose(DiagnoseDoc diagnoseDoc);


    /**
     * 修改医生诊断的的状态
     * @param diagnoseDoc
     * @return
     */
    int updateDiagnose(DiagnoseDoc diagnoseDoc);


}
