package com.ruoyi.xindian.patient_management.mapper;

import com.ruoyi.system.domain.vo.SymCountVo;
import com.ruoyi.xindian.jecg_large_screen.vo.AlertLogVO;
import com.ruoyi.xindian.jecg_large_screen.vo.RightBottomVo;
import com.ruoyi.xindian.jecg_large_screen.vo.RightTopVo;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.domain.PhoneList;
import com.ruoyi.xindian.patient_management.domain.SingleHistoryInfo;
import com.ruoyi.xindian.patient_management.vo.*;
import com.ruoyi.xindian.report.domain.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 患者管理Mapper接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface PatientManagementMapper
{
    /**
     * 查询患者管理
     *
     * @param pId 患者管理主键
     * @return 患者管理
     */
    public PatientManagement selectPatientManagementByPId(String pId);

    /**
     * 通过pId获取患者电话
     * @param pId
     * @return
     */

    public  PatientManagement getPhoneByPId(String pId);

    /**
     * 查询患者管理列表
     *
     * @param patientManagement 患者管理
     * @return 患者管理集合
     */
    public List<PatientManagement> selectPatientManagementList(PatientManagement patientManagement);

    public List<PatientManagement> selectPatientManagementSPList(PatientManagement patientManagement);


    public List<PatientManagement> selectPatientManagementJECGList(PatientManagement patientManagement);

    public List<PatientManagement> selectPatientManagementJECGList();
    public List<PatientManagement> selectPatientManagementJECGList1();
    public List<PatientManagement> selectPatientManagementJECGsingle(PatientManagement patientManagement);
    public List<PatientManagement> selectPatientManagementJECG4(PatientManagement patientManagement);


    public List<PatientManagement> selectListOrderByTime(PatientManagement patientManagement);


    public List<PatientManagement> selectPatientManagementListDECGsingle(PatientManagement patientManagement);

    public List<PatientManagement> selectPatientManagementListJECG12(PatientManagement patientManagement);
    public List<PatientManagement> selectPatientManagementListJECGDUO(PatientManagement patientManagement);

    public List<PatientManagement> selectPatientManagementListDECG12(PatientManagement patientManagement);
    public List<PatientManagement> selectPatientManagementList12(PatientManagement patientManagement);

    /**
     * 新增患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    public int insertPatientManagement(PatientManagement patientManagement);




    PatientManagement selectByPId(String pId);

    /**
     * 修改患者管理
     *
     * @param patientManagement 患者管理
     * @return 结果
     */
    public int updatePatientManagement(PatientManagement patientManagement);
    public int updatePatientManagementAes(@Param("aesList") List<PatientManagement> patientManagement);

    /**
     * 删除患者管理
     *
     * @param pId 患者管理主键
     * @return 结果
     */
    public int deletePatientManagementByPId(String pId);

    /**
     * 批量删除患者管理
     *
     * @param pIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientManagementByPIds(String[] pIds);

    public void updateStatusAll();

    public void updateStatus(String[] pIds);

    /**
     * 通过phone获取历史信息
     */
    public List<SingleHistoryInfo> selectSingleHistoryInfoList(SingleHistoryInfo singleHistoryInfo);
    public List<SingleHistoryInfo> selectSingleHistoryInfoListOne(Limit limit);

    List<DateListVO> selectAllDateList(PatientManagement patientManagement);


    PInfoVO selectALLTotal(Report report);
    PInfoVO selectALLTotal01(Report report);
    PInfoVO selectALLTotal02(Report report);


    int updateApBeatInt(SingleHistoryInfo singleHistoryInfo);
    /**
     * 批量修改手机号加密
     */

    List<PatientManagement> selectAll(Limit limit);
    PhoneList selectpatientByEquipmentCode(String deviceSn);



    List<DocVO> selectEcgTypeList(@Param("ecgType") String ecgType);


    List<PatientManagement> selectPatientManagementByPm(PatientManagement patientManagement);


    int selectPatientManagementCount(PatientManagement patientManagement);

    List<PatientManagementVO> selectPatientManagementByCode(String equipmentCode);

    Long getDetectinCountAll(String patientPhone);

    Long getDetectinCountToday(String patientPhone);

    int updatePatientManagementStatus(String pId);


    List<SymCountVo> selectCount(@Param("hospitalCode")String hospitalCode);
    List<AlertLogVO> selectAlertLogList(@Param("hospitalCode")String hospitalCode);
    List<RightTopVo> selectLevelList(@Param("hospitalCode")String hospitalCode);
    List<AlertLogVO> selectIntelligentList(@Param("hospitalCode")String hospitalCode);
    List<RightBottomVo> selectReportList(@Param("hospitalCode")String hospitalCode);
}
