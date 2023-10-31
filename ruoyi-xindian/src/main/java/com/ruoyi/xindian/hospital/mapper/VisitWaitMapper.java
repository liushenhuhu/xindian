package com.ruoyi.xindian.hospital.mapper;

import com.ruoyi.xindian.hospital.domain.VisitWait;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xindian.patient_management.vo.DocVO;

import java.util.List;

/**
* @author 13401
* @description 针对表【visit_wait(门诊候诊)】的数据库操作Mapper
* @createDate 2023-10-27 15:26:31
* @Entity com.ruoyi.xindian.hospital.domain.VisitWait
*/
public interface VisitWaitMapper extends BaseMapper<VisitWait> {


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
     * 删除门诊候诊
     *
     * @param waitId 门诊候诊主键
     * @return 结果
     */
    public int deleteVisitWaitByWaitId(Long waitId);

    /**
     * 批量删除门诊候诊
     *
     * @param waitIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisitWaitByWaitIds(Long[] waitIds);

}




