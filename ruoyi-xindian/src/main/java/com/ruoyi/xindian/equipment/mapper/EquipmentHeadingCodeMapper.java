package com.ruoyi.xindian.equipment.mapper;

import com.ruoyi.xindian.equipment.domain.EquipmentHeadingCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 13401
* @description 针对表【equipment_heading_code】的数据库操作Mapper
* @createDate 2023-09-07 17:47:23
* @Entity com.ruoyi.xindian.equipment.domain.EquipmentHeadingCode
*/
public interface EquipmentHeadingCodeMapper extends BaseMapper<EquipmentHeadingCode> {


    /**
     * 查询善行设备管理
     *
     * @param codeId 善行设备管理主键
     * @return 善行设备管理
     */
    public EquipmentHeadingCode selectEquipmentHeadingCodeByCodeId(Long codeId);

    /**
     * 查询善行设备管理列表
     *
     * @param equipmentHeadingCode 善行设备管理
     * @return 善行设备管理集合
     */
    public List<EquipmentHeadingCode> selectEquipmentHeadingCodeList(EquipmentHeadingCode equipmentHeadingCode);

    /**
     * 新增善行设备管理
     *
     * @param equipmentHeadingCode 善行设备管理
     * @return 结果
     */
    public int insertEquipmentHeadingCode(EquipmentHeadingCode equipmentHeadingCode);

    /**
     * 修改善行设备管理
     *
     * @param equipmentHeadingCode 善行设备管理
     * @return 结果
     */
    public int updateEquipmentHeadingCode(EquipmentHeadingCode equipmentHeadingCode);

    /**
     * 删除善行设备管理
     *
     * @param codeId 善行设备管理主键
     * @return 结果
     */
    public int deleteEquipmentHeadingCodeByCodeId(Long codeId);

    /**
     * 批量删除善行设备管理
     *
     * @param codeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentHeadingCodeByCodeIds(Long[] codeIds);
}




