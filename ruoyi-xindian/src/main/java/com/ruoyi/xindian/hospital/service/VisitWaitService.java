package com.ruoyi.xindian.hospital.service;

import com.ruoyi.xindian.hospital.domain.VisitWait;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【visit_wait(门诊候诊)】的数据库操作Service
* @createDate 2023-10-27 15:26:31
*/
public interface VisitWaitService extends IService<VisitWait> {


    /**
     * 查询门诊候诊
     *
     * @param waitId 门诊候诊主键
     * @return 门诊候诊
     */
    public VisitWait selectVisitWaitByWaitId(Long waitId);

    /**
     * 查询门诊候诊列表
     *
     * @param visitWait 门诊候诊
     * @return 门诊候诊集合
     */
    public List<VisitWait> selectVisitWaitList(VisitWait visitWait);

    /**
     * 新增门诊候诊
     *
     * @param visitWait 门诊候诊
     * @return 结果
     */
    public int insertVisitWait(VisitWait visitWait);

    /**
     * 修改门诊候诊
     *
     * @param visitWait 门诊候诊
     * @return 结果
     */
    public int updateVisitWait(VisitWait visitWait);

    /**
     * 批量删除门诊候诊
     *
     * @param waitIds 需要删除的门诊候诊主键集合
     * @return 结果
     */
    public int deleteVisitWaitByWaitIds(Long[] waitIds);

    /**
     * 删除门诊候诊信息
     *
     * @param waitId 门诊候诊主键
     * @return 结果
     */
    public int deleteVisitWaitByWaitId(Long waitId);


    int insertVisit(String appointmentId);



    public int getAccomplishStatus(VisitWait visitWait);
}
