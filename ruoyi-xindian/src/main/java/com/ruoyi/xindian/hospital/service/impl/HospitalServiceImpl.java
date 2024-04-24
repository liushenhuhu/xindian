package com.ruoyi.xindian.hospital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;

import javax.annotation.Resource;

/**
 * 医院Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Service
public class HospitalServiceImpl implements IHospitalService
{
    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;


    /**
     * 查询医院
     *
     * @param hospitalId 医院主键
     * @return 医院
     */
    @Override
    public Hospital selectHospitalByHospitalId(Long hospitalId)
    {
        return hospitalMapper.selectHospitalByHospitalId(hospitalId);
    }

    @Override
    public Hospital selectHospitalByHospitalCode(String hospitalCode) {
        return hospitalMapper.selectHospitalByHospitalCode(hospitalCode);
    }

    /**
     * 查询医院列表
     *
     * @param hospital 医院
     * @return 医院
     */
    @Override
    public List<Hospital> selectHospitalList(Hospital hospital)
    {
        return hospitalMapper.selectHospitalList(hospital);
    }

    /**
     * 新增医院
     *
     * @param hospital 医院
     * @return 结果
     */
    @Override
    public int insertHospital(Hospital hospital)
    {
        return hospitalMapper.insertHospital(hospital);
    }

    /**
     * 修改医院
     *
     * @param hospital 医院
     * @return 结果
     */
    @Override
    public int updateHospital(Hospital hospital)
    {
        return hospitalMapper.updateHospital(hospital);
    }

    /**
     * 批量删除医院
     *
     * @param hospitalIds 需要删除的医院主键
     * @return 结果
     */
    @Override
    public int deleteHospitalByHospitalIds(Long[] hospitalIds)
    {
        return hospitalMapper.deleteHospitalByHospitalIds(hospitalIds);
    }

    /**
     * 删除医院信息
     *
     * @param hospitalId 医院主键
     * @return 结果
     */
    @Override
    public int deleteHospitalByHospitalId(Long hospitalId)
    {
        return hospitalMapper.deleteHospitalByHospitalId(hospitalId);
    }


    @Override
    public List<Hospital> selectUserId(Hospital hospital) {

        return hospitalMapper.selectHospitalList(hospital);
    }

    @Override
    public Hospital selectId(Long userId) {
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        Hospital hospital = new Hospital();
        hospital.setHospitalName(sysUser.getHospitalName());
        if (sysUser.getHospitalCode()!=null&&!"".equals(sysUser.getHospitalCode())){
            hospital.setHospitalCode(sysUser.getHospitalCode());
        }
        return hospitalMapper.selectId(hospital);
    }

    @Override
    public Hospital selectCode(String hospitalName) {
        return hospitalMapper.selectHospitalCodeHospital(hospitalName);
    }

    // 查询医院及医院下的医生
    @Override
    public AjaxResult getDocInHospital() throws Exception {
        Map<String, Object> retMap = new HashMap<>();
        Hospital hospitalQ = new Hospital();
        List<Hospital> hospitalList = hospitalMapper.selectHospitalList(hospitalQ);
        Doctor doctorQ = new Doctor();
        for (Hospital hospital : hospitalList) {
            doctorQ.setHospital(hospital.getHospitalName());
            List<Doctor> doctorsList = doctorMapper.selectDoctorList(doctorQ);
            for (Doctor doctor : doctorsList) {
                //解密
                if(!StringUtils.isEmpty(doctor.getDoctorName())){
                    doctor.setDoctorName(aesUtils.decrypt(doctor.getDoctorName()));
                }
                if(!StringUtils.isEmpty(doctor.getDoctorPhone())){
                    doctor.setDoctorPhone(aesUtils.decrypt(doctor.getDoctorPhone()));
                }
            }
            hospital.setDoctorList(doctorsList);
        }
        retMap.put("options",hospitalList);
        return AjaxResult.success(retMap);
    }


}
