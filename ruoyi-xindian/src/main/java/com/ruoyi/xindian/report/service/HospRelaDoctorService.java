package com.ruoyi.xindian.report.service;

import com.ruoyi.xindian.report.domain.HospRelaDoctor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【hosp_rela_doctor】的数据库操作Service
* @createDate 2024-09-10 17:35:38
*/
public interface HospRelaDoctorService extends IService<HospRelaDoctor> {

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
     * 批量删除报告自动提交
     *
     * @param ids 需要删除的报告自动提交主键集合
     * @return 结果
     */
    public int deleteHospRelaDoctorByIds(Long[] ids);

    /**
     * 删除报告自动提交信息
     *
     * @param id 报告自动提交主键
     * @return 结果
     */
    public int deleteHospRelaDoctorById(Long id);
}
