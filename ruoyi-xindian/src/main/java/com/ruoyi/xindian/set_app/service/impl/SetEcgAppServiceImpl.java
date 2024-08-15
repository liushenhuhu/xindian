package com.ruoyi.xindian.set_app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.set_app.domain.SetEcgApp;
import com.ruoyi.xindian.set_app.service.SetEcgAppService;
import com.ruoyi.xindian.set_app.mapper.SetEcgAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author w
* @description 针对表【set_ecg_app(小程序设置)】的数据库操作Service实现
* @createDate 2024-08-15 16:38:37
*/
@Service
public class SetEcgAppServiceImpl extends ServiceImpl<SetEcgAppMapper, SetEcgApp>
    implements SetEcgAppService{

    @Autowired
    private SetEcgAppMapper setEcgAppMapper;

    /**
     * 查询小程序设置
     *
     * @param setId 小程序设置主键
     * @return 小程序设置
     */
    @Override
    public SetEcgApp selectSetEcgAppBySetId(Long setId)
    {
        return setEcgAppMapper.selectSetEcgAppBySetId(setId);
    }

    /**
     * 查询小程序设置列表
     *
     * @param setEcgApp 小程序设置
     * @return 小程序设置
     */
    @Override
    public List<SetEcgApp> selectSetEcgAppList(SetEcgApp setEcgApp)
    {
        return setEcgAppMapper.selectSetEcgAppList(setEcgApp);
    }

    @Override
    public boolean selectSetEcgAppListByPId(SetEcgApp setEcgApp) {

        List<SetEcgApp> setEcgApps = setEcgAppMapper.selectSetEcgAppList(setEcgApp);
        if (setEcgApps.isEmpty()) {
            return false;
        }
        List<SetEcgApp> collect = setEcgApps.stream().filter(r -> r.getStatus().equals("0")).collect(Collectors.toList());
        if (collect.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 新增小程序设置
     *
     * @param setEcgApp 小程序设置
     * @return 结果
     */
    @Override
    public int insertSetEcgApp(SetEcgApp setEcgApp)
    {
        setEcgApp.setCreateTime(DateUtils.getNowDate());
        return setEcgAppMapper.insertSetEcgApp(setEcgApp);
    }

    /**
     * 修改小程序设置
     *
     * @param setEcgApp 小程序设置
     * @return 结果
     */
    @Override
    public int updateSetEcgApp(SetEcgApp setEcgApp)
    {
        return setEcgAppMapper.updateSetEcgApp(setEcgApp);
    }

    /**
     * 批量删除小程序设置
     *
     * @param setIds 需要删除的小程序设置主键
     * @return 结果
     */
    @Override
    public int deleteSetEcgAppBySetIds(Long[] setIds)
    {
        return setEcgAppMapper.deleteSetEcgAppBySetIds(setIds);
    }

    /**
     * 删除小程序设置信息
     *
     * @param setId 小程序设置主键
     * @return 结果
     */
    @Override
    public int deleteSetEcgAppBySetId(Long setId)
    {
        return setEcgAppMapper.deleteSetEcgAppBySetId(setId);
    }
}




