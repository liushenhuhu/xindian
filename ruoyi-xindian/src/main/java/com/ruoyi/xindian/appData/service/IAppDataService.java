package com.ruoyi.xindian.appData.service;

import java.util.List;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.patient_management.vo.Limit;

/**
 * app相关数据Service接口
 * 
 * @author hanhan
 * @date 2022-10-06
 */
public interface IAppDataService 
{
    /**
     * 查询app相关数据
     * 
     * @param appDataId app相关数据主键
     * @return app相关数据
     */
    public AppData selectAppDataByAppDataId(Long appDataId);

    /**
     * 查询app相关数据(手机号)
     *
     * @param patientPhone 手机号
     * @return app相关数据
     */
    public AppData selectAppDataByPatientPhone(String patientPhone);

    /**
     * 查询app相关数据列表
     * 
     * @param appData app相关数据
     * @return app相关数据集合
     */
    public List<AppData> selectAppDataList(AppData appData);

    /**
     * 新增app相关数据
     * 
     * @param appData app相关数据
     * @return 结果
     */
    public int insertAppData(AppData appData) throws Exception;

    /**
     * 修改app相关数据
     * 
     * @param appData app相关数据
     * @return 结果
     */
    public int updateAppData(AppData appData);

    /**
     * 批量删除app相关数据
     * 
     * @param appDataIds 需要删除的app相关数据主键集合
     * @return 结果
     */
    public int deleteAppDataByAppDataIds(Long[] appDataIds);

    /**
     * 删除app相关数据信息
     * 
     * @param appDataId app相关数据主键
     * @return 结果
     */
    public int deleteAppDataByAppDataId(Long appDataId);

    public int deleteDataByAppDataIds(String[] appDataIds);


    void appAesCopy(Limit limit) throws Exception;
}
