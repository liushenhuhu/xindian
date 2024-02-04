package com.ruoyi.xindian.patient_management.service.impl;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.equipment.mapper.EquipmentMapper;
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
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.patient_management.vo.PInfoVO;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.relationship.mapper.PatientRelationshipMapper;
import com.ruoyi.xindian.report.domain.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private EquipmentMapper equipmentMapper;

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
    public List<PatientManagement> selectPatientManagementSPList(PatientManagement patientManagement, Integer pageSize,Integer pageNum) {

        PageHelper.startPage(pageNum,pageSize);
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementSPList(patientManagement);
        return patientManagements;
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
            if (c.getFamilyPhone()!=null&&!"".equals(c.getFamilyPhone())){
                c.setFamilyPhone(aesUtils.encrypt(c.getFamilyPhone()));
                patientMapper.updatePatientAes(c);
            }

        }

//        List<Equipment> equipmentList = equipmentMapper.selectALl(limit);
//        for(Equipment c : equipmentList){
//
//            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
//                c.setPatientPhone(aesUtils.encrypt(c.getPatientPhone()));
//                equipmentMapper.updateEquipment(c);
//            }
//        }




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

    @Override
    public List<DocVO> selectEcgType(String ecgType) {
        return patientManagementMapper.selectEcgTypeList(ecgType);
    }

    @Override
    public Map<String, List<String[]>> selectPatientManagementCount(PatientManagement patientManagement) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
        return patientManagementCountApp(formatter, patientManagement);
    }

    @Override
    public Map<String, List<List<Object>>> selectPatientManagementCountApp(PatientManagement patientManagement) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, List<List<Object>>> map = new HashMap<>();
        if (StringUtils.isNotEmpty(patientManagement.getPatientPhone())){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementByPm(patientManagement);
        List<List<Object>> hr_mean = new ArrayList<>();
        List<List<Object>> P_time = new ArrayList<>();
        List<List<Object>> QRS_interval = new ArrayList<>();
        List<List<Object>> PR_interval = new ArrayList<>();
        List<List<Object>> QTc = new ArrayList<>();
        List<List<Object>> RMSSD = new ArrayList<>();

        for (PatientManagement c : patientManagements){
            if (c.getPmEcgData()!=null){
                String formatted= formatter.format(c.getConnectionTime());
                Date dateTime = formatter.parse(formatted);
                long formattedDate = dateTime.getTime() / 1000;
                PmEcgData pmEcgData = c.getPmEcgData();
                if (StringUtils.isNotEmpty(pmEcgData.getHrMean())){
                    Double aDouble = getDouble(pmEcgData.getHrMean());
                    if (aDouble!=null){
                        List<Object> list = new ArrayList<>();
                        list.add(formattedDate);
                        list.add(aDouble);
                        hr_mean.add(list);
                    }

                }
                if (StringUtils.isNotEmpty(pmEcgData.getpTime())){
                    Double aDouble = getDouble(pmEcgData.getpTime());
                    if (aDouble!=null){
                        List<Object> list = new ArrayList<>();
                        list.add(formattedDate);
                        list.add(aDouble);
                        P_time.add(list);
                    }
                }
                if (StringUtils.isNotEmpty(pmEcgData.getQrsInterval())){

                    Double aDouble = getDouble(pmEcgData.getQrsInterval());
                    if (aDouble!=null){
                        List<Object> list = new ArrayList<>();
                        list.add(formattedDate);
                        list.add(aDouble);
                        QRS_interval.add(list);
                    }
                }

                if (StringUtils.isNotEmpty(pmEcgData.getPrInterval())){
                    Double aDouble = getDouble(pmEcgData.getPrInterval());
                    if (aDouble!=null){
                        List<Object> list = new ArrayList<>();
                        list.add(formattedDate);
                        list.add(aDouble);
                        PR_interval.add(list);
                    }
                }

                if (StringUtils.isNotEmpty(pmEcgData.getQtc())){

                    Double aDouble = getDouble(pmEcgData.getQtc());
                    if (aDouble!=null){
                        List<Object> list = new ArrayList<>();
                        list.add(formattedDate);
                        list.add(aDouble);
                        QTc.add(list);
                    }
                }

                if (StringUtils.isNotEmpty(pmEcgData.getRmssd())){
                    Double aDouble = getDouble(pmEcgData.getRmssd());
                    if (aDouble!=null){
                        List<Object> list = new ArrayList<>();
                        list.add(formattedDate);
                        list.add(aDouble);
                        RMSSD.add(list);
                    }
                }
            }


        }
        map.put("hr_mean",hr_mean);
        map.put("P_time",P_time);
        map.put("QRS_interval",QRS_interval);
        map.put("PR_interval",PR_interval);
        map.put("QTc",QTc);
        map.put("RMSSD",RMSSD);
        return map;
    }

    private Double getDouble(String str) {
        try {
            return Double.parseDouble(str);
        }catch (Exception e) {
            return null;
        }

    }

    private Map<String, List<String[]>> patientManagementCountApp(SimpleDateFormat formatter,PatientManagement patientManagement) throws Exception {
        Map<String, List<String[]>> map = new HashMap<>();
        if (StringUtils.isNotEmpty(patientManagement.getPatientPhone())){
            patientManagement.setPatientPhone(aesUtils.encrypt(patientManagement.getPatientPhone()));
        }
        List<PatientManagement> patientManagements = patientManagementMapper.selectPatientManagementByPm(patientManagement);
        List<String[]> hr_mean = new ArrayList<>();
        List<String[]> P_time = new ArrayList<>();
        List<String[]> QRS_interval = new ArrayList<>();
        List<String[]> PR_interval = new ArrayList<>();
        List<String[]> QTc = new ArrayList<>();
        List<String[]> RMSSD = new ArrayList<>();

        for (PatientManagement c : patientManagements){
            if (c.getPmEcgData()!=null){
                String formattedDate = formatter.format(c.getConnectionTime());
                PmEcgData pmEcgData = c.getPmEcgData();
                if (StringUtils.isNotEmpty(pmEcgData.getHrMean())){
                    String[] strings = {formattedDate,pmEcgData.getHrMean(),c.getpId()};
                    hr_mean.add(strings);
                }
                if (StringUtils.isNotEmpty(pmEcgData.getpTime())){
                    String[] strings = {formattedDate,pmEcgData.getpTime(),c.getpId()};
                    P_time.add(strings);
                }
                if (StringUtils.isNotEmpty(pmEcgData.getQrsInterval())){
                    String[] strings = {formattedDate,pmEcgData.getQrsInterval(),c.getpId()};
                    QRS_interval.add(strings);
                }

                if (StringUtils.isNotEmpty(pmEcgData.getPrInterval())){
                    String[] strings = {formattedDate,pmEcgData.getPrInterval(),c.getpId()};
                    PR_interval.add(strings);
                }

                if (StringUtils.isNotEmpty(pmEcgData.getQtc())){
                    String[] strings = {formattedDate,pmEcgData.getQtc(),c.getpId()};
                    QTc.add(strings);
                }

                if (StringUtils.isNotEmpty(pmEcgData.getRmssd())){
                    String[] strings = {formattedDate,pmEcgData.getRmssd(),c.getpId()};
                    RMSSD.add(strings);
                }
            }


        }
        map.put("hr_mean",hr_mean);
        map.put("P_time",P_time);
        map.put("QRS_interval",QRS_interval);
        map.put("PR_interval",PR_interval);
        map.put("QTc",QTc);
        map.put("RMSSD",RMSSD);
        return map;
    }

}
