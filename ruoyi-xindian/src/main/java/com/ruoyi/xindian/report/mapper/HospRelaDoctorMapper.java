package com.ruoyi.xindian.report.mapper;

import com.ruoyi.xindian.report.domain.HospRelaDoctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【hosp_rela_doctor】的数据库操作Mapper
* @createDate 2024-09-10 17:35:38
* @Entity com.ruoyi.xindian.report.domain.HospRelaDoctor
*/
public interface HospRelaDoctorMapper extends BaseMapper<HospRelaDoctor> {

    /**
     * 查询报告自动提交
     *
     * @param id 报告自动提交主键
     * @return 报告自动提交
     */
    public HospRelaDoctor selectHospRelaDoctorById(Long id);

    /**
     * 查询报告自动提交列表
     *
     * @param hospRelaDoctor 报告自动提交
     * @return 报告自动提交集合
     */
    public List<HospRelaDoctor> selectHospRelaDoctorList(HospRelaDoctor hospRelaDoctor);

    /**
     * 新增报告自动提交
     *
     * @param hospRelaDoctor 报告自动提交
     * @return 结果
     */
    public int insertHospRelaDoctor(HospRelaDoctor hospRelaDoctor);

    /**
     * 修改报告自动提交
     *
     * @param hospRelaDoctor 报告自动提交
     * @return 结果
     */
    public int updateHospRelaDoctor(HospRelaDoctor hospRelaDoctor);

    /**
     * 删除报告自动提交
     *
     * @param id 报告自动提交主键
     * @return 结果
     */
    public int deleteHospRelaDoctorById(Long id);

    /**
     * 批量删除报告自动提交
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospRelaDoctorByIds(Long[] ids);
}




