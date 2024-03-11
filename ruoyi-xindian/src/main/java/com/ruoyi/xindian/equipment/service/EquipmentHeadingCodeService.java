package com.ruoyi.xindian.equipment.service;

import com.ruoyi.xindian.equipment.domain.EquipmentHeadingCode;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 13401
* @description 针对表【equipment_heading_code】的数据库操作Service
* @createDate 2023-09-07 17:47:23
*/
public interface EquipmentHeadingCodeService extends IService<EquipmentHeadingCode> {


    /**
     * 通过识别码查询
     * @param code
     * @return
     */
    EquipmentHeadingCode selectByCode(String code);

    /**
     * 通过SN码查询查询
     * @param code
     * @return
     */
    EquipmentHeadingCode selectEquipmentCode(String code);



    void selectCodeState(String code) throws Exception;

    void selectCodeState15(String code) throws Exception;


    void selectCodeStateAgain(String code) throws Exception;

    void getCodeStatus(String code) throws Exception;




    Boolean addSXReport(String phone,String pId) throws Exception;


    Boolean submitDetectionReport(String pId, HttpServletRequest request) throws Exception;



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
     * 批量删除善行设备管理
     *
     * @param codeIds 需要删除的善行设备管理主键集合
     * @return 结果
     */
    public int deleteEquipmentHeadingCodeByCodeIds(Long[] codeIds);

    /**
     * 删除善行设备管理信息
     *
     * @param codeId 善行设备管理主键
     * @return 结果
     */
    public int deleteEquipmentHeadingCodeByCodeId(Long codeId);

}
