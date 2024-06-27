package com.ruoyi.xindian.sign.service;

import java.util.List;
import com.ruoyi.xindian.sign.domain.SignConfig;

/**
 * configService接口
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
public interface ISignConfigService 
{
    /**
     * 查询config
     * 
     * @param id config主键
     * @return config
     */
    public SignConfig selectSignConfigById(Long id);

    /**
     * 查询config列表
     * 
     * @param signConfig config
     * @return config集合
     */
    public List<SignConfig> selectSignConfigList(SignConfig signConfig);

    /**
     * 新增config
     * 
     * @param signConfig config
     * @return 结果
     */
    public int insertSignConfig(SignConfig signConfig);

    /**
     * 修改config
     * 
     * @param signConfig config
     * @return 结果
     */
    public int updateSignConfig(SignConfig signConfig);

    /**
     * 批量删除config
     * 
     * @param ids 需要删除的config主键集合
     * @return 结果
     */
    public int deleteSignConfigByIds(Long[] ids);

    /**
     * 删除config信息
     * 
     * @param id config主键
     * @return 结果
     */
    public int deleteSignConfigById(Long id);
}
