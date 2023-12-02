package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient.domain.PatientBlood;
import com.ruoyi.xindian.patient.service.PatientBloodService;
import com.ruoyi.xindian.patient.mapper.PatientBloodMapper;
import com.ruoyi.xindian.wx_pay.util.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 13401
* @description 针对表【patient_blood】的数据库操作Service实现
* @createDate 2023-11-16 17:09:20
*/
@Service
public class PatientBloodServiceImpl extends ServiceImpl<PatientBloodMapper, PatientBlood>
    implements PatientBloodService{



    @Resource
    private AesUtils aesUtils;


    @Autowired
    private PatientBloodMapper patientBloodMapper;

    /**
     * 查询【患者血压】
     *
     * @param bloodId 【患者血压】主键
     * @return 【患者血压】
     */
    @Override
    public PatientBlood selectPatientBloodByBloodId(String bloodId) throws Exception {
        PatientBlood patientBlood = patientBloodMapper.selectPatientBloodByBloodId(bloodId);
        if (patientBlood!=null){
            patientBlood.setPatientPhone(aesUtils.decrypt(patientBlood.getPatientPhone()));
        }
        return patientBlood;
    }

    /**
     * 查询【患者血压】列表
     *
     * @param patientBlood 【患者血压】
     * @return 【患者血压】
     */
    @Override
    public List<PatientBlood> selectPatientBloodList(PatientBlood patientBlood) throws Exception {
        if (StringUtils.isNotEmpty(patientBlood.getPatientPhone())){
            patientBlood.setPatientPhone(aesUtils.encrypt(patientBlood.getPatientPhone()));
        }
        return patientBloodMapper.selectPatientBloodList(patientBlood).stream().peek(r->{
            if (StringUtils.isNotEmpty(r.getPatientPhone())){
                try {
                    r.setPatientPhone(aesUtils.decrypt(r.getPatientPhone()));
                } catch (Exception e) {
                    throw new ServiceException("数据异常，请联系管理员");
                }
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<PatientBlood> selectPatientBloodStatistics(PatientBlood patientBlood) throws Exception {
        if (StringUtils.isNotEmpty(patientBlood.getPatientPhone())){
            patientBlood.setPatientPhone(aesUtils.encrypt(patientBlood.getPatientPhone()));
        }
        return patientBloodMapper.selectPatientBloodStatistics(patientBlood).stream().peek(r->{
            if (StringUtils.isNotEmpty(r.getPatientPhone())){
                try {
                    r.setPatientPhone(aesUtils.decrypt(r.getPatientPhone()));
                } catch (Exception e) {
                    throw new ServiceException("数据异常，请联系管理员");
                }
            }
        }).collect(Collectors.toList());
    }

    /**
     * 新增【患者血压】
     *
     * @param patientBlood 【患者血压】
     * @return 结果
     */
    @Override
    public int insertPatientBlood(PatientBlood patientBlood)
    {
        if (StringUtils.isNotEmpty(patientBlood.getPatientPhone())){
            try {
                patientBlood.setPatientPhone(aesUtils.encrypt(patientBlood.getPatientPhone()));
            } catch (Exception e) {
                throw new ServiceException("数据异常，请联系管理员");
            }
        }
        patientBlood.setBloodId(OrderNoUtils.getNo());
        patientBlood.setCreateTime(DateUtils.getNowDate());
        return patientBloodMapper.insertPatientBlood(patientBlood);
    }

    /**
     * 修改【患者血压】
     *
     * @param patientBlood 【患者血压】
     * @return 结果
     */
    @Override
    public int updatePatientBlood(PatientBlood patientBlood)
    {
        if (StringUtils.isNotEmpty(patientBlood.getPatientPhone())){
            try {
                patientBlood.setPatientPhone(aesUtils.encrypt(patientBlood.getPatientPhone()));
            } catch (Exception e) {
                throw new ServiceException("数据异常，请联系管理员");
            }
        }
        patientBlood.setUpdateTime(DateUtils.getNowDate());
        return patientBloodMapper.updatePatientBlood(patientBlood);
    }

    /**
     * 批量删除【患者血压】
     *
     * @param bloodIds 需要删除的【患者血压】主键
     * @return 结果
     */
    @Override
    public int deletePatientBloodByBloodIds(String[] bloodIds)
    {
        return patientBloodMapper.deletePatientBloodByBloodIds(bloodIds);
    }

    /**
     * 删除【患者血压】信息
     *
     * @param bloodId 【患者血压】主键
     * @return 结果
     */
    @Override
    public int deletePatientBloodByBloodId(String bloodId)
    {
        return patientBloodMapper.deletePatientBloodByBloodId(bloodId);
    }
}




