package com.ruoyi.xindian.hospital.service;

import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.patient_management.vo.ListDocVO;

import java.util.List;

/**
 * 医生Service接口
 * 
 * @author hanhan
 * @date 2022-12-03
 */
public interface IDoctorService 
{
    /**
     * 查询医生
     * 
     * @param doctorId 医生主键
     * @return 医生
     */
    public Doctor selectDoctorByDoctorId(Long doctorId);

    public Doctor selectDoctorByDoctorPhone(String doctorPhone);

    /**
     * 查询医生列表
     * 
     * @param doctor 医生
     * @return 医生集合
     */
    public List<Doctor> selectDoctorList(Doctor doctor);

    public List<String> selectDoctorNameList(Doctor doctor);

    /**
     * 新增医生
     * 
     * @param doctor 医生
     * @return 结果
     */
    public int insertDoctor(Doctor doctor);

    /**
     * 修改医生
     * 
     * @param doctor 医生
     * @return 结果
     */
    public int updateDoctor(Doctor doctor);

    /**
     * 批量删除医生
     * 
     * @param doctorIds 需要删除的医生主键集合
     * @return 结果
     */
    public int deleteDoctorByDoctorIds(Long[] doctorIds);

    /**
     * 删除医生信息
     * 
     * @param doctorId 医生主键
     * @return 结果
     */
    public int deleteDoctorByDoctorId(Long doctorId);

    /**
     * 查询医院所在医生
     */
    List<ListDocVO> listDoc();


    List<Doctor> selectUserDoc(Doctor doctor);

}
