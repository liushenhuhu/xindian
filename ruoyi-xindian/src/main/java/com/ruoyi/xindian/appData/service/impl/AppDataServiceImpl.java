package com.ruoyi.xindian.appData.service.impl;

import java.util.List;
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
    public int insertAppData(AppData appData)
    {
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
}
