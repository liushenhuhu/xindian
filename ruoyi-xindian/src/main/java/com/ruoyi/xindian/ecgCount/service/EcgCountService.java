package com.ruoyi.xindian.ecgCount.service;

import com.ruoyi.xindian.ecgCount.domain.EcgCount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.ecgCount.vo.TypeListVo;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
* @author 13401
* @description 针对表【ecg_count】的数据库操作Service
* @createDate 2023-10-07 16:23:51
*/
public interface EcgCountService extends IService<EcgCount> {


    void importEcgCount(String path) throws Exception;

    /**
     * 查询数据库统计
     *
     * @param id 数据库统计主键
     * @return 数据库统计
     */
    public EcgCount selectEcgCountById(Long id);

    /**
     * 查询数据库统计列表
     *
     * @param ecgCount 数据库统计
     * @return 数据库统计集合
     */
    public List<EcgCount> selectEcgCountList(EcgCount ecgCount);

    /**
     * 新增数据库统计
     *
     * @param ecgCount 数据库统计
     * @return 结果
     */
    public int insertEcgCount(EcgCount ecgCount);

    /**
     * 修改数据库统计
     *
     * @param ecgCount 数据库统计
     * @return 结果
     */
    public int updateEcgCount(EcgCount ecgCount);

    /**
     * 批量删除数据库统计
     *
     * @param ids 需要删除的数据库统计主键集合
     * @return 结果
     */
    public int deleteEcgCountByIds(Long[] ids);

    /**
     * 删除数据库统计信息
     *
     * @param id 数据库统计主键
     * @return 结果
     */
    public int deleteEcgCountById(Long id);


    List<ListValueAndLabelVO> getLogType();

    List<AgeStatistics> ageListByMan(Map<String,Object> type);

    List<AgeStatistics> ageListByWoman(Map<String,Object> type);


    Map<String , List<TypeListVo>> getTypeList() throws InstantiationException, IllegalAccessException;


    List<TypeListVo> getZCXDTCount();

    List<TypeListVo> getAgeYoung(Map<String,Object> type);

}
