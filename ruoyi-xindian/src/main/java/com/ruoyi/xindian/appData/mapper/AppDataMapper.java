package com.ruoyi.xindian.appData.mapper;

import java.util.List;
import com.ruoyi.xindian.appData.domain.AppData;

/**
 * app相关数据Mapper接口
 * 
 * @author hanhan
 * @date 2022-10-06
 */
public interface AppDataMapper 
{
    /**
     * 查询app相关数据
     * 
     * @param appDataId app相关数据主键
     * @return app相关数据
     */
    public AppData selectAppDataByAppDataId(Long appDataId);

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
    public int insertAppData(AppData appData);

    /**
     * 修改app相关数据
     * 
     * @param appData app相关数据
     * @return 结果
     */
    public int updateAppData(AppData appData);

    /**
     * 删除app相关数据
     * 
     * @param appDataId app相关数据主键
     * @return 结果
     */
    public int deleteAppDataByAppDataId(Long appDataId);

    /**
     * 批量删除app相关数据
     * 
     * @param appDataIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppDataByAppDataIds(Long[] appDataIds);

    public int deleteDataByAppDataIds(String[] appDataIds);
}
