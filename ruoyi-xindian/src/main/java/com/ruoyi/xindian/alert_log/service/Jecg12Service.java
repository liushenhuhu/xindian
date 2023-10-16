package com.ruoyi.xindian.alert_log.service;

import com.ruoyi.xindian.alert_log.domain.Jecg12;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【jecg_12(静态12导心电数据)】的数据库操作Service
* @createDate 2023-10-16 14:10:47
*/
public interface Jecg12Service extends IService<Jecg12> {


    /**
     * 通过Pid查询
     */
    Jecg12 selectById(String pId);


    /**
     * 修改标签数据
     */
    int updateWaveLabel(Jecg12 jecg12);


    int updateBeatLabel(Jecg12 jecg12);

}
