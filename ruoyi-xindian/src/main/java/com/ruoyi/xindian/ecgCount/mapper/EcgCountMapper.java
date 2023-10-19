package com.ruoyi.xindian.ecgCount.mapper;

import com.ruoyi.xindian.ecgCount.domain.EcgCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.xindian.ecgCount.vo.TypeListVo;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

/**
* @author 13401
* @description 针对表【ecg_count】的数据库操作Mapper
* @createDate 2023-10-07 16:23:51
* @Entity com.ruoyi.xindian.ecgCount.domain.EcgCount
*/
public interface EcgCountMapper extends BaseMapper<EcgCount> {

    
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
     * 删除数据库统计
     *
     * @param id 数据库统计主键
     * @return 结果
     */
    public int deleteEcgCountById(Long id);

    /**
     * 批量删除数据库统计
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEcgCountByIds(Long[] ids);



    int insertEcgCounts(List<EcgCount> ecgCountList);


    List<ListValueAndLabelVO> getLogType();
    List<TypeListVo> getLogTypeAndValue();


    List<AgeStatistics> ageListByMan(Map<String,Object> type);

    List<AgeStatistics> ageListByWoman(Map<String,Object> type);



    TypeListVo getTypeList(@Param("type") String type);

    EcgCount getEcgTypeList();


    TypeListVo getEcgValue(@Param("value") String value);


    List<TypeListVo> getZCXDTCount();
    List<TypeListVo> getAgeYoung(Map<String,Object> type);

}




