package com.ruoyi.xindian.hospital.service;

import com.ruoyi.xindian.hospital.domain.DoctorTerm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【doctor_term】的数据库操作Service
* @createDate 2023-09-11 15:40:26
*/
public interface DoctorTermService extends IService<DoctorTerm> {

    /**
     * 查询医生术语
     *
     * @param  termId 医生术语主键
     * @return 医生术语
     */
    public DoctorTerm selectDoctorTermByTermId(Long  termId);

    /**
     * 查询医生术语列表
     *
     * @param doctorTerm 医生术语
     * @return 医生术语集合
     */
    public List<DoctorTerm> selectDoctorTermList(DoctorTerm doctorTerm);

    /**
     * 新增医生术语
     *
     * @param doctorTerm 医生术语
     * @return 结果
     */
    public int insertDoctorTerm(DoctorTerm doctorTerm);

    /**
     * 修改医生术语
     *
     * @param doctorTerm 医生术语
     * @return 结果
     */
    public int updateDoctorTerm(DoctorTerm doctorTerm);

    /**
     * 批量删除医生术语
     *
     * @param  termIds 需要删除的医生术语主键集合
     * @return 结果
     */
    public int deleteDoctorTermByTermIds(Long[]  termIds);

    /**
     * 删除医生术语信息
     *
     * @param  termId 医生术语主键
     * @return 结果
     */
    public int deleteDoctorTermByTermId(Long  termId);

}
