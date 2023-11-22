package com.ruoyi.xindian.hospital.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.DoctorMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private HospitalMapper hospitalMapper;

    @Autowired
    private AesUtils aesUtils;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

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


    @Override
    public Doctor selectDoctorByDoctorPhone(String doctorPhone){
        return doctorMapper.selectDoctorByDoctorPhone(doctorPhone);
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

    @Override
    public List<ListValueAndLabelVO> listDoc() throws Exception {

        List<ListValueAndLabelVO> listDocVOS = doctorMapper.selectDoc();

        for (ListValueAndLabelVO c : listDocVOS){
            Doctor doctor = new Doctor();
            doctor.getHospitalNameList().add(c.getLabel());
            List<Doctor> doctors = doctorMapper.selectDoctorList(doctor);
            for (Doctor d : doctors){
                DocVO listDocVO = new DocVO();
                listDocVO.setLabel(aesUtils.decrypt(d.getDoctorName()));
                listDocVO.setValue(aesUtils.decrypt(d.getDoctorPhone()));
                c.getChildren().add(listDocVO);
            }
        }
        return listDocVOS;
    }

    @Override
    public List<Doctor> selectUserDoc(Doctor doctor) {


        return doctorMapper.selectDoctorList(doctor);
    }

    @Override
    public List<Doctor> selectIsDoc() {
        return doctorMapper.selectIsDoctors();
    }

    @Override
    public List<Doctor> selectVisitDoc(String hospitalName) {
        return doctorMapper.selectVisitDoc(hospitalName);
    }

    @Override
    public List<Doctor> selectXDDoc(String hospitalName) {

        return doctorMapper.selectXDDoc(hospitalName).stream().peek(d->{
            if (StringUtils.isNotEmpty(d.getDoctorName())){
                try {
                    d.setDoctorName(aesUtils.decrypt(d.getDoctorName()));
                } catch (Exception e) {
                    throw new ServiceException("数据错误");
                }
            }
            if (StringUtils.isNotEmpty(d.getDoctorPhone())){
                try {
                    d.setDoctorPhone(aesUtils.decrypt(d.getDoctorPhone()));
                } catch (Exception e) {
                    throw new ServiceException("数据错误");
                }
            }

        }).collect(Collectors.toList());
    }
}
