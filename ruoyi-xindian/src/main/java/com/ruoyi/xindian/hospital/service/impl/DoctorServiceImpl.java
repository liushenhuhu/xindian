package com.ruoyi.xindian.hospital.service.impl;

import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.mapper.DoctorMapper;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生Service业务层处理
 * 
 * @author hanhan
 * @date 2022-12-03
 */
@Service
public class DoctorServiceImpl implements IDoctorService 
{
    @Autowired
    private DoctorMapper doctorMapper;

    /**
     * 查询医生
     * 
     * @param doctorId 医生主键
     * @return 医生
     */
    @Override
    public Doctor selectDoctorByDoctorId(Long doctorId)
    {
        return doctorMapper.selectDoctorByDoctorId(doctorId);
    }

    /**
     * 查询医生列表
     * 
     * @param doctor 医生
     * @return 医生
     */
    @Override
    public List<Doctor> selectDoctorList(Doctor doctor)
    {
        return doctorMapper.selectDoctorList(doctor);
    }

    @Override
    public List<String> selectDoctorNameList(Doctor doctor) {
        List<Doctor> doctors = doctorMapper.selectDoctorList(doctor);
        List<String> doctorNameList = new ArrayList<>();
        for (Doctor doctor1 : doctors) {
            doctorNameList.add(doctor1.getDoctorName());
        }
        return doctorNameList;
    }


    /**
     * 新增医生
     * 
     * @param doctor 医生
     * @return 结果
     */
    @Override
    public int insertDoctor(Doctor doctor)
    {
        return doctorMapper.insertDoctor(doctor);
    }

    /**
     * 修改医生
     * 
     * @param doctor 医生
     * @return 结果
     */
    @Override
    public int updateDoctor(Doctor doctor)
    {
        return doctorMapper.updateDoctor(doctor);
    }

    /**
     * 批量删除医生
     * 
     * @param doctorIds 需要删除的医生主键
     * @return 结果
     */
    @Override
    public int deleteDoctorByDoctorIds(Long[] doctorIds)
    {
        return doctorMapper.deleteDoctorByDoctorIds(doctorIds);
    }

    /**
     * 删除医生信息
     * 
     * @param doctorId 医生主键
     * @return 结果
     */
    @Override
    public int deleteDoctorByDoctorId(Long doctorId)
    {
        return doctorMapper.deleteDoctorByDoctorId(doctorId);
    }
}
