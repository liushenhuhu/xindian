package com.ruoyi.xindian.disease_explains.service;

import com.ruoyi.xindian.disease_explains.domain.DiseaseExplains;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【disease_explains(心电图报告解析说明)】的数据库操作Service
* @createDate 2024-08-16 16:39:32
*/
public interface DiseaseExplainsService extends IService<DiseaseExplains> {

    /**
     * 查询心电图报告解析说明
     *
     * @param diseaseId 心电图报告解析说明主键
     * @return 心电图报告解析说明
     */
    public DiseaseExplains selectDiseaseExplainsByDiseaseId(Long diseaseId);

    /**
     * 查询心电图报告解析说明列表
     *
     * @param diseaseExplains 心电图报告解析说明
     * @return 心电图报告解析说明集合
     */
    public List<DiseaseExplains> selectDiseaseExplainsList(DiseaseExplains diseaseExplains);

    /**
     * 新增心电图报告解析说明
     *
     * @param diseaseExplains 心电图报告解析说明
     * @return 结果
     */
    public int insertDiseaseExplains(DiseaseExplains diseaseExplains);

    /**
     * 修改心电图报告解析说明
     *
     * @param diseaseExplains 心电图报告解析说明
     * @return 结果
     */
    public int updateDiseaseExplains(DiseaseExplains diseaseExplains);

    /**
     * 批量删除心电图报告解析说明
     *
     * @param diseaseIds 需要删除的心电图报告解析说明主键集合
     * @return 结果
     */
    public int deleteDiseaseExplainsByDiseaseIds(Long[] diseaseIds);

    /**
     * 删除心电图报告解析说明信息
     *
     * @param diseaseId 心电图报告解析说明主键
     * @return 结果
     */
    public int deleteDiseaseExplainsByDiseaseId(Long diseaseId);
}
