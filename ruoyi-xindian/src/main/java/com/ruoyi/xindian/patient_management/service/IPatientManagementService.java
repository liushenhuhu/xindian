package com.ruoyi.xindian.patient_management.service;

import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.domain.PhoneList;
import com.ruoyi.xindian.patient_management.domain.SingleHistoryInfo;
import com.ruoyi.xindian.patient_management.vo.DateListVO;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.patient_management.vo.PInfoVO;
import com.ruoyi.xindian.report.domain.Report;

import java.util.List;
import java.util.Map;

/**
 * 患者管理Service接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface IPatientManagementService
{
    /**
     * 查询患者管理
     *
     * @param pId 患者管理主键
     * @return 患者管理
     */
    public PatientManagement selectPatientManagementByPId(String pId);

    /**
     * 查询患者管理列表
     *
     * @param patientManagement 患者管理
     * @return 患者管理集合
     */
    public List<PatientManagement> selectPatientManagementList(PatientManagement patientManagement);


    public List<PatientManagement> selectPatientManagementSPList(PatientManagement patientManagement, Integer pageSize,Integer pageNum);



    public List<PatientManagement> selectPatientManagementList();
    public List<PatientManagement> selectPatientManagementList1();
    public List<PatientManagement> selectPatientManagementJECGList(PatientManagement patientManagement);
    public List<PatientManagement> selectPatientManagementJECGsingle(PatientManagement patientManagement);

    public List<PatientManagement> selectListOrderByTime(PatientManagement patientManagement);

    public List<PatientManagement> selectPatientManagementListDECGsingle(PatientManagement patientManagement);

    public List<PatientManagement> selectPatientManagementListDECG12(PatientManagement patientManagement);

    public List<PatientManagement> selectPatientManagementListJECG12(PatientManagement patientManagement);

    /**
     * 新增患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    public int insertPatientManagement(PatientManagement patientManagement);

    /**
     * 修改患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    public int updatePatientManagement(PatientManagement patientManagement);
     PInfoVO updatePatient(Report rep);

    /**
     * 批量删除患者管理
     *
     * @param pIds 需要删除的患者管理主键集合
     * @return 结果
     */
    public int deletePatientManagementByPIds(String[] pIds);

    /**
     * 删除患者管理信息
     *
     * @param pId 患者管理主键
     * @return 结果
     */
    public int deletePatientManagementByPId(String pId);

    void updateStatusAll();

    void updateStatus(String[] pIds);

    public List<SingleHistoryInfo> selectSingleHistoryInfoList(SingleHistoryInfo singleHistoryInfo);

    public  PatientManagement getPhoneByPId(String pId);





    List<DateListVO> getDateList(PatientManagement patientManagement);


    List<PatientManagement> selectPatientManagementList12(PatientManagement patientManagement);


    /**
     * 分批次修改
     */
    void aesCopy(Limit limit) throws Exception;

    public PhoneList selectpatientByEquipmentCode(String deviceSn);


    List<DocVO> selectEcgType(String ecgType);


    Map<String, List<String[]>> selectPatientManagementCount(PatientManagement patientManagement) throws Exception;

    Map<String, List<String[]>> selectPatientManagementCountApp(PatientManagement patientManagement) throws Exception;
}
