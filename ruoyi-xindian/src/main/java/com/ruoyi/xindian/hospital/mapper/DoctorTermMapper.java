package com.ruoyi.xindian.hospital.mapper;

import com.ruoyi.xindian.hospital.domain.DoctorTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 13401
* @description 针对表【doctor_term】的数据库操作Mapper
* @createDate 2023-09-11 15:40:26
* @Entity com.ruoyi.xindian.hospital.domain.DoctorTerm
*/
public interface DoctorTermMapper extends BaseMapper<DoctorTerm> {


    /**
     * 查询【请填写功能名称】列表
     *
     * @param doctorTerm 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DoctorTerm> selectDoctorTermList(DoctorTerm doctorTerm);

}




