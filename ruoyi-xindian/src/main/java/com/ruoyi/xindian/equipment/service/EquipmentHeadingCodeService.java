package com.ruoyi.xindian.equipment.service;

import com.ruoyi.xindian.equipment.domain.EquipmentHeadingCode;
import com.baomidou.mybatisplus.extension.service.IService;

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


}
