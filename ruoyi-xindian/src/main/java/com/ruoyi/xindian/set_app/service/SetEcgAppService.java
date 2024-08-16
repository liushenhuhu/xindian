package com.ruoyi.xindian.set_app.service;

import com.ruoyi.xindian.set_app.domain.SetEcgApp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【set_ecg_app(小程序设置)】的数据库操作Service
* @createDate 2024-08-15 16:38:37
*/
public interface SetEcgAppService extends IService<SetEcgApp> {

    /**
     * 查询小程序设置
     *
     * @param setId 小程序设置主键
     * @return 小程序设置
     */
    public SetEcgApp selectSetEcgAppBySetId(Long setId);

    /**
     * 查询小程序设置列表
     *
     * @param setEcgApp 小程序设置
     * @return 小程序设置集合
     */
    public List<SetEcgApp> selectSetEcgAppList(SetEcgApp setEcgApp);
    public boolean selectSetEcgAppListByPId(SetEcgApp setEcgApp);

    /**
     * 新增小程序设置
     *
     * @param setEcgApp 小程序设置
     * @return 结果
     */
    public int insertSetEcgApp(SetEcgApp setEcgApp);

    /**
     * 修改小程序设置
     *
     * @param setEcgApp 小程序设置
     * @return 结果
     */
    public int updateSetEcgApp(SetEcgApp setEcgApp);

    /**
     * 批量删除小程序设置
     *
     * @param setIds 需要删除的小程序设置主键集合
     * @return 结果
     */
    public int deleteSetEcgAppBySetIds(Long[] setIds);

    /**
     * 删除小程序设置信息
     *
     * @param setId 小程序设置主键
     * @return 结果
     */
    public int deleteSetEcgAppBySetId(Long setId);
}
