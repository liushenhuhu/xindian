package com.ruoyi.xindian.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.hospital.domain.DoctorTerm;
import com.ruoyi.xindian.hospital.service.DoctorTermService;
import com.ruoyi.xindian.hospital.mapper.DoctorTermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
* @author 13401
* @description 针对表【doctor_term】的数据库操作Service实现
* @createDate 2023-09-11 15:40:26
*/
@Service
public class DoctorTermServiceImpl extends ServiceImpl<DoctorTermMapper, DoctorTerm>
    implements DoctorTermService{

    @Resource
    private DoctorTermMapper doctorTermMapper;


    /**
     * 查询医生术语
     *
     * @param  termId 医生术语主键
     * @return 医生术语
     */
    @Override
    public DoctorTerm selectDoctorTermByTermId(Long  termId)
    {
        return doctorTermMapper.selectById(termId);
    }

    /**
     * 查询医生术语列表
     *
     * @param doctorTerm 医生术语
     * @return 医生术语
     */
    @Override
    public List<DoctorTerm> selectDoctorTermList(DoctorTerm doctorTerm)
    {
        return doctorTermMapper.selectDoctorTermList(doctorTerm);
    }

    /**
     * 新增医生术语
     *
     * @param doctorTerm 医生术语
     * @return 结果
     */
    @Override
    public int insertDoctorTerm(DoctorTerm doctorTerm)
    {
        doctorTerm.setCreateTime(DateUtils.getNowDate());
        return doctorTermMapper.insert(doctorTerm);
    }

    /**
     * 修改医生术语
     *
     * @param doctorTerm 医生术语
     * @return 结果
     */
    @Override
    public int updateDoctorTerm(DoctorTerm doctorTerm)
    {
        DoctorTerm doctorTerm1 = doctorTermMapper.selectOne(new QueryWrapper<DoctorTerm>().eq("doctor_phone", doctorTerm.getDoctorPhone()));
        if (doctorTerm1==null){
            doctorTerm.setCreateTime(DateUtils.getNowDate());
            return doctorTermMapper.insert(doctorTerm);
        }
        doctorTerm.setTermId(doctorTerm1.getTermId());
        return doctorTermMapper.updateById(doctorTerm);
    }

    /**
     * 批量删除医生术语
     *
     * @param  termIds 需要删除的医生术语主键
     * @return 结果
     */
    @Override
    public int deleteDoctorTermByTermIds(Long[]  termIds)
    {
        return doctorTermMapper.deleteBatchIds(Arrays.asList(termIds));
    }

    /**
     * 删除医生术语信息
     *
     * @return 结果
     */
    @Override
    public int deleteDoctorTermByTermId(Long  termId)
    {
        return doctorTermMapper.deleteById(termId);
    }
}




