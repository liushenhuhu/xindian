package com.ruoyi.xindian.patient_management.service.impl;

import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.domain.PhoneList;
import com.ruoyi.xindian.patient_management.domain.SingleHistoryInfo;
import com.ruoyi.xindian.patient_management.mapper.PatientManagementMapper;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.patient_management.vo.DateListVO;
import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.patient_management.vo.PInfoVO;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.mapper.PatientRelationshipMapper;
import com.ruoyi.xindian.report.domain.Report;
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
private PatientRelationshipMapper patientRelationshipMapper;


@Resource
private PatientMapper patientMapper;
@Resource
private AesUtils aesUtils;
    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
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

        return  patientManagementMapper.selectPatientManagementList(patientManagement);
    }

    @Override
    public List<PatientManagement> selectPatientManagementList() {
        return patientManagementMapper.selectPatientManagementJECGList();
    }

    @Override
    public List<PatientManagement> selectPatientManagementList1() {
        return patientManagementMapper.selectPatientManagementJECGList1();
    }
    @Override
    public List<PatientManagement> selectPatientManagementJECGList(PatientManagement patientManagement) {

        return  patientManagementMapper.selectPatientManagementJECGList(patientManagement);

    }

    @Override
    public List<PatientManagement> selectPatientManagementJECGsingle(PatientManagement patientManagement) {


        return patientManagementMapper.selectPatientManagementJECGsingle(patientManagement);

    }


    @Override
    public List<PatientManagement> selectListOrderByTime(PatientManagement patientManagement) {
        return patientManagementMapper.selectListOrderByTime(patientManagement);
    }

    @Override
    public List<PatientManagement> selectPatientManagementListDECGsingle(PatientManagement patientManagement) {

        return patientManagementMapper.selectPatientManagementListDECGsingle(patientManagement);
    }

    @Override
    public List<PatientManagement> selectPatientManagementListJECG12(PatientManagement patientManagement) {

        return patientManagementMapper.selectPatientManagementListJECG12(patientManagement);
    }

    @Override
    public List<PatientManagement> selectPatientManagementListDECG12(PatientManagement patientManagement) {


        return patientManagementMapper.selectPatientManagementListDECG12(patientManagement);

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

    @Override
    public PInfoVO updatePatient(Report rep) {
        PInfoVO pInfoVO = patientManagementMapper.selectALLTotal(rep);
        if (rep.getEcgType()!=null&&!"".equals(rep.getEcgType())){
            PInfoVO pInfoVO1 = patientManagementMapper.selectALLTotal01(rep);
            pInfoVO.setTotalSingle(pInfoVO1.getTotalSingle());
        }

        PInfoVO pInfoVO2 = patientManagementMapper.selectALLTotal02(rep);


        pInfoVO.setTotalConsult(pInfoVO2.getTotalConsult());
        return pInfoVO;
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

    @Override
    public List<PatientManagement> selectPatientManagementList12(PatientManagement patientManagement) {
        return patientManagementMapper.selectPatientManagementList12(patientManagement);
    }

    @Override
    public void aesCopy(Limit limit) throws Exception {
//        List<PatientManagement> patientManagements = patientManagementMapper.selectAll(limit);
//        for (PatientManagement c :patientManagements){
//            c.setPatientPhone(aesUtils.encrypt(c.getPatientPhone()));
//            patientManagementMapper.updatePatientManagement(c);
//            System.out.println(c);
//        }


//        List<PatientRelationship> patientRelationships = patientRelationshipMapper.selectPatientRelationshipListLimit(limit);
//        for (PatientRelationship c:patientRelationships){
//
//            c.setFatherPhone(aesUtils.encrypt(c.getFatherPhone()));
//            c.setSonPhone(aesUtils.encrypt(c.getSonPhone()));
//            patientRelationshipMapper.updatePatientRelationship(c);
//        }
        List<Patient> patients = patientMapper.selectAlertLogListPatients(limit);
        for (Patient c:patients){
            if (c.getPatientName()!=null&&!"".equals(c.getPatientName())){
                c.setPatientNameAes(aesUtils.encrypt(c.getPatientName()));
            }
            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
                c.setPatientPhoneAes(aesUtils.encrypt(c.getPatientPhone()));
            }
            patientMapper.updatePatientAes(c);
        }
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

    @Override
    public PhoneList selectpatientByEquipmentCode(String deviceSn) {
        return patientManagementMapper.selectpatientByEquipmentCode(deviceSn);
    }

}
