package com.ruoyi.xindian.vipPatient.service;

import com.ruoyi.xindian.vipPatient.domain.SxReportUnscramble;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【sx_report_unscramble(动态心电图解读服务次数)】的数据库操作Service
* @createDate 2023-12-23 10:14:36
*/
public interface SxReportUnscrambleService extends IService<SxReportUnscramble> {


    /**
     * 查询动态心电图解读服务次数
     * @param patientPhone 动态心电图解读服务次数ID
     * @return 动态心电图解读服务次数
     * @throws Exception
     */
    SxReportUnscramble selectSxReportUnscrambleById(String patientPhone) throws Exception;



    /**
     * 新增动态心电图解读服务次数
     *
     * @return 结果
     *
     * @throws Exception
     */
    int insertSxReportUnscramble(String patientPhone,Long num) throws Exception;


    /**
     * 修改动态心电图解读服务次数
     * @param sxReportUnscramble 动态心电图解读服务次数
     * @return 结果
     * @throws Exception
     */
    int updateSxReportUnscramble(SxReportUnscramble sxReportUnscramble) throws Exception;


    /**
     * 减少服务次数
     * @param patientPhone
     * @return
     * @throws Exception
     */
    int updateSxReportUnscrambleByNumReduce(String patientPhone) throws Exception;

}
