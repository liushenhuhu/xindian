package com.ruoyi.xindian.set_app.mapper;

import com.ruoyi.xindian.set_app.domain.SetEcgApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【set_ecg_app(小程序设置)】的数据库操作Mapper
* @createDate 2024-08-15 16:38:37
* @Entity com.ruoyi.xindian.set_app.domain.SetEcgApp
*/
public interface SetEcgAppMapper extends BaseMapper<SetEcgApp> {

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
     * 删除小程序设置
     *
     * @param setId 小程序设置主键
     * @return 结果
     */
    public int deleteSetEcgAppBySetId(Long setId);

    /**
     * 批量删除小程序设置
     *
     * @param setIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSetEcgAppBySetIds(Long[] setIds);
}




