package com.ruoyi.xindian.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.report.domain.HospRelaDoctor;
import com.ruoyi.xindian.report.service.HospRelaDoctorService;
import com.ruoyi.xindian.report.mapper.HospRelaDoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author w
* @description 针对表【hosp_rela_doctor】的数据库操作Service实现
* @createDate 2024-09-10 17:35:38
*/
@Service
public class HospRelaDoctorServiceImpl extends ServiceImpl<HospRelaDoctorMapper, HospRelaDoctor>
    implements HospRelaDoctorService{

    @Autowired
    private HospRelaDoctorMapper hospRelaDoctorMapper;

    /**
     * 查询报告自动提交
     *
     * @param id 报告自动提交主键
     * @return 报告自动提交
     */
    @Override
    public HospRelaDoctor selectHospRelaDoctorById(Long id)
    {
        return hospRelaDoctorMapper.selectHospRelaDoctorById(id);
    }

    /**
     * 查询报告自动提交列表
     *
     * @param hospRelaDoctor 报告自动提交
     * @return 报告自动提交
     */
    @Override
    public List<HospRelaDoctor> selectHospRelaDoctorList(HospRelaDoctor hospRelaDoctor)
    {
        return hospRelaDoctorMapper.selectHospRelaDoctorList(hospRelaDoctor);
    }

    /**
     * 新增报告自动提交
     *
     * @param hospRelaDoctor 报告自动提交
     * @return 结果
     */
    @Override
    public int insertHospRelaDoctor(HospRelaDoctor hospRelaDoctor)
    {
        return hospRelaDoctorMapper.insertHospRelaDoctor(hospRelaDoctor);
    }

    /**
     * 修改报告自动提交
     *
     * @param hospRelaDoctor 报告自动提交
     * @return 结果
     */
    @Override
    public int updateHospRelaDoctor(HospRelaDoctor hospRelaDoctor)
    {
        return hospRelaDoctorMapper.updateHospRelaDoctor(hospRelaDoctor);
    }

    /**
     * 批量删除报告自动提交
     *
     * @param ids 需要删除的报告自动提交主键
     * @return 结果
     */
    @Override
    public int deleteHospRelaDoctorByIds(Long[] ids)
    {
        return hospRelaDoctorMapper.deleteHospRelaDoctorByIds(ids);
    }

    /**
     * 删除报告自动提交信息
     *
     * @param id 报告自动提交主键
     * @return 结果
     */
    @Override
    public int deleteHospRelaDoctorById(Long id)
    {
        return hospRelaDoctorMapper.deleteHospRelaDoctorById(id);
    }
}




