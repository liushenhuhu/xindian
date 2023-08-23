package com.ruoyi.xindian.appData.service.impl;

import java.util.List;

import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.mapper.MedicalHistoryMapper;
import com.ruoyi.xindian.patient_management.vo.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.appData.mapper.AppDataMapper;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;

/**
 * app相关数据Service业务层处理
 * 
 * @author hanhan
 * @date 2022-10-06
 */
@Service
public class AppDataServiceImpl implements IAppDataService 
{
    @Autowired
    private AppDataMapper appDataMapper;

    @Autowired
    private MedicalHistoryMapper medicalHistoryMapper;

    @Autowired
    private AesUtils aesUtils;

    /**
     * 查询app相关数据
     * 
     * @param appDataId app相关数据主键
     * @return app相关数据
     */
    @Override
    public AppData selectAppDataByAppDataId(Long appDataId)
    {
        return appDataMapper.selectAppDataByAppDataId(appDataId);
    }

    @Override
    public AppData selectAppDataByPatientPhone(String patientPhone) {
        return appDataMapper.selectAppDataByPatientPhone(patientPhone);
    }

    /**
     * 查询app相关数据列表
     * 
     * @param appData app相关数据
     * @return app相关数据
     */
    @Override
    public List<AppData> selectAppDataList(AppData appData)
    {
        return appDataMapper.selectAppDataList(appData);
    }

    /**
     * 新增app相关数据
     * 
     * @param appData app相关数据
     * @return 结果
     */
    @Override
    public int insertAppData(AppData appData) throws Exception {
        if (appData.getPatientName()!=null&&!"".equals(appData.getPatientName())){
            appData.setPatientNameAes(aesUtils.decrypt(appData.getPatientName()));
        }
        if (appData.getPatientPhone()!=null&&!"".equals(appData.getPatientPhone())){
            appData.setPatientPhoneAes(aesUtils.decrypt(appData.getPatientPhone()));
        }
        return appDataMapper.insertAppData(appData);
    }

    /**
     * 修改app相关数据
     * 
     * @param appData app相关数据
     * @return 结果
     */
    @Override
    public int updateAppData(AppData appData)
    {
        return appDataMapper.updateAppData(appData);
    }

    /**
     * 批量删除app相关数据
     * 
     * @param appDataIds 需要删除的app相关数据主键
     * @return 结果
     */
    @Override
    public int deleteAppDataByAppDataIds(Long[] appDataIds)
    {
        return appDataMapper.deleteAppDataByAppDataIds(appDataIds);
    }

    /**
     * 删除app相关数据信息
     * 
     * @param appDataId app相关数据主键
     * @return 结果
     */
    @Override
    public int deleteAppDataByAppDataId(Long appDataId)
    {
        return appDataMapper.deleteAppDataByAppDataId(appDataId);
    }

    @Override
    public int deleteDataByAppDataIds(String[] appDataIds) {
        return appDataMapper.deleteDataByAppDataIds(appDataIds);
    }

    @Override
    public void appAesCopy(Limit limit) throws Exception {
//        List<AppData> appData = appDataMapper.searchAllByAppDataIdAppData(limit);
//        for (AppData c : appData){
//            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
//                c.setPatientPhone(aesUtils.encrypt(c.getPatientPhone()));
//            }
//            if (c.getPatientName()!=null&&!"".equals(c.getPatientName())){
//                c.setPatientName(aesUtils.encrypt(c.getPatientName()));
//            }
//            if (c.getUserName()!=null&&!"".equals(c.getUserName())){
//                c.setUserName(aesUtils.encrypt(c.getUserName()));
//            }
//            appDataMapper.updateAppData(c);
//        }

        List<MedicalHistory> medicalHistories = medicalHistoryMapper.selectPastMedicalHistoryList(limit);
        for (MedicalHistory c:medicalHistories){
            c.setPatientPhone(aesUtils.encrypt(c.getPatientPhone()));
            medicalHistoryMapper.updateMedicalHistory(c);
//            System.out.println(c);
        }

    }
}
