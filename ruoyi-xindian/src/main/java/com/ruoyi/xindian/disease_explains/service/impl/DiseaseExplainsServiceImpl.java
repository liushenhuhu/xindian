package com.ruoyi.xindian.disease_explains.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.disease_explains.domain.DiseaseExplains;
import com.ruoyi.xindian.disease_explains.service.DiseaseExplainsService;
import com.ruoyi.xindian.disease_explains.mapper.DiseaseExplainsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author w
* @description 针对表【disease_explains(心电图报告解析说明)】的数据库操作Service实现
* @createDate 2024-08-16 16:39:32
*/
@Service
public class DiseaseExplainsServiceImpl extends ServiceImpl<DiseaseExplainsMapper, DiseaseExplains>
    implements DiseaseExplainsService{

    @Autowired
    private DiseaseExplainsMapper diseaseExplainsMapper;

    /**
     * 查询心电图报告解析说明
     *
     * @param diseaseId 心电图报告解析说明主键
     * @return 心电图报告解析说明
     */
    @Override
    public DiseaseExplains selectDiseaseExplainsByDiseaseId(Long diseaseId)
    {
        return diseaseExplainsMapper.selectDiseaseExplainsByDiseaseId(diseaseId);
    }

    /**
     * 查询心电图报告解析说明列表
     *
     * @param diseaseExplains 心电图报告解析说明
     * @return 心电图报告解析说明
     */
    @Override
    public List<DiseaseExplains> selectDiseaseExplainsList(DiseaseExplains diseaseExplains)
    {
        return diseaseExplainsMapper.selectDiseaseExplainsList(diseaseExplains);
    }

    /**
     * 新增心电图报告解析说明
     *
     * @param diseaseExplains 心电图报告解析说明
     * @return 结果
     */
    @Override
    public int insertDiseaseExplains(DiseaseExplains diseaseExplains)
    {
        diseaseExplains.setCreateTime(DateUtils.getNowDate());
        return diseaseExplainsMapper.insertDiseaseExplains(diseaseExplains);
    }

    /**
     * 修改心电图报告解析说明
     *
     * @param diseaseExplains 心电图报告解析说明
     * @return 结果
     */
    @Override
    public int updateDiseaseExplains(DiseaseExplains diseaseExplains)
    {
        return diseaseExplainsMapper.updateDiseaseExplains(diseaseExplains);
    }

    /**
     * 批量删除心电图报告解析说明
     *
     * @param diseaseIds 需要删除的心电图报告解析说明主键
     * @return 结果
     */
    @Override
    public int deleteDiseaseExplainsByDiseaseIds(Long[] diseaseIds)
    {
        return diseaseExplainsMapper.deleteDiseaseExplainsByDiseaseIds(diseaseIds);
    }

    /**
     * 删除心电图报告解析说明信息
     *
     * @param diseaseId 心电图报告解析说明主键
     * @return 结果
     */
    @Override
    public int deleteDiseaseExplainsByDiseaseId(Long diseaseId)
    {
        return diseaseExplainsMapper.deleteDiseaseExplainsByDiseaseId(diseaseId);
    }
}




