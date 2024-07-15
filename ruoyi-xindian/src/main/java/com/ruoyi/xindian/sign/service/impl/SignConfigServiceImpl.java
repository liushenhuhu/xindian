package com.ruoyi.xindian.sign.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.sign.mapper.SignConfigMapper;
import com.ruoyi.xindian.sign.domain.SignConfig;
import com.ruoyi.xindian.sign.service.ISignConfigService;

/**
 * configService业务层处理
 * 
 * @author chenpeng
 * @date 2024-06-27
 */
@Service
public class SignConfigServiceImpl implements ISignConfigService 
{
    @Autowired
    private SignConfigMapper signConfigMapper;

    /**
     * 查询config
     * 
     * @param id config主键
     * @return config
     */
    @Override
    public SignConfig selectSignConfigById(Long id)
    {
        return signConfigMapper.selectSignConfigById(id);
    }

    /**
     * 查询config列表
     * 
     * @param signConfig config
     * @return config
     */
    @Override
    public List<SignConfig> selectSignConfigList(SignConfig signConfig)
    {
        return signConfigMapper.selectSignConfigList(signConfig);
    }

    /**
     * 新增config
     * 
     * @param signConfig config
     * @return 结果
     */
    @Override
    public int insertSignConfig(SignConfig signConfig)
    {
        return signConfigMapper.insertSignConfig(signConfig);
    }

    /**
     * 修改config
     * 
     * @param signConfig config
     * @return 结果
     */
    @Override
    public int updateSignConfig(SignConfig signConfig)
    {
        return signConfigMapper.updateSignConfig(signConfig);
    }

    /**
     * 批量删除config
     * 
     * @param ids 需要删除的config主键
     * @return 结果
     */
    @Override
    public int deleteSignConfigByIds(Long[] ids)
    {
        return signConfigMapper.deleteSignConfigByIds(ids);
    }

    /**
     * 删除config信息
     * 
     * @param id config主键
     * @return 结果
     */
    @Override
    public int deleteSignConfigById(Long id)
    {
        return signConfigMapper.deleteSignConfigById(id);
    }
}
