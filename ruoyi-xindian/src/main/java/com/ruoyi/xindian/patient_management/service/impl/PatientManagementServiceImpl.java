package com.ruoyi.xindian.patient_management.service.impl;

import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.domain.SingleHistoryInfo;
import com.ruoyi.xindian.patient_management.mapper.PatientManagementMapper;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.patient_management.vo.DateListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 患者管理Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Service
public class PatientManagementServiceImpl implements IPatientManagementService {
    @Autowired
    private PatientManagementMapper patientManagementMapper;

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;
    /**
     * 查询患者管理
     *
     * @param pId 患者管理主键
     * @return 患者管理
     */
    @Override
    public PatientManagement selectPatientManagementByPId(String pId) {
        return patientManagementMapper.selectPatientManagementByPId(pId);
    }


    /**
     * 查询患者管理列表
     *
     * @param patientManagement 患者管理
     * @return 患者管理
     */
    @Override
    public List<PatientManagement> selectPatientManagementList(PatientManagement patientManagement) {
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementList(patientManagement);

        String code = patientManagement.getHospitalCode();
        if (code!=null&&!"".equals(code)){
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(code);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.setHospitalCode(hospital1.getHospitalCode());
                    List<PatientManagement> patientManagements1 =  patientManagementMapper.selectPatientManagementList(patientManagement);
                    patientManagements.addAll(patientManagements1);
                }
            }
        }
        return patientManagements;
    }

    @Override
    public List<PatientManagement> selectPatientManagementList() {
        return patientManagementMapper.selectPatientManagementJECGList();
    }

    @Override
    public List<PatientManagement> selectPatientManagementJECGList(PatientManagement patientManagement) {
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementJECGList(patientManagement);

        String code = patientManagement.getHospitalCode();
        if (code!=null&&!"".equals(code)){
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(code);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.setHospitalCode(hospital1.getHospitalCode());
                    List<PatientManagement> patientManagements1 =  patientManagementMapper.selectPatientManagementJECGList(patientManagement);
                    patientManagements.addAll(patientManagements1);
                }
            }
        }
        return patientManagements;
    }

    @Override
    public List<PatientManagement> selectPatientManagementJECGsingle(PatientManagement patientManagement) {
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementJECGsingle(patientManagement);

        String code = patientManagement.getHospitalCode();
        if (code!=null&&!"".equals(code)){
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(code);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.setHospitalCode(hospital1.getHospitalCode());
                    List<PatientManagement> patientManagements1 = patientManagementMapper.selectPatientManagementJECGsingle(patientManagement);
                    patientManagements.addAll(patientManagements1);
                }
            }
        }
        return patientManagements;
    }


    @Override
    public List<PatientManagement> selectListOrderByTime(PatientManagement patientManagement) {
        return patientManagementMapper.selectListOrderByTime(patientManagement);
    }

    @Override
    public List<PatientManagement> selectPatientManagementListDECGsingle(PatientManagement patientManagement) {
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementListDECGsingle(patientManagement);
        String code = patientManagement.getHospitalCode();
        if (code!=null&&!"".equals(code)){
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(code);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.setHospitalCode(hospital1.getHospitalCode());
                    List<PatientManagement> patientManagements1 = patientManagementMapper.selectPatientManagementListDECGsingle(patientManagement);
                    patientManagements.addAll(patientManagements1);
                }
            }
        }
        return patientManagements;
    }

    @Override
    public List<PatientManagement> selectPatientManagementListJECG12(PatientManagement patientManagement) {
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementListJECG12(patientManagement);
        String code = patientManagement.getHospitalCode();
        if (code!=null&&!"".equals(code)){
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(code);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.setHospitalCode(hospital1.getHospitalCode());
                    List<PatientManagement> patientManagements1 = patientManagementMapper.selectPatientManagementListJECG12(patientManagement);
                    patientManagements.addAll(patientManagements1);
                }
            }
        }


        return patientManagements;
    }

    @Override
    public List<PatientManagement> selectPatientManagementListDECG12(PatientManagement patientManagement) {
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementListDECG12(patientManagement);
        String code = patientManagement.getHospitalCode();
        if (code!=null&&!"".equals(code)){
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(code);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    patientManagement.setHospitalCode(hospital1.getHospitalCode());
                    List<PatientManagement> patientManagements1 = patientManagementMapper.selectPatientManagementListDECG12(patientManagement);
                    patientManagements.addAll(patientManagements1);
                }
            }
        }

        return patientManagements;
    }


    /**
     * 新增患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    @Override
    public int insertPatientManagement(PatientManagement patientManagement) {
        return patientManagementMapper.insertPatientManagement(patientManagement);
    }

    /**
     * 修改患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    @Override
    public int updatePatientManagement(PatientManagement patientManagement) {
        return patientManagementMapper.updatePatientManagement(patientManagement);
    }

    /**
     * 批量删除患者管理
     *
     * @param pIds 需要删除的患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPIds(String[] pIds) {
        return patientManagementMapper.deletePatientManagementByPIds(pIds);
    }
    @Override
    public  PatientManagement getPhoneByPId(String pId){
        return patientManagementMapper.getPhoneByPId(pId);
    }

    @Override
    public  List<DateListVO> getDateList(PatientManagement patientManagement) {
        List<DateListVO> dateListVOS = patientManagementMapper.selectAllDateList(patientManagement);
        List<DateListVO> dateListVOS1 = new ArrayList<>();
        for (DateListVO c : dateListVOS){
            if (c.getDate()!=null&&!"".equals(c.getDate())){
                c.setInfo(c.getInfo()+"条");
                dateListVOS1.add(c);
            }
        }
        return dateListVOS1;
    }

    /**
     * 删除患者管理信息
     *
     * @param pId 患者管理主键
     * @return 结果
     */
    @Override
    public int deletePatientManagementByPId(String pId) {
        return patientManagementMapper.deletePatientManagementByPId(pId);
    }

    @Override
    public void updateStatusAll() {
        patientManagementMapper.updateStatusAll();
    }

    @Override
    public void updateStatus(String[] pIds) {
        patientManagementMapper.updateStatus(pIds);
    }

    @Override
    public List<SingleHistoryInfo> selectSingleHistoryInfoList(SingleHistoryInfo singleHistoryInfo) {
        return patientManagementMapper.selectSingleHistoryInfoList(singleHistoryInfo);
    }



}
